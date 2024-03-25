package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amusingstickerbox.adapter.MyPhotoAdapter;
import com.example.amusingstickerbox.tools.DatabaseHelper;
import com.example.amusingstickerbox.tools.MyPhotoBean;

import java.util.ArrayList;
import java.util.List;

public class MyWork extends AppCompatActivity {

    public static List<MyPhotoBean> data = new ArrayList<>();

    private DatabaseHelper databaseHelper;


    public RecyclerView recyclerView;

    public MyPhotoAdapter my_work_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        ImageView my_edit = findViewById(R.id.to_edit);
        my_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageView my_work = findViewById(R.id.to_my_work);
        my_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyWork.this,"您已经在当前界面",Toast.LENGTH_LONG);
            }
        });


        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        recyclerView = findViewById(R.id.my_work_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        my_work_adapter = new MyPhotoAdapter(this);

        Cursor cursor = db.rawQuery("SELECT * FROM sticker", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String pack = cursor.getString(cursor.getColumnIndex("pack"));
                @SuppressLint("Range") String owner = cursor.getString(cursor.getColumnIndex("owner"));
                @SuppressLint("Range") String source = cursor.getString(cursor.getColumnIndex("source"));
                MyPhotoBean item = new MyPhotoBean(id,pack,owner,source);
                data.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        databaseHelper.close();
        my_work_adapter.notifyDataSetChanged();
        my_work_adapter.setData(data);
        recyclerView.setAdapter(my_work_adapter);
        my_work_adapter.setOnItemClickListener(new MyPhotoAdapter.onItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyWork.this, Edit_sticker.class);

                startActivity(intent);
            }
        });

    }
    protected void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }



}