package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GirisEkrani extends AppCompatActivity {
    Button btnGonulluGiris,btnGormeEngelliGiris;
    TextView txtGormeEngelli, txtGonullu,txtAciklama,txtGormeEngelliSayisi,txtGonulluSayisi;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ekrani);

        btnGonulluGiris=(Button)findViewById(R.id.btnGonulluGiris);
        btnGormeEngelliGiris=(Button)findViewById(R.id.btnGormeEngelliGiris);

        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.resim);
        txtGormeEngelli = (TextView) findViewById(R.id.txtGormeEngelli);
        txtGonullu = (TextView) findViewById(R.id.txtGonullu);
        txtAciklama = (TextView) findViewById(R.id.txtAciklama);
        txtGormeEngelliSayisi = (TextView) findViewById(R.id.txtGormeEngelliSayisi);
        txtGonulluSayisi = (TextView) findViewById(R.id.txtGonulluSayisi);

        btnGonulluGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GirisEkrani.this,GonulluAnaEkrani.class);
                startActivity(i);
            }
        });
        btnGormeEngelliGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GirisEkrani.this,GormeEngelliAnaEkrani.class);
                startActivity(i);
            }
        });
    }
}
