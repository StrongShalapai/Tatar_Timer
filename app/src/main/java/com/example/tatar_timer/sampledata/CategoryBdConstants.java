package com.example.tatar_timer.sampledata;

public class CategoryBdConstants {
    public static final String TABLE_NAME = "categoryList";
    public static final String _ID = "_id";
    public static final String CATEGORYNAME = "name";

    //При добавлении стоблцов, обновить версию бд
    public static final String DB_NAME = "categoryDb.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, "
                    + CATEGORYNAME + " TEXT" + ")";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
