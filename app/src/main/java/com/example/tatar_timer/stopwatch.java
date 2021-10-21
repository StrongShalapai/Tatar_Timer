package com.example.tatar_timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatar_timer.sampledata.CategoryBdHelper;

import java.util.ArrayList;
import java.util.Locale;

public class stopwatch extends Activity {
    private static String currentCategoryName = "Категория не выбрана!"; //Это тоже должно.
    private Button chooseCategory;
    private TextView tv_currentCategory;
    private long totalSeconds = 0; //Это пойдет в БД
    private static final String TAG = "myLogs";
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;
    SharedPreferences sharedPreferences;
    CategoryBdHelper dbHelper;


    //геттер и сеттер для текущей категории. будем ставить его через preferences
    public static void setCurrentCategoryName(String currentCategoryName_) {
        currentCategoryName = currentCategoryName_;

    }

    private void toaster(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void init() {
        Button btn_goToStopwatch = findViewById(R.id.btn_stopwatch);
        tv_currentCategory = findViewById(R.id.tvCurrentCategory);
        chooseCategory = findViewById(R.id.btn_chooseCategory1);
        dbHelper = new CategoryBdHelper(this);
        chronometer = findViewById(R.id.chronometer);
        Button startChrono = findViewById(R.id.btn_StartChrono);
        Button pauseChrono = findViewById(R.id.btn_StopChrono);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d(TAG, "onCreate: Started StopWatch activity");
        init();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentCategoryName = bundle.getString("ChosenActivity");
        }

//        if (!currentCategoryName.equals("Empty")) {
//            sharedPreferences.edit().putString("currentCategory", currentCategoryName).apply();
//            Log.d(TAG, "текущая хуита" + currentCategoryName);
//        }
        tv_currentCategory.setText(currentCategoryName);


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
        if (!currentCategoryName.equals("Категория не выбрана!")) {
            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
                chronometer.start();
                running = true;
            }
        }
        else{
            toaster("Выберите категорию!");
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

    private void showInfo(long totalMilliseconds) {
        // Seconds
        long totalSecs = totalMilliseconds / 1000;
        // Show Info
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        totalSeconds = totalSecs;
        long wholeSeconds = (totalMilliseconds / 1000);

        if (totalMilliseconds < wholeSeconds) {
            totalMilliseconds = wholeSeconds;
        }

        Log.d(TAG, "MillSeconds = " + totalMilliseconds + "\n" + "Whole seconds = " + wholeSeconds);
        toaster("Всего: " + totalSecs + " из них минут " + minutes + " и секунд " + wholeSeconds);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Closing all!))");
//        Intent intent = new Intent(stopwatch.this, MainActivity.class);
//        startActivity(intent);
//        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Destroying!");
        finish();
    }
}

