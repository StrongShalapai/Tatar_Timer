package inm.project.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarSecond extends AppCompatActivity {

    LinearLayout ll_calendar2;
    First_DB fdb;
    Second_DB sdb;
    Cursor dataSDB;
    TextView[] tv_array;
    int countSDB, number, size = 17, color = 1;
    LinearLayout.LayoutParams linLayoutParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_second);

        init();
        /*for(int i = 0; i < countSDB; i = i + 1) {
            ll_calendar2.addView(tv_array[i]);
        }*/
    }

    public void init(){
        ll_calendar2 = findViewById(R.id.ll_calendar2);
        fdb = new First_DB(this);
        sdb = new Second_DB(this);
        dataSDB = sdb.getData();
        countSDB = sdb.getProfilesCount();
        Intent intent = getIntent();
        number = intent.getIntExtra("id", 1);
        tv_array = tv_array();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                tv_array = tv_array();
            }
        }).start();*/
    }

    public TextView[] tv_array(){
        TextView[] array = new TextView[countSDB];
        linLayoutParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //dataSDB.moveToFirst();
        for(int i = 0; i < countSDB; i = i + 1){
            TextView tv = new TextView(CalendarSecond.this);
            dataSDB.moveToNext();
            if(dataSDB.getInt(1) == number) {
                String activity, text;
                int time, hours_time, minutes_time, hours, minutes, hours_end, minutes_end;
                activity = dataSDB.getString(2);
                hours = dataSDB.getInt(4);
                minutes = dataSDB.getInt(5);
                hours_end = dataSDB.getInt(6);
                minutes_end = dataSDB.getInt(7);
                time = dataSDB.getInt(3);
                hours_time = time / 60;
                minutes_time = time - hours_time * 60;
                text = "  " + activity + ": " + create_string(hours_time, minutes_time) + "    " + "[" +
                       "" +create_string(hours, minutes) + " - " + create_string(hours_end, minutes_end) + "]";
                tv.setText(text);
                tv.setLayoutParams(linLayoutParam);
                tv.setTextSize(size);
                //tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                if (color % 2 == 0) {
                    tv.setBackgroundColor(getResources().getColor(R.color.grey));
                    tv.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                    tv.setTextColor(getResources().getColor(R.color.grey));
                }
                color = color + 1;
                ll_calendar2.addView(tv, linLayoutParam);
            }
            //dataSDB.moveToNext();
            array[i] = tv;
        }
        return array;
    }

    public String create_string(int hours, int minutes){
        String string = "";
        if(minutes / 10 == 0 && minutes != 0){
            string = String.valueOf(hours) + ":0" + String.valueOf(minutes);
        }
        if(minutes == 0){
            string = String.valueOf(hours) + ":00";
        }
        if(minutes / 10 != 0){
            string = String.valueOf(hours) + ":" + String.valueOf(minutes);
        }
        return string;
    }
}