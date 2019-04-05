package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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




