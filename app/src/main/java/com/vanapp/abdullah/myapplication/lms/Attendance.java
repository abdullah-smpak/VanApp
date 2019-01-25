package com.vanapp.abdullah.myapplication.lms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vanapp.abdullah.myapplication.Adpters.ListViewAdapter;
import com.vanapp.abdullah.myapplication.Models.Students;
import com.vanapp.abdullah.myapplication.OtherServices.HttpParse;
import com.vanapp.abdullah.myapplication.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Attendance extends AppCompatActivity {


    String clsresult = null, clsline = null;
    List<String> cl_list;
    MaterialBetterSpinner sp_class;
    InputStream is1 = null;
    String[] classes;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/lms/mark_att.php";
    ArrayList<Students> students = null;
    ListViewAdapter adapter;
    ListView listView = null;
    Button upload;
    public String class_id = null;
    String cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sp_class = findViewById(R.id.sp_class);
        listView = findViewById(R.id.mListView);
        upload = findViewById(R.id.upload);


        try {
            fetchclass();
        } catch (Exception ex) { }



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                String date = mdformat.format(calendar.getTime());

                try {
                    String type = "att";
                    SendPushNotificationPre sendPushNotificationMor = new SendPushNotificationPre(Attendance.this);
                    sendPushNotificationMor.execute(type, class_id, date);

                    //Absent
                    SendPushNotificationAbs sendPushNotificationEve = new SendPushNotificationAbs(Attendance.this);
                    sendPushNotificationEve.execute(type, class_id, date);
                } catch (Exception ex) { }

            }
        });


    }


    private void fetchclass() {
        cl_list = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.mucaddam.pk/abdullah/lms/fetch_classes.php");
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
        } catch (Exception ex) { }



        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((clsline = reader.readLine()) != null) {
                sb.append(clsline + "\n");
            }
            is1.close();
            clsresult = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray JA = new JSONArray(clsresult);
            JSONObject json = null;

            classes = new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                classes[i] = json.getString("class_name");
            }

            for (int i = 0; i < classes.length; i++) {
                cl_list.add(classes[i]);
            }
            spinner_sch1();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //school spinner populate
    private void spinner_sch1() {

        try {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(Attendance.this, android.R.layout.simple_dropdown_item_1line, classes);
            sp_class.setAdapter(dataAdapter1);
        } catch (Exception ex) { }

        try {
            sp_class.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    cls = sp_class.getText().toString();

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = mdformat.format(calendar.getTime());

                    String type = "chk";

                    CheckAtt checkAtt = new CheckAtt(Attendance.this);
                    checkAtt.execute(type,cls, date);



                    /**/


                }
            });
        } catch (Exception ex) { }


    }


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

            progressDialog = ProgressDialog.show(Attendance.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                try {
                    //JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject1 = null;
                    students = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = (JSONObject) jsonArray.get(i);
                        students.add(new Students(Integer.valueOf(jsonObject1.optString("std_id")), jsonObject1.optString("name"), jsonObject1.optString("std_img"), jsonObject1.optString("class_id")));
                        class_id = jsonObject1.optString("class_id");

                    }
                    //  Toast.makeText(context, jsonObject + "", Toast.LENGTH_SHORT).show();

                adapter = new ListViewAdapter(Attendance.this, R.layout.model, students);

                //Toast.makeText(context, adapter+"", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                Toast.makeText(context, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();




        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/fetch_students_list.php";
            if (type.equals("class")) {
                try {
                    String cls = params[1];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("class_id", "UTF-8") + "=" + URLEncoder.encode(cls, "UTF-8");
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

    public class CheckAtt extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        CheckAtt(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // progressDialog = ProgressDialog.show(Attendance.this, "Please Wait...", null, true, true);


        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if(result.equals("Not Marked"))
                {
                    String type = "class";

                    FetchSchoolDetails fetchStudents = new FetchSchoolDetails(Attendance.this);
                    fetchStudents.execute(type, cls);
                }
                else if(result.equals("Attendance Marked Already"))
                {
                    listView.setAdapter(null);
                    Toast.makeText(context, "Attendance Marked Already", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) { }


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/chk_att.php";
            if (type.equals("chk")) {
                try {
                    String cls = params[1];
                    String date = params[2];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("cls", "UTF-8") + "=" + URLEncoder.encode(cls, "UTF-8")+"&"+
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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

    public class SendPushNotificationPre extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        SendPushNotificationPre(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Attendance.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/noti_pre.php";
            if (type.equals("att")) {
                try {
                    String route = params[1];
                    String date = params[2];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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

    public class SendPushNotificationAbs extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        SendPushNotificationAbs(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Attendance.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/noti_abs.php";
            if (type.equals("att")) {
                try {
                    String route = params[1];
                    String date = params[2];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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
}
