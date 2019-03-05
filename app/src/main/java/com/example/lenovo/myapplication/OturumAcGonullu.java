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
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OturumAcGonullu extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnOturumAc;
    EditText etEmail,etSifre;
    ImageView imageView;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oturum_ac_gonullu);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etSifre = findViewById(R.id.etSifre);


        imageView=(ImageView)findViewById(R.id.imageView);
        btnOturumAc= (Button) findViewById(R.id.btnOturumAc) ;
    }

    public void OturumAcGonullu(View view) {
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etSifre.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OturumAcGonullu.this, GonulluKullaniciEkrani.class);
                            startActivity(intent);


                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
