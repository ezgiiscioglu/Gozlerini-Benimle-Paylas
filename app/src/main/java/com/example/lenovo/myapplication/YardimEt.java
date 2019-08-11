package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class YardimEt extends AppCompatActivity {
    private ImageButton mikrofon;
    private MediaRecorder mRecorder;
    private String mFileName=null;
    private static final String LOG_TAG="Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private MediaController mController;
    private String kullaniciId;
    private FirebaseAuth mAuth;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yardimet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth=FirebaseAuth.getInstance();

        kullaniciId = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(YardimEt.this);
        mikrofon = (ImageButton) findViewById(R.id.mikrofon);

        mFileName=Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName+="/recorded_audio.3gp";

        mikrofon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    startRecording();

                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    stopRecording();
                    uploadAudio();
                    Toast.makeText(YardimEt.this, "Ses kaydı bitti.",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;


    }
    private void uploadAudio() {

        mProgress.setMessage("Ses kaydı yükleniyor...");
        mProgress.show();
        String randomName = UUID.randomUUID().toString();
        final StorageReference filePath = mStorage.child("Audio").child(randomName + ".3gp");
        
        final Uri uri= Uri.fromFile(new File(mFileName));
        UploadTask task = filePath.putFile(uri);   // BURDA HANİ FİLE URİ TUTUYODU YA SONRA SEN BİŞEYLER YAPTIN ONU STORAGE URL SİNE DÖNÜŞTÜRDÜN O NERDE
        //bilmiyorum şu an ben naptım :D HAHA TAMMA :d
        Task<Uri> uriTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Yükleme Basarılı Degil.", Toast.LENGTH_SHORT).show();
                    Log.d("YardimEt", "yüklenemedi");

                }                   
                mProgress.dismiss();
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(YardimEt.this,"Ses kaydı yüklendi...",Toast.LENGTH_LONG).show();
                    Log.d("YardimEt", "yüklendi");
                    mProgress.dismiss();

                    Uri download_uri;
                    download_uri = task.getResult();
                    uriKaydet(download_uri);
                    
                }
            }
        });

    }

    private void uriKaydet(Uri download_uri) {
        Log.d("YardimEt", "uriKaydet");

        Intent intent=getIntent();
        String id=intent.getStringExtra("idgonder");  // kişi id si
        UUID uuıd=UUID.randomUUID();
        String uuidstring=uuıd.toString();
        String url = download_uri.toString();
        Log.d("YardimEt", "ID: " + id);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("GormeEngelliKullanicilar");
        databaseReference.child(id).child("SesKaydi").child(uuidstring).child("GonderilenSes").setValue(url);
        databaseReference.child(id).child("SesKaydi").child(uuidstring).child("GonderenKisi").setValue(kullaniciId);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), NavigationBar.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
