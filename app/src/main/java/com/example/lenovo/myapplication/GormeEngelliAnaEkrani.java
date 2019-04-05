package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GormeEngelliAnaEkrani  extends AppCompatActivity {
    Button btnHesapOlustur, btnOturumAc;
    ImageView imgUser;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_ana_ekran);


        btnHesapOlustur = (Button) findViewById(R.id.btnHesapOlustur);
        btnOturumAc = (Button) findViewById(R.id.btnOturumAc);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        imgUser.setImageResource(R.drawable.user1);
        btnHesapOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GormeEngelliAnaEkrani.this,KayitOlGormeEngelli.class);
                startActivity(i);
            }
        });
        btnOturumAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GormeEngelliAnaEkrani.this,OturumAcGormeEngelli.class);
                startActivity(i);
            }
        });
    }
    }

