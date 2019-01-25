package com.vanapp.abdullah.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vanapp.abdullah.myapplication.Van.Driver_Login;
import com.vanapp.abdullah.myapplication.Van.Parent_Login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Forget_Password extends Activity {

    String user, mob_no, password,trim;
    EditText new_pass;
    Button reset_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        new_pass = findViewById(R.id.new_pass);
        reset_pwd = findViewById(R.id.reset_pwd);

        password = new_pass.getText().toString();

        user = getIntent().getExtras().getString("user");
        mob_no = getIntent().getExtras().getString("mob_no");

         trim = "0" + mob_no.substring(3);

        reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.equals("d")) {

                    String type = "dri_for";
                    BackgroundDriverFor bkdrifor = new BackgroundDriverFor(Forget_Password.this);
                    bkdrifor.execute(type, trim, new_pass.getText().toString());

                } else if (user.equals("p")) {
                    String type = "par_for";
                    BackgroundParentFor bkprfor = new BackgroundParentFor(Forget_Password.this);
                    bkprfor.execute(type, trim, new_pass.getText().toString());
                }

            }
        });



    }


    public class BackgroundDriverFor extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        BackgroundDriverFor(Context ctx) {
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
           // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Forget_Password.this,Driver_Login.class);
            startActivity(intent);

        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/forget_dri.php";
            if (type.equals("dri_for")) {
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

    public class BackgroundParentFor extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        BackgroundParentFor(Context ctx) {
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
            Toast.makeText(context, "Password Changed Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Forget_Password.this,Parent_Login.class);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/forget_par.php";
            if (type.equals("par_for")) {
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
