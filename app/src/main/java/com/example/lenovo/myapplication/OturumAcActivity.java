package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OturumAcActivity extends AppCompatActivity {
    EditText etEmail,etSifre;
    Button btnOturumAc;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oturum);

        etEmail= (EditText) findViewById(R.id.etEmail);
        etSifre= (EditText) findViewById(R.id.etSifre);
        btnOturumAc= (Button) findViewById(R.id.btnOturumAc) ;

        btnOturumAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OturumAcActivity.this,GonulluKullaniciEkrani.class);
                startActivity(intent);
            }
        });
    }
}
