package com.white5703.akyuu;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hippo.drawerlayout.DrawerLayout;
import com.white5703.akyuu.Utility.DBTool;

import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    LinearLayout mDrawerMenu;
    RecyclerView mMenuList;

    ItemListMenuAdapter adapter;

    LinearLayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLightGrey));
        }

        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initLayout();
        initEvent();
        initMenuData();

    }

    private void initMenuData() {
        Log.v("Akyuu","initMenuData()");
        mMenuList.setLayoutManager(new LinearLayoutManager(this));
        List<String> tags = DBTool.getTagList();
        adapter = new ItemListMenuAdapter(tags);
        mMenuList.setAdapter(adapter);
    }


    private void initLayout() {
        mDrawerLayout = findViewById(R.id.activity_list_drawer);
        mDrawerMenu = findViewById(R.id.activity_list_menu);

        mMenuList = findViewById(R.id.activity_list_menu_list);
        mMenuList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMenuList.setLayoutManager(mLayoutManager);

        mMenuList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    private void initEvent() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerSlide(View drawerView, float percent) {
                return;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                initMenuData();
                return;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                return;
            }

            @Override
            public void onDrawerStateChanged(View drawerView, int newState) {
                return;
            }

        });

    }




}
