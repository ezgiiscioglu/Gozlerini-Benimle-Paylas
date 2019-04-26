package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GormeEngelliKullaniciEkrani extends AppCompatActivity  implements ValueEventListener {
    private Button btnVideoBaslat,btnVideoServer,btnDinle;
    private VideoView video;
    private static final int PICK_VIDEO_REQUEST=101;
    private Uri videouri;
    private MediaController mController;
    private StorageReference storageReference;

    private FirebaseAuth mAuth;

    private String kullaniciId;
    private DatabaseReference mDatabase;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_kullanici_ekran);

        btnVideoBaslat = (Button) findViewById(R.id.btnVideoBaslat);
        btnDinle=(Button) findViewById(R.id.btnDinle);
        btnVideoServer= (Button) findViewById(R.id.btnVideoServer);

        video=(VideoView) findViewById(R.id.video);

        mAuth = FirebaseAuth.getInstance();

        kullaniciId = mAuth.getCurrentUser().getUid();
        storageReference=FirebaseStorage.getInstance().getReference();
       mDatabase = FirebaseDatabase.getInstance().getReference().child("GormeEngelliKullanicilar").child(kullaniciId);
        mDatabase.addValueEventListener(this);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mController=new MediaController(GormeEngelliKullaniciEkrani.this);
                        video.setMediaController(mController);
                        mController.setAnchorView(video);
                    }
                });
            }
        });
        video.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_VIDEO_REQUEST && resultCode==RESULT_OK && data!=null) {
            videouri=data.getData();
            video.setVideoURI(videouri);

        }
    }


    public void videoServerYukle(View view) {
        if(videouri!=null) {

            final StorageReference kullaniciVideo = storageReference.child("GormeEngelliKullaniciVideolari").child(kullaniciId + ".mp4");
            UploadTask task=kullaniciVideo.putFile(videouri);
            Task<Uri> urlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Task Basarılı Degil.", Toast.LENGTH_SHORT).show();

                    }

                    return kullaniciVideo.getDownloadUrl();
                }
            }).addOnCompleteListener(GormeEngelliKullaniciEkrani.this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Video Başarı İle Kaydedildi.", Toast.LENGTH_SHORT).show();

                        Uri download_uri;

                        video.setVideoPath(videouri.getPath());
                        download_uri = task.getResult();

                        uriGuncelle(download_uri);
     }
                }
            });
        }
        else {
            Toast.makeText(GormeEngelliKullaniciEkrani.this, "Yüklenecek video yok",Toast.LENGTH_LONG).show();
        }
    }

    private void uriGuncelle(Uri download_uri) {

        mDatabase.removeEventListener(this);   //  ??

        Map<String, Object> userUpdateMap = new HashMap<>();

        userUpdateMap.put("video", download_uri.toString());

        if(mDatabase == null){
            Log.d("GonulluKullaniciEkrani", "mDatabase null");
            return;
        }

        mDatabase.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {


                    Toast.makeText(getApplicationContext(), "Video Başarı İle Kaydedildi.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GonulluKullaniciEkranie", "exception: " + e.getMessage());

            }
        });




    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        String profilResmi = dataSnapshot.child("video").getValue().toString();
        videouri = Uri.parse(profilResmi);



    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
    public void dinle(View view) {
        MediaPlayer mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/androidapp-b3cf1.appspot.com/o/Audio%2Fnew_audio.3gp?alt=media&token=b24d301e-0e66-4182-a060-eacc9a88f326");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void videoYukle(View view) {

        Intent i =new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        i.putExtra(MediaStore.EXTRA_OUTPUT,videouri);
        startActivityForResult(i,PICK_VIDEO_REQUEST);


    }
    public String getFileName(Uri uri) {
        String result=null;
        if(uri.getScheme().equals("content")) {
            Cursor cursor =getContentResolver().query(uri,null,null,null,null);
            try {
                if(cursor !=null && cursor.moveToFirst())  {
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if(result==null) {
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if(cut != -1) {
                result=result.substring(cut+1);
            }

        }
        return result;
    }

}