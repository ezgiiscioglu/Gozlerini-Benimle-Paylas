package com.example.lenovo.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;


public class Video extends AppCompatActivity implements ValueEventListener {
    private VideoView video;
    private MediaController mController;
    private Uri videouri;
    private String kullaniciId;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    String gelenUri;
    private static final int PICK_VIDEO_REQUEST = 101;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        video = (VideoView) findViewById(R.id.video);
        gelenUri = getIntent().getStringExtra("videoUri");
        Toast.makeText(getApplicationContext(), gelenUri, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();

        kullaniciId = mAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("GormeEngelliKullanicilar").child(kullaniciId);
        mDatabase.addValueEventListener(this);

        if (gelenUri != null) {
            videouri = Uri.parse(gelenUri);
            video.setVideoURI(videouri);

        }

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mController = new MediaController(Video.this);
                        video.setMediaController(mController);
                        mController.setAnchorView(video);
                    }
                });
            }
        });
        video.start();

    }

    public void videoYukle(View view) {
        if (videouri != null) {
            videouri = Uri.parse(gelenUri);
            final StorageReference kullaniciVideo = storageReference.child("GormeEngelliKullaniciVideolari").child(kullaniciId + ".mp4");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GormeEngelliKullaniciVideolari");
            reference.child(kullaniciId).child("video id").setValue(kullaniciId);
            UploadTask task = kullaniciVideo.putFile(videouri);
            Task<Uri> urlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Task Basarılı Degil.", Toast.LENGTH_SHORT).show();
                    }

                    return kullaniciVideo.getDownloadUrl();
                }
            }).addOnCompleteListener(Video.this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Video Başarı İle Kaydedildi.", Toast.LENGTH_SHORT).show();

                        Uri download_uri;

                        video.setVideoPath(videouri.getPath());
                        download_uri = task.getResult();

                        uriGuncelle(download_uri);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Video", "Failure: " + e.getMessage());
                }
            });
        } else {
            Toast.makeText(Video.this, "Yüklenecek video yok", Toast.LENGTH_LONG).show();
        }
    }

    private void uriGuncelle(Uri download_uri) {

        mDatabase.removeEventListener(this);   //  ??

        Map<String, Object> userUpdateMap = new HashMap<>();

        userUpdateMap.put("video", download_uri.toString());

        if (mDatabase == null) {
            Log.d("Video", "mDatabase null");
            return;
        }

        mDatabase.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    Toast.makeText(getApplicationContext(), "Video Başarı İle Kaydedildi.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Video", "exception: " + e.getMessage());

            }
        });


    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        /*
        String profilResmi = dataSnapshot.child("video").getValue().toString();
        videouri = Uri.parse(profilResmi);
        */

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }

        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), GormeEngelliKullanici.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}