package com.example.lenovo.myapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class ProfilDuzenlemeEkrani extends AppCompatActivity {

    EditText etAd,etSoyad,etEmail;
    Button btnProfiliGuncelle;
    ImageView profilResmii;
    Context context;
    private  FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Uri imageUri=null;
    private ProgressDialog profilProgress;
    private StorageReference storageReference;
    private Boolean kontrol=false;

         protected void onCreate(@Nullable Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.profili_duzenle);

        etAd = (EditText) findViewById(R.id.etAd);
        etSoyad = (EditText) findViewById(R.id.etSoyad);
        etEmail = (EditText) findViewById(R.id.etEmail);
        profilResmii=(ImageView)findViewById(R.id.profilResmi);
        btnProfiliGuncelle=(Button)findViewById(R.id.btnProfiliGuncelle);
        profilProgress=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();
        final String kullaniciId=mAuth.getCurrentUser().getUid();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("GonulluKullanicilar").child(kullaniciId);  // Profili Düzenle Sayfası Sadece Gonullu Kullanıcı İçin var.
        mDatabase.addValueEventListener(new ValueEventListener() {  // Databaseden verileri dinleme metodu
         @Override
          public void onDataChange( DataSnapshot dataSnapshot) {

        String gonulluAd=dataSnapshot.child("ad").getValue().toString();
        String gonulluSoyad=dataSnapshot.child("soyad").getValue().toString();
        String gonulluEmail=dataSnapshot.child("email").getValue().toString();
        String profilResmi=dataSnapshot.child("profilResmi").getValue().toString();
        imageUri=Uri.parse(profilResmi);



        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.login);
        Glide.with(ProfilDuzenlemeEkrani.this).setDefaultRequestOptions(requestOptions).load(imageUri).into(profilResmii);

        etAd.setText(gonulluAd);
        etSoyad.setText(gonulluSoyad);
        etEmail.setText(gonulluEmail);

    }

         @Override
         public void onCancelled( DatabaseError databaseError) {

    }
})  ;

              btnProfiliGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String gonulluKullaniciAdi=etAd.getText().toString();

              profilProgress.setTitle("Güncelleniyor.");
              profilProgress.setMessage("Bilgileriniz güncelleniyor.Lütfen bekleyiniz.");
              profilProgress.show();

              if( !TextUtils.isEmpty(gonulluKullaniciAdi) && imageUri!=null){
                 if(kontrol){     // Kırpma islemi gerceklesmis ise (   Imageview de kırpılmıs resim var ise ) calısır
        StorageReference kullaniciResim = storageReference.child("GonulluKullaniciResimleri").child(kullaniciId + ".jpg");

        kullaniciResim.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {      // ImageUri yi kullaniciResim'e gönderme
         @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

              if (task.isSuccessful()) {

                 final Uri download_uri;
                 if(task !=  null){   // İçinde kırpılmıs resim degeri varsa

                     Bitmap bitmap=BitmapFactory.decodeFile(imageUri.getPath());
                     profilResmii.setImageBitmap(bitmap);

                     //download_uri=task.getResult().getDownloadUrl();

            }
            else {      // Yoksa default olarak sectiğim imageUri ' yi kullan
                download_uri=imageUri ;
            }
            Map userUpdateMap=new HashMap();
            userUpdateMap.put("ad",etAd.getText().toString());
            userUpdateMap.put("soyad",etSoyad.getText().toString());
            userUpdateMap.put("email",etEmail.getText().toString());
          //  userUpdateMap.put("profilResmi",download_uri);


            mDatabase.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(Task task) {
                    if (task.isSuccessful()) {
                        profilProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"Güncelleme Başarı İle Tamamlandı.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        profilProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"Hata:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}) ;
                 }
              }
            }
        });

                profilResmii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ProfilDuzenlemeEkrani.this, new String []{Manifest.permission.READ_EXTERNAL_STORAGE },1);

                CropImage.activity()              // Profil Fotografı Kırpma İşlemi için
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfilDuzenlemeEkrani.this);
            }
        });


    }
    @Override
         public void onActivityResult(int requestCode, int resultCode, Intent data) {   // Kırpma işleminden sonra geri gelen kırpılmıs resmi yakalama kodu
             if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                  CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
               imageUri = result.getUri();     // kırpılmış resmi imageUri ye at
                profilResmii.setImageURI(imageUri);
                kontrol=true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
