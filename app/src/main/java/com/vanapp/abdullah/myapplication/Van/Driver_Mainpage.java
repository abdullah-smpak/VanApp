package com.vanapp.abdullah.myapplication.Van;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Picasso;
import com.vanapp.abdullah.myapplication.GPSTracker;
import com.vanapp.abdullah.myapplication.Models.LocationData;
import com.vanapp.abdullah.myapplication.OtherServices.HttpParse;
import com.vanapp.abdullah.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Driver_Mainpage extends AppCompatActivity   implements LocationListener {

    String d_nam, van_no, d_id, dri_img, make, model, school_nam, school_loc, dri_address, mob_no;
    Button pick, drop;
    TextView name, btn_pro;
    CircleImageView pro_img;
    private GoogleMap mMap;
    String dri_mob;
    private DatabaseReference mDatabase;
    GPSTracker gps;
    String date,time;


    /*GpsTracker gps;
    String lat_holder, lon_holder, date, time, finalResult;
    HttpParse httpParse = new HttpParse();
    HashMap<String, String> hashMap = new HashMap<>();
    String HttpURL = "http://www.mucaddam.pk/abdullah/vanapp/van_tracking.php";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__mainpage);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        startGettingLocations();


        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gps = new GPSTracker(Driver_Mainpage.this);
                                if (gps.canGetLocation()) {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    String lat = String.valueOf(latitude);
                                    String lon = String.valueOf(longitude);

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                                    date = mdformat.format(calendar.getTime());

                                    SimpleDateFormat mdformat1 = new SimpleDateFormat("HH:mm:ss");
                                    time = mdformat1.format(calendar.getTime());




                                    LocationData locationData = new LocationData(latitude, longitude,date,time);
                                    mDatabase.child("Location").child(van_no).setValue(locationData);




                                } else {
                                    gps.showSettingsAlert();
                                }



                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();


        pick = findViewById(R.id.pick);
        drop = findViewById(R.id.drop);
        name = findViewById(R.id.name);
        btn_pro = findViewById(R.id.btn_pro);
        pro_img = findViewById(R.id.pro_img);


        dri_mob = getIntent().getExtras().getString("mob_no");


        String type = "mob_no";
        FetchDriverData fetchDriverData = new FetchDriverData(Driver_Mainpage.this);
        fetchDriverData.execute(type, dri_mob);

        //sendloc
        //  sendloc();

        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Mainpage.this, Driver_Profile.class);
                intent.putExtra("d_nam", d_nam);
                intent.putExtra("van_no", van_no);
                intent.putExtra("d_id", d_id);
                intent.putExtra("make", make);
                intent.putExtra("model", model);
                intent.putExtra("school_nam", school_nam);
                intent.putExtra("school_loc", school_loc);
                intent.putExtra("dri_address", dri_address);
                intent.putExtra("mob_no", mob_no);
                startActivity(intent);
            }
        });

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Mainpage.this, mor_van_att.class);
                intent.putExtra("d_nam", d_nam);
                intent.putExtra("van_no", van_no);
                intent.putExtra("d_id", d_id);
                startActivity(intent);
            }
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Driver_Mainpage.this, eve_van_att.class);
                intent.putExtra("d_nam", d_nam);
                intent.putExtra("van_no", van_no);
                intent.putExtra("d_id", d_id);
                startActivity(intent);
            }
        });


    }

    public class FetchDriverData extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchDriverData(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Driver_Mainpage.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();
         /*  try {

                fetchdriverdata(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


*/

         try{
             JSONArray arr = null;
             try {
                 arr = new JSONArray(result);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             JSONObject jObj = null;

             try {
                 jObj = arr.getJSONObject(0);

             } catch (JSONException e) {
                 e.printStackTrace();
             }
             try {
                 d_nam = jObj.getString("dri_nam");
                 d_id = jObj.getString("id");
                 van_no = jObj.getString("van_no");
                 dri_img = jObj.getString("dri_img");
                 make = jObj.getString("make");
                 model = jObj.getString("model");
                 mob_no = jObj.getString("mob_no");
                 dri_address = jObj.getString("dri_address");
                 school_nam = jObj.getString("school_nam");
                 school_loc = jObj.getString("school_loc");

                 name.setText("Hello! " + d_nam);

                 fetchdriverimg();
             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }catch(Exception ex) {

         }


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_driver_data.php";
            if (type.equals("mob_no")) {
                try {
                    String route = params[1];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("mob_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
                    bufferedWriter.write(post_data);

                    bufferedWriter.flush();

                    bufferedWriter.close();

                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


    }

    private void fetchdriverimg() {
        String url = "http://www.mucaddam.pk/abdullah/vanapp/" + dri_img;
        Picasso.with(this).load(url).placeholder(R.drawable.driverico).error(R.drawable.driverico).into(pro_img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }


    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(this)
                .setTitle("Logout")
                .setBackgroundColor(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                .setMessage("Do you really want to Logout ?")
                .setNegativeBtnText("No")
                .setPositiveBtnBackground(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Yes")
                .setNegativeBtnBackground(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.warning, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(Driver_Mainpage.this, Driver_Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS disabled!");
        alertDialog.setMessage("Activate GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    private void startGettingLocations() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;// Distance in meters
        long MIN_TIME_BW_UPDATES = 100;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);


            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, "321", Toast.LENGTH_SHORT).show();

        if(van_no !=null)
        {
            LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude(), date, time);
            mDatabase.child("Location").child(van_no).setValue(locationData);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
