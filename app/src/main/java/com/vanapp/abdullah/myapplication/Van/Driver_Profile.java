package com.vanapp.abdullah.myapplication.Van;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.vanapp.abdullah.myapplication.R;

public class Driver_Profile extends AppCompatActivity {


    TextView txtnam, txtph, txtadd, txtvno, txtmk,txtmod, txtsn, txtsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__profile);


        String dri_nam = getIntent().getExtras().getString("d_nam");
        String dri_ph = getIntent().getExtras().getString("mob_no");
        String dri_add = getIntent().getExtras().getString("dri_address");
        String dri_vno = getIntent().getExtras().getString("van_no");
        String dri_mk = getIntent().getExtras().getString("make");
        String dri_mod = getIntent().getExtras().getString("model");
        String dri_sn = getIntent().getExtras().getString("school_nam");
        String dri_sl = getIntent().getExtras().getString("school_loc");


        txtnam = findViewById(R.id.txtnam);
        txtph = findViewById(R.id.txtph);
        txtadd = findViewById(R.id.txtadd);
        txtvno = findViewById(R.id.txtvno);
        txtmk = findViewById(R.id.txtmk);
        txtmod = findViewById(R.id.txtmod);
        txtsn = findViewById(R.id.txtsn);
        txtsl = findViewById(R.id.txtsl);



        txtnam.setText("Driver's Name : " + dri_nam);
        txtph.setText("Phone Number : " + dri_ph);
        txtadd.setText("Address : " + dri_add);
        txtvno.setText("Van Number : " + dri_vno);
        txtmk.setText("Van Make : " + dri_mk);
        txtmod.setText("Van Model : " + dri_mod);
        txtsn.setText("School Name : " + dri_sn);
        txtsl.setText("School Branch : " + dri_sl);

    }
}
