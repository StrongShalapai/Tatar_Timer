package com.example.tatar_timer.sampledata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoryBdHelper extends SQLiteOpenHelper {

    public void deleteDb(SQLiteDatabase db) {
        db.execSQL(CategoryBdConstants.SQL_DELETE_TABLE);//Удаляем прошлую ДБ
        onCreate(db);
    }

    public CategoryBdHelper(Context context) {
        super(context, CategoryBdConstants.DB_NAME, null, CategoryBdConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryBdConstants.SQL_CREATE_TABLE);//Создаем новое ДБ
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CategoryBdConstants.SQL_DELETE_TABLE);
        onCreate(db);
    }
}
