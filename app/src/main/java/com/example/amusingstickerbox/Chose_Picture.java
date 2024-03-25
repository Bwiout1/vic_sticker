package com.example.amusingstickerbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amusingstickerbox.adapter.Gallery_Adapter;
import com.example.amusingstickerbox.tools.Get_Local_album;
import com.example.amusingstickerbox.tools.Photo_Source;

import java.util.ArrayList;
import java.util.List;

public class Chose_Picture extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static int REQUEST_PERMISSION_CODE = 1;

    private List<String> photoList = new ArrayList<>();
    List<Photo_Source> imagesource = new ArrayList<>();

    public static List<String> localist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_picture);

        //checkPermission();
        GetLocal();
        ImageView img_back = findViewById(R.id.chose_pic_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayout start_edit = findViewById(R.id.start_edit);
        start_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chose_Picture.this,Edit_sticker.class);
                startActivity(intent);
            }
        });
        checkPermission();

        RecyclerView my_gall_recycle = findViewById(R.id.my_gallery_recycle);
//        Get_Local_album getLocal = new Get_Local_album();
//        photoList = getLocal.getLocalAlbumPhotos(this);
        photoList = localist;
        my_gall_recycle.setLayoutManager(new GridLayoutManager(this,3));
        Gallery_Adapter gallery_adapter = new Gallery_Adapter(this);
        Can_show();
        gallery_adapter.setData(imagesource);
        my_gall_recycle.setAdapter(gallery_adapter);
        gallery_adapter.setListener(new Gallery_Adapter.onItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                String path = imagesource.get(position).getPath();
                Intent intent1 = new Intent(Chose_Picture.this, Crop_Photo_Activity.class);
                intent1.putExtra("Chosed_Pic",path);
                startActivity(intent1);
            }
        });

    }

    private void Can_show(){
        for(int i = 0;i<photoList.size();i++){
            Photo_Source item = new Photo_Source();
            item.setPath(photoList.get(i));
            imagesource.add(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkPermission();

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            String imagePath = getRealPathFromURI(selectedImageUri);
            // 在这里可以使用获取到的图片路径进行后续操作
            // 例如显示图片到ImageView

        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        checkPermission();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
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

    private void GetLocal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
            String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
            String[] selectionArgs = {"image/jpeg"};
            String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    //使用获取到的uri进行操作
                    localist.add(String.valueOf(imageUri));
                }
                cursor.close();
            }
        }else {
            Get_Local_album getLocal = new Get_Local_album();
            localist = getLocal.getLocalAlbumPhotos(this);
        }
    }
}