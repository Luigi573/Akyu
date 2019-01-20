package com.white5703.akyuu.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.ui.adapter.ItemListAdapter;
import com.white5703.akyuu.util.CommonUtils;
import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    RecyclerView mListRecycleView;
    ItemListAdapter adapter;
    LinearLayoutManager mLayoutManager;

    List<Note> mItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assert actionBar != null;
            actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorSecondary)));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorSecondary));
        }

        initLayout();
        initEvent();
        initListData("Any");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_select_by_tag:
                showTagSelectDialog();
                return true;
            default:
                return true;
        }
    }

    @SuppressWarnings("unchecked")
    private void initListData(String tag) {
        //Log.v("Akyuu","initMenuData()");
        mItemList = DbManager.getNoteList(tag);
        adapter = new ItemListAdapter(mItemList);
        mListRecycleView.setAdapter(adapter);
    }


    private void initLayout() {
        mListRecycleView = findViewById(R.id.list_rv);
        mListRecycleView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListRecycleView.setLayoutManager(mLayoutManager);
    }

    private void initEvent() {

    }

    private void showTagSelectDialog() {
        //final String[] items = { "我是1","我是2","我是3","我是4" };
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ItemListActivity.this);
        dialog.setTitle("Select by Tag");
        dialog.setItems(CommonUtils.listToArray(DbManager.getTagList()),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initListData(DbManager.getTagList().get(which));
                    dialog.dismiss();
                }
            }
        );
        dialog.show();
    }




}
