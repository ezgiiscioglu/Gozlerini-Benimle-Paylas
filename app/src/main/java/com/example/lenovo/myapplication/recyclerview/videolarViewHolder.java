package com.example.lenovo.myapplication.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.lenovo.myapplication.GormeEngelliVideolar;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.YardimEt;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class videolarViewHolder extends RecyclerView.ViewHolder {

    VideoView videolar;
    ProgressBar progressBar;
    private MediaController mController;

    public videolarViewHolder(View itemView) {
        super(itemView);  // xml deki obje tanımlamalarını itemView. diyerek yapmaya yarar.

        videolar = (VideoView) itemView.findViewById(R.id.videolar);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    public void getVideos(final Context context, final GormeEngelliVideolar gormeEngelliVideolar) {

        itemView.setTag(gormeEngelliVideolar);  // itemView 'e gormeEngelliVideolar 'ı etiketliyoruz.
        //  videolar.setVideoURI(gormeEngelliVideolar.getVideoUrl());

        videolar.requestFocus();
        String videoUri = gormeEngelliVideolar.getVideoUrl();
        videolar.setVideoURI(Uri.parse(videoUri));
        videolar.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mController = new MediaController(context);
                        videolar.setMediaController(mController);
                        mController.setAnchorView(videolar);
                    }
                });


                progressBar.setVisibility(View.INVISIBLE);


            }
        });


        videolar.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                switch(what){

                    case MediaPlayer.MEDIA_ERROR_IO:
                        Log.d("videolarViewHolder", "io error");
                        break;
                    case MediaPlayer.MEDIA_ERROR_MALFORMED:
                        Log.d("videolarViewHolder", "malformed error");
                        break;
                    case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                        Log.d("videolarViewHolder", "uzun error");
                        break;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        Log.d("videolarViewHolder", "server died error");
                        break;
                    case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        Log.d("videolarViewHolder", "timed out error");
                        break;
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        Log.d("", "unknown error");
                        break;
                    default:
                        Log.d("videolarViewHolder", "error: " + what);


                }

                return false;
            }
        });


        videolar.start();

    }
}
