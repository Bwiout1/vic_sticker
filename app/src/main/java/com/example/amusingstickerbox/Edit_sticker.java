package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.amusingstickerbox.adapter.Emoji_Adpater;
import com.example.amusingstickerbox.tools.Emoji_Source;
import com.example.amusingstickerbox.tools.Photo_Source;
import com.example.amusingstickerbox.tools.VerticalSpaceItemDecoration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Edit_sticker extends AppCompatActivity {

    /**
     //emoji编辑
     */
    LinearLayout emoji_show_view;
    List<Photo_Source> emoji_data = new ArrayList<>();
    Emoji_Adpater emoji_adpater;
    Emoji_Source emoji_source;
    RecyclerView emoji_recycle;
    ImageView pic_view;//emoji和图片的显示
    ImageView had_save;//保存按钮

    ImageView croped_view;

    /**
     //字体改变
     */
    EditText add_text;


    private Typeface currentFont;
    private Typeface font0;
    private Typeface font1;
    private Typeface font2;
    private Typeface font3;
    private Typeface font4;
    private Typeface font5;
    int i=0;
    boolean flag_chose_kinds = false;

    /**
     //字体颜色改变
     */
    int current_color = R.color.black;


    private int xDelta, yDelta;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sticker);

        TextView txt_text = findViewById(R.id.txt_text);
        TextView txt_emoji = findViewById(R.id.txt_emoji);
        add_text = findViewById(R.id.edit_text);
        emoji_show_view = findViewById(R.id.emoji_show);
        pic_view = findViewById(R.id.emoji_place);
        had_save = findViewById(R.id.had_save);
        croped_view = findViewById(R.id.picture_view);


        ImageView edit_back = findViewById(R.id.edit_back);
        edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         //保存编辑后的文件
         */
        had_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginSaveImage();
            }
        });

        //接收传来的图片
        /**
         //显示裁剪后的图片
         */
        Intent intent =getIntent();
        String croped_pic = intent.getDataString();
        if(croped_pic!=null){
            croped_view.setImageURI(Uri.parse(croped_pic));
            //设置裁剪后的图片可以拖动
            croped_view.setOnTouchListener(onTouchListener);
        }

        txt_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView emoji_cancel = findViewById(R.id.emoji_cancel);
                ImageView emoji_save = findViewById(R.id.emoji_done);
                emoji_show_view.setVisibility(View.VISIBLE);
                //Emoji表情的显示
                emoji_source = new Emoji_Source();
                emoji_adpater = new Emoji_Adpater(Edit_sticker.this);
                emoji_recycle = findViewById(R.id.emoji_view_recycle);

                emoji_recycle.setLayoutManager(new GridLayoutManager(Edit_sticker.this,5));
                for (int i = 0; i < emoji_source.emoji_id.length; i++) {
                    Photo_Source item = new Photo_Source();
                    item.setId(emoji_source.emoji_id[i]);
                    emoji_data.add(item);
                }
                emoji_adpater.SetData(emoji_data);
                emoji_recycle.setAdapter(emoji_adpater);
                int verticalSpacing = 130;
                emoji_recycle.addItemDecoration(new VerticalSpaceItemDecoration(verticalSpacing));

                emoji_adpater.setOnItemClickListener(new Emoji_Adpater.onItemViewClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        emoji_show_view.setVisibility(View.INVISIBLE);
                        pic_view.setImageResource(emoji_source.emoji_id[position]);
                        pic_view.setOnTouchListener(onTouchListener);
                    }
                });
            }
        });

        /**
         //改变文字字体和颜色
         */
        font0 = Typeface.createFromAsset(getAssets(),"font/imfell_itatic.ttf");
        font1 = Typeface.createFromAsset(getAssets(),"font/mango.ttf");
        font2 = Typeface.createFromAsset(getAssets(),"font/imfell_regular.ttf");
        font3 = Typeface.createFromAsset(getAssets(),"font/mario.ttf");
        font4 = Typeface.createFromAsset(getAssets(),"font/cheese.otf");
        font5 = Typeface.createFromAsset(getAssets(),"font/next_sunday.otf");
        currentFont = font0;
        txt_text.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                ConstraintLayout text_show_view = findViewById(R.id.text_chang_show);
                text_show_view.setVisibility(View.VISIBLE);

                /**
                 //修改字体
                 */
                TextView txt_chang_kinds = findViewById(R.id.txt_kinds);
                txt_chang_kinds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag_chose_kinds = true;
                        i++;
                        if(i%5==1){
                            currentFont = font1;
                            add_text.setTypeface(font1);
                        } else if (i%5==2) {
                            currentFont = font2;
                            add_text.setTypeface(font2);
                        } else if (i%5==3) {
                            currentFont = font3;
                            add_text.setTypeface(font3);
                        } else if (i%5==4) {
                            currentFont = font4;
                            add_text.setTypeface(font4);
                        } else if (i%5==0) {
                            currentFont = font5;
                            add_text.setTypeface(font5);
                        }

                    }
                });
                /**
                 //修改文字颜色
                 */
                RadioButton txt_black = findViewById(R.id.black);
                RadioButton txt_red = findViewById(R.id.red);
                RadioButton txt_yellow = findViewById(R.id.yellow);
                RadioButton txt_green = findViewById(R.id.green);
                RadioButton txt_blue = findViewById(R.id.blue);
                RadioButton txt_purple = findViewById(R.id.purple);
                RadioButton txt_anhong = findViewById(R.id.anhong);
                RadioButton txt_slan = findViewById(R.id.slan);

                txt_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color = Color.BLACK;
                        add_text.setTextColor(current_color);
                    }
                });
                txt_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=Color.RED;
                        add_text.setTextColor(current_color);
                    }
                });
                txt_yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=R.color.yellow;
                        add_text.setTextColor(ContextCompat.getColor(Edit_sticker.this, current_color));
                    }
                });
                txt_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=Color.GREEN;
                        add_text.setTextColor(current_color);
                    }
                });
                txt_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=R.color.blue;
                        add_text.setTextColor(ContextCompat.getColor(Edit_sticker.this, current_color));
                    }
                });
                txt_purple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=R.color.purple;
                        add_text.setTextColor(ContextCompat.getColor(Edit_sticker.this, current_color));
                    }
                });
                txt_anhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=R.color.anhong;
                        add_text.setTextColor(ContextCompat.getColor(Edit_sticker.this, current_color));
                    }
                });
                txt_slan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_color=R.color.slan;
                        add_text.setTextColor(ContextCompat.getColor(Edit_sticker.this, current_color));
                    }
                });
                /**
                 //保存修改
                 */
                ImageView chosed_kinds = findViewById(R.id.chosed_kinds);
                chosed_kinds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag_chose_kinds =false;
                        text_show_view.setVisibility(View.GONE);
                    }
                });


            }
        });

        /**
         //设置Edit Text可以拖动并且松手后弹出键盘
         */
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 100;
        layoutParams.topMargin = 100;
        add_text.setLayoutParams(layoutParams);
        add_text.setFocusable(true);
        add_text.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = x - lastX;
                        int deltaY = y - lastY;

                        // 更新控件的位置
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                        layoutParams.leftMargin += deltaX;
                        layoutParams.topMargin += deltaY;
                        v.setLayoutParams(layoutParams);

                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        // 弹出键盘
                        add_text.requestFocus();
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                        break;
                }
                return true;
            }
        });
    }
    /**
     //移动图片、emoji
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    xDelta = x - lParams.leftMargin;
                    yDelta = y - lParams.topMargin;
                    break;

                case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = x - xDelta;
                    layoutParams.topMargin = y - yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            return true;
        }
    };


    /**
     //保存图片和文字的函数
     */
    private void beginSaveImage() {
        add_text.setBackground(null);
        View view = findViewById(R.id.final_save);
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

        Intent intent = new Intent(Edit_sticker.this, Had_save_view.class);
        intent.setData(imageUri);
        startActivity(intent);
    }






}