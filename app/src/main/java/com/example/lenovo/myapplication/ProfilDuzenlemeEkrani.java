package com.example.lenovo.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilDuzenlemeEkrani extends AppCompatActivity {

    EditText etAd,etSoyad,etEmail;
    Button btnProfiliGuncelle;
    Context context;
    private  FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profili_duzenle);
        etAd = (EditText) findViewById(R.id.etAd);
        etSoyad = (EditText) findViewById(R.id.etSoyad);
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnProfiliGuncelle=(Button)findViewById(R.id.btnProfiliGuncelle);

        mAuth=FirebaseAuth.getInstance();
        String kullaniciId=mAuth.getCurrentUser().getUid();

   //   mDatabase = FirebaseDatabase.getInstance().getReference().child().child(kullaniciId);  ??????????????????


        btnProfiliGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Güncelleme Başarılı", Toast.LENGTH_LONG).show();

            }
        });
    }
}
