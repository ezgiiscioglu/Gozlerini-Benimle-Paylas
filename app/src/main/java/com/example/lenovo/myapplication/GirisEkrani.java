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
    Button gonulluGiris,gormeEngelliGiris;
    TextView textView, textView2,textView3;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ekrani);

        gonulluGiris=(Button)findViewById(R.id.gonulluGiris);
        gormeEngelliGiris=(Button)findViewById(R.id.btnHesapOlustur);

        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.resim);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        gonulluGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GirisEkrani.this,GonulluAnaEkrani.class);
                startActivity(i);
            }
        });
        gormeEngelliGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GirisEkrani.this,GormeEngelliAnaEkrani.class);
                startActivity(i);
            }
        });
    }
}
