package com.white5703.akyuu.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.white5703.akyuu.R;
import java.util.List;

public class ItemListMenuAdapter
    extends RecyclerView.Adapter<ItemListMenuAdapter.MenuItemViewHolder> {

    private List<String> menuItemList;

    private OnClickInterface onClickInterface;

    public ItemListMenuAdapter(List<String> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public void setOnClickListener(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    @NonNull @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        Log.v("Akyuu","onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item,
            parent,false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemViewHolder holder, final int position) {
        Log.v("Akyuu","OnBindViewHolder");
        String item = menuItemList.get(holder.getAdapterPosition());

        holder.vText.setText(item);

        holder.vText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.v("Akyuu","OnLongClick() "+listInterface.toString());
                if (onClickInterface != null) {
                    onClickInterface.onClick(v,holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public interface OnClickInterface {
        public void onClick(View v, int position);
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView vText;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            vText = itemView.findViewById(R.id.list_menu_item_textView);
        }
    }
}
