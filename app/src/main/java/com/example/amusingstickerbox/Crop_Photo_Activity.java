package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.amusingstickerbox.self_made.CircleCropView;
import com.example.amusingstickerbox.self_made.CropPhotoView;
import com.example.amusingstickerbox.self_made.CustomView;

import java.io.IOException;
import java.io.OutputStream;

public class Crop_Photo_Activity extends AppCompatActivity {

    ImageView img_chosed;
    FrameLayout save_view;
    CropPhotoView squareview;
    CustomView fingerview;
    CircleCropView ovalview;
    public static Bitmap changed_pic;
    ImageView save_crop_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_photo);

        img_chosed = findViewById(R.id.chosed_pic);

        Intent intent = getIntent();
        init_view(intent,img_chosed);

        save_view = findViewById(R.id.photo_crop_layout);
        squareview = findViewById(R.id.square_crop);
        fingerview = findViewById(R.id.manual_view);
        ovalview = findViewById(R.id.oval_crop);
        save_crop_view = findViewById(R.id.crop_done);


        LinearLayout wake_finger = findViewById(R.id.wake_finger_cut);
        LinearLayout wake_crop = findViewById(R.id.wake_square_cut);
        LinearLayout wake_oval = findViewById(R.id.wake_circle_cut);
        LinearLayout crop_tab = findViewById(R.id.crop_tab);
        ConstraintLayout finger_detail = findViewById(R.id.finger_cut_view);
        ConstraintLayout square_detail = findViewById(R.id.square_cut_view);
        ConstraintLayout oval_detail = findViewById(R.id.oval_cut_view);

        //显示自由裁剪框
        wake_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_tab.setVisibility(View.INVISIBLE);
                finger_detail.setVisibility(View.VISIBLE);
                fingerview.setVisibility(View.VISIBLE);
                fingerview.setBitmap(((BitmapDrawable) img_chosed.getDrawable()).getBitmap());


                ImageView save_finger_view = findViewById(R.id.finger_save);
                ImageView cancel_finger_view = findViewById(R.id.finger_back);
                cancel_finger_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finger_detail.setVisibility(View.GONE);
                        fingerview.setVisibility(View.GONE);
                        crop_tab.setVisibility(View.VISIBLE);
                    }
                });

                save_finger_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fingerview.setBitmap(((BitmapDrawable) img_chosed.getDrawable()).getBitmap());
                        finger_detail.setVisibility(View.GONE);
                        fingerview.setVisibility(View.GONE);
                        crop_tab.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        //显示方形裁剪框
        wake_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_tab.setVisibility(View.INVISIBLE);
                square_detail.setVisibility(View.VISIBLE);
                squareview.setVisibility(View.VISIBLE);
                Begin_CropView(img_chosed);
                //保存裁剪后的图片
                ImageView img_square_save = findViewById(R.id.square_save);
                img_square_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        changed_pic = squareview.getBitmap(getApplicationContext(),0,0);
                        square_detail.setVisibility(View.GONE);
                        squareview.setVisibility(View.GONE);
                        img_chosed.setImageBitmap(changed_pic);
                        crop_tab.setVisibility(View.VISIBLE);
                    }
                });
                //不保存裁剪后的图片
                ImageView img_square_back = findViewById(R.id.square_back);
                img_square_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        square_detail.setVisibility(View.GONE);
                        squareview.setVisibility(View.GONE);
                        crop_tab.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        //显示圆形裁剪框
        wake_oval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oval_detail.setVisibility(View.VISIBLE);
                ovalview.setVisibility(View.VISIBLE);
                ovalview.setPicture(((BitmapDrawable) img_chosed.getDrawable()).getBitmap());
                crop_tab.setVisibility(View.INVISIBLE);
                ovalview.setCircleRadius(300);

                //保存裁剪后的图片
                ImageView img_square_save = findViewById(R.id.oval_save);
                img_square_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap bitmap1 = ovalview.cropPicture();
                        img_chosed.setImageBitmap(bitmap1);
                        oval_detail.setVisibility(View.GONE);
                        ovalview.setVisibility(View.GONE);
                        crop_tab.setVisibility(View.VISIBLE);

                    }
                });
                //不保存裁剪后的图片
                ImageView img_square_back = findViewById(R.id.oval_back);
                img_square_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oval_detail.setVisibility(View.GONE);
                        ovalview.setVisibility(View.GONE);
                        crop_tab.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        //保存图片并发送
        save_crop_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_view.setBackground(null);
                beginSaveImage();
            }
        });

    }

    /**
     *
     * 显示图片
     */
    public  void init_view(Intent intent, ImageView imageView){
        String path = intent.getStringExtra("Chosed_Pic");
        imageView.setImageURI(Uri.parse(path));

    }
    /**
     *
     * 自由裁剪保存图片
     */
    public void Begin_CropView(ImageView show){
        Bitmap bitmap = ((BitmapDrawable)show.getDrawable()).getBitmap();
        squareview.setBitmap(bitmap,1,1);
    }

    /**
     *
     * 保存图片
     */
    private void beginSaveImage() {
        save_view.setBackground(null);
        View view = findViewById(R.id.photo_crop_layout);
        view.setBackground(null);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "file_name.png");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream outputStream = resolver.openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 最后，发送广播通知系统更新相册
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
        }

        Intent intent = new Intent(Crop_Photo_Activity.this, Edit_sticker.class);
        intent.setData(imageUri);
        startActivity(intent);
    }

}
