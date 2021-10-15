package com.example.tatar_timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tatar_timer.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryChooseActivity extends AppCompatActivity {

    private RecyclerView categoryList;
    private CategoryAdapter categoryAdapter;
    private Button button_commit;
    private EditText editText;
    private RecyclerView recyclerView;
    final String SAVED_CATEGORY = "saved_category";
    SharedPreferences preferences;
    private static final String TAG = "myLogs";
    Context context;

    private String newCater;
    private List<String> data;


    private void toaster(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        button_commit = findViewById(R.id.btn_commitNewCategory);
        editText = findViewById(R.id.et_newCategory);


        Bundle receivedCategories = getIntent().getExtras();
        String[] categories = receivedCategories.getStringArray("categoryArray");
        ArrayList<String> updatedList = new ArrayList<>(Arrays.asList(categories));
//        data = new ArrayList<String>();
        data = Arrays.asList(receivedCategories.getStringArray("categoryArray"));


        categoryList = findViewById(R.id.rc_names);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //создаем столько вью сколько есть всего категорий
        categoryAdapter = new CategoryAdapter(this, categories.length, updatedList); //Наш адаптер
//
        categoryList.setLayoutManager(layoutManager);
        categoryList.setHasFixedSize(false);
        categoryList.setAdapter(categoryAdapter);


//        ArrayList<String> updatedList = new ArrayList<String>(categories.);

        View.OnClickListener commitAndUpdate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedList.add(updatedList.size(), editText.getText().toString());
                categoryAdapter.notifyItemInserted(updatedList.size());
                Log.d(TAG, editText.getText().toString() + "\n" + updatedList.size());
                categoryList.setAdapter(new CategoryAdapter(context, categories.length, updatedList));
                categoryList.invalidate();
            }
        };

        button_commit.setOnClickListener(commitAndUpdate);

    }


}