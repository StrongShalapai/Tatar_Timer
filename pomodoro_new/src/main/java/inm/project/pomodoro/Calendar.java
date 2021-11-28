package inm.project.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Calendar extends AppCompatActivity {

    Button[] btn_array;
    First_DB fdb;
    Second_DB sdb;
    LinearLayout ll_calendar;
    Cursor dataFDB;
    Cursor dataSDB;
    int countFDB, countSDB, color = 1, size = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        init();
        for(int i = 0; i < countFDB; i = i + 1){
            ll_calendar.addView(btn_array[i]);
        }
    }

    public void init(){
        fdb = new First_DB(Calendar.this);
        sdb = new Second_DB(Calendar.this);

        ll_calendar = findViewById(R.id.ll_calendar2);
        dataFDB = fdb.getData();
        dataSDB = sdb.getData();
        countFDB = fdb.getProfilesCount();
        countSDB = sdb.getProfilesCount();
        btn_array = fill_array(dataFDB);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                btn_array = fill_array(dataFDB);
            }
        }).start();*/
    }

    public Button[] fill_array(Cursor dataFDB){
        Button[] array = new Button[countFDB];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String text, day, month, year, study, total;
        int study_int, total_int, study_hours, study_minutes, total_hours, total_minutes;
        dataFDB.moveToFirst();
        for(int i = 0; i < countFDB; i = i + 1){
            //dataFDB.moveToNext();
            Button btn = new Button(this, null, android.R.style.Widget_Material_Button_Borderless);
            if(dataFDB.getInt(7) == 0){
                btn.setVisibility(View.GONE);
            }
            else{
                day = String.valueOf(dataFDB.getInt(1));
                month = String.valueOf(dataFDB.getInt(2));
                year = String.valueOf(dataFDB.getInt(3));
                study_int = dataFDB.getInt(4);
                total_int = dataFDB.getInt(5);
                study_hours = study_int / 60;
                study_minutes = study_int - study_hours * 60;
                total_hours = total_int / 60;
                total_minutes = total_int - total_hours * 60;
                study = create_string(study_hours, study_minutes);
                total = create_string(total_hours, total_minutes);
                text = "  " + day + "." + month + "." + year + "\n" +
                        "  Pomodoro: " + study + "\n" +
                        "  Всего: " + total;
                btn.setLayoutParams(layoutParams);
                btn.setText(text);
                btn.setId(dataFDB.getInt(7));
                btn.setTextSize(size);
                btn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                btn.setTag(Integer.valueOf(i));
                btn.setOnClickListener(click);
                if (color % 2 == 0) {
                    btn.setBackgroundColor(getResources().getColor(R.color.grey));
                    btn.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btn.setBackgroundColor(getResources().getColor(R.color.white));
                    btn.setTextColor(getResources().getColor(R.color.grey));
                }
                color = color + 1;
            }
            dataFDB.moveToNext();
            array[i] = btn;
        }
        return array;
    }

    public String create_string(int hours, int minutes){
        String string = "";
        if(minutes / 10 == 0 && minutes != 0){
            string = hours + "ч. 0" + minutes + "м.";
        }
        if(minutes == 0){
            string = hours + "ч. 00м.";
        }
        if(minutes / 10 != 0){
            string = hours + "ч. " + minutes + "м.";
        }
        return string;
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Calendar.this, CalendarSecond.class);
            intent.putExtra("id", v.getId());
            startActivity(intent);
            //Log.d("Calendar", "num is " + v.getId());
        }
    };
}