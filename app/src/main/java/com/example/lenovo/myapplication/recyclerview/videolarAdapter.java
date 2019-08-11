package com.example.lenovo.myapplication.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.myapplication.GormeEngelliVideolar;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.YardimEt;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class videolarAdapter extends RecyclerView.Adapter<videolarViewHolder> implements View.OnClickListener {

    private ArrayList<GormeEngelliVideolar> gormeEngelliVideolars;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private MyListener myListener;
    private Button yardımEt;

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof GormeEngelliVideolar) {  // Gelen tag GormeEngelliVideolar'a aitse
            GormeEngelliVideolar gormeEngelliVideolar = (GormeEngelliVideolar) v.getTag(); // videolarViewHolder 'de yaptıgımız etiketi geri alıyoruz. videoları getTag() ile etiketlemeye yarar.Bu sayede hangi video hangi etiket ile alınır bilebiliriz.
            myListener.MyListener(gormeEngelliVideolar); // tıklanan videoya ulasmıs oluyoruz
        }
    }

    //constructor değiştirdim, bu classtaki arraylisti nasıl aldığını bilmiyordum daha önceden
    //bildiğim şekilde güncelledim, constructorda arraylist geliyor, Yardim.java classından
    //orda populate ediyoz çünkü.
    public videolarAdapter(AppCompatActivity appCompatActivity, MyListener myListener, ArrayList<GormeEngelliVideolar> gormeEngelliVideolar) {
        this.appCompatActivity = appCompatActivity;
        this.myListener = myListener;
        layoutInflater = appCompatActivity.getLayoutInflater();
        this.gormeEngelliVideolars = gormeEngelliVideolar;
    }

    @NonNull
    @Override
    public videolarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // videolar_recyclerview_list ile  kendi recyclerview imizmi birbirine baglama
        View listview = layoutInflater.inflate(R.layout.videolar_recyclerview_list, parent, false);
        listview.setOnClickListener(this);
        yardımEt=listview.findViewById(R.id.yardimEt);

        return new videolarViewHolder(listview);
    }

    @Override
    public void onBindViewHolder(@NonNull final videolarViewHolder holder, final int position) { // (ViewHolder 'deki getVideos metodunu cağırma bölümü )ViewHolder ile adapter i bağladığımız yer
        holder.getVideos(appCompatActivity, gormeEngelliVideolars.get(position));

       final GormeEngelliVideolar gormeEngelliVideolar=gormeEngelliVideolars.get(position);

       yardımEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = gormeEngelliVideolars.get(position).getUserId();
                Intent ıntent=new Intent(appCompatActivity,YardimEt.class);
                ıntent.putExtra("idgonder",id);
                appCompatActivity.startActivity(ıntent);
            }
        });



    }

    @Override
    public int getItemCount() {  // ne kadar video varsa onları getirmesi için
        return gormeEngelliVideolars.size();
    }

    public interface MyListener {  // Bir videoya tıklandıgında ne olacagını gösteren metod (Kendi Listener ımızı olusturma)

        public void MyListener(GormeEngelliVideolar gormeEngelliVideolar);  // hangi videoya tıkladığımızı anlayabilmek için
    }
}
