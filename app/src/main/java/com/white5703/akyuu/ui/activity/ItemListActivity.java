package com.white5703.akyuu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.white5703.akyuu.R;
import com.white5703.akyuu.app.Constant;
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

    String currentTag = "Any";

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
        initListData("Any");
        initEvent();

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
            case R.id.action_add:
                DbManager.insertDefaultNote();
                Note note = DbManager.getLatestNote();
                Intent intent = new Intent(ItemListActivity.this, EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY_ID, note.getId());
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListData(currentTag);
        initEvent();
    }

    @SuppressWarnings("unchecked")
    private void initListData(String tag) {
        //Log.v("Akyuu","initMenuData()");
        currentTag = tag;
        mItemList = DbManager.getNoteList(tag);
        adapter = new ItemListAdapter(mItemList);
        mListRecycleView.setAdapter(adapter);
        initEvent();
    }


    private void initLayout() {
        mListRecycleView = findViewById(R.id.list_rv);
        mListRecycleView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListRecycleView.setLayoutManager(mLayoutManager);
    }

    private void initEvent() {
        adapter.setOnClickListener(new ItemListAdapter.OnClickInterface() {
            @Override public void onClick(View v, long noteId, int position) {
                Intent intent = new Intent(ItemListActivity.this, EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY_ID, noteId);
                startActivity(intent);
            }
        });

        adapter.setOnLongClickListener(new ItemListAdapter.OnLongClickInterface() {
            @Override public void onLongClick(View v, long noteId, int position) {
                showMenuDialog(noteId);
            }
        });

    }

    private void showMenuDialog(final long noteId) {
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setItems(getResources().getStringArray(R.array.item_list_item_long_click_dialog),
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                initListData(DbManager.getNote(noteId).getTag());
                                dialog.dismiss();
                                break;
                            case 1:
                                Intent intent =
                                    new Intent(ItemListActivity.this, EditActivity.class);
                                intent.putExtra(Constant.INTENT_KEY_ID, noteId);
                                startActivity(intent);
                                dialog.dismiss();
                                break;
                            case 2:
                                showDeleteConfirmDialog(noteId);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
            .create();
        dialog.show();
    }

    private void showTagSelectDialog() {
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

    private void showDeleteConfirmDialog(final long noteId) {
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Really delete it?")
            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    DbManager.deleteNote(noteId);
                    initListData(currentTag);
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




}
