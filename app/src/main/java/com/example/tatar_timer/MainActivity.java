package com.example.tatar_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_goToStopwatch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btn_goToStopwatch = findViewById(R.id.btn_stopwatch);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
}