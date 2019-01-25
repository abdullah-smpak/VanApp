package com.vanapp.abdullah.myapplication.Adpters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vanapp.abdullah.myapplication.Models.MorStudents;
import com.vanapp.abdullah.myapplication.OtherServices.HttpParse;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EvListViewAdapter extends ArrayAdapter<MorStudents> {

    private Context mContext;
    ProgressDialog progressDialog;
    String finalResult;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/vanapp/eve_att.php";
    HashMap<String, String> hashMap = new HashMap<>();
    private int mResource;
    public String van_no;
    String chk = null;

    private static class ViewHandler {

        TextView bookNameTextView;
        TextView bookAuthorTextView;

    }

    public EvListViewAdapter(Context context, int resource, @NonNull ArrayList<MorStudents> object) {
        super(context, resource, object);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @SuppressWarnings("ConstantConditions")
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        try {
            //ViewHolder object

            if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(R.layout.van_model, parent, false);
            }

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            String date = mdformat.format(calendar.getTime());

            SimpleDateFormat mdformat1 = new SimpleDateFormat("HH:mm:ss");
            String time = mdformat1.format(calendar.getTime());
            String id = String.valueOf(getItem(position).getId());
            String van_no = String.valueOf(getItem(position).getVan_no());


            TextView bookNameTextView = convertView.findViewById(R.id.nameTextView);
            final CheckBox chkTechExists = convertView.findViewById(R.id.myCheckBox);
            final CheckBox chkTechExists1 = convertView.findViewById(R.id.confirm);
            final CircleImageView std_img = convertView.findViewById(R.id.std_img);


            bookNameTextView.setText(getItem(position).getName());


            String url = "http://www.mucaddam.pk/abdullah/vanapp/" + getItem(position).getImg();
            Picasso.with(mContext).load(url).placeholder(R.drawable.driverico).error(R.drawable.driverico).into(std_img, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });

            /*String type = "checkmark";
            CheckAtt checkAtt = new CheckAtt(mContext);
            checkAtt.execute(type, date);
            if(chk.equals("1"))
            {
                chkTechExists.setEnabled(false);
            }*/

            chkTechExists1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkTechExists1.isChecked()) {

                        String type = "notify";
                        SendPushNotification sendPushNotification = new SendPushNotification(mContext);
                        sendPushNotification.execute(type, String.valueOf(getItem(position).getId()));
                    }
                }
            });
            chkTechExists.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (chkTechExists.isChecked()) {

                        Log.d("chkk", van_no);
                        Mor_Att(id, van_no, date,
                                time, "1");


                        // Toast.makeText(context, thisSpacecraft.getStd_id() + "", Toast.LENGTH_SHORT).show();


                        // String van_no = mo.van_no;
                        //Toast.makeText(mContext, getItem(position).getVan_no(), Toast.LENGTH_SHORT).show();
                        // MarkAtt(a, 1);
                    } else if (!chkTechExists.isChecked()) {
                        String type = "absent";
                        MarkAbs loginwork = new MarkAbs(mContext);
                        loginwork.execute(type, id, date);
                    }
                }
            });


            return convertView;
        } catch (IllegalArgumentException e) {
            //Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        } catch (NullPointerException ex) {
            Toast.makeText(mContext, ex.toString(), Toast.LENGTH_SHORT).show();
            Log.e("Error", ex.toString());
            return convertView;
        }

    }//end of getView


    private void Mor_Att(String std_id_holder, String van_no_holdder,
                         String date_holder, String time_holder,
                         String status_holder) {
        class Mor_AttClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(mContext, "Loading Data", null, true, true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(mContext, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("std_id", params[0]);
                hashMap.put("van_no", params[1]);
                hashMap.put("date", params[2]);
                hashMap.put("time", params[3]);
                hashMap.put("status", params[4]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        Mor_AttClass mor_attClass = new Mor_AttClass();

        mor_attClass.execute(std_id_holder, van_no_holdder, date_holder,
                time_holder, status_holder);
    }


    public class MarkAbs extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        MarkAbs(Context ctx) {
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

        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/eve_att_abs.php";
            if (type.equals("absent")) {
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

                    String post_data = URLEncoder.encode("std_id", "UTF-8") + "=" + URLEncoder.encode(mobno, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");
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

            progressDialog = ProgressDialog.show(context, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {
            chk = result;
            progressDialog.dismiss();

        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/check_att.php";
            if (type.equals("login")) {
                try {
                    String mobno = params[1];

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

                    String post_data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(mobno, "UTF-8");
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

            progressDialog = ProgressDialog.show(mContext, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/noti_ind_eve_att.php";
            if (type.equals("notify")) {
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

                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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