package com.white5703.akyuu;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemListMenuAdapter extends RecyclerView.Adapter<ItemListMenuAdapter.MenuItemViewHolder> {

    private List<String> menuItemList;

    private onClickInterface onClickInterface;

    public ItemListMenuAdapter(List<String> menuItemList){
        this.menuItemList = menuItemList;
    }

    public void setOnClickListener(onClickInterface onClickInterface){
        this.onClickInterface = onClickInterface;
    }
    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        Log.v("Akyuu","onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item
                ,parent,false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, final int position){
        Log.v("Akyuu","OnBindViewHolder");
        String item = menuItemList.get(position);

        holder.vText.setText(item);

        holder.vText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("Akyuu","OnLongClick() "+listInterface.toString());
                if(onClickInterface != null){
                    onClickInterface.onClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return menuItemList.size();
    }

    public interface onClickInterface{
        public void onClick(View v, int position);
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView vText;
        public MenuItemViewHolder(View itemView){
            super(itemView);
            vText = itemView.findViewById(R.id.list_menu_item_textView);
        }
    }
}
