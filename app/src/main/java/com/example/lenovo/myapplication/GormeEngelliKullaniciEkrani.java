package com.example.lenovo.myapplication;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GormeEngelliKullaniciEkrani extends AppCompatActivity {
    Button btnAramaBaslat;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_kullanici_ekran);

        btnAramaBaslat = (Button) findViewById(R.id.btnAramaBaslat);

        btnAramaBaslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GormeEngelliKullaniciEkrani.this, "Arama Başlatılıyor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
