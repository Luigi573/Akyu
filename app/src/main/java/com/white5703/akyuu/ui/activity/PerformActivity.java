package com.white5703.akyuu.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.hippo.drawerlayout.DrawerLayout;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.util.CommonUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        mNavigationView = findViewById(R.id.perform_nav_view);
        mDrawerLayout = findViewById(R.id.perform_drawerLayout);
        tvBrief = findViewById(R.id.perform_textView_brief);
        tvDetail = findViewById(R.id.perform_textView_detail);
        tvNavFooter = findViewById(R.id.perform_nav_footer_tv);
        mFab = findViewById(R.id.perform_fab_next);



        Intent intent = getIntent();
        Tag = intent.getStringExtra(MainActivity.MESSAGE_TAG);

        initEvents();
        initData(nextItem(Tag));

        setHided();


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
                            initData(noteList.get(cur));
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
                            initData(noteList.get(cur));
                            break;
                        case R.id.perform_nav_item_delete:
                            if (noteList.get(cur).getId() == 999999L) {
                                break;
                            }
                            DbManager.deleteNote(noteList.get(cur).getId());
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

        tvDetail.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                String string = "Index: " + cur + " ," + "Tag: " + noteList.get(cur).getTag() + " ,"
                      + "Priority: " + noteList.get(cur).getPriority();
                //TODO: 长按修改
                Toast toast = Toast.makeText(PerformActivity.this,null,
                    Toast.LENGTH_SHORT);
                toast.setText(string);
                toast.show();
                return true;
            }
        });



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
            tvDetail.setText(noteList.get(cur).getDetail());
            tvDetail.setTextSize(18F);
            tvDetail.setGravity(Gravity.LEFT);
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
        tvBrief.setText(note.getBrief());
        tvDetail.setText(note.getDetail());
        tvNavFooter.setText(buildNavFooterText(note));
    }

    //返回下一个随机到的Note并将其加入noteList
    @SuppressWarnings("unchecked")
    private Note nextItem(String Tag) {
        Note ret = null;
        List<Note> allNote = DbManager.getNoteList(Tag);
        if (allNote.isEmpty()) {
            noteList.add(new Note(999999L, "Empty List!", "Empty List!", "Wrong",
                9, new Date(), "null"));
            return new Note(999999L, "Empty List!", "Empty List!", "Wrong",
                9, new Date(), "null");
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

    private int getPriorityColor(int priority) {
        switch (priority) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority1);
                }
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority3);
                }
                break;
            case 5:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority5);
                }
                break;
            case 7:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority7);
                }
                break;
            case 9:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority9);
                }
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorBlack);
                }
                break;
        }

        return 0;
    }

    private SpannableStringBuilder buildNavFooterText(Note note) {
        SpannableString idStr =
            new SpannableString(String.format(Locale.CHINA, "No.%d", note.getId()));
        idStr.setSpan(
            new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_medium)),
            3, idStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString priorityStr =
            new SpannableString(String.format(Locale.CHINA, "Priority:%d", note.getPriority()));
        priorityStr.setSpan(
            new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_medium)),
            9, priorityStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        priorityStr.setSpan(new ForegroundColorSpan(getPriorityColor(note.getPriority())),
            9, priorityStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString tagStr = new SpannableString(note.getTag());
        tagStr.setSpan(new UnderlineSpan(), 0, tagStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tagStr.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, tagStr.length(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString refStr = new SpannableString("");
        boolean flag = false;
        if (!(note.getReference() == null || note.getReference().equals("")
            || note.getReference().equals("null"))) {
            flag = true;
            refStr = new SpannableString(note.getReference());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                refStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorSecondaryLight)),
                    0, refStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        SpannableString timeStr = new SpannableString(CommonUtils.formatDate(note.getUpdatetime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorGrey)),
                0, timeStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        SpannableStringBuilder rtn = new SpannableStringBuilder();
        if (flag) {
            rtn.append(idStr)
                .append("  ")
                .append(priorityStr)
                .append("  ")
                .append(tagStr)
                .append("\n")
                .append(refStr)
                .append("\n")
                .append(timeStr);
        } else {
            rtn.append(idStr)
                .append("  ")
                .append(priorityStr)
                .append("  ")
                .append(tagStr)
                .append("\n")
                .append(timeStr);
        }

        return rtn;
    }

}



