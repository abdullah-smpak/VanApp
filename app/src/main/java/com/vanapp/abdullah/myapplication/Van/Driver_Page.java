package com.vanapp.abdullah.myapplication.Van;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vanapp.abdullah.myapplication.PhoneReg;
import com.vanapp.abdullah.myapplication.R;

public class Driver_Page extends AppCompatActivity {
Button reg,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__page);

        reg = findViewById(R.id.reg);
        login = findViewById(R.id.login);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Page.this, PhoneReg.class);
                intent.putExtra("user", "driver");
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Page.this, Driver_Login.class);
                startActivity(intent);
            }
        });
    }
}
