package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KayitOlGonullu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText etAd,etSoyad,etEmail,etSifre;
    Button btnKayitOl;
    ImageView profilResmi;
    Context context;
    ProgressDialog kayitProgress;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit_ol_gonullu);

        mAuth = FirebaseAuth.getInstance();
        btnKayitOl=(Button)findViewById(R.id.btnKayitOl) ;
        etAd=(EditText)findViewById(R.id.etAd);
        etSoyad=(EditText)findViewById(R.id.etSoyad);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etSifre = (EditText)findViewById(R.id.etSifre);
        profilResmi=(ImageView)findViewById(R.id.profilResmi);
        kayitProgress=new ProgressDialog(this);

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ad = etAd.getText().toString();
                String soyad = etSoyad.getText().toString();
                String email = etEmail.getText().toString();
                String sifre = etSifre.getText().toString();


                if (!TextUtils.isEmpty(ad) && !TextUtils.isEmpty(soyad) && !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(sifre)) {

                    kayitProgress.setTitle("Kaydediliyor.");
                    kayitProgress.setMessage("Hesabınız oluşturuluyor.Lütfen bekleyiniz.");
                    kayitProgress.setCanceledOnTouchOutside(false);
                    kayitProgress.show();

                    gonulluKayit(ad, soyad, email, sifre);

                }
                else{
                    Toast.makeText(KayitOlGonullu.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();

                }

            }

    });
}

   private void gonulluKayit(final String ad, final String soyad, final String email, final String sifre) {
        mAuth.createUserWithEmailAndPassword(email, sifre)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String kullaniciId=mAuth.getCurrentUser().getUid();  // Kullanicinin id sini getirme
                            mDatabase=FirebaseDatabase.getInstance().getReference().child("GonulluKullanicilar").child(kullaniciId);
                            HashMap<String,String> userMap=new HashMap<>();
                            userMap.put("ad",ad);
                            userMap.put("soyad",soyad);
                            userMap.put("email",email);
                            userMap.put("sifre",sifre);
                            userMap.put("profilResmi","profil");
                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {  // Değerleri veritabanına gönder
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                kayitProgress.dismiss();
                                Intent i = new Intent(KayitOlGonullu.this, OturumAcGonullu.class);
                                startActivity(i);
                                }
                                }
                            });

                        } else {
                            kayitProgress.dismiss();
                            // mailde @ işareti yoksa ve şifre 6 karakter değilse meydana gelen hata
                            Toast.makeText(KayitOlGonullu.this, "Lütfen uygun formda email ve şifre giriniz.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }

}
