package com.example.customer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customer.R;

public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                String logined = sharedPreferences.getString("logined", "default");
                if (logined.equals("true")) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                } else {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);

                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
