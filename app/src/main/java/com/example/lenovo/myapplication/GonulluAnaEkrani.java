package com.example.lenovo.myapplication;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GonulluAnaEkrani extends AppCompatActivity {
    Button btnHesapOlustur, btnOturumAc;
    ImageView imgUser;
    CardView cardView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gonullu_ana_ekran);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardView = (CardView) findViewById(R.id.card_view);
        cardView.setBackgroundResource(R.drawable.purple);
        btnHesapOlustur = (Button) findViewById(R.id.btnHesapOlustur);
        btnOturumAc = (Button) findViewById(R.id.btnOturumAc);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        imgUser.setImageResource(R.drawable.pan);
        btnHesapOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GonulluAnaEkrani.this, KayitOlGonullu.class);
                startActivity(i);
            }
        });
        btnOturumAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GonulluAnaEkrani.this, OturumAcGonullu.class);
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), GirisEkrani.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}