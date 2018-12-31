package com.white5703.akyuu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.white5703.akyuu.Dao.Note;
import com.white5703.akyuu.Utility.AkyuuUtility;
import com.white5703.akyuu.Utility.DBTool;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class PerformActivity extends AppCompatActivity {

    List<Note> noteList = new ArrayList<>();
    int cur = 0; //当前在noteList中的位置

    String Tag;

    AutofitTextView tvHide;
    AutofitTextView tvContent;
    Button btnDown;
    Button btnUp;
    Button btnPre;
    Button btnNext;

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

        Intent intent = getIntent();
        Tag = intent.getStringExtra(MainActivity.MESSAGE_TAG);

        tvHide = findViewById(R.id.perform_textView_hide);
        tvContent = findViewById(R.id.perform_textView_content);
        btnDown = findViewById(R.id.perform_button_down);
        btnUp = findViewById(R.id.perform_button_up);
        btnPre = findViewById(R.id.perform_button_pre);
        btnNext = findViewById(R.id.perform_button_next);

        initButtonEvent();
        initData(NextItem(Tag));


    }

    private void initButtonEvent() {
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteList.get(cur).getId() == 999999L) return;
                if(noteList.get(cur).getPriority() <= R.integer.min_priority){
                    Toast toast = Toast.makeText(PerformActivity.this,null
                            ,Toast.LENGTH_SHORT);
                    toast.setText(R.string.performance_already_min_priority);
                    toast.show();
                    return;
                }
                DBTool.decreasePriority(noteList.get(cur).getId());
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteList.get(cur).getId() == 999999L) return;
                if(noteList.get(cur).getPriority() >= R.integer.max_priority){
                    Toast toast = Toast.makeText(PerformActivity.this,null
                            ,Toast.LENGTH_SHORT);
                    toast.setText(R.string.performance_already_max_priority);
                    toast.show();
                    return;
                }
                DBTool.increasePriority(noteList.get(cur).getId());
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur == 0){
                    Toast.makeText(PerformActivity.this,R.string.performance_already_first
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                cur--;
                initData(noteList.get(cur));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur == noteList.size() - 1){
                    initData(NextItem(Tag));
                    cur++;
                    return;
                }
                cur++;
                initData(noteList.get(cur));
            }
        });

        tvContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String string = "Index: " + cur + " ," + "Tag: " + noteList.get(cur).getTag() + " ,"
                        + "Priority: " + noteList.get(cur).getPriority();
                //TODO: 长按修改
                Toast toast = Toast.makeText(PerformActivity.this,null
                        ,Toast.LENGTH_SHORT);
                toast.setText(string);
                toast.show();
                return false;
            }
        });

        tvHide.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String string = "Index: " + cur + " ," + "Tag: " + noteList.get(cur).getTag() + " ,"
                        + "Priority: " + noteList.get(cur).getPriority();
                //TODO: 长按修改
                Toast toast = Toast.makeText(PerformActivity.this,null
                        ,Toast.LENGTH_SHORT);
                toast.setText(string);
                toast.show();
                return false;
            }
        });

    }

    private void initData(Note note){
        tvContent.setText(note.getContent());
        tvHide.setText(note.getHided());
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



