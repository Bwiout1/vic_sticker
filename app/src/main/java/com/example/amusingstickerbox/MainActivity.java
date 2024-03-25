package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        long startTime = SystemClock.elapsedRealtime();
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //这里打开app的首页
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }, Math.max(0, 5000 - elapsedTime)); //不足5秒则等待5秒
    }
}