package inm.project.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Goals_edit extends AppCompatActivity {

    EditText et_name, et_hours, et_minutes;
    Button go;
    InputFilterMinMax for_hours, for_minutes;
    Goals_raw_DB grdb;
    ToggleButton tgbtn;
    int check = 0, mode, id = -100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_edit);

        init();
    }

    public void init(){
        et_name = findViewById(R.id.name_field);
        et_hours = findViewById(R.id.hours_field);
        et_minutes = findViewById(R.id.minutes_field);
        tgbtn = findViewById(R.id.toggleButton);
        grdb = new Goals_raw_DB(this);

        for_hours = new InputFilterMinMax("0", "23"){};
        for_minutes = new InputFilterMinMax("0", "59"){};

        et_hours.setFilters(new InputFilter[]{for_hours});
        et_minutes.setFilters(new InputFilter[]{for_minutes});

        go = findViewById(R.id.btn_ready);
        go.setOnClickListener(click);
        tgbtn.setOnCheckedChangeListener(tgl);
        mode();
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            func();
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //func();
    }

    public void func(){
        String name;
        int hours, minutes;
        name = et_name.getText().toString();
        hours = Integer.valueOf(et_hours.getText().toString());
        minutes = Integer.valueOf(et_minutes.getText().toString());
        if(mode == 0) {
            grdb.putData(name, 1, check, hours, minutes);
        }
        else{
            grdb.updateName(name, id);
            grdb.updateHours(hours, id);
            grdb.updateMinutes(minutes, id);
            grdb.updateDone(check, id);
        }
    }

    CompoundButton.OnCheckedChangeListener tgl = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                check = 1;
            }
            else {
                check = 0;
            }
        }
    };

    public void mode(){
        Intent intent = getIntent();
        int a = intent.getIntExtra("edit", 0);
        mode = a;
        if(a == 1){
            id = intent.getIntExtra("id", -100);
            restore();
        }
    }

    public void restore(){
        Cursor cursor = grdb.getData();
        int count = grdb.getProfilesCount();
        for(int i = 0; i < count; i++){
            cursor.moveToNext();
            if(cursor.getInt(0) == id){
                et_name.setText(cursor.getString(1));
                et_hours.setText(cursor.getString(4));
                et_minutes.setText(cursor.getString(5));
                //int a = cursor.getInt(3);
                if(cursor.getInt(3) == 1){
                    tgbtn.setChecked(false);
                }
                else{
                    tgbtn.setChecked(true);
                }
            }
        }
    }
}