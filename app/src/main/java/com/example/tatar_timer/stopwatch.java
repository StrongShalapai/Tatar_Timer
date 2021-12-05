package com.example.tatar_timer;

import androidx.annotation.NonNull;

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


public class stopwatch extends Activity {
    private static String currentCategoryName = "Категория не выбрана!"; //Это тоже должно.
    private Button chooseCategory;
    private TextView tv_currentCategory;
    private long totalSeconds = 0; //Это пойдет в БД
    private static final String TAG = "myLogs";
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;
    private final String[] arrayOfCategories = {"Прогулка", "Учеба", "Спорт"}; //Стандратный массив. Отправляем если у нас нет сохраненных массивов
    private final String[] defaultArray = {"Прогулка", "Учеба", "Спорт"};
    CategoryBdHelper dbHelper;
    SharedPreferences savedString;
    private final String DELIMITER = "%_%";
    private String  loadedString;

//test commit
    private void toaster(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void init() {
        tv_currentCategory = findViewById(R.id.tvCurrentCategory);
        chooseCategory = findViewById(R.id.btn_chooseCategory1);
        dbHelper = new CategoryBdHelper(this);
        chronometer = findViewById(R.id.chronometer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d(TAG, "onCreate: Started StopWatch activity");
        init();
        Intent goToList = new Intent(stopwatch.this, CategoryChooseActivity.class);

        String[] loadedCategories = null; //Массив, который заполнится если мы найдем сохранненные файлы
        loadData();
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
            saveData(buildStringFromArray(receivedArray));

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
        } else {
            toaster("Выберите категорию!");
        }
    }

    private void ScanArray(String[] array, String name) {
        Log.d(TAG, "ScanArray: Started");
        Log.d(TAG, "ScanningArray: " + name);
        for (String s : array) {
            Log.d(TAG, "Выбранный элемент: \n" + s);
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
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
        totalSeconds = totalSecs;
        long wholeSeconds = (totalMilliseconds / 1000);

        if (totalMilliseconds < wholeSeconds) {
            totalMilliseconds = wholeSeconds;
        }

        Log.d(TAG, "MillSeconds = " + totalMilliseconds + "\n" + "Whole seconds = " + wholeSeconds);
        toaster("Всего прошло секунд: " + totalSecs);
    }

    public String buildStringFromArray(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s);
            builder.append(DELIMITER);
        }
        return builder.toString();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        saveData();
        Log.d(TAG, "onStop: Destroying!");
        if (!running) {
            finish();
        }
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

