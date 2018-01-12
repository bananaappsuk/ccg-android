package com.ccg.banana.ccg;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplashing();
    }
    private void startSplashing() {
        int SPLASH_TIME_OUT = 500; //3000
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchMainScreen();
            }
        }, SPLASH_TIME_OUT);
    }
    private void launchMainScreen() {
        Intent intent = new Intent(Splash.this, Login.class);
        startActivity(intent);
        finish();

    }
}
