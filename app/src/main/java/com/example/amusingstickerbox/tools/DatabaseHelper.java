package com.example.amusingstickerbox.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sticker.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表格
        String createTableQuery = "CREATE TABLE IF NOT EXISTS sticker (id INTEGER PRIMARY KEY AUTOINCREMENT, pack TEXT,owner TEXT,source TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库升级时的操作
        String dropTableQuery = "DROP TABLE IF EXISTS sticker";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
}