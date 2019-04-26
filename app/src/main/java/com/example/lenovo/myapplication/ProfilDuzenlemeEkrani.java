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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilDuzenlemeEkrani extends AppCompatActivity implements ValueEventListener{

    EditText etAd, etSoyad, etEmail;
    Button btnProfiliGuncelle;
    CircleImageView profilResmii;
    Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Uri imageUri = null;
    private ProgressDialog profilProgress;
    private StorageReference storageReference;
    private Boolean kontrol = false;
    private String kullaniciId;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profili_duzenle);

        etAd = (EditText) findViewById(R.id.etAd);
        etSoyad = (EditText) findViewById(R.id.etSoyad);
        etEmail = (EditText) findViewById(R.id.etEmail);
        profilResmii = (CircleImageView) findViewById(R.id.profilResmi);
        btnProfiliGuncelle = (Button) findViewById(R.id.btnProfiliGuncelle);
        profilProgress = new ProgressDialog(this);

        etEmail.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        kullaniciId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("GonulluKullanicilar").child(kullaniciId);  // Profili Düzenle Sayfası Sadece Gonullu Kullanıcı İçin var.
        mDatabase.addValueEventListener(this);    // implements ValueEventListener  --> onDataChange calısır.

        btnProfiliGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gonulluKullaniciAdi = etAd.getText().toString();
                String gonulluKullaniciSoyadi = etSoyad.getText().toString();
                profilProgress.setTitle("Güncelleniyor.");
                profilProgress.setMessage("Bilgileriniz güncelleniyor.Lütfen bekleyiniz.");
                profilProgress.setCancelable(false);
                profilProgress.show();

                if (!TextUtils.isEmpty(gonulluKullaniciAdi) && !TextUtils.isEmpty(gonulluKullaniciSoyadi) && imageUri != null) {
                    Log.d("ProfilDuzenlemeEkranı", "if içi");
                    if (kontrol) {     // Kırpma islemi gerceklesmis ise (   Imageview de kırpılmıs resim var ise ) calısır
                        Log.d("ProfilDuzenlemeEkranı", "if içi 2");
                        final StorageReference kullaniciResim = storageReference.child("GonulluKullaniciResimleri").child(kullaniciId + ".jpg");
                        UploadTask task = kullaniciResim.putFile(imageUri);

                        Task<Uri> urlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {

                                    if(profilProgress.isShowing())
                                        profilProgress.dismiss();

                                }

                                return kullaniciResim.getDownloadUrl();
                            }
                        }).addOnCompleteListener(ProfilDuzenlemeEkrani.this, new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){

                                    Log.d("ProfilDuzenlemee", "Upload task successful: " + task.getResult().toString());

                                    Uri download_uri;
                                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
                                    profilResmii.setImageBitmap(bitmap);
                                    download_uri = task.getResult();

                                    kullaniciGuncelle(download_uri);

                                }
                            }
                        });

                    }
                    else{     // Profil düzenlemeden - Ayarlar a geçip tekrar Profiluzenleme yaptıgımızda veya sadece adsoyad guncellediğimizde

                        profilProgress.dismiss();
                        adSoyadGuncelle();
                    }

                }
                else{
                    profilProgress.dismiss();
                    Toast.makeText(ProfilDuzenlemeEkrani.this, "Ad, soyad bilgilerinin dolu olması zorunludur.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        profilResmii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ProfilDuzenlemeEkrani.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                CropImage.activity()              // Profil Fotografı Kırpma İşlemi için
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfilDuzenlemeEkrani.this);
            }
        });


    }

    private void kullaniciGuncelle(Uri download_uri) {

        mDatabase.removeEventListener(this);   //  ??

        Map<String, Object> userUpdateMap = new HashMap<>();
        userUpdateMap.put("ad", etAd.getText().toString());
        userUpdateMap.put("soyad", etSoyad.getText().toString());
        userUpdateMap.put("profilResmi", download_uri.toString());

        if(mDatabase == null){
            Log.d("ProfilDuzenleme", "mDatabase null");
            return;
        }

        mDatabase.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("ProfilDuzenleme", "task succ");
                    profilProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Güncelleme Başarı İle Tamamlandı.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ProfilDuzenleme", "exception: " + e.getMessage());
                if(profilProgress.isShowing())
                    profilProgress.dismiss();
            }
        });




    }


    private void adSoyadGuncelle() {

        mDatabase.removeEventListener(this);   //  ??

        Map<String, Object> userUpdateMap = new HashMap<>();
        userUpdateMap.put("ad", etAd.getText().toString());
        userUpdateMap.put("soyad", etSoyad.getText().toString());


        if(mDatabase == null){
            Log.d("ProfilDuzenleme", "mDatabase null");
            return;
        }

        mDatabase.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("ProfilDuzenleme", "task succ");
                    profilProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Profil Güncelleme Başarı İle Tamamlandı.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ProfilDuzenleme", "exception: " + e.getMessage());
                if(profilProgress.isShowing())
                    profilProgress.dismiss();
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
                kontrol = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String gonulluAd = dataSnapshot.child("ad").getValue().toString();
        String gonulluSoyad = dataSnapshot.child("soyad").getValue().toString();
        String gonulluEmail = dataSnapshot.child("email").getValue().toString();
        String profilResmi = dataSnapshot.child("profilResmi").getValue().toString();
        imageUri = Uri.parse(profilResmi);


    RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.login);
        Glide.with(ProfilDuzenlemeEkrani.this).setDefaultRequestOptions(requestOptions).load(imageUri).into(profilResmii);

        etAd.setText(gonulluAd);
        etSoyad.setText(gonulluSoyad);
        etEmail.setText(gonulluEmail);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}

