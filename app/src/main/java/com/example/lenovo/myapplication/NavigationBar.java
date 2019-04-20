package com.example.lenovo.myapplication;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.lenovo.myapplication.AyarlarEkrani;
import com.example.lenovo.myapplication.GonulluKullaniciEkrani;
import com.example.lenovo.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NavigationBar extends AppCompatActivity {

    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar_page);

        navigation=(BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navigationTiklama);  //  Navigation daki  herhangi bir Item secildiğinde

        FragmentTransaction fragmentCagir=getSupportFragmentManager().beginTransaction();
        fragmentCagir.replace(R.id.burayaCagir,new GonulluKullaniciEkrani());   // Sayfa ilk yüklendiğinde  GonulluKullaniciEkrani;  default olarak gelir, yani sayfa 1.xml baslar.
        fragmentCagir.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationTiklama=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment secilenFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.anasayfa: //sayfa1 item i secildiyse
                            secilenFragment = new GonulluKullaniciEkrani();   // Secilen fragment sayfa 1 ise ,onu sayfa1 classına yonlendir.
                            break;
                        case R.id.yardim: //sayfa1 item i secildiyse
                            secilenFragment = new Yardim();   // Secilen fragment sayfa 1 ise ,onu sayfa1 classına yonlendir.
                            break;
                        case R.id.ayarlar:
                            secilenFragment = new AyarlarEkrani();   // Secilen fragment sayfa 2 ise ,onu sayfa2 classına yonlendir.
                            break;

                    }
                    FragmentTransaction fragmentCagir=getSupportFragmentManager().beginTransaction();
                    fragmentCagir.replace(R.id.burayaCagir,secilenFragment);   // Secilen fragment yerine  new sayfa3(); yasak default olarak sayfa 3 baslar.
                    fragmentCagir.commit();
                    return  true;


                }
            };

}
