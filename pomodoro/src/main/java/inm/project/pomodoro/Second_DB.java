package inm.project.pomodoro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Second_DB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Second_DB";
    private static final String COL0 = "id";
    private static final String COL1 = "number";
    private static final String COL2 = "activity";
    private static final String COL3 = "time";
    private static final String COL4 = "hours";
    private static final String COL5 = "minutes";


    public Second_DB(Context context) {
        super(context,TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key autoincrement,"
                + COL1 + " INTEGER," + COL2 + " INTEGER," + COL3 + " INTEGER," + COL4 + " INTEGER," +
                COL5 + " INTEGER" +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    public boolean putData(int number, String activity, int time, int hours,
                           int minutes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, number);
        contentValues.put(COL2, activity);
        contentValues.put(COL3, time);
        contentValues.put(COL4, hours);
        contentValues.put(COL5, minutes);

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

    public void updateName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + 0 + "' WHERE " + COL1 + " = '" + id + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
}
