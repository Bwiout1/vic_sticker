package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExitActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        TextView txt_yes = findViewById(R.id.out_yes);
        TextView txt_no = findViewById(R.id.out_no);

        txt_yes.setOnClickListener(this::onClick);
        txt_no.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.out_yes){
            System.exit(0);
        } else if (v.getId()==R.id.out_no) {
            onBackPressed();
        }

    }
}