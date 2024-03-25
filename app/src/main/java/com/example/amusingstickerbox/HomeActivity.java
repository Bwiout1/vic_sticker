package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amusingstickerbox.tools.DatabaseHelper;

public class HomeActivity extends AppCompatActivity {

    private static int REQUEST_PERMISSION_CODE = 1;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermission();

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        createStickerTable();

        deleteAllData();

        LinearLayout lay_addd = findViewById(R.id.home_add_sticker);
        LinearLayout lay_my = findViewById(R.id.home_my_work);

        lay_addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Add_Sticker.class);
                startActivity(intent);
            }
        });

        lay_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyWork.class);
                startActivity(intent);
            }
        });
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, getString(R.string.please_open_the_relevant_permissions_otherwise_the_application_cannot_be_used_normally), Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);

        } else {
            Toast.makeText(this, getString(R.string.authorization_succeeded), Toast.LENGTH_SHORT).show();
            Log.e("aaaaa", getString(R.string.checkPermission_Authorization_succeeded));
        }
    }

    private void createStickerTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS sticker (id INTEGER PRIMARY KEY AUTOINCREMENT, pack TEXT,owner TEXT,source TEXT)";
        db.execSQL(createTableQuery);
    }

    public void deleteAllData() {
        String deletetable = "delete from sticker ";
        db.execSQL(deletetable);
        db.close();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomeActivity.this,ExitActivity.class);
        startActivity(intent);
    }
}