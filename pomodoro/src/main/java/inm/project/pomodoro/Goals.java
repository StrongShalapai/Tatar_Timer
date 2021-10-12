package inm.project.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Goals extends AppCompatActivity {

    LinearLayout ll_res;
    First_DB fdb;
    Second_DB sdb;
    Goals_DB gdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        init();
        asset();
    }

    public void init(){
        ll_res = findViewById(R.id.llout_res);
        fdb = new First_DB(this);
        sdb = new Second_DB(this);
        gdb = new Goals_DB(this);
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
}