package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AyarlarEkrani extends Fragment {
    ListView listeBirDegiskeni,listeIkiDegiskeni,listeUcDegiskeni;

    String liste1[]={"                        Profil Ayarları "};
    String liste2[]={"                          Facebook ","                            Twitter ","                          Instagram "};
    String liste3[]={"                     Betül SAKARYA ","                      Cansu ATALAY ","                 Ayşe Ezgi İŞÇİOĞLU "};
    public View onCreateView(@NonNull LayoutInflater inflater,  // Fragment dan extends ettiğimiz için OnCreateView metodu kullanılır.
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ayarlar, container, false);

        listeBirDegiskeni=(ListView) view.findViewById(R.id.liste1);
        listeIkiDegiskeni=(ListView) view.findViewById(R.id.liste2);
        listeUcDegiskeni=(ListView) view.findViewById(R.id.liste3);


        listeBirDegiskeni.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,liste1));

        listeBirDegiskeni.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // Listview 'in herhangi bir item ına tıklandığında yapılacaklar
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getContext(),ProfilDuzenlemeEkrani.class);
                startActivity(i);
            }
        });

        listeIkiDegiskeni.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,liste2));

        listeIkiDegiskeni.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // Listview 'in herhangi bir item ına tıklandığında yapılacaklar
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sosyalMedyaSecim=listeIkiDegiskeni.getItemAtPosition(position).toString();
                if(sosyalMedyaSecim==liste2[0]){

                    Uri facebookLink = Uri.parse("https://www.facebook.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, facebookLink);
                    startActivity(intent);

                }
                else if(sosyalMedyaSecim==liste2[1]){

                    Uri twitterLink = Uri.parse("https://twitter.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, twitterLink);
                    startActivity(intent);

                }
                else{

                    Uri instagramLink = Uri.parse("https://instagram.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, instagramLink);
                    startActivity(intent);


                }
            }
        });

        listeUcDegiskeni.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,liste3));

        listeUcDegiskeni.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // Listview 'in herhangi bir item ına tıklandığında yapılacaklar
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sosyalMedyaSecim=listeUcDegiskeni.getItemAtPosition(position).toString();
                if(sosyalMedyaSecim==liste3[0]){

                    Uri linkedinLink = Uri.parse("https://linkedin.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, linkedinLink);
                    startActivity(intent);

                }
                else if(sosyalMedyaSecim==liste3[1]){

                    Uri linkedinLink = Uri.parse("https://linkedin.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, linkedinLink);
                    startActivity(intent);

                }
                else{

                    Uri linkedinLink = Uri.parse("https://linkedin.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, linkedinLink);
                    startActivity(intent);


                }
            }
        });

        return view;
    }
}