package com.example.librarymanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        sharedPreferences = getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("LoginStatus",false)) {

                    Intent i = new Intent(SplashActivity.this, BooksAvailable_COE.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent j = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(j);
                    finish();
                }

            }
        },2500);

    }
}
