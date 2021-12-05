package inm.project.zebalsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pomodoro extends AppCompatActivity {

    TextView tv_timer;
    Button btn_study, btn_relax, btn_long, btn_launch, btn_test;
    int minutes, seconds, time_full, secs, check = 0, time = 0;
    boolean click = false, reset = false, work = true,
            background = true, check_bool = false;
    int phase2 = 0, number_for_DB, hours_begin, minutes_begin;
    int[] array;
    String TAG = "tomato";

    First_DB fdb;
    Second_DB sdb;

    Handler mainHandler = new Handler();

    volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        init();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_study:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showAlertDialog(R.id.btn_study);
                                }
                            });
                        }
                    }).start();
                    break;
                case R.id.btn_relax:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showAlertDialog(R.id.btn_relax);
                                }
                            });
                        }
                    }).start();
                    break;
                case R.id.btn_long:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showAlertDialog(R.id.btn_long);
                                }
                            });
                        }
                    }).start();
                    break;
                case R.id.btn_launch:
                    if(!click){
                        startThreadNew();
                        click = true;
                        btn_launch.setText("стоп");
                    }
                    else{
                        stopThread();
                        click = false;
                        btn_launch.setText("запуск");
                    }
                    break;
                case R.id.btn_test:
                    //test();
                    //Log.d("First_DB", "clicked");
                    //sendDataFDB(1,1,1,1,1,1,1);
                    //Intent intent = new Intent(Pomodoro.this, Second.class);
                    //startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public void init(){
        tv_timer = findViewById(R.id.tv_timer);
        btn_study = findViewById(R.id.btn_study);
        btn_relax = findViewById(R.id.btn_relax);
        btn_long = findViewById(R.id.btn_long);
        btn_launch = findViewById(R.id.btn_launch);
        btn_test = findViewById(R.id.btn_test);

        btn_study.setOnClickListener(onClickListener);
        btn_relax.setOnClickListener(onClickListener);
        btn_long.setOnClickListener(onClickListener);
        btn_launch.setOnClickListener(onClickListener);
        btn_test.setOnClickListener(onClickListener);

        fdb = new First_DB(this);
        sdb = new Second_DB(this);

        array = getFromSP();
        minutes = array[0];
        seconds = array[1];
        number_for_DB = array[2];
        setText();
        time_full = minutes * 60 + seconds;
        secs = time_full * 10;
        applySettings();
    }

    public void startThreadNew() {
        SimpleDateFormat hours_new = new SimpleDateFormat("HH");
        SimpleDateFormat minutes_new = new SimpleDateFormat("mm");
        hours_begin = Integer.valueOf(hours_new.format(new Date(System.currentTimeMillis())));
        minutes_begin = Integer.valueOf(minutes_new.format(new Date(System.currentTimeMillis())));
        if(reset){
            phase2 = 0;
        }
        if(minutes == 0 && seconds != 25){
            work = true;
        }
        if(seconds == 25){
            work = false;
        }
        stopThread = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                check_bool = true;
                for (int i = 0; i < secs; i++) {
                    if(stopThread) {return;}
                    try {
                        check = check + 1;
                        Thread.sleep(100);
                        if(seconds / 10 == 0 && seconds != 0) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_timer.setText(minutes + ":0" + seconds);
                                }
                            });
                        }
                        else if(seconds == 0){
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_timer.setText(minutes + ":00");
                                }
                            });
                        }
                        else{
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_timer.setText(minutes + ":" + seconds);
                                }
                            });
                        }
                        //secs = secs - 1;
                        if(check % 10 == 0) {
                            time = time + 1;
                            time_full = time_full - 1;
                            minutes = time_full / 60;
                            seconds = time_full - minutes * 60;
                        }
                        if(minutes == 0 && seconds == 0){
                            //phase = phase + 1;
                            if(work){work = false; phase2 = phase2 + 1;}
                            else{work = true;}
                            phaseChoose(phase2);
                            stopThread();
                            click = false;
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    btn_launch.setText("запуск");
                                }
                            });
                            reset = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopThread() {
        stopThread = true;
    }

    public void phaseChoose(int a){
        if(!work && a % 4 != 0){
            minutes = 5; seconds = 0;
            //minutes = 0; seconds = 5;
        }
        if(!work && a % 4 == 0){
            minutes = 15; seconds = 0;
            //minutes = 0; seconds = 15;
        }
        if(work){
            minutes = 25; seconds = 0;
            //minutes = 0; seconds = 25;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                tv_timer.setText(minutes + ":00");
                //tv_timer.setText("0:" + seconds);
            }
        });
        time_full = minutes * 60 + seconds;
        secs = time_full * 10;
    }

    public void showAlertDialog(int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Прогресс");
        alert.setMessage("Вы точно хотите сбросить свой прогресс?\t " +
                "Дождитесь окончания таймера.");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetProgress(id);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    public void resetProgress(int id){
        stopThread();
        switch(id){
            case R.id.btn_study:
                minutes = 25; seconds = 0;
                //minutes = 0; seconds = 25;
                break;
            case R.id.btn_relax:
                minutes = 5; seconds = 0;
                //minutes = 0; seconds = 5;
                reset = true;
                break;
            case R.id.btn_long:
                minutes = 15; seconds = 0;
                //minutes = 0; seconds = 15;
                reset = true;
                break;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                tv_timer.setText(minutes + ":00");
                //tv_timer.setText("0:" + seconds);
            }
        });
        time_full = minutes * 60 + seconds;
        secs = time_full * 10;
        click = false;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                btn_launch.setText("запуск");
            }
        });
    }

    public void sendDataSDB(int number, String activity, int time,
                            int hours, int minutes, int hours_end, int seconds_end){
        boolean info = sdb.putData(number,activity,time,hours,minutes,hours_end,seconds_end);
        if (info) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    public void sendDataFDB(int day, int month, int year, int study,
                            int total, int number, int visibility){
        boolean info = fdb.putData(day,month,year,study,total,number,visibility);
        if (info) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    //cамый важный фрагмент кода здесь
    public void check(Cursor data, int day, int month, int year, int seconds){
        //sendDataFDB(day, month, year, seconds, 1, 1, 1);
        if(data.moveToFirst()) {
            Log.d(TAG, "moved to first");
            for (int i = 0; i < fdb.getProfilesCount(); i = i + 1) {
                if (data.getInt(1) == day && data.getInt(2) == month
                        && data.getInt(3) == year) {
                    Log.d(TAG, "updating");
                    int a = data.getInt(4);
                    int b = data.getInt(5);
                    fdb.updateStudy(a + seconds, data.getInt(1));
                    fdb.updateTotal(b + seconds, data.getInt(1));
                } else {
                    //sendDataFDB(day, month, year, seconds, 1, 1, 1);
                    data.moveToNext();
                }
            }
        }
        else{
            sendDataFDB(day, month, year, 0, 0, number_for_DB, 1);
            number_for_DB = number_for_DB + 1;
            Log.d(TAG, "all zeros");
        }
    }

    public void test(){
        Cursor data = fdb.getData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat seconds = new SimpleDateFormat("ss");
                int time_seconds = Integer.valueOf(seconds.format(new Date(System.currentTimeMillis())));
                SimpleDateFormat year = new SimpleDateFormat("yyyy");
                int date_year = Integer.valueOf(year.format(new Date(System.currentTimeMillis())));
                SimpleDateFormat month = new SimpleDateFormat("MM");
                int date_month = Integer.valueOf(month.format(new Date(System.currentTimeMillis())));
                SimpleDateFormat day = new SimpleDateFormat("dd");
                int date_day = Integer.valueOf(day.format(new Date(System.currentTimeMillis())));
                check(data, date_day, date_month, date_year, time);
            }
        }).start();
    }

    public void test_for_SDB(){
        Cursor data = sdb.getData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat hours_new = new SimpleDateFormat("HH");
                SimpleDateFormat minutes_new = new SimpleDateFormat("mm");
                int hours = Integer.valueOf(hours_new.format(new Date(System.currentTimeMillis())));
                int minutes = Integer.valueOf(minutes_new.format(new Date(System.currentTimeMillis())));
                sendDataSDB(number_for_DB, "Pomodoro", time, hours_begin, minutes_begin, hours, minutes);
            }
        }).start();
    }

    public int[] getFromSP(){
        int[] array = new int[3];
        SharedPreferences sp = getSharedPreferences("pomodoro_file", MODE_PRIVATE);
        array[0] = sp.getInt("minutes", 25);
        array[1] = sp.getInt("seconds", 0);
        //array[0] = sp.getInt("minutes", 0);
        //array[1] = sp.getInt("seconds", 25);
        array[2] = sp.getInt("number_for_DB", 1);
        return array;
    }

    public void toSP(int minutes, int seconds, int number){
        SharedPreferences sp = getSharedPreferences("pomodoro_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("minutes", minutes);
        editor.putInt("seconds", seconds);
        editor.putInt("number_for_DB", number);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!stopThread){
            toSP(minutes, seconds, number_for_DB);
        }
        else{
            toSP(25, 0, number_for_DB);
            //toSP(0, 25, number_for_DB);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
        test();
        if(check_bool) {
            test_for_SDB();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!background) {
            array = getFromSP();
            minutes = array[0];
            seconds = array[1];
            number_for_DB = array[2];
            setText();
            time_full = minutes * 60 + seconds;
            secs = time_full * 10;
        }
    }

    public void setText(){
        if(seconds % 10 == 0){
            tv_timer.setText(minutes + ":0" + seconds);
        }
        else{
            tv_timer.setText(minutes + ":" + seconds);
        }
    }

    public void applySettings(){
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        background = sp.getBoolean("background", true);
    }
}