
package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Yardim extends Fragment {
    private Button mikrofon;
    private MediaRecorder mRecorder;
    private String mFileName=null;
    private static final String LOG_TAG="Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private MediaController mController;
    private VideoView gelenVideo;
    private Uri videouri;

    public View onCreateView(@NonNull LayoutInflater inflater,  // Fragment dan extends ettiğimiz için OnCreateView metodu kullanılır.
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yardim, container, false);



        mikrofon = (Button) view.findViewById(R.id.mikrofon);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(getContext());
        gelenVideo = (VideoView) view.findViewById(R.id.videoGoster);
        videouri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/androidapp-b3cf1.appspot.com/o/uploads%2Fvideos%2FVID-20190316-WA0003.mp4?alt=media&token=a853b4a6-1e43-43de-a184-a2130acdbf6c");
        gelenVideo.setVideoURI(videouri);
        gelenVideo.requestFocus();

        gelenVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mController = new MediaController(getContext());
                        gelenVideo.setMediaController(mController);
                        mController.setAnchorView(gelenVideo);
                    }
                });
            }
        });
        gelenVideo.start();




        return  view;
    }




}

