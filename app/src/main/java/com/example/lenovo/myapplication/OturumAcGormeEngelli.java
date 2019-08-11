package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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

public class OturumAcGormeEngelli extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button btnOturumAc;
    EditText etEmail,etSifre;
    ImageView imageView;
    Context context;
    ProgressDialog oturumProgress;
    private DatabaseReference mDatabase;
    CardView cardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oturum_ac_gonullu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etSifre = findViewById(R.id.etSifre);
        imageView=(ImageView)findViewById(R.id.profilResmi);
        btnOturumAc= (Button) findViewById(R.id.btnOturumAc) ;
        cardView=(CardView) findViewById(R.id.card_view);
        cardView.setBackgroundResource(R.drawable.purple);
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

    private void gormeEngelliOturum( final String email, String sifre) {
        mAuth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    gormeEngelliKontrol(email);

                } else{
                    oturumProgress.dismiss();
                    Toast.makeText(OturumAcGormeEngelli.this,"Giriş yapılamadı.Email ve şifrenizi kontrol ediniz.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void gormeEngelliKontrol(final String kontrolEmail) {

        try{
            String kullaniciId=mAuth.getCurrentUser().getUid();  // Kullanicinin id sini getirme

            mDatabase=FirebaseDatabase.getInstance().getReference().child("GormeEngelliKullanicilar").child(kullaniciId);

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String email  = (String) dataSnapshot.child("email").getValue();

                    if(email == null) {   // Gorme engelli tablosundan bir kullanıcı ise
                        email = "";
                        Toast.makeText(OturumAcGormeEngelli.this, "Bu sayfaya giriş izniniz bulunmamaktadır.", Toast.LENGTH_SHORT).show();
                        oturumProgress.dismiss();
                        return;
                    }
                    if (email.equals(kontrolEmail)) {
                        Intent i = new Intent(getApplicationContext(), GormeEngelliKullanici.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), GormeEngelliAnaEkrani.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
