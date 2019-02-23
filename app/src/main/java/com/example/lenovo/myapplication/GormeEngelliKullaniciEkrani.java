package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GormeEngelliKullaniciEkrani extends AppCompatActivity {
    Button btngormeengelli;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_kullanici_ekran);

        btngormeengelli = (Button) findViewById(R.id.btngormeengelli);

        btngormeengelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GormeEngelliKullaniciEkrani.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
