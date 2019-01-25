package com.vanapp.abdullah.myapplication.lms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HomeWork extends AppCompatActivity {

    ImageView img_hw;
    MaterialBetterSpinner sp_subject, sp_class;
    EditText fnam;
    Button upload;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://www.mucaddam.pk/abdullah/lms/class_hw.php";
    private String imageFilePath1 = "";
    Bitmap photo1;
    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;
    public static final int REQUEST_PERMISSION = 200;
    InputStream is1 = null, is2 = null;
    List<String> cl_list, co_list;
    String[] classes, courses;

    String cls_holder, sub_holder, fnam_name_holder, clsresult = null, clsline = null, couresult = null, couline = null, teacher_name, filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        teacher_name = getIntent().getExtras().getString("teacher_name");

        img_hw = findViewById(R.id.img_hw);
        sp_subject = findViewById(R.id.sp_subject);
        sp_class = findViewById(R.id.sp_class);
        fnam = findViewById(R.id.fnam);
        upload = findViewById(R.id.upload);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        try {
            fetchclass();
        } catch (Exception ex) { }
        try {
            fetchcourse();
        } catch (Exception ex) { }




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cls_holder = sp_class.getText().toString();
                sub_holder = sp_subject.getText().toString();
                fnam_name_holder = fnam.getText().toString();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                String strDate = mdformat.format(calendar.getTime());

                try {
                    clsact(cls_holder, sub_holder, imagetostr(photo1), strDate, teacher_name, filename);
                } catch (Exception ex) { }


            }
        });


        img_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageFileName = null;
                filename = fnam.getText().toString();
                if (filename.equals("")) {
                    Toast.makeText(HomeWork.this, "Enter File Name First!", Toast.LENGTH_SHORT).show();
                } else {

                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                        File photoFile;

                        imageFileName = fnam.getText().toString();
                        File storageDir = new File("/storage/emulated/0/Pictures/");
                        File image = new File(storageDir, imageFileName + ".jpg");

                        imageFilePath1 = image.getAbsolutePath();
                        photoFile = image;

                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", photoFile);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(pictureIntent, 1);
                    }
                }
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
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(HomeWork.this, android.R.layout.simple_dropdown_item_1line, classes);
            sp_class.setAdapter(dataAdapter1);
        } catch (Exception ex) { }


    }

    //school spinner populate
    private void spinner_sch2() {
        try {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(HomeWork.this, android.R.layout.simple_dropdown_item_1line, courses);
            sp_subject.setAdapter(dataAdapter1);
        } catch (Exception ex) { }


    }

    private void fetchcourse() {
        co_list = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.mucaddam.pk/abdullah/lms/fetch_courses.php");
        HttpResponse response = null;
        try {

            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            HttpEntity entity = response.getEntity();
            try {
                is2 = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception ex) { }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((couline = reader.readLine()) != null) {
                sb.append(couline + "\n");
            }
            is2.close();
            couresult = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray JA = new JSONArray(couresult);
            JSONObject json = null;

            courses = new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                courses[i] = json.getString("subject_name");
            }

            for (int i = 0; i < courses.length; i++) {
                co_list.add(courses[i]);
            }
            spinner_sch2();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //permission
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                img_hw.setImageURI(Uri.parse(imageFilePath1));
                photo1 = ((BitmapDrawable) img_hw.getDrawable()).getBitmap();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void clsact(String cls_holder, String sub_holder, String img_holder,
                        String date_holder, String teacher_name, String filename) {

        class Clsactclass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(HomeWork.this, "Uploading...", null, true, true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(HomeWork.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                new FancyAlertDialog.Builder(HomeWork.this)
                        .setTitle("Add More")
                        .setBackgroundColor(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                        .setMessage("Do you  want to Upload More ?")
                        .setNegativeBtnText("No")
                        .setPositiveBtnBackground(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                        .setPositiveBtnText("Yes")
                        .setNegativeBtnBackground(Color.parseColor("#fbb107"))  //Don't pass R.color.colorvalue
                        .setAnimation(Animation.POP)
                        .isCancellable(false)
                        .setIcon(R.drawable.quetion, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                finish();
                                Intent intent = new Intent(HomeWork.this, HomeWork.class);
                                intent.putExtra("teacher_name", teacher_name);
                                startActivity(intent);

                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Intent intent = new Intent(HomeWork.this, TeacherMain.class);
                                intent.putExtra("username", teacher_name);

                                startActivity(intent);
                            }
                        })
                        .build();

              /*  Intent intent = new Intent(Driver_Reg.this, Driver_Login.class);
                startActivity(intent);
                finish();*/

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("hw_class", params[0]);
                hashMap.put("hw_subject", params[1]);
                hashMap.put("hw_image", params[2]);
                hashMap.put("hw_date", params[3]);
                hashMap.put("teacher_name", params[4]);
                hashMap.put("filename", params[5]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        Clsactclass clsactClass = new Clsactclass();

        try {
            clsactClass.execute(cls_holder, sub_holder, img_holder,
                    date_holder, teacher_name, filename);
        } catch (Exception ex) { }



    }


    private String imagetostr(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);

    }


}
