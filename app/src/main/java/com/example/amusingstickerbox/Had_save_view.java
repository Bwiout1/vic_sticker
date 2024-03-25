package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amusingstickerbox.tools.DatabaseHelper;
import com.example.amusingstickerbox.tools.MyPhotoBean;

public class Had_save_view extends AppCompatActivity {

    static  long id;

    private DatabaseHelper databaseHelper;

    EditText pack;
    EditText owner;
    TextView create;

    MyPhotoBean myPhotoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_had_save_view);

        Intent intent =getIntent();
        ImageView img = findViewById(R.id.com_img);
        img.setImageURI(intent.getData());


        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        id = DatabaseUtils.queryNumEntries(db, "sticker");

        create = findViewById(R.id.create_now);
        pack = findViewById(R.id.had_save_pack);
        owner = findViewById(R.id.had_save_owner);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pack_name = pack.getText().toString();
                String owner_name = owner.getText().toString();
                String pic_source = intent.getData().toString();

                myPhotoBean = new MyPhotoBean(id,pack_name,owner_name,pic_source);
                insertData(id,pack_name,owner_name,pic_source);
                Intent intent1 = new Intent(Had_save_view.this, MyWork.class);
                intent1.putExtra("pack",pack_name);
                intent1.putExtra("owner",owner_name);
                intent1.putExtra("source",pic_source);
                startActivity(intent1);

            }
        });


    }

    private void insertData(long id,String pack,String owner,String source) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("pack",pack);
        values.put("owner",owner);
        values.put("source",source);
        long result = db.insert("sticker", null, values);
        if (result == -1) {
            Toast.makeText(this, "插入数据失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}