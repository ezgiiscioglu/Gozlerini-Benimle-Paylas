package com.example.lenovo.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

public class deneme extends Activity {
TextView txt1,txt2,txtGormeEngelli,txtGonullu;
ImageView imageView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deneme);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.resim);
        txt1=(TextView) findViewById(R.id.txtGormeEngelliSayisi);
        txt2=(TextView) findViewById(R.id.txtGonulluSayisi);
        txtGormeEngelli=(TextView) findViewById(R.id.txtGormeEngelli);
        txtGonullu=(TextView) findViewById(R.id.txtGonullu);
    }
}