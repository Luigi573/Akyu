package com.white5703.akyuu;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_main_2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        DBTool.clearTableNote();

        adapter = new MainSpinnerAdapter(this
                ,getResources().getStringArray(R.array.spinner_test));

        Spinner spinner = findViewById(R.id.main_spinner);
        spinner.setAdapter(adapter);





    }
}

