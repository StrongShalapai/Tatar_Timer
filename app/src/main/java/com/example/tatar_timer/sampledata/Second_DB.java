package com.example.tatar_timer.sampledata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class Second_DB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Second_DB";
    private static final String COL0 = "id";
    private static final String COL1 = "number";
    private static final String COL2 = "activity";
    private static final String COL3 = "time";
    private static final String COL4 = "hours";
    private static final String COL5 = "minutes";
    private static final String COL6 = "hours_end";
    private static final String COL7 = "minutes_end";
    private static final String TAG = "Logs";


    public Second_DB(Context context) {
        super(context,TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key autoincrement,"
                + COL1 + " INTEGER," + COL2 + " INTEGER," + COL3 + " INTEGER," + COL4 + " INTEGER," +
                COL5 + " INTEGER," + COL6 + " INTEGER," + COL7 + " INTEGER" +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    public boolean putData(int number, String activity, int time, int hours,
                           int minutes, int hours_end, int minutes_end){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, number);
        contentValues.put(COL2, activity);
        contentValues.put(COL3, time);
        contentValues.put(COL4, hours);
        contentValues.put(COL5, minutes);
        contentValues.put(COL6, hours_end);
        contentValues.put(COL7, minutes_end);

        //Log.d(TAG, "addData: Adding " + name + " and " + surname + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        return count;
    }

    public void updateName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + 0 + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void putDataMilliOnly(int number, long milliseconds, String activity, int startHours, int startMinutes,
                                 int hours_end, int minutes_end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int seconds, minutesPassed, hours;
        int totalSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        if (totalSeconds < 60) {
            minutesPassed = 0;
//            seconds = totalSeconds;
        } else {
            minutesPassed = totalSeconds / 60;
//            seconds = totalSeconds - (minutesPassed * 60);
        }
//        if (minutes < 60) {
//            hours = 0;
//        } else {
//            hours = minutes / 60;
//            minutes = minutes - (hours * 60);
//            seconds = totalSeconds - (hours * 3600) - (minutes * 60);
//        }
        contentValues.put(COL1, number);
        contentValues.put(COL2, activity);
        contentValues.put(COL3, minutesPassed);
        contentValues.put(COL4, startHours);
        contentValues.put(COL5, startMinutes);
        contentValues.put(COL6, hours_end);
        contentValues.put(COL7, minutes_end);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "putDataMilliOnly: " + result);
    }
}
