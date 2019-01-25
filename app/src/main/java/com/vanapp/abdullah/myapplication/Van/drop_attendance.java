package com.vanapp.abdullah.myapplication.Van;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.vanapp.abdullah.myapplication.OtherServices.HttpParse;
import com.vanapp.abdullah.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class drop_attendance extends AppCompatActivity {

    InputStream is = null;
    String result1 = null, finalResult;
    String line1 = null;
    String[] kids_names;
    String dri_name, van_no, d_id, date_holder, kid_nam_holder, time_holder, status_holder;
    TextView kid;
    List<String> k_list;
    MaterialSpinner kid_list;
    RadioButton present, absent;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/vanapp/kid_drop_att.php";

    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    Boolean CheckEditText;
    Button submit, offload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_attendance);

        kid_list = findViewById(R.id.kids_list);
        kid = findViewById(R.id.kid_name);
        present = findViewById(R.id.present);
        absent = findViewById(R.id.absent);
        submit = findViewById(R.id.submit);
        offload = findViewById(R.id.offloaded);

        dri_name = getIntent().getExtras().getString("d_nam");
        van_no = getIntent().getExtras().getString("van_no");
        d_id = getIntent().getExtras().getString("d_id");

        String type = "van_no";
        FetchKids fetchKids = new FetchKids(drop_attendance.this);
        fetchKids.execute(type, van_no);

        offload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FancyAlertDialog.Builder(drop_attendance.this)
                        .setTitle("Notify")
                        .setBackgroundColor(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                        .setMessage("Have you Picked All Students ?")
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
                                SendPushNotification sendPushNotification = new SendPushNotification(drop_attendance.this);
                                sendPushNotification.execute(type, van_no);
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


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                date_holder = mdformat.format(calendar.getTime());

                SimpleDateFormat mdformat1 = new SimpleDateFormat("HH:mm:ss");
                time_holder = mdformat1.format(calendar.getTime());


                if (present.isChecked()) {
                    status_holder = "Present";
                } else {
                    status_holder = "Absent";
                }


                kid_nam_holder = kid.getText().toString();

                dropattn(date_holder, kid_nam_holder, time_holder,
                        status_holder,van_no);





            }
        });
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

            progressDialog = ProgressDialog.show(drop_attendance.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            try {
                fetchkidsname(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

    private void fetchkidsname(String result) throws IOException, JSONException {
        k_list = new ArrayList<>();
        InputStream targetStream = new ByteArrayInputStream(result.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(targetStream, "iso-8859-1"), 8);

        StringBuilder sb = new StringBuilder();

        while ((line1 = reader.readLine()) != null) {
            sb.append(line1 + "\n");
        }

        result1 = sb.toString();


        JSONArray JA = new JSONArray(result1);
        JSONObject json = null;


        kids_names = new String[JA.length()];
        for (int i = 0; i < JA.length(); i++) {
            json = JA.getJSONObject(i);
            kids_names[i] = json.getString("kid_nam");

        }
        for (int i = 0; i < kids_names.length; i++) {
            k_list.add(kids_names[i]);
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(drop_attendance.this, android.R.layout.simple_spinner_item, kids_names);
        kid_list.setAdapter(dataAdapter2);

        kid_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > -1) {

                    kid.setText(kid_list.getSelectedItem().toString());


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void dropattn(String date_holder, String kid_nam_holder, String time_holder, String status_holder, String van_no) {
        class ParentRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(drop_attendance.this, "Loading Data", null, true, true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                present.setChecked(false);
                absent.setChecked(false);
                Toast.makeText(drop_attendance.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("date", params[0]);
                hashMap.put("kid", params[1]);
                hashMap.put("time", params[2]);
                hashMap.put("status", params[3]);
                hashMap.put("van_no", params[4]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ParentRegistrationClass parentRegistrationClass = new ParentRegistrationClass();

        parentRegistrationClass.execute(date_holder, kid_nam_holder, time_holder,
                status_holder,van_no);

    }

    public class SendPushNotification extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        SendPushNotification(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(drop_attendance.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();




        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/onload.php";
            if (type.equals("van_no")) {
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

                    String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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
