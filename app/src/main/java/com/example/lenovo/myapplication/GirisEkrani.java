package com.example.lenovo.myapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GirisEkrani extends AppCompatActivity {

    private Button btnGonulluGiris,btnGormeEngelliGiris;
    private TextView txtGormeEngelli, txtGonullu,txtAciklama,txtGormeEngelliSayisi,txtGonulluSayisi;
    private ImageView imageView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ekrani);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

     //   Log.d("GirisEkrani", user.getEmail());
        //kullanıcı giriş yapınca onesignal sistemine o emaili kaydediyoz, sonra da şurdan
      //  OneSignal.setEmail(user.getEmail());


        btnGonulluGiris=(Button)findViewById(R.id.btnGonulluGiris);
        btnGormeEngelliGiris=(Button)findViewById(R.id.btnHesapOlustur);

        imageView=(ImageView)findViewById(R.id.profilResmi);
        imageView.setImageResource(R.drawable.ar);
        txtGormeEngelli = (TextView) findViewById(R.id.txtGormeEngelli);
        txtGonullu = (TextView) findViewById(R.id.txtGonullu);
        txtAciklama = (TextView) findViewById(R.id.txtAciklama);
        txtGormeEngelliSayisi = (TextView) findViewById(R.id.txtGormeEngelliSayisi);
        txtGonulluSayisi = (TextView) findViewById(R.id.txtGonulluSayisi);

        btnGonulluGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null){

                    Intent intent=new Intent(GirisEkrani.this,NavigationBar.class);
                    startActivity(intent);
                }else{
                    Intent i=new Intent(GirisEkrani.this,GonulluAnaEkrani.class);
                    startActivity(i);
                }

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
