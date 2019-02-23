package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GonulluKullaniciEkrani extends AppCompatActivity {

    Button btnAnasayfa,btnAyarlar;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gonullu_kullanici_ekran);

        btnAnasayfa = (Button) findViewById(R.id.btnAnasayfa);
        btnAyarlar = (Button) findViewById(R.id.btnAyarlar);

        btnAnasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GonulluKullaniciEkrani.this,GonulluKullaniciEkrani.class);
                startActivity(i);
            }
        });
        btnAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GonulluKullaniciEkrani.this,AyarlarEkrani.class);
                startActivity(i);
            }
        });
    }


    }




