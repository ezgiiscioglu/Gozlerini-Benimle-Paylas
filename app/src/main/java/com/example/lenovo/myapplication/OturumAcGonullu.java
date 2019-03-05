package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OturumAcGonullu extends AppCompatActivity {
    Button btnOturumAc;
    EditText etEmail,etSifre;
    ImageView imageView;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oturum_ac_gonullu);

        etEmail= (EditText) findViewById(R.id.etEmail);
        etSifre= (EditText) findViewById(R.id.etSifre);
        imageView=(ImageView)findViewById(R.id.imageView);
        btnOturumAc= (Button) findViewById(R.id.btnOturumAc) ;

        btnOturumAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OturumAcGonullu.this,GonulluKullaniciEkrani.class);
                startActivity(intent);
            }
        });
    }
}
