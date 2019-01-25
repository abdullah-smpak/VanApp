package com.vanapp.abdullah.myapplication.Van;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.vanapp.abdullah.myapplication.R;

public class Parent_Profile extends AppCompatActivity {

    TextView txtnam, txtmnam, txtmono, txtfnam, txtfno, txtsnam, txtsloc, txtcls, txtfee, txtvno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);


        String std_nam = getIntent().getExtras().getString("std_nam");
        String kid_img = getIntent().getExtras().getString("kid_img");
        String sch_name = getIntent().getExtras().getString("sch_name");
        String sch_location = getIntent().getExtras().getString("sch_location");
        String cls = getIntent().getExtras().getString("cls");
        String fee = getIntent().getExtras().getString("fee");
        String mother_nam = getIntent().getExtras().getString("mother_nam");
        String mother_no = getIntent().getExtras().getString("mother_no");
        String father_nam = getIntent().getExtras().getString("father_nam");
        String father_no = getIntent().getExtras().getString("father_no");
        String van_no = getIntent().getExtras().getString("van_no");

        txtnam = findViewById(R.id.txtnam);
        txtmnam = findViewById(R.id.txtmnam);
        txtmono = findViewById(R.id.txtmono);
        txtfnam = findViewById(R.id.txtfnam);
        txtfno = findViewById(R.id.txtfno);
        txtsnam = findViewById(R.id.txtsnam);
        txtsloc = findViewById(R.id.txtsloc);
        txtfee = findViewById(R.id.txtfee);
        txtcls = findViewById(R.id.txtcls);
        txtvno = findViewById(R.id.txtvano);


        txtnam.setText("Student's Name : " + std_nam);
        txtmnam.setText("Mother's Name : " + mother_nam);
        txtmono.setText("Mother's Number : " + mother_no);
        txtfnam.setText("Father's Name : " + father_nam);
        txtfno.setText("Father's Number : " + father_no);
        txtsnam.setText("School Name : " + sch_name);
        txtsloc.setText("School Branch : " + sch_location);
        txtfee.setText("Fee : " + fee);
        txtcls.setText("Class : " + cls);
        txtvno.setText("Van Number : " + van_no);


    }
}
