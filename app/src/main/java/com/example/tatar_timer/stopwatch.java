package com.example.tatar_timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatar_timer.sampledata.CategoryBdHelper;

import java.util.ArrayList;
import java.util.Locale;

public class stopwatch extends Activity {
    private Button btn_goToStopwatch;
    private TextView tv_currentCategory;
    private Button btn_goToCategoryList;
    private Button chooseCategory;
    private int seconds = 0;
    private static final String TAG = "myLogs";

    private boolean running, wasRunning;
    //CategoryBdHelper
    CategoryBdHelper dbHelper;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        btn_goToStopwatch = findViewById(R.id.btn_stopwatch);
        tv_currentCategory = findViewById(R.id.tvCurrentCategory);
        chooseCategory = findViewById(R.id.btn_chooseCategory1);
        dbHelper = new CategoryBdHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (savedInstanceState != null) {

            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
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
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }


    public void onClickStart(View view) {
        if (running) {
            toaster("Секундомер уже запущен!");
        } else {
            running = true;
            toaster("Секундомер запущен");
        }
    }

    public void onClickStop(View view) {
        running = false;
        toaster("Секундомер остановлен");
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.tv_stopwatch);

        final Handler handler = new Handler();
        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.

                timeView.setText(time);
                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

}