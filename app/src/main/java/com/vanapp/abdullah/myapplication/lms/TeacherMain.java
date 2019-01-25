package com.vanapp.abdullah.myapplication.lms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.vanapp.abdullah.myapplication.R;
import com.vanapp.abdullah.myapplication.Strat;

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

public class TeacherMain extends AppCompatActivity {

    String emp_name, emp_cnic, emp_address, emp_mobile;
    CardView btn_syllabus,btn_cw,btn_hw,btn_ct,btn_ass,btn_att;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        btn_syllabus = findViewById(R.id.btn_syllabus);
        btn_cw = findViewById(R.id.btn_cw);
        btn_hw = findViewById(R.id.btn_hw);
        btn_ct = findViewById(R.id.btn_ct);
        btn_ass = findViewById(R.id.btn_ass);
        btn_att = findViewById(R.id.btn_att);


        btn_syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMain.this, Syllabus.class);
                intent.putExtra("teacher_name", emp_name);
                startActivity(intent);
            }
        });


        btn_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMain.this, Attendance.class);
                intent.putExtra("teacher_name", emp_name);
                startActivity(intent);
            }
        });

        btn_ass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMain.this, Assignment.class);
                intent.putExtra("teacher_name", emp_name);
                startActivity(intent);
            }
        });

        btn_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMain.this, Test.class);
                intent.putExtra("teacher_name", emp_name);
                startActivity(intent);
            }
        });

        btn_cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(TeacherMain.this, ClassWork.class);
                 intent.putExtra("teacher_name", emp_name);
                 startActivity(intent);
            }
        });

        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMain.this, HomeWork.class);
                intent.putExtra("teacher_name", emp_name);
                startActivity(intent);
            }
        });


        String username = getIntent().getExtras().getString("username");

        String type = "fetch";
        BackgroundTeacherdetails fetchdetails = new BackgroundTeacherdetails(TeacherMain.this);
        fetchdetails.execute(type, username);


    }


    public class BackgroundTeacherdetails extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        BackgroundTeacherdetails(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Please Wait...", null, true, true);
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
                    emp_name = jObj.getString("emp_name");
                    emp_cnic = jObj.getString("emp_cnic");
                    emp_address = jObj.getString("emp_address");
                    emp_mobile = jObj.getString("emp_address");


                    //Toast.makeText(context, "Welcome "+ emp_name, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) { }


            // Toast.makeText(context, "Welcome " + result, Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/fetch_teacher_details.php";
            if (type.equals("fetch")) {
                try {
                    String unam = params[1];

                    url = new URL(login_url);
                    //
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(14000);

                    httpURLConnection.setConnectTimeout(14000);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoInput(true);

                    httpURLConnection.setDoOutput(true);

                    outputStream = httpURLConnection.getOutputStream();

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(unam, "UTF-8");
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



    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(this)
                .setTitle("Exit")
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
                        finish();
                        Intent intent = new Intent(TeacherMain.this,Strat.class);
                        startActivity(intent);

                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }
}
