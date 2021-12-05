package com.example.tatar_timer;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatar_timer.sampledata.CategoryBdHelper;
import com.example.tatar_timer.sampledata.HLP_Class;
import com.example.tatar_timer.sampledata.Second_DB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//import inm.project.pomodoro.Second_DB;


public class stopwatch extends Activity {
    private static String currentCategoryName = "Категория не выбрана!"; //Это тоже должно.
    private Button chooseCategory;
    private TextView tv_currentCategory, textView_startTime, textView_endTime;
    private static final String TAG = "myLogs";
    private Chronometer chronometer;
    private boolean running;
    private int minutesStart, hoursStart, minutesEnd, hoursEnd;
    private long pauseOffSet; //Время таймера. Нужно к нему прировнять
    private final String[] arrayOfCategories = {"Прогулка", "Учеба", "Спорт"}; //Стандратный массив. Отправляем если у нас нет сохраненных массивов
    private final String[] defaultArray = {"Прогулка", "Учеба", "Спорт"};
    @SuppressLint("SimpleDateFormat")
    DateFormat hoursFormat = new SimpleDateFormat("k");
    @SuppressLint("SimpleDateFormat")
    DateFormat minutesFormat = new SimpleDateFormat("m");
    //    CategoryBdHelper dbHelper;
    Second_DB second_db;
    SharedPreferences savedString;
    private String loadedString;

    //test commit
    private void toaster(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void init() {
        tv_currentCategory = findViewById(R.id.tvCurrentCategory);
        chooseCategory = findViewById(R.id.btn_chooseCategory1);
//        dbHelper = new CategoryBdHelper(this);
        chronometer = findViewById(R.id.chronometer);
        textView_startTime = findViewById(R.id.tv_startTime);
        textView_startTime.setVisibility(View.INVISIBLE);
        textView_endTime = findViewById(R.id.tv_endTime);
        textView_endTime.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d(TAG, "onCreate: Started StopWatch activity");
        init();
        Intent goToList = new Intent(stopwatch.this, CategoryChooseActivity.class);
        second_db = new Second_DB(this); //Adding Falito's DB

        String[] loadedCategories = null; //Массив, который заполнится если мы найдем сохранненные файлы
        loadData();
        String DELIMITER = "%_%";
        if (loadedString != null) {
            Log.d(TAG, "Loaded string is " + loadedString);
            loadedCategories = loadedString.split(DELIMITER);
        }
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) { //Проверяем пришел ли нам модифицированный список
            currentCategoryName = bundle.getString("ChosenActivity"); //Получаем выбранную категорию
            String[] receivedArray = bundle.getStringArray("Array");

            ScanArray(receivedArray, "Массив получен от прошлого активити");
            goToList.putExtra("categoryArray", receivedArray);
            saveData(HLP_Class.buildStringFromArray(receivedArray, DELIMITER));

        } else if (loadedCategories != null) { //Если нет, то проверяем наличие сохраненного списка
            ScanArray(loadedCategories, "Получили сохраненный массив"); //Выводим его в логи
            goToList.putExtra("categoryArray", loadedCategories); //Добавляем в намерение для дальнейшего взаимодействия
        } else { //Нет так отправим стандартный массив
            ScanArray(defaultArray, "Стандартный массив");
            goToList.putExtra("categoryArray", defaultArray);
        }
        tv_currentCategory.setText(currentCategoryName); //Назначаем выбранную категорию
        View.OnClickListener goToListYES = v -> startActivity(goToList);
        chooseCategory.setOnClickListener(goToListYES);

//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                //ПРи каждом тики можем выполнять действия
//            }
//        });


    }

    public void startChronometer(View v) {
        if (!currentCategoryName.equals("Категория не выбрана!")) {
            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
                chronometer.start();
                running = true;
            }
        } else {
            toaster("Выберите категорию!");
        }
        Date currentDate = new Date();
        //Попробуем получить текущее время.
        if (!currentCategoryName.equals("Категория не выбрана!")) {
            textView_startTime.setVisibility(View.VISIBLE);
            String currentTime = "Время запуска: " + hoursFormat.format(currentDate) +
                    ":" +
                    minutesFormat.format(currentDate);
            textView_startTime.setText(currentTime);
        }
        try {
            minutesStart = Integer.parseInt(minutesFormat.format(currentDate));
            hoursStart = Integer.parseInt(hoursFormat.format(currentDate));
        } catch (NullPointerException e) {
            minutesStart = -1;
            hoursStart = -1;
            Log.d(TAG, "startChronometer: ОШИБКА НАХУЙ!");
//            finish();
        }


    }


    public void pauseChronometer(View v) {

        Bundle bundle = getIntent().getExtras();
        String currentActivity = "null";
        if (bundle != null) {
            currentActivity = bundle.getString("ChosenActivity");
        }
        if (running) {
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            showInfo(SystemClock.elapsedRealtime() - chronometer.getBase());
        }
        Date currentDate = new Date();
        if (!currentCategoryName.equals("Категория не выбрана!")) {
            String currentTime = "Время отключения: " + hoursFormat.format(currentDate) +
                    ":" +
                    minutesFormat.format(currentDate);
            textView_endTime.setText(currentTime);
            textView_endTime.setVisibility(View.VISIBLE);
        }
        if (pauseOffSet != 0) {
            try {
                minutesEnd = Integer.parseInt(minutesFormat.format(currentDate));
                hoursEnd = Integer.parseInt(hoursFormat.format(currentDate));
            } catch (NullPointerException e) {
                minutesEnd = -1;
                hoursEnd = -1;
                Log.d(TAG, "startChronometer: ОШИБКА НАХУЙ!");
                finish();
            }
            long mil = SystemClock.elapsedRealtime() - chronometer.getBase();
            Log.d(TAG, "pauseChronometer Puttied data: " + mil + " '" + currentActivity + "' " + hoursStart
                    + ":" + minutesStart + " " + hoursEnd + ":" + minutesEnd);
            second_db.putDataMilliOnly(5, mil,
                    currentActivity, hoursStart, minutesStart, hoursEnd, minutesEnd);
        }
///     private int minutesStart, hoursStart, minutesEnd, hoursEnd;
//        second_db.putDataMilliOnly(5, SystemClock.elapsedRealtime() - chronometer.getBase() , bundle.getString("ChosenActivity"));
    }

//    public void resetChronometer(View v) {
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        pauseOffSet = 0;
//    }
//
//    private void showCurrentTime() {
//        long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
//        toaster(String.valueOf(elapsedTime));
//    }

    private void ScanArray(String[] array, String name) {
        Log.d(TAG, "ScanArray: Started");
        Log.d(TAG, "ScanningArray: " + name);
        for (String s : array) {
            Log.d(TAG, "Выбранный элемент: \n" + s);
        }
    }

    private void showInfo(long totalMilliseconds) {
        // Seconds
        long totalSecs = totalMilliseconds / 1000;
        long wholeSeconds = (totalMilliseconds / 1000);

        if (totalMilliseconds < wholeSeconds) {
            totalMilliseconds = wholeSeconds;
        }

        Log.d(TAG, "MillSeconds = " + totalMilliseconds + "\n" + "Whole seconds = " + wholeSeconds);
        toaster("Всего прошло секунд: " + totalSecs);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Destroying!");
        if (pauseOffSet == 0 && !running) {
            finish();
//        } else {
//            if (!running) {
//                finish();
//            }
        }
        //Сохранение добавить на стоп
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putStringArray("savedArray", arrayOfCategories);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //
        String[] sentCategories = savedInstanceState.getStringArray("savedArray");
        Log.d(TAG, "onRestoreInstanceState: Scanning saved array");
        ScanArray(sentCategories, "saved sentCategories");
    }

    //Методы для SharedPreferences
    private void saveData(String newString) {
        savedString = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = savedString.edit();
        editor.putString("savedString", newString);
        editor.apply();
        Log.d(TAG, "saveData: Data Saved" + newString);
    }

    private void loadData() {
        savedString = getPreferences(MODE_PRIVATE);
        loadedString = savedString.getString("savedString", null);
        Log.d(TAG, "loadData: Data loaded");
        Log.d(TAG, "loadedData: " + loadedString);
    }

}

