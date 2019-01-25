package com.vanapp.abdullah.myapplication.Van;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;


import android.net.Uri;
import android.os.AsyncTask;

import android.os.StrictMode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vanapp.abdullah.myapplication.GPSTracker;
import com.vanapp.abdullah.myapplication.Models.Common;
import com.vanapp.abdullah.myapplication.OtherServices.HttpParse;
import com.vanapp.abdullah.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;

public class Driver_Reg extends AppCompatActivity {

    CircleImageView img;
    private GPSTracker gpsTracker;
    double latitude, longitude;
    EditText van_no, dri_add, pass, pass_con, model, driver_name;
    MaterialSpinner make, sch_nam, sch_loc;
    Button submit;
    ImageButton getloc;

    String van_no_holder, model_holder, driver_name_holder, mob_num_holder,
            dri_add_holder, make_holder, sch_nam_holder, sch_loc_holder, pass_holder,
            schresult = null,
            schline = null, schools, braresult = null, braline = null, d_lat, d_lon;

    String[] school_names, branch_names;
    Bitmap pro_img;
    Uri resulturi;
    List<String> s_list, sb_list;
    InputStream is1 = null;


    Boolean CheckEditText;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/vanapp/driver_reg.php";

    Location location;
    String model_regex = "[0-9]{4}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__reg);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mob_num_holder = getIntent().getExtras().getString("mob_no");

        if (Common.isConnected(getBaseContext())) {
            van_no = findViewById(R.id.van_no);
            model = findViewById(R.id.model);
            driver_name = findViewById(R.id.driver_name);
            dri_add = findViewById(R.id.dri_add);
            pass = findViewById(R.id.pass);
            pass_con = findViewById(R.id.pass_con);
            make = findViewById(R.id.make);
            sch_nam = findViewById(R.id.sch_nam);
            sch_loc = findViewById(R.id.sch_loc);
            submit = findViewById(R.id.submit);
            getloc = findViewById(R.id.getloc);
            img = findViewById(R.id.img);


            List<String> list = new ArrayList<>();
            list.add("Suzuki");
            list.add("Toyota");
            list.add("Honda");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            make.setAdapter(dataAdapter);


            //sch_nam.setAdapter(dataAdapter);
            //sch_loc.setAdapter(dataAdapter);

            model.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (model.getText().toString().matches(model_regex)) {
                        model_holder = model.getText().toString();

                    } else {
                        model.setError("Invalid Make");
                    }
                }
            });


            getloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FancyAlertDialog.Builder(Driver_Reg.this)
                            .setTitle("Confirm")
                            .setBackgroundColor(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                            .setMessage("Are you really at your Home Location ?")
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
                                    gpsTracker = new GPSTracker(Driver_Reg.this);
                                    if (gpsTracker.canGetLocation()) {
                                        latitude = gpsTracker.getLatitude();
                                        longitude = gpsTracker.getLongitude();
                                        //Toast.makeText(Driver_Reg.this, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                                    } else {
                                        gpsTracker.showSettingsAlert();
                                    }


                                }
                            })
                            .OnNegativeClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {

                                }
                            })
                            .build();
                }
            });


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(Driver_Reg.this);

                }


            });


            fetchschools();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Checking whether EditText is Empty or Not
                    CheckEditTextIsEmptyOrNot();


                    if (CheckEditText == true) {

                        if (!pass.getText().toString().equals(pass_con.getText().toString())) {
                            Toast.makeText(Driver_Reg.this, "Password you entered is not same", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                DriverRegistration(van_no_holder, model_holder,
                                        driver_name_holder, mob_num_holder, dri_add_holder,
                                        make_holder, sch_nam_holder, sch_loc_holder, pass_holder, imagetostr(pro_img)
                                        , String.valueOf(latitude), String.valueOf(longitude));
                                submit.setEnabled(false);
                            } catch (Exception ex) {
                                Toast.makeText(Driver_Reg.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                            }


                        }


                    } else {
                        Toast.makeText(Driver_Reg.this, "Please Fill Form Completely", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        } else {

            Toast.makeText(Driver_Reg.this, "Please Check Internet Connection !!!", Toast.LENGTH_SHORT).show();
        }

    }


    //fetch schools function
    private void fetchschools() {
        s_list = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.mucaddam.pk/abdullah/vanapp/fetch_schools.php");
        HttpResponse response = null;
        try {

            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();
        try {
            is1 = entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((schline = reader.readLine()) != null) {
                sb.append(schline + "\n");
            }
            is1.close();
            schresult = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray JA = new JSONArray(schresult);
            JSONObject json = null;

            school_names = new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                school_names[i] = json.getString("school_name");
            }

            for (int i = 0; i < school_names.length; i++) {
                s_list.add(school_names[i]);
            }
            spinner_sch();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //school spinner populate
    private void spinner_sch() {
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(Driver_Reg.this, android.R.layout.simple_spinner_item, school_names);
        sch_nam.setAdapter(dataAdapter1);

        sch_nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position < 0) {
                    sch_nam_holder = null;
                } else {
                    if (position > -1) {
                        schools = sch_nam.getSelectedItem().toString();

                        //fetching drivers name by routes
                        String type = "schools";

                        FetchSchoolDetails fetchSchoolDetails = new FetchSchoolDetails(Driver_Reg.this);
                        fetchSchoolDetails.execute(type, schools);


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                resulturi = result.getUri();
                img.setImageURI(resulturi);
                pro_img = ((BitmapDrawable) img.getDrawable()).getBitmap();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void DriverRegistration(String van_no_holder, String model_holder,
                                    String driver_name_holder, String mob_num_holder,
                                    String dri_add_holder, String make_holder,
                                    String sch_nam_holder, String sch_loc_holder,
                                    String pass_holder, String pro_img, String dri_lat_holder, String dri_lon_holder) {

        class DriverRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Driver_Reg.this, "Registering Driver", null, true, true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Driver_Reg.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Driver_Reg.this, Driver_Login.class);
                startActivity(intent);
                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("van_no", params[0]);
                hashMap.put("make", params[1]);
                hashMap.put("dri_nam", params[2]);
                hashMap.put("model", params[3]);
                hashMap.put("mob_no", params[4]);
                hashMap.put("dri_address", params[5]);
                hashMap.put("school_nam", params[6]);
                hashMap.put("school_loc", params[7]);
                hashMap.put("password", params[8]);
                hashMap.put("dri_img", params[9]);
                hashMap.put("dri_lat", params[10]);
                hashMap.put("dri_lon", params[11]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        DriverRegistrationClass driverRegistrationClass = new DriverRegistrationClass();

        driverRegistrationClass.execute(van_no_holder, make_holder,
                driver_name_holder, model_holder,
                mob_num_holder, dri_add_holder, sch_nam_holder,
                sch_loc_holder, pass_holder, pro_img, dri_lat_holder, dri_lon_holder);


    }


    public void CheckEditTextIsEmptyOrNot() {


        if (make.getSelectedItem() != null
                && sch_nam.getSelectedItem() != null
                && van_no.getText() != null
                && model.getText() != null
                && driver_name.getText() != null
                && pro_img != null
                && dri_add.getText() != null
                && sch_loc.getSelectedItem() != null) {

            make_holder = make.getSelectedItem().toString();
            sch_nam_holder = sch_nam.getSelectedItem().toString();
            sch_loc_holder = sch_loc.getSelectedItem().toString();

            van_no_holder = van_no.getText().toString();
            model_holder = model.getText().toString();
            driver_name_holder = driver_name.getText().toString();

            dri_add_holder = dri_add.getText().toString();
            pass_holder = pass.getText().toString();


            CheckEditText = true;
        } else {

            CheckEditText = false;
        }


    }

    private String imagetostr(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);

    }

    //fetching branches name class by schools
    public class FetchSchoolDetails extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchSchoolDetails(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Driver_Reg.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();
            try {

                fetchschoolbranches(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ;


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_branches.php";
            if (type.equals("schools")) {
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

                    String post_data = URLEncoder.encode("schools", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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

    //fetching branches in spinner
    private void fetchschoolbranches(String result) throws IOException, JSONException {

        sb_list = new ArrayList<>();
        InputStream targetStream = new ByteArrayInputStream(result.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(targetStream, "iso-8859-1"), 8);

        StringBuilder sb = new StringBuilder();

        while ((braline = reader.readLine()) != null) {
            sb.append(braline + "\n");
        }
        //  is1.close();
        braresult = sb.toString();


        JSONArray JA = new JSONArray(braresult);
        JSONObject json = null;


        branch_names = new String[JA.length()];
        for (int i = 0; i < JA.length(); i++) {
            json = JA.getJSONObject(i);
            branch_names[i] = json.getString("branch");
        }

        for (int i = 0; i < branch_names.length; i++) {
            sb_list.add(branch_names[i]);
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(Driver_Reg.this, android.R.layout.simple_spinner_item, branch_names);
        sch_loc.setAdapter(dataAdapter2);
    }


}
