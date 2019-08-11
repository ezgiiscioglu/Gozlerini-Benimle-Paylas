package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.myapplication.recyclerview.videolarAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Yardim extends Fragment implements videolarAdapter.MyListener {

  /*  private String mFileName=null;
    private static final String LOG_TAG="Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private MediaController mController;
    private VideoView gelenVideo;
    private Uri videouri;
*/

    private videolarAdapter videolarAdapterr;
    private RecyclerView recyclerView;
    private ArrayList<GormeEngelliVideolar> gormeEngelliVideolars;
    private DatabaseReference ref;

    Button yardimet;

    public static Yardim newInstance() {
        return new Yardim();
    }



    public View onCreateView(@NonNull LayoutInflater inflater,  // Fragment dan extends ettiğimiz için OnCreateView metodu kullanılır.
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yardim, container, false);

        //boş arraylist tanımladım, bu farklıydı az önceki kodda. nasıl çalıştığını anlamış değilim o yüzden kendi
        //bildiğim şekilde yaptım :D
        gormeEngelliVideolars = new ArrayList<>();

        //burda adapter classına gönderiyoruz boş arrayi, gerisi zaten aynıydı, diğer parametreler.
        videolarAdapterr = new videolarAdapter((AppCompatActivity) getActivity(), this, gormeEngelliVideolars);  // listener 'ı  implement etmek için;  implements videolarAdapter.MyListener dedik.

        //bu satırda da klasik database referansımızı alıyoruz. aşağıda döngüye gircez
        ref = FirebaseDatabase.getInstance().getReference().child("GormeEngelliKullanicilar");

        recyclerView = view.findViewById(R.id.videolarRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // Aynı sayfada 2 video yan yana göstermekm için

        if (isAdded()) {   // Fragment aktiviteye eklendi mi?
            recyclerView.setAdapter(videolarAdapterr);
        }

        //döngü başlangıcı
        getGormeEngelliVideolars();

        return view;
    }

    /*



     */
    private void getGormeEngelliVideolars() {

        //şimdi burda GormeEngelliKullanicilar referansına listener ekliyoruz, bu tek seferlik çalışıyo,
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //bu methodda datasnapshot, ben bunu log ettirmiştim, GormeEngelliKullanicilar stringini döndürüyor, getKey metodu
                //getValue ise komple o kısmı alıyor, yani GormeEngelliKullanicilar içinde ne varsa.
                //Map şeklinde, Map<Key, Value> key burada databasedeki key, random string olan, Value (object) olan ise içindeki veriler
                collectURLs((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void collectURLs(Map<String, Object> users) {

        //o işaretlediğim kısım bi map, random string key, altındakiler value.
        // burda da foreach döngüsü var, sırayla bütün entryleri dolaşıyor.
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Map.Entry dediği yer ise kırmızı kısım.
            Map singleUser = (Map) entry.getValue();

            //burası zaten basit, video stringini çekiyoz.
            GormeEngelliVideolar video = new GormeEngelliVideolar(singleUser.get("video").toString(), entry.getKey());
            Log.d("Yardim", "USERID: " + entry.getKey());
            //burda da bi if koydum galiba default olarak, defaultVideo yazdırıyosunuz, MediaPlayer biraz salak olduğu için
            //bunu url sanıp açmaya çalışıyo, o yüzden deeğer defaultVideodan farklıysa, ArrayListe ekliyor.
            if(!video.getVideoUrl().equals("defaultVideo"))
                gormeEngelliVideolars.add(video);
        }
        //bu satır, adapterı dürtüyor. ArrayList değişti, kendine çeki düzen ver falan gibi bi şey demek bu :D
        videolarAdapterr.notifyDataSetChanged();
    }

    @Override
    public void MyListener(GormeEngelliVideolar gormeEngelliVideolar) {

    }


        /*

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

*/


}
