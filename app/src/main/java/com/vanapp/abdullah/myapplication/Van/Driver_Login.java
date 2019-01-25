package com.vanapp.abdullah.myapplication.Van;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vanapp.abdullah.myapplication.Models.Common;
import com.vanapp.abdullah.myapplication.PhoneReg;
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

public class Driver_Login extends AppCompatActivity {

    MaterialEditText mob_no, pass;
    TextView forget;
    Button login;

    String mobile_no, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_log);

        forget = findViewById(R.id.forget);
        login = findViewById(R.id.login);
        mob_no = findViewById(R.id.unam);
        pass = findViewById(R.id.pas);


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Login.this, PhoneReg.class);
                intent.putExtra("user", "driver_for");
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Common.isConnected(getBaseContext())) {
                    mobile_no = mob_no.getText().toString();
                    password = pass.getText().toString();

                    String type = "login";
                    BackgroundDriverLogin loginwork = new BackgroundDriverLogin(Driver_Login.this);
                    loginwork.execute(type, mobile_no, password);

                }
                else {

                    Toast.makeText(Driver_Login.this, "Please Check Internet Connection !!!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    public class BackgroundDriverLogin extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        BackgroundDriverLogin(Context ctx) {
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

                    Intent intent = new Intent(Driver_Login.this, Driver_Mainpage.class);
                    intent.putExtra("mob_no", mob_no.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, result + "", Toast.LENGTH_SHORT).show();
                }

            }catch(Exception ex){
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/driver_login.php";
            if (type.equals("login")) {
                try {
                    String mobno = params[1];
                    String pwd = params[2];
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

                    String post_data = URLEncoder.encode("mobno", "UTF-8") + "=" + URLEncoder.encode(mobno, "UTF-8") + "&" +
                            URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");
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
