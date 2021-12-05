package inm.project.zebalsya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class First_DB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "First_DB";
    private static final String COL0 = "id";
    private static final String COL1 = "day";
    private static final String COL2 = "month";
    private static final String COL3 = "year";
    private static final String COL4 = "study";
    private static final String COL5 = "total";
    private static final String COL6 = "number";
    private static final String COL7 = "visibility";
    private static final String TAG = "First_DB";

    public First_DB(Context context) {
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

    public boolean putData(int day, int month, int year, int study,
                           int total, int number, int visibility){
        Log.d(TAG, "putData started");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "writable DB");
        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "CV created");
        contentValues.put(COL1, day);
        contentValues.put(COL2, month);
        contentValues.put(COL3, year);
        contentValues.put(COL4, study);
        contentValues.put(COL5, total);
        contentValues.put(COL6, number);
        contentValues.put(COL7, visibility);
        Log.d(TAG, "contentValues ended");

        //Log.d(TAG, "addData: Adding " + name + " and " + surname + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
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

    public void updateStudy(int a, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + a + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void updateTotal(int a, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + a + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
}
