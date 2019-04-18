package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OturumAcGonullu extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnOturumAc;
    EditText etEmail,etSifre;
    ImageView imageView;
    Context context;
    ProgressDialog oturumProgress;
    private DatabaseReference mDatabase;
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

                    gonulluOturum(email,sifre);

                }
                else{
                    Toast.makeText(OturumAcGonullu.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void gonulluOturum(final String email, String sifre) {
             mAuth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 gonulluKontrol(email);

             } else{
                 oturumProgress.dismiss();
                 Toast.makeText(OturumAcGonullu.this,"Giriş yapılamadı.Email ve şifrenizi kontrol ediniz.",Toast.LENGTH_SHORT).show();
             }

    }
});
    }

    private void gonulluKontrol(final String kontrolEmail) {

        try{
            String kullaniciId=mAuth.getCurrentUser().getUid();  // Kullanicinin id sini getirme

            mDatabase=FirebaseDatabase.getInstance().getReference().child("GonulluKullanicilar").child(kullaniciId);

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String email  = (String) dataSnapshot.child("email").getValue();

                    if(email == null) {   // Gorme engelli tablosundan bir kullanıcı ise
                        email = "";
                        Toast.makeText(OturumAcGonullu.this, "Bu sayfaya giriş izniniz bulunmamaktadır.", Toast.LENGTH_SHORT).show();
                        oturumProgress.dismiss();
                        return;
                    }
                    if (email.equals(kontrolEmail)) {
                        Intent i = new Intent(getApplicationContext(), GonulluKullaniciEkrani.class);
                        startActivity(i);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e){
            Log.d("OturumAcGonullu", "Exception: " + e.getMessage());
        }

    }


}
