package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KayitOlActivity extends AppCompatActivity {
    EditText etAd,etSoyad,etEmail,etSifre;
    Button btnKayitOl;
    Context context;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit);
        etAd= (EditText) findViewById(R.id.etAd);
        etSoyad= (EditText) findViewById(R.id.etSoyad);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etSifre= (EditText) findViewById(R.id.etSifre);
        btnKayitOl= (Button) findViewById(R.id.btnKayitOl) ;

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KayitOlActivity.this,OturumAcActivity.class);
                startActivity(intent);
            }
        });
    }
}
