package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUser,etPass;
    RelativeLayout rlt;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etUser= (EditText) findViewById(R.id.etUser);
        etPass= (EditText) findViewById(R.id.etPass);

        rlt=findViewById(R.id.rlt);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt:
                if(etUser.getText().toString().equalsIgnoreCase("admin") && etPass.getText().toString().equals("1")) {
                    Intent intent=new Intent(context,HomeActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(context,"Kullanıcı adı veya parola yanlış" , Toast.LENGTH_SHORT).show();
                }
        }
    }
}
