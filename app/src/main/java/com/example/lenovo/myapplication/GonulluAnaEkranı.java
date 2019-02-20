package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GonulluAnaEkranı extends AppCompatActivity implements View.OnClickListener {
    Button btnHesapOlustur, btnGirisYap;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gonullu_ana_ekran);

        btnHesapOlustur = (Button) findViewById(R.id.btnHesapOlustur);
        btnGirisYap = (Button) findViewById(R.id.btnGirisYap);
        btnHesapOlustur.setOnClickListener(this);
        btnGirisYap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHesapOlustur:
                Intent intent=new Intent(GonulluAnaEkranı.this,KayitOlActivity.class);
                startActivity(intent);
                break;
            case R.id.btnGirisYap:
                Intent i=new Intent(GonulluAnaEkranı.this,OturumAcActivity.class);
                startActivity(i);
                break;

        }

    }
}
