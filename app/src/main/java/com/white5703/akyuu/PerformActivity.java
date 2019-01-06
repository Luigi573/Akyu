package com.white5703.akyuu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hippo.drawerlayout.DrawerLayout;
import com.white5703.akyuu.Dao.Note;
import com.white5703.akyuu.Utility.AkyuuUtility;
import com.white5703.akyuu.Utility.DBTool;

import java.util.ArrayList;
import java.util.List;

public class PerformActivity extends AppCompatActivity {

    List<Note> noteList = new ArrayList<>();
    int cur = 0; //当前在noteList中的位置

    String Tag;

    int hideFlag = 1;

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    FloatingActionButton mFab;
    TextView tvBrief;
    TextView tvDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLightGrey));
        }
        setContentView(R.layout.activity_perform);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mNavigationView = findViewById(R.id.perform_nav_view);
        mDrawerLayout = findViewById(R.id.perform_drawerLayout);
        tvBrief = findViewById(R.id.perform_textView_brief);
        tvDetail = findViewById(R.id.perform_textView_detail);
        mFab = findViewById(R.id.perform_fab_next);


        Intent intent = getIntent();
        Tag = intent.getStringExtra(MainActivity.MESSAGE_TAG);

        initEvents();
        initData(NextItem(Tag));

        setHided();


    }

    private void initEvents() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.perform_nav_item_up:
                        if(noteList.get(cur).getId() == 999999L) break;
                        if(noteList.get(cur).getPriority() >= getResources().getInteger(R.integer.max_priority)){
                            Toast toast = Toast.makeText(PerformActivity.this,null
                                    ,Toast.LENGTH_SHORT);
                            toast.setText(R.string.performance_already_max_priority);
                            toast.show();
                            break;
                        }
                        DBTool.increasePriority(noteList.get(cur).getId());
                        break;
                    case R.id.perform_nav_item_down:
                        if(noteList.get(cur).getId() == 999999L) break;
                        if(noteList.get(cur).getPriority() <= getResources().getInteger(R.integer.min_priority)){
                            Toast toast = Toast.makeText(PerformActivity.this,null
                                    ,Toast.LENGTH_SHORT);
                            toast.setText(R.string.performance_already_min_priority);
                            toast.show();
                            break;
                        }
                        DBTool.decreasePriority(noteList.get(cur).getId());
                        break;
                    case R.id.perform_nav_item_delete:
                        if(noteList.get(cur).getId() == 999999L) break;
                        DBTool.deleteNote(noteList.get(cur).getId());
                        break;
                    case R.id.perform_nav_item_pre:
                        if(cur == 0){
                            Toast.makeText(PerformActivity.this,R.string.performance_already_first
                                    ,Toast.LENGTH_SHORT).show();
                            break;
                        }

                        cur--;
                        initData(noteList.get(cur));
                        setHided();
                        break;
                    case R.id.perform_nav_item_next:
                        if(cur == noteList.size() - 1){
                            initData(NextItem(Tag));
                            cur++;
                            setHided();
                            break;
                        }

                        cur++;
                        initData(noteList.get(cur));
                        setHided();
                        break;
                }
                mDrawerLayout.closeDrawers();
                menuItem.setChecked(false);
                return true;
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur == noteList.size() - 1){
                    initData(NextItem(Tag));
                    cur++;
                    setHided();
                    return;
                }

                cur++;
                initData(noteList.get(cur));
                setHided();
            }
        });

        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHideStatus();
            }
        });

        tvDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String string = "Index: " + cur + " ," + "Tag: " + noteList.get(cur).getTag() + " ,"
                        + "Priority: " + noteList.get(cur).getPriority();
                //TODO: 长按修改
                Toast toast = Toast.makeText(PerformActivity.this,null
                        ,Toast.LENGTH_SHORT);
                toast.setText(string);
                toast.show();
                return true;
            }
        });


    }

    private void setHided(){
        tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_grey));
        tvDetail.setText(R.string.performance_hint_hided);
        tvDetail.setTextSize(56F);
        tvDetail.setGravity(Gravity.CENTER);
        hideFlag = 1;
    }
    private void toggleHideStatus(){
        if(hideFlag == 1){
            tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_light_grey));
            tvDetail.setText(noteList.get(cur).getHided());
            tvDetail.setTextSize(18F);
            tvDetail.setGravity(Gravity.LEFT);
            hideFlag = 0;
        }else{
            tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_grey));
            tvDetail.setText(R.string.performance_hint_hided);
            tvDetail.setTextSize(56F);
            tvDetail.setGravity(Gravity.CENTER);
            hideFlag = 1;
        }
    }


    private void initData(Note note){
        tvBrief.setText(note.getContent());
        tvDetail.setText(note.getHided());
    }

    //返回下一个随机到的Note并将其加入noteList
    private Note NextItem(String Tag){
        Note ret = null;
        List<Note> allNote = DBTool.getNoteList(Tag);
        if (allNote.isEmpty()){
            noteList.add(new Note(999999L,"Empty List!","Empty List!","Wrong",9));
            return new Note(999999L,"Empty List!","Empty List!","Wrong",9);
        }
        long noteCount = allNote.size();
        long prioritySum = getPrioritySum(allNote);
        int miniumPriority = AkyuuUtility.getMiniumPriority(allNote);

        long randomnum = AkyuuUtility.genRandomNumber(miniumPriority, prioritySum);
        long nowsum = 0;
        long nextsum = 0;
        for (int j = 0; j < noteCount; j++) {
            nowsum += allNote.get(j).getPriority();
            if(j == noteCount - 1){
                nextsum = nowsum;
            }else {
                nextsum = nowsum + allNote.get(j+1).getPriority();
            }

            if (randomnum <= nowsum) {
                ret = allNote.get(j);
                noteList.add(ret);
                break;
            }else if (randomnum > nowsum && randomnum <= nextsum) {
                ret = allNote.get(j+1);
                noteList.add(ret);
                break;
            }
        }
        return ret;

    }

    private long getPrioritySum(List<Note> list){
        long sum = 0L;
        for(int i = 0; i < list.size(); i++) sum += list.get(i).getPriority();
        return sum;
    }

}



