package com.example.lenovo.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class GormeEngelliKullanici extends AppCompatActivity  {

    private Button videoCek, sesDinle;
    public Uri videouri;
    private String uri, id;
    private MediaPlayer mediaPlayer;
    private static final int PICK_VIDEO_REQUEST=101;
    private FirebaseAuth firebaseAuth;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorme_engelli_kullanici);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        videoCek = (Button) findViewById(R.id.videoCek);
        sesDinle = findViewById(R.id.sesDinle);
        firebaseAuth = FirebaseAuth.getInstance();

        id = firebaseAuth.getCurrentUser().getUid();  // Engelli id si
        Log.d("GormeEngelliKullanici", id);

        mediaPlayer = new MediaPlayer();


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();

            }
        });

        sesDinle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sesDinle.setEnabled(false);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GormeEngelliKullanicilar");

                ref.child(id).child("SesKaydi").addListenerForSingleValueEvent(new ValueEventListener() {  // id altındaki ses kaydı bilgileri
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        getAudio((Map<String, Object>) dataSnapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        sesDinle.setEnabled(true);
                    }
                });

            }
        });

    }

    private void getAudio(Map<String, Object> value) {

        if(value == null || value.size() < 1)            // map te eleman yoksa
            return;

        for (Map.Entry<String, Object> entry : value.entrySet()) {

            Map singleUser = (Map) entry.getValue();

            String url = "";
            url = singleUser.get("GonderilenSes").toString();
            Log.d("Video", "Ses URL: " + url);


            if(url != null){         // gonderilen ses url si varsa çal dedin
                sesiCal(url);
            }

        }
    }

    private void sesiCal(String url) {
        Log.d("GormeEngelliKull", "sesiCal");

        if(mediaPlayer == null)
            mediaPlayer = new MediaPlayer();          // null ise, mediaplayer oluştur

        try{

            if(!mediaPlayer.isPlaying()){                        // eğer dosya oynatılıyorsa yeni bir şey çalmaması için
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

        } catch (IOException e){
            Log.d("GormeEngelliKullanici", "IOException: " + e.getMessage());
        } catch (IllegalStateException e){
            Log.d("GormeEngelliKullanici", "IllegalState: " + e.getMessage());
        } catch (Exception e){
            Log.d("GormeEngelliKullanici", "Exception: " + e.getMessage());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  // ? Bunu kim yazdı hatırlamıyorum ben veya sen ama anlaamadım napıyoruz
        super.onActivityResult(requestCode, resultCode, data);
        //bende böyle değil hiç böyle alışmamışım :D

        // şöyle bi method var, bu mesela video kaydediyosun ya, bu activityden çıkıp, kameranın olduğu aktiviteye geçiyor
        //o aktiviteyi kapatıp bu aktivitiye geri döndüğün zaman, bu metod çalışıyor,
        //burda da request, result, ve data diye 3 parametre var, burda mesela sen kamera aktivitisinden geldiğini
        //anlayabilesin diye requestCode var, detaya girmiyorum
        //resultCode ise başarılı başarısız olduğunu belirtiyor
        //data ise ordan gelen intent
        //mesela burda data.getDate ile kaydedilen videonun pathi çekilmiş. OHA ANLATINCA COK MANTIKLI :d TAMAMDIR

        if(requestCode==PICK_VIDEO_REQUEST && resultCode==RESULT_OK && data!=null) {
            videouri=data.getData();
            uri=videouri.toString();

        }
    }

    public void videoCek(View view) {
        if(videouri ==null ) {
            Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            i.putExtra(MediaStore.EXTRA_OUTPUT, videouri);
            startActivityForResult(i, PICK_VIDEO_REQUEST);

        }
        else{
            Intent intent = new Intent(getApplicationContext(),Video.class);
            intent.putExtra("videoUri",uri);
            startActivity(intent);
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), OturumAcGormeEngelli.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
