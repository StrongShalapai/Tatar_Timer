package com.example.tatar_timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.tatar_timer.adapters.CategoryAdapter;

import java.util.ArrayList;

public class CategoryChooseActivity extends AppCompatActivity {

    private RecyclerView categoryList;
    private CategoryAdapter categoryAdapter;
    final String SAVED_CATEGORY = "saved_category";
    SharedPreferences preferences;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Bundle receivedCategories = getIntent().getExtras();
        String[] categories = receivedCategories.getStringArray("categoryArray");

        categoryList = findViewById(R.id.rc_names);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //создаем столько вью сколько есть всего категорий
        categoryAdapter = new CategoryAdapter(categories.length, categories);

        categoryList.setLayoutManager(layoutManager);
        categoryList.setHasFixedSize(true);
        categoryList.setAdapter(categoryAdapter);




    }



}