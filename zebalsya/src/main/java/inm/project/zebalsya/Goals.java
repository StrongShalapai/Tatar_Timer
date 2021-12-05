package inm.project.zebalsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Goals extends AppCompatActivity {

    LinearLayout ll_res;
    First_DB fdb;
    Second_DB sdb;
    Goals_DB gdb;
    Goals_raw_DB grdb;
    TextView tv;
    Cursor cursor_grdb;
    Button btn;
    int color = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        init();
        tv_array(cursor_grdb);
    }

    public void init(){
        ll_res = findViewById(R.id.llout_res);
        fdb = new First_DB(this);
        sdb = new Second_DB(this);
        gdb = new Goals_DB(this);
        grdb = new Goals_raw_DB(this);
        tv = new TextView(this);
        btn = findViewById(R.id.edit_btn);
        btn.setOnClickListener(click_btn);
        cursor_grdb = grdb.getData();
        check(cursor_grdb);
    }

    public void check(Cursor cursor){
        if(!cursor.moveToFirst()){
            tv.setVisibility(View.GONE);
            tv.setText("smt");
            ll_res.addView(tv);
        }
    }

    public void asset(){
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        tv1.setText("sample1");
        TextView tv2 = new TextView(this);
        tv2.setText("sample2");
        ll.addView(tv1, lp);
        ll.addView(tv2, lp);
        ll_res.addView(ll, lp);
    }

    public void tv_array(Cursor cursor){
        if(cursor.moveToFirst()){
            int grdbCount = grdb.getProfilesCount();
            TextView[] array = new TextView[grdbCount];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for(int i = 0; i < grdbCount; i = i + 1){
                //TextView tv = new TextView(Goals.this);
                Button tv = new Button(Goals.this);
                String name, done_st, text;
                int hours, minutes, done_int, show;
                tv.setId(cursor.getInt(0));
                name = cursor.getString(1);
                hours = cursor.getInt(4);
                minutes = cursor.getInt(5);
                done_int = cursor.getInt(3);
                show = cursor.getInt(2);
                if(show == 0){
                    tv.setVisibility(View.GONE);
                }
                else {
                    if (done_int == 0) {
                        done_st = "В процессе";
                    } else {
                        done_st = "Выполнено";
                    }
                    text = "\t" + name + "\n\tПродолжительность:\n" + "\t"
                            + hours + "ч. " + minutes + "м." + "\n\t" + done_st;
                    tv.setText(text);

                    /*if (color % 2 == 0) {
                        tv.setBackgroundColor(getResources().getColor(R.color.grey));
                        tv.setTextColor(getResources().getColor(R.color.white));

                    } else {
                        tv.setBackgroundColor(getResources().getColor(R.color.white));
                        tv.setTextColor(getResources().getColor(R.color.grey));
                    }*/
                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                    tv.setTextColor(getResources().getColor(R.color.grey));
                    tv.setOnClickListener(click_for_tv);
                    tv.setOnLongClickListener(longClickListener);


                    color++;
                }
                ll_res.addView(tv);
                cursor_grdb.moveToNext();
            }
        }
        //TextView[] array = new TextView[2];
    }

    View.OnClickListener click_btn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent edit = new Intent(Goals.this, Goals_edit.class);
            startActivity(edit);
        }
    };

    View.OnClickListener click_for_tv = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent edit = new Intent(Goals.this, Goals_edit.class);
            edit.putExtra("id", v.getId());
            edit.putExtra("edit", 1);
            startActivity(edit);
        }
    };

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            showAlertDialog(v.getId());
            return true;
        }
    };

    public void showAlertDialog(int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Удаление");
        alert.setMessage("Вы точно хотите удалить цель?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGoal(id);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    public void deleteGoal(int id){
        grdb.updateShow(0, id);
        updateScreen();
    }

    public void updateScreen(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}