package com.example.stuacademics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splashscreen extends AppCompatActivity {
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Splashscreen.this,Login.class);
                startActivity(i);
                finish();
            }
        },3000);

    }
}