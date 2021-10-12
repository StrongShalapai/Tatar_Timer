package com.example.tatar_timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.tatar_timer.adapters.CategoryAdapter;

public class CategoryChooseActivity extends AppCompatActivity {

    private RecyclerView categoryList;
    private CategoryAdapter categoryAdapter;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryList = findViewById(R.id.rc_names);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryAdapter = new CategoryAdapter(100);
        categoryList.setLayoutManager(layoutManager);
        categoryList.setHasFixedSize(false);
        categoryList.setAdapter(categoryAdapter);







    }
}