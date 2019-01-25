package com.vanapp.abdullah.myapplication;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class testotp extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap mgoogleMap;
/*    GpsTracker gps;
    String lat_holder,lon_holder,date,time,finalResult,idholder;
    HttpParse httpParse = new HttpParse();
    HashMap<String, String> hashMap = new HashMap<>();
    String HttpURL = "http://surveyors.com.pk/mobile_app/tracking.php";
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testotp);

        mapView = findViewById(R.id.mapView);
   /*     Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gps = new GpsTracker(testotp.this);
                                if (gps.canGetLocation()) {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    String lat = String.valueOf(latitude);
                                    String lon = String.valueOf(longitude);

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy ");
                                    date = mdformat.format(calendar.getTime());

                                    SimpleDateFormat mdformat1 = new SimpleDateFormat("HH:mm:ss");
                                    time = mdformat1.format(calendar.getTime());



                                    SendLoc(idholder,lat, lon,date,time);




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


    }

    private void SendLoc(String user_id ,String lat_holder, String lon_holder, String date,String time) {

        class SendLocClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // progressDialog = ProgressDialog.show(Driver_Reg.this, "Registering Driver", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                // Toast.makeText(checklist_form.this, "Send", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("user_id", params[0]);
                hashMap.put("lat", params[1]);
                hashMap.put("lon", params[2]);
                hashMap.put("date", params[3]);
                hashMap.put("time", params[4]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }


        }
        SendLocClass sendLocClass = new SendLocClass();

        sendLocClass.execute(user_id,lat_holder, lon_holder,date,time);

    }
*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getBaseContext());
        mgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("You"));
        CameraPosition loc = CameraPosition.builder().target(new LatLng(40.689247, -74.044502)).zoom(16).bearing(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(loc));
    }
}

