package com.example.lenovo.myapplication;

import android.content.Intent;
import android.database.Cursor;
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
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;

public class GormeEngelliKullaniciEkrani extends AppCompatActivity {
    private Button btnVideoBaslat,btnVideoServer,btnDinle;
    private VideoView video;
    private static final int PICK_VIDEO_REQUEST=101;
    private Uri videouri;
    private MediaController mController;
    private StorageReference mStorageRef;
    private String videoname;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_kullanici_ekran);

        btnVideoBaslat = (Button) findViewById(R.id.btnVideoBaslat);
        btnDinle=(Button) findViewById(R.id.btnDinle);
        btnVideoServer= (Button) findViewById(R.id.btnVideoServer);
        mStorageRef=FirebaseStorage.getInstance().getReference("uploads").child("videos");
        video=(VideoView) findViewById(R.id.video);

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
            videoname=getFileName(videouri);
        }
    }

    /* public void videoServerYukle(View view) {
        mStorageRef.child(videoname).putFile(videouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Toast.makeText(GormeEngelliKullaniciEkrani.this, "Video yüklendi"+downloadUrl.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(GormeEngelliKullaniciEkrani.this, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    */
    public void videoServerYukle(View view) {
        if(videouri!=null) {
            UploadTask uploadTask=mStorageRef.child(videoname).putFile(videouri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GormeEngelliKullaniciEkrani.this, "Video yüklenemedi"+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(GormeEngelliKullaniciEkrani.this, "Video yüklendi",Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(GormeEngelliKullaniciEkrani.this, "Yüklenecek video yok",Toast.LENGTH_LONG).show();
        }
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
        //i.setType("video/*");
        //i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra(MediaStore.EXTRA_OUTPUT,videouri);
        startActivityForResult(i,PICK_VIDEO_REQUEST);
        //Toast.makeText(GormeEngelliKullaniciEkrani.this, "Video seçiniz.", Toast.LENGTH_SHORT).show();

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
