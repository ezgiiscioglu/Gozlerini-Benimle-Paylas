package com.example.lenovo.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AyarlarEkrani extends AppCompatActivity {
    TextView txtProfil,txtIletisimeGec,txtGelistiriciler;
    Button btnKisiselDetaylar,btnFacebook,btnTwitter,btnInstagram,btnBetulLinkedin,btnCansuLinkedin,btnEzgiLinkedin,btnCikis;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayarlar);

        txtProfil = (TextView) findViewById(R.id.txtProfil);
        txtIletisimeGec = (TextView) findViewById(R.id.txtIletisimeGec);
        txtGelistiriciler = (TextView) findViewById(R.id.txtGelistiriciler);
        btnKisiselDetaylar=(Button) findViewById(R.id.btnKisiselDetaylar);
        btnFacebook=(Button) findViewById(R.id.btnFacebook);
        btnTwitter=(Button) findViewById(R.id.btnTwitter);
        btnInstagram=(Button) findViewById(R.id.btnInstagram);
        btnBetulLinkedin=(Button) findViewById(R.id.btnBetulLinkedin);
        btnCansuLinkedin=(Button) findViewById(R.id.btnCansuLinkedin);
        btnEzgiLinkedin=(Button) findViewById(R.id.btnEzgiLinkedin);
        btnCikis=(Button) findViewById(R.id.btnCikis);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri facebookLink= Uri.parse("https://www.facebook.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,facebookLink);
                startActivity(intent);
            }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri twitterLink= Uri.parse("https://twitter.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,twitterLink);
                startActivity(intent);
            }
        });
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri instagramLink= Uri.parse("https://instagram.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,instagramLink);
                startActivity(intent);
            }
        });
        btnBetulLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri linkedinLink= Uri.parse("https://linkedin.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,linkedinLink);
                startActivity(intent);
            }
        });
        btnCansuLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri linkedinLink= Uri.parse("https://linkedin.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,linkedinLink);
                startActivity(intent);
            }
        });
        btnEzgiLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri linkedinLink= Uri.parse("https://linkedin.com");
                Intent intent=new Intent(Intent.ACTION_VIEW,linkedinLink);
                startActivity(intent);
            }
        });

        btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AyarlarEkrani.this,GirisEkrani.class);
                startActivity(intent);
            }
        });
    }
}