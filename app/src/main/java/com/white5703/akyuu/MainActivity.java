package com.white5703.akyuu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;

import com.white5703.akyuu.Utility.DBTool;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String MESSAGE_TAG = "com.white5703.akyuu.MESSAGE_TAG";

    List<String> list;
    MainSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLightGrey));
        }
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        DBTool.clearTableNote();
        DBTool.insertNote("Test1","Test1","测试",1);
        DBTool.insertNote("Test9","Test9","测试",9);
        DBTool.insertNote("Tset1","Tset1","试测",1);
        DBTool.insertNote("Tset9","Tset9","试测",9);

        adapter = new MainSpinnerAdapter(this
                ,DBTool.getTagList());

        Spinner spinner = findViewById(R.id.main_spinner);
        spinner.setAdapter(adapter);

        initButtonEvent();

    }

    private void initButtonEvent() {
        // set start button
        ConstraintLayout constraintLayout = findViewById(R.id.activity_main_start);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PerformActivity.class);
                Spinner spinner = findViewById(R.id.main_spinner);
                String tag = spinner.getSelectedItem().toString();
                intent.putExtra(MESSAGE_TAG,tag);
                startActivity(intent);
            }
        });
    }
}

