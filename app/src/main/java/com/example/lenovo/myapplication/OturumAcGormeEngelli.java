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

public class OturumAcGormeEngelli extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnOturumAc;
    EditText etEmail,etSifre;
    ImageView imageView;
    Context context;
    ProgressDialog oturumProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oturum_ac_gonullu);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etSifre = findViewById(R.id.etSifre);
        imageView=(ImageView)findViewById(R.id.profilResmi);
        btnOturumAc= (Button) findViewById(R.id.btnOturumAc) ;
        oturumProgress=new ProgressDialog(this);


        btnOturumAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String sifre=etSifre.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(sifre)){
                    oturumProgress.setTitle("Oturum Açılıyor");
                    oturumProgress.setMessage("Hesabınıza giriş yapılıyor.Lütfen bekleyiniz.");
                    oturumProgress.setCanceledOnTouchOutside(false);
                    oturumProgress.show();

                    gormeEngelliOturum(email,sifre);

                }
                else{
                    Toast.makeText(OturumAcGormeEngelli.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void gormeEngelliOturum(String email, String sifre) {
        mAuth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    oturumProgress.dismiss();
                    Intent i=new Intent(OturumAcGormeEngelli.this,GormeEngelliKullaniciEkrani.class);
                    startActivity(i);

                }else{
                    oturumProgress.dismiss();
                    Toast.makeText(OturumAcGormeEngelli.this,"Giriş yapılamadı.Email ve şifrenizi kontrol ediniz.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
