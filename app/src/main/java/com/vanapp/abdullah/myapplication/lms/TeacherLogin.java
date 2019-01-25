package com.vanapp.abdullah.myapplication.lms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vanapp.abdullah.myapplication.Models.Common;
import com.vanapp.abdullah.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TeacherLogin extends AppCompatActivity {

    MaterialEditText unam,pas;
    Button login;
    String user_name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);


        unam  = findViewById(R.id.unam);
        pas = findViewById(R.id.pas);

        login= findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnected(getBaseContext())) {

                    user_name = unam.getText().toString();
                    password = pas.getText().toString();

                    try {
                        String type = "login";
                        BackgroundTeacherLogin loginwork = new BackgroundTeacherLogin(TeacherLogin.this);
                        loginwork.execute(type, user_name, password);
                    } catch (Exception ex) {
                        Toast.makeText(TeacherLogin.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Toast.makeText(TeacherLogin.this, "Please Check Internet Connection !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class BackgroundTeacherLogin extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        BackgroundTeacherLogin(Context ctx) {
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
                try{
                    if (result.equals("Login Successfully")) {

                        Intent intent = new Intent(TeacherLogin.this, TeacherMain.class);
                        intent.putExtra("username", user_name);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, result + "", Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception ex){
                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/lms/teacher_login.php";
            if (type.equals("login")) {
                try {
                    String unam = params[1];
                    String pas = params[2];
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

                    String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(unam, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pas, "UTF-8");
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
