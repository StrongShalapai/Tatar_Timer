package com.example.tatar_timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatar_timer.sampledata.CategoryBdHelper;

import java.util.ArrayList;
import java.util.Locale;

public class stopwatch extends Activity {
    private Button btn_goToStopwatch, startChrono, pauseChrono, chooseCategory, btn_goToCategoryList;
    private TextView tv_currentCategory;
    private int seconds = 0;
    private static final String TAG = "myLogs";
    private Chronometer chronometer;
    private boolean running, wasRunning;
    CategoryBdHelper dbHelper;
    private long pauseOffSet;

    private String currentCategoryName;

    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    //геттер и сеттер для текущей категории. будем ставить его через preferences
    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }

    private void toaster(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void init() {
        btn_goToStopwatch = findViewById(R.id.btn_stopwatch);
        tv_currentCategory = findViewById(R.id.tvCurrentCategory);
        chooseCategory = findViewById(R.id.btn_chooseCategory1);
        dbHelper = new CategoryBdHelper(this);
        chronometer = findViewById(R.id.chronometer);
        startChrono = findViewById(R.id.btn_StartChrono);
        pauseChrono = findViewById(R.id.btn_StopChrono);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        init();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        if (savedInstanceState != null) {
//
//            seconds = savedInstanceState.getInt("seconds");
//            running = savedInstanceState.getBoolean("running");
//            wasRunning = savedInstanceState.getBoolean("wasRunning");
//        }
//        runTimer();
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Прогулка");
        categories.add("Учеба");
        categories.add("Спорт");
        String[] sentCategories = categories.toArray(new String[0]); //Превращаем бесконечный массив в статичный


        View.OnClickListener goToListYES = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToList = new Intent(stopwatch.this, CategoryChooseActivity.class);
                goToList.putExtra("categoryArray", sentCategories); //отправляем его в ресайклер
                startActivity(goToList);
            }
        };
        chooseCategory.setOnClickListener(goToListYES);


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //ПРи каждом тики можем выполнять действия
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
//            showCurrentTime();
        showInfo(SystemClock.elapsedRealtime() - chronometer.getBase());
        }

    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
    }

    private void showCurrentTime() {
        long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        toaster(String.valueOf(elapsedTime));
    }

    private void showInfo(long totalMilliseconds)  {
        // Seconds
        long totalSecs = totalMilliseconds / 1000;
        // Show Info
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        toaster("Base Time: " + totalSecs +" ~ " + hours + " hours " + minutes+" minutes " + seconds + " seconds");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        savedInstanceState.putInt("seconds", seconds);
//        savedInstanceState.putBoolean("running", running);
//        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }


}

