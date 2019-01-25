package com.white5703.akyuu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.hippo.drawerlayout.DrawerLayout;
import com.white5703.akyuu.R;
import com.white5703.akyuu.app.Constant;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.util.CommonUtils;
import com.white5703.akyuu.util.TextSpanUtil;
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
    TextView tvNavFooter;

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

        initLayout();
        initEvents();
        initData(nextItem(Tag));

        setHided();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(noteList.get(cur));
        initEvents();
        setHided();
    }

    private void initLayout() {
        mNavigationView = findViewById(R.id.perform_nav_view);
        mDrawerLayout = findViewById(R.id.perform_drawerLayout);
        tvBrief = findViewById(R.id.perform_textView_brief);
        tvDetail = findViewById(R.id.perform_textView_detail);
        tvNavFooter = findViewById(R.id.perform_nav_footer_tv);
        mFab = findViewById(R.id.perform_fab_next);
    }

    private void initEvents() {
        mNavigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    int maxPriority = getResources().getInteger(R.integer.max_priority);
                    int minPriority = getResources().getInteger(R.integer.min_priority);

                    switch (menuItem.getItemId()) {
                        case R.id.perform_nav_item_up:
                            if (noteList.get(cur).getId() == 999999L) {
                                break;
                            }

                            if (noteList.get(cur).getPriority() >= maxPriority) {
                                Toast toast = Toast.makeText(PerformActivity.this, null,
                                    Toast.LENGTH_SHORT);
                                toast.setText(R.string.performance_already_max_priority);
                                toast.show();
                                break;
                            }
                            DbManager.increasePriority(noteList.get(cur).getId());
                            tvNavFooter.setText(TextSpanUtil.buildNavFooterText(noteList.get(cur)));
                            break;
                        case R.id.perform_nav_item_down:
                            if (noteList.get(cur).getId() == 999999L) {
                                break;
                            }
                            if (noteList.get(cur).getPriority() <= minPriority) {
                                Toast toast = Toast.makeText(PerformActivity.this, null,
                                    Toast.LENGTH_SHORT);
                                toast.setText(R.string.performance_already_min_priority);
                                toast.show();
                                break;
                            }
                            DbManager.decreasePriority(noteList.get(cur).getId());
                            tvNavFooter.setText(TextSpanUtil.buildNavFooterText(noteList.get(cur)));
                            break;
                        case R.id.perform_nav_item_delete:
                            if (noteList.get(cur).getId() == 999999L) {
                                break;
                            }
                            showDeleteConfirmDialog();
                            break;
                        case R.id.perform_nav_item_pre:
                            if (cur == 0) {
                                String noticeFirst = getResources()
                                    .getString(R.string.performance_already_first);
                                Toast.makeText(PerformActivity.this, noticeFirst,
                                    Toast.LENGTH_SHORT).show();
                                break;
                            }

                            cur--;
                            initData(noteList.get(cur));
                            setHided();
                            break;
                        case R.id.perform_nav_item_edit:
                            Intent intent = new Intent(PerformActivity.this, EditActivity.class);
                            intent.putExtra(Constant.INTENT_KEY_ID, noteList.get(cur).getId());
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                    mDrawerLayout.closeDrawers();
                    menuItem.setChecked(false);
                    return true;
                }
            }
        );

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cur == noteList.size() - 1) {
                    initData(nextItem(Tag));
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
    }

    private void showDeleteConfirmDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Really delete it?")
            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    DbManager.deleteNote(noteList.get(cur).getId());
                    dialog.dismiss();
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create();
        dialog.show();
    }

    private void setHided() {
        tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_grey));
        tvDetail.setText(R.string.performance_hint_hided);
        tvDetail.setTextSize(56F);
        tvDetail.setGravity(Gravity.CENTER);
        hideFlag = 1;
    }

    private void toggleHideStatus() {
        if (hideFlag == 1) {
            tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_light_grey));
            tvDetail.setText(TextSpanUtil.buildDetailText(this, noteList.get(cur), tvDetail));
            tvDetail.setGravity(noteList.get(cur).getDetailgravity());
            hideFlag = 0;
        } else {
            tvDetail.setBackground(getResources().getDrawable(R.drawable.ripple_grey));
            tvDetail.setText(R.string.performance_hint_hided);
            tvDetail.setTextSize(56F);
            tvDetail.setGravity(Gravity.CENTER);
            hideFlag = 1;
        }
    }


    private void initData(Note note) {
        tvBrief.setGravity(note.getBriefgravity());
        tvBrief.setText(TextSpanUtil.buildBriefText(note));
        tvDetail.setGravity(note.getDetailgravity());
        tvDetail.setText(TextSpanUtil.buildDetailText(this, note, tvDetail));
        tvNavFooter.setText(TextSpanUtil.buildNavFooterText(note));
    }

    //返回下一个随机到的Note并将其加入noteList
    @SuppressWarnings("unchecked")
    private Note nextItem(String Tag) {
        Note ret = null;
        List<Note> allNote = DbManager.getNoteList(Tag);
        if (allNote.isEmpty()) {
            Note note = CommonUtils.getDefaultNote();
            note.setId(999L);
            noteList.add(note);
            return note;
        }
        long noteCount = allNote.size();
        long prioritySum = getPrioritySum(allNote);
        int miniumPriority = CommonUtils.getMinimumPriority(allNote);

        long randomnum = CommonUtils.genRandomNumber(miniumPriority, prioritySum);
        long nowsum = 0;
        long nextsum = 0;
        for (int j = 0; j < noteCount; j++) {
            nowsum += allNote.get(j).getPriority();
            if (j == noteCount - 1) {
                nextsum = nowsum;
            } else {
                nextsum = nowsum + allNote.get(j + 1).getPriority();
            }

            if (randomnum <= nowsum) {
                ret = allNote.get(j);
                noteList.add(ret);
                break;
            } else if (randomnum <= nextsum) {
                ret = allNote.get(j + 1);
                noteList.add(ret);
                break;
            }
        }
        return ret;

    }

    private long getPrioritySum(List<Note> list) {
        long sum = 0L;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getPriority();
        }
        return sum;
    }

}



