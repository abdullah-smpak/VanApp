package com.vanapp.abdullah.myapplication.Van;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.vanapp.abdullah.myapplication.Adpters.EvListViewAdapter;
import com.vanapp.abdullah.myapplication.Models.LocationData;
import com.vanapp.abdullah.myapplication.Models.MorStudents;
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

public class eve_van_att extends AppCompatActivity {
    String van_no;
    ArrayList<MorStudents> students = null;
    EvListViewAdapter adapter;
    ListView listView = null;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eve_van_att);
        listView = findViewById(R.id.mListView);
        submit = findViewById(R.id.submit);
        van_no = getIntent().getExtras().getString("van_no");






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(eve_van_att.this)
                        .setTitle("Notify")
                        .setBackgroundColor(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                        .setMessage("Have you Picked All Students?")
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
                                String type = "van_no";
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                                String date = mdformat.format(calendar.getTime());

                                //Present
                                SendPushNotificationPre sendPushNotificationMor = new SendPushNotificationPre(eve_van_att.this);
                                sendPushNotificationMor.execute(type, van_no,date);

                                //Absent
                                SendPushNotificationAbs sendPushNotificationEve = new SendPushNotificationAbs(eve_van_att.this);
                                sendPushNotificationEve.execute(type, van_no,date);


                                /*Intent intent1 = new Intent(eve_van_att.this, Driver_Mainpage.class);
                                startActivity(intent1);
                                finish();*/
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


        String type = "van_no";
        FetchKids fetchKids = new FetchKids(eve_van_att.this);
        fetchKids.execute(type, van_no);

    }


    public class FetchKids extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchKids(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(eve_van_att.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject1 = null;
                students = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject1 = (JSONObject) jsonArray.get(i);
                    students.add(new MorStudents(Integer.valueOf(jsonObject1.optString("id")), jsonObject1.optString("kid_nam"),jsonObject1.optString("pro_img"),jsonObject1.optString("van_no")));


                }
                //  Toast.makeText(context, jsonObject + "", Toast.LENGTH_SHORT).show();



            adapter = new EvListViewAdapter(eve_van_att.this, R.layout.model,students);

            // Toast.makeText(context, adapter+"", Toast.LENGTH_SHORT).show();
            listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
           // Toast.makeText(context, result+"", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_kids.php";
            if (type.equals("van_no")) {
                try {
                    String van_no = params[1];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(van_no, "UTF-8");
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

            progressDialog = ProgressDialog.show(eve_van_att.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/onload_pre.php";
            if (type.equals("van_no")) {
                try {
                    String route = params[1];
                    String date = params[1];

                    url = new URL(login_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8")+"&"+
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

            progressDialog = ProgressDialog.show(eve_van_att.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/onload_abs.php";
            if (type.equals("van_no")) {
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

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8")+"&"+
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
