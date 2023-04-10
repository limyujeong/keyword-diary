package com.cookandroid.keyworddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1.5초의 딜레이 발생 후 MainActivity 로 이동
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // MainActivity 로 이동하는 구간
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);    // Intent 의 대표적인 기능은 액티비티(화면) 간의 이동
                startActivity(mainIntent);
                finish();
            }
        }, 1500);   // 딜레이하려는 초 (1000밀리초 = 1초)
    }
}