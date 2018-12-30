package com.white5703.akyuu;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;
import com.white5703.akyuu.Dao.Note;
import com.white5703.akyuu.Utility.AkyuuUtility;
import com.white5703.akyuu.Utility.DBTool;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MainListAdapter adapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    List<ItemInfo> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        setContentView(R.layout.activity_main);


//        DBTool.clearTableNote();

        mRecyclerView = (RecyclerView) findViewById(R.id.card_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);

        refreshItemList();

        initButtonEvent();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.action_add:
                doAdd();
                return true;
            case R.id.action_refresh:
                refreshItemList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initButtonEvent(){
        adapter.buttonSetOnclick(new MainListAdapter.ButtonInterface() {
            @Override
            public void onClick(View view, int postion, int btnType) {
//                Toast.makeText(MainActivity.this,postion+"",Toast.LENGTH_LONG).show();
                CardView cardView = (CardView) mLayoutManager.findViewByPosition(postion);
                TextView vPriority = cardView.findViewById(R.id.text_priority);
                int nowPriority = Integer.parseInt(vPriority.getText().toString());
                if (btnType == MainListAdapter.BUTTON_TYPE_UP && nowPriority != 9) {
                    vPriority.setText((nowPriority + 1) + "");
                    //increase priority
                    DBTool.increasePriority(mList.get(postion).getId());

                } else if (btnType == MainListAdapter.BUTTON_TYPE_DOWN && nowPriority != 1) {
                    vPriority.setText((nowPriority - 1) + "");
                    //decrease priority
                    DBTool.decreasePriority(mList.get(postion).getId());
                } else if (btnType == MainListAdapter.BUTTON_TYPE_DELETE){
                    DBTool.deleteNote(mList.get(postion).getId());
                }
            }
        });
    }

    private void doAdd() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null);
        final EditText etText = view.findViewById(R.id.dialog_add_text);
        final SwipeSelector slPriority = view.findViewById(R.id.dialog_add_priority);
        final Button btnRed = view.findViewById(R.id.dialog_add_red);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();
                etText.setText(text + "<font color='#FF0000'></font>");
            }
        });
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
                .setNegativeButton(R.string.dialog_add_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.dialog_add_button_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = etText.getText().toString();
                        SwipeItem swipeItem = slPriority.getSelectedItem();
                        int priority = (int)swipeItem.value;
//                        Toast.makeText(MainActivity.this, text + " " + priority, Toast.LENGTH_SHORT).show();

                        DBTool.insertNote(text,priority);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    private void refreshItemList(){
        mList = genItemList();
        adapter = new MainListAdapter(mList);
        mRecyclerView.setAdapter(adapter);
        initButtonEvent();
    }


    private List<ItemInfo> genItemList() {
//        DBTool.clearTableNote();
//        DBTool.insertNote("111",1);
//        DBTool.insertNote("222",2);
//        DBTool.insertNote("333",3);
//        DBTool.insertNote("444",4);

        List<ItemInfo> itemlist = new ArrayList<>();


        List<Note> allNote = DBTool.getNoteList();
        if (allNote.isEmpty()) return itemlist;
        long noteCount = DBTool.getNoteCount();
        long prioritySum = DBTool.getPrioritySum();
        int miniumPriority = AkyuuUtility.getMiniumPriority(allNote);
        //出10次权值随机
        for (int i = 0; i < 10; i++) {
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
                    ItemInfo itemInfo = new ItemInfo();
                    itemInfo.setText(allNote.get(j).getText());
                    itemInfo.setPriority(allNote.get(j).getPriority() + "");
                    itemInfo.setId(allNote.get(j).getId());
                    itemlist.add(itemInfo);
                    break;
                }else if (randomnum > nowsum && randomnum <= nextsum) {
                    ItemInfo itemInfo = new ItemInfo();
                    itemInfo.setText(allNote.get(j + 1).getText());
                    itemInfo.setPriority(allNote.get(j + 1).getPriority() + "");
                    itemInfo.setId(allNote.get(j+1).getId());
                    itemlist.add(itemInfo);
                    break;
                }
            }
        }
        return itemlist;
    }
}



