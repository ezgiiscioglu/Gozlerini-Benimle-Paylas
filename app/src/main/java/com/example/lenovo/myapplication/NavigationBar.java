package com.example.lenovo.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lenovo.myapplication.AyarlarEkrani;
import com.example.lenovo.myapplication.GonulluKullaniciEkrani;
import com.example.lenovo.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NavigationBar extends AppCompatActivity {

    FirebaseAuth auth;

    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth=FirebaseAuth.getInstance();

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

    public void cikisyap(View view){

        auth.getInstance().signOut();
        Intent intent=new Intent(NavigationBar.this,GirisEkrani.class);
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), OturumAcGonullu.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
