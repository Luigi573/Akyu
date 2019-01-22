package com.white5703.akyuu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;
import com.white5703.akyuu.R;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.ui.adapter.MainSpinnerAdapter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final  String MESSAGE_TAG = "com.white5703.akyuu.MESSAGE_TAG";

    List<String> list;
    MainSpinnerAdapter adapter;

    ConstraintLayout btnStart;
    ConstraintLayout btnList;
    ConstraintLayout btnSetting;

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

        //DbManager.clearTableNote();
        //DbManager.insertNote("Test1","Test1","测试",1);
        //DbManager.insertNote("Test9","Test9","测试",9);
        //DbManager.insertNote("Tset1","Tset1","试测",1);
        //DbManager.insertNote("Tset9","Tset9","试测",9);

        initSpinnerData();

        initLayout();
        initButtonEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();

        initSpinnerData();
    }

    private void initSpinnerData() {
        adapter = new MainSpinnerAdapter(this,DbManager.getTagList());

        Spinner spinner = findViewById(R.id.main_spinner);
        spinner.setAdapter(adapter);
    }


    private void initLayout() {
        btnStart = findViewById(R.id.activity_main_start);
        btnList = findViewById(R.id.activity_main_list);
        btnSetting = findViewById(R.id.activity_main_setting);
    }

    private void initButtonEvent() {
        // set start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PerformActivity.class);
                Spinner spinner = findViewById(R.id.main_spinner);
                String tag = spinner.getSelectedItem().toString();
                intent.putExtra(MESSAGE_TAG,tag);
                startActivity(intent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ItemListActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doAdd();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doAdd() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null);
        final EditText etContent = view.findViewById(R.id.dialog_add_content);
        final EditText etHide = view.findViewById(R.id.dialog_add_hide);
        final EditText etTag = view.findViewById(R.id.dialog_add_tag);
        final SwipeSelector slPriority = view.findViewById(R.id.dialog_add_priority);

        slPriority.setItems(
                new SwipeItem(1,"1",""),
                new SwipeItem(2,"2",""),
                new SwipeItem(3,"3",""),
                new SwipeItem(4,"4",""),
                new SwipeItem(5,"5",""),
                new SwipeItem(6,"6",""),
                new SwipeItem(7,"7",""),
                new SwipeItem(8,"8",""),
                new SwipeItem(9,"9",""));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.dialog_add_title)
                .setNegativeButton(R.string.dialog_add_button_cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                .setPositiveButton(R.string.dialog_add_button_confirm,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String content = etContent.getText().toString();
                            String hide = etHide.getText().toString();
                            String tag = etTag.getText().toString();
                            SwipeItem swipeItem = slPriority.getSelectedItem();
                            int priority = (int)swipeItem.value;
                            DbManager.insertNote(content, hide, tag, priority, "");
                            initSpinnerData();
                            dialog.dismiss();
                        }
                    })
                .create();
        dialog.show();



    }
}

