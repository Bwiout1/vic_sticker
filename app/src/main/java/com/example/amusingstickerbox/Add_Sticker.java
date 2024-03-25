package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.amusingstickerbox.adapter.Create_Adapter;
import com.example.amusingstickerbox.tools.Photo_Source;

import java.util.ArrayList;
import java.util.List;

public class Add_Sticker extends AppCompatActivity {

    List<Photo_Source> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sticker);

        ImageView back =  findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView add_re = findViewById(R.id.add_recycle);
        Create_Adapter create_adapter = new Create_Adapter(this);

        add_re.setLayoutManager(new GridLayoutManager(this,2));
        for (int i= 1;i<31;i++){
            Photo_Source item = new Photo_Source();
            item.setId(i);
            data.add(item);
        }
        create_adapter.setData(data);
        add_re.setAdapter(create_adapter);
        create_adapter.setOnItemClickListener(new Create_Adapter.onItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Add_Sticker.this, Chose_Picture.class);
                startActivity(intent);
            }
        });


    }
}