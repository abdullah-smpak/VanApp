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
import android.widget.TextView;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Picasso;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Parent_MainPage extends AppCompatActivity {


    String std_nam, kid_img, kid_id, sch_name, sch_bra, cls, fee, mother_nam, mother_no, father_nam, father_no, van_no;
    CircleImageView pro_img;
    Button profile, map, status;
    TextView txt_nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__main_page);

        profile = findViewById(R.id.profile);
        map = findViewById(R.id.map);
      //  status = findViewById(R.id.status);
        txt_nam = findViewById(R.id.txt_nam);
        pro_img = findViewById(R.id.pro_img);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parent_MainPage.this, dri_track.class);
                intent.putExtra("van", van_no);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Parent_MainPage.this, Parent_Profile.class);
                intent.putExtra("std_nam", std_nam);
                intent.putExtra("kid_img", kid_img);
                intent.putExtra("sch_name", sch_name);
                intent.putExtra("sch_location", sch_bra);
                intent.putExtra("cls", cls);
                intent.putExtra("fee", fee);
                intent.putExtra("mother_nam", mother_nam);
                intent.putExtra("mother_no", mother_no);
                intent.putExtra("father_nam", father_nam);
                intent.putExtra("father_no", father_no);
                intent.putExtra("van_no", van_no);
                startActivity(intent);

            }
        });


        String dri_mob = getIntent().getExtras().getString("mob_no");


        String type = "mob_no";
        FetchKidData fetchKidData = new FetchKidData(Parent_MainPage.this);
        fetchKidData.execute(type, dri_mob);
    }


    public class FetchKidData extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        Context context;
        OutputStream outputStream;
        URL url;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;


        FetchKidData(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Parent_MainPage.this, "Please Wait...", null, true, true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();

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
                std_nam = jObj.getString("kid_nam");
                kid_id = jObj.getString("id");
                kid_img = jObj.getString("pro_img");
                sch_name = jObj.getString("school_name");
                sch_bra = jObj.getString("school_location");
                cls = jObj.getString("class");
                fee = jObj.getString("fee");
                mother_nam = jObj.getString("mother_nam");
                mother_no = jObj.getString("mother_no");
                father_nam = jObj.getString("father_nam");
                father_no = jObj.getString("father_no");
                van_no = jObj.getString("van_no");


                txt_nam.setText("Hello! " + std_nam);

                fetchstudentimg();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_student_data.php";
            if (type.equals("mob_no")) {
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

                    String post_data = URLEncoder.encode("mob_no", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
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

    private void fetchstudentimg() {
        String url = "http://www.mucaddam.pk/abdullah/vanapp/" + kid_img;
        Picasso.with(this).load(url).placeholder(R.drawable.driverico).error(R.drawable.driverico).into(pro_img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(this)
                .setTitle("Logout")
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
                        Intent intent = new Intent(Parent_MainPage.this, Parent_Login.class);
                        startActivity(intent);
                        finish();
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
