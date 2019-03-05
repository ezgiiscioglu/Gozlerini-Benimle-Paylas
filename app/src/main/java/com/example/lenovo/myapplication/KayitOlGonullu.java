package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitOlGonullu extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText etAd,etSoyad,etEmail,etSifre;
    Button btnKayitOl;
    Context context;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit_ol_gonullu);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etSifre = findViewById(R.id.etSifre);
    }
    public void KayitOlGonullu(View view) {
        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etSifre.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(KayitOlGonullu.this, "Kayit Başarıyla Tamamlandı.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(KayitOlGonullu.this, OturumAcGonullu.class);
                        startActivity(i);
                    }

                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(KayitOlGonullu.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }}
