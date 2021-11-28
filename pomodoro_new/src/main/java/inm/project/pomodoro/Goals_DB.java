package inm.project.pomodoro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Goals_DB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Goals_DB";
    private static final String COL0 = "id";
    private static final String COL1 = "name_of_goal";
    private static final String COL2_1 = "component1";
    private static final String COL2_2 = "component2";
    private static final String COL2_3 = "component3";
    private static final String COL3 = "hours";
    private static final String COL4 = "minutes";
    private static final String COL5 = "hours_goal";
    private static final String COL6 = "minutes_goal";
    private static final String COL7 = "percent";
    private static final String TAG = "Goals_DB";

    public Goals_DB(Context context) {
        super(context,TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key autoincrement,"
                + COL1 + " TEXT," + COL2_1 + " TEXT," + COL2_2 + " TEXT,"
                + COL2_3 + " TEXT," + COL3 + " INTEGER," + COL4 + " INTEGER,"
                + COL5 + " INTEGER," + COL6 + " INTEGER," + COL7 + " INTEGER" +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    public boolean putData(String name, String component1, String component2,
                           String component3, int hours, int minutes,
                           int hours_goal, int minutes_goal, int percent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2_1, component1);
        contentValues.put(COL2_2, component2);
        contentValues.put(COL2_3, component3);
        contentValues.put(COL3, hours);
        contentValues.put(COL4, minutes);
        contentValues.put(COL5, hours_goal);
        contentValues.put(COL6, minutes_goal);
        contentValues.put(COL7, percent);

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

    public void updateName(int a, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2_1 +
                " = '" + a + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void updateMinutes(int minutes, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2_1 +
                " = '" + minutes + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
    
}
