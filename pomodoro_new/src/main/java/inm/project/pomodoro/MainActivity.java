package inm.project.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_pomodoro, btn_timer, btn_calendar, btn_settings, btn_goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_pomodoro:
                    Intent pomodoro = new Intent(MainActivity.this, Pomodoro.class);
                    startActivity(pomodoro);
                    break;
                case R.id.btn_timer:
                    break;
                case R.id.btn_calendar:
                    Intent calendar = new Intent(MainActivity.this, Calendar.class);
                    startActivity(calendar);
                    break;
                case R.id.btn_settings:
                    Intent settings = new Intent(MainActivity.this, Settings.class);
                    startActivity(settings);
                    break;
                case R.id.btn_goals:
                    Intent goals = new Intent(MainActivity.this, Goals.class);
                    Intent edit = new Intent(MainActivity.this, Goals_edit.class);
                    startActivity(goals);
                    break;
            }
        }
    };

    public void init(){
        btn_pomodoro = findViewById(R.id.btn_pomodoro);
        btn_timer = findViewById(R.id.btn_timer);
        btn_calendar = findViewById(R.id.btn_calendar);
        btn_settings = findViewById(R.id.btn_settings);
        btn_goals = findViewById(R.id.btn_goals);

        btn_pomodoro.setOnClickListener(onClickListener);
        btn_timer.setOnClickListener(onClickListener);
        btn_calendar.setOnClickListener(onClickListener);
        btn_settings.setOnClickListener(onClickListener);
        btn_goals.setOnClickListener(onClickListener);
    }
}