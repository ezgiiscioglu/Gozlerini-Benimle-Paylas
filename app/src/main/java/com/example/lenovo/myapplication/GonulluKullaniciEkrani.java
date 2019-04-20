package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GonulluKullaniciEkrani extends Fragment {




    public View onCreateView(@NonNull LayoutInflater inflater,  // Fragment dan extends ettiğimiz için OnCreateView metodu kullanılır.
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gonullu_kullanici_ekran, container, false);


        return  view;

    }

}







