package com.vanapp.abdullah.myapplication.Van;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

import com.google.firebase.iid.FirebaseInstanceId;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Picasso;
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


public class Parent_Registration extends AppCompatActivity {
    CircleImageView pro_img, dri_img;
    String d_nam, van_no, token;
    double latitude, longitude;
    MaterialSpinner van_route, sch_nam, cls, sch_loc, driver_name;
    EditText address, mother_nam, father_nam, father_no, pass, con_pass, kid_nam;
    Button submit;


    String van_route_holder, sch_nam_holder, cls_holder, sch_loc_holder,
            address_holder, mother_nam_holder, mother_no_holder, father_nam_holder, father_no_holder, pass_holder,
            kid_nam_holdder;
    Bitmap pro_img_bitmap;

    Uri resulturi;

    InputStream is = null, is1 = null;
    String result1 = null, result2 = null, schresult = null, braresult = null;
    String line1 = null, line2 = null, schline = null, braline = null;
    String[] routes_name, driver_names, school_names, branch_names;
    String routes, schools, names;
    String mob_regex = "[0-9]{11}";


    Boolean CheckEditText;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/vanapp/parent_reg.php";
    List<String> r_list, dr_list, s_list, sb_list;
    private GPSTracker gpsTracker;
    ImageButton getloc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_registration);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//

        if (Common.isConnected(getBaseContext())) {
            mother_no_holder = getIntent().getExtras().getString("mob_no");//   mother_no_holder ="1234567890";
            // mother_no_holder = "+923432933953";//   mother_no_holder ="1234567890";
            // Toast.makeText(this, mother_no_holder, Toast.LENGTH_SHORT).show();

            getloc = findViewById(R.id.getloc);
            van_route = findViewById(R.id.van_route);
            sch_nam = findViewById(R.id.sch_nam);
            sch_loc = findViewById(R.id.sch_loc);
            driver_name = findViewById(R.id.driver_name);
            cls = findViewById(R.id.cls);
            address = findViewById(R.id.address);
            mother_nam = findViewById(R.id.mother_nam);
            father_nam = findViewById(R.id.father_nam);
            father_no = findViewById(R.id.father_no);
            con_pass = findViewById(R.id.con_pass);
            pass = findViewById(R.id.pass);
            kid_nam = findViewById(R.id.kid_nam);
            submit = findViewById(R.id.submit);
            pro_img = findViewById(R.id.pro_img);
            dri_img = findViewById(R.id.dri_img);


            try {
                // fetching routes function from mysql
                fetchroutes();
            } catch (Exception ex) {

            }

            try {

// fetching schhols function from mysql
                fetchschools();
            } catch (Exception ex) {

            }

            try {
//get classes in spinner
                classes();
            } catch (Exception ex) {

            }


            father_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (father_no.getText().toString().matches(mob_regex)) {
                        father_no_holder = father_no.getText().toString();

                    } else {
                        father_no.setError("Invalid Father's Number");
                    }
                }
            });


//profile images
            pro_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(Parent_Registration.this);

                }
            });


            getloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FancyAlertDialog.Builder(Parent_Registration.this)
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
                                    gpsTracker = new GPSTracker(Parent_Registration.this);
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

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Checking whether EditText is Empty or Not
                    CheckEditTextIsEmptyOrNot();

                    if (CheckEditText) {

                        // If EditText is not empty and CheckEditText = True then this block will execute.


                        token = FirebaseInstanceId.getInstance().getToken();

                        if (!pass.getText().toString().equals(con_pass.getText().toString())) {
                            Toast.makeText(Parent_Registration.this, "Password you entered is not same", Toast.LENGTH_SHORT).show();
                        } else {


                            try {
                                ParentRegistration(van_route_holder, kid_nam_holdder, sch_nam_holder,
                                        sch_loc_holder, cls_holder, address_holder, mother_nam_holder, mother_no_holder,
                                        father_nam_holder, father_no_holder, pass_holder, imagetostr(pro_img_bitmap), van_no, token,
                                        String.valueOf(latitude), String.valueOf(longitude));
                                submit.setEnabled(false);

                            } catch (Exception ex) {
                                Toast.makeText(Parent_Registration.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                            }


                        }


                    } else {

                        // If EditText is empty then this block will execute .
                        Toast.makeText(Parent_Registration.this, "Please fill all Form fields.", Toast.LENGTH_LONG).show();

                    }

                }

            });
        } else {

            Toast.makeText(Parent_Registration.this, "Please Check Internet Connection !!!", Toast.LENGTH_SHORT).show();
        }


    }


    //putting classes in spinner
    private void classes() {
        List<String> list = new ArrayList<>();
        list.add("Mont.");
        list.add("Nur");
        list.add("KG 1");
        list.add("KG 2");
        list.add("Class 1");
        list.add("Class 2");
        list.add("Class 3");
        list.add("Class 4");
        list.add("Class 5");
        list.add("Class 6");
        list.add("Class 7");
        list.add("Class 8");
        list.add("Class 9");
        list.add("Class 10");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cls.setAdapter(dataAdapter);
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

        try {
            HttpEntity entity = response.getEntity();
            try {
                is1 = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {

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
        try {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(Parent_Registration.this, android.R.layout.simple_spinner_item, school_names);
            sch_nam.setAdapter(dataAdapter1);
        } catch (Exception ex) {

        }


        sch_nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > -1) {
                    schools = sch_nam.getSelectedItem().toString();

                    //fetching drivers name by routes
                    String type = "schools";

                    FetchSchoolDetails fetchSchoolDetails = new FetchSchoolDetails(Parent_Registration.this);
                    fetchSchoolDetails.execute(type, schools);


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //fetch routes function
    private void fetchroutes() {

        r_list = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.mucaddam.pk/abdullah/vanapp/fetch_routes.php");
        HttpResponse response = null;
        try {

            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HttpEntity entity = response.getEntity();
            try {
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {

        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((line1 = reader.readLine()) != null) {
                sb.append(line1 + "\n");
            }
            is.close();
            result1 = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray JA = new JSONArray(result1);
            JSONObject json = null;

            routes_name = new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                routes_name[i] = json.getString("routes_name");
            }

            for (int i = 0; i < routes_name.length; i++) {
                r_list.add(routes_name[i]);
            }
            spinner_dri();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //driver spinner populate
    private void spinner_dri() {

        try {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(Parent_Registration.this, android.R.layout.simple_spinner_item, routes_name);
            van_route.setAdapter(dataAdapter1);
        } catch (Exception ex) {

        }


        van_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > -1) {
                    routes = van_route.getSelectedItem().toString();

                    //fetching drivers name by routes
                    String type = "route";
                    FetchDriverDetails1 fetchDriverDetails1 = new FetchDriverDetails1(Parent_Registration.this);
                    fetchDriverDetails1.execute(type, routes);


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
                pro_img.setImageURI(resulturi);
                pro_img_bitmap = ((BitmapDrawable) pro_img.getDrawable()).getBitmap();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    //sending data to mysql
    private void ParentRegistration(String van_route_holder, String kid_nam_holdder,
                                    String sch_nam_holder, String sch_loc_holder,
                                    String cls_holder, String address_holder,
                                    String mother_nam_holder, String mother_no_holder,
                                    String father_nam_holder, String father_no_holder,
                                    String pass_holder, String pro_img, String van_no, String token_holder,
                                    String dri_lat_holder, String dri_lon_holder) {
        class ParentRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Parent_Registration.this, "Loading Data", null, true, true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Parent_Registration.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Parent_Registration.this, Parent_Login.class);
                startActivity(intent);
                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("van_route", params[0]);
                hashMap.put("kid_nam", params[1]);
                hashMap.put("school_name", params[2]);
                hashMap.put("school_location", params[3]);
                hashMap.put("class", params[4]);
                hashMap.put("address", params[5]);
                hashMap.put("mother_nam", params[6]);
                hashMap.put("mother_no", params[7]);
                hashMap.put("father_nam", params[8]);
                hashMap.put("father_no", params[9]);
                hashMap.put("pass", params[10]);
                hashMap.put("pro_img", params[11]);
                hashMap.put("van_no", params[12]);
                hashMap.put("token", params[13]);
                hashMap.put("p_lat", params[14]);
                hashMap.put("p_lon", params[15]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ParentRegistrationClass parentRegistrationClass = new ParentRegistrationClass();


        try {
            parentRegistrationClass.execute(van_route_holder, kid_nam_holdder, sch_nam_holder,
                    sch_loc_holder, cls_holder, address_holder, mother_nam_holder, mother_no_holder,
                    father_nam_holder, father_no_holder, pass_holder, pro_img, van_no, token_holder,dri_lat_holder,dri_lon_holder);
        } catch (Exception ex) {

        }


    }


    //validation
    public void CheckEditTextIsEmptyOrNot() {

        if (van_route.getSelectedItem() != null && cls.getSelectedItem() != null
                && sch_nam.getSelectedItem() != null
                && kid_nam.getText() != null
                && address.getText() != null
                && pass.getText() != null
                && pro_img != null
                && mother_nam.getText() != null && father_nam.getText() != null
                && father_no.getText() != null
                && sch_loc.getSelectedItem() != null) {

            van_route_holder = van_route.getSelectedItem().toString();
            sch_nam_holder = sch_nam.getSelectedItem().toString();
            sch_loc_holder = sch_loc.getSelectedItem().toString();
            cls_holder = cls.getSelectedItem().toString();

            kid_nam_holdder = kid_nam.getText().toString();
            address_holder = address.getText().toString();
            mother_nam_holder = mother_nam.getText().toString();

            father_nam_holder = father_nam.getText().toString();
            father_no_holder = father_no.getText().toString();
            pass_holder = pass.getText().toString();


            CheckEditText = true;
        } else {

            CheckEditText = false;
        }


        CheckEditText = true;


    }


    //bytes to string
    private String imagetostr(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);

    }


    //fetching drivers name class by routes
    public class FetchDriverDetails1 extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchDriverDetails1(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Parent_Registration.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();
            try {

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
                    d_nam = jObj.getString("dri_nam").toString();

                    van_no = jObj.getString("van_no").toString();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fetchdriver(result);
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
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_driversnames.php";
            if (type.equals("route")) {
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

                    String post_data = URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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

            progressDialog = ProgressDialog.show(Parent_Registration.this, "Please Wait...", null, true, true);
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

    //fetch driver image
    public class FetchDriverimg extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchDriverimg(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Parent_Registration.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();
            try {

                fetchdriverimg(result);
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
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_dri_img.php";
            if (type.equals("d_names")) {
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

                    String post_data = URLEncoder.encode("d_names", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(Parent_Registration.this, android.R.layout.simple_spinner_item, branch_names);
        sch_loc.setAdapter(dataAdapter2);
    }

    //fetching drivers in spinner
    private void fetchdriver(String result) throws IOException, JSONException {

        dr_list = new ArrayList<>();
        InputStream targetStream = new ByteArrayInputStream(result.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(targetStream, "iso-8859-1"), 8);

        StringBuilder sb = new StringBuilder();

        while ((line2 = reader.readLine()) != null) {
            sb.append(line2 + "\n");
        }
        //  is1.close();
        result2 = sb.toString();


        JSONArray JA = new JSONArray(result2);
        JSONObject json = null;


        driver_names = new String[JA.length()];
        for (int i = 0; i < JA.length(); i++) {
            json = JA.getJSONObject(i);
            driver_names[i] = json.getString("dri_nam");
        }

        for (int i = 0; i < driver_names.length; i++) {
            dr_list.add(driver_names[i]);
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(Parent_Registration.this, android.R.layout.simple_spinner_item, driver_names);
        driver_name.setAdapter(dataAdapter2);

        driver_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > -1) {
                    names = driver_name.getSelectedItem().toString();

                    //fetching drivers name by routes
                    String type = "d_names";
                    FetchDriverimg fetchDriverimg = new FetchDriverimg(Parent_Registration.this);
                    fetchDriverimg.execute(type, names);


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //fetching drivers in img
    private void fetchdriverimg(String result) throws IOException, JSONException {

        String url = "http://www.mucaddam.pk/abdullah/vanapp/" + result;
        Picasso.with(this).load(url).placeholder(R.drawable.driverico).error(R.drawable.driverico).into(dri_img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }

}
