package com.white5703.akyuu.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.util.CommonUtils;
import java.util.List;

public class ItemListAdapter
    extends RecyclerView.Adapter<ItemListAdapter.MenuItemViewHolder> {

    private List<Note> itemList;

    private OnClickInterface onClickInterface;
    private OnLongClickInterface onLongClickInterface;

    public ItemListAdapter(List<Note> itemList) {
        this.itemList = itemList;
    }

    public void setOnClickListener(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    public void setOnLongClickListener(OnLongClickInterface onLongClickInterface) {
        this.onLongClickInterface = onLongClickInterface;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        //Log.v("Akyuu","onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
            parent,false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemViewHolder holder, final int position) {
        //Log.v("Akyuu","OnBindViewHolder");
        final Note item = itemList.get(holder.getAdapterPosition());

        holder.tvBrief.setText(item.getBrief());
        holder.tvDetial.setText(item.getDetail());
        holder.tvTag.setText(item.getTag());
        holder.tvTime.setText(CommonUtils.formatDate(item.getUpdatetime()));

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (onClickInterface != null) {
                    onClickInterface.onClick(v, item.getId(), holder.getAdapterPosition());
                }
            }
        });

        holder.cardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                onLongClickInterface.onLongClick(v, item.getId(), holder.getAdapterPosition());
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnClickInterface {
        void onClick(View v, long noteId, int position);
    }

    public interface OnLongClickInterface {
        void onLongClick(View v, long noteId, int position);
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardItem;
        TextView tvBrief;
        TextView tvDetial;
        TextView tvTag;
        TextView tvTime;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.list_item_cv);
            tvBrief = itemView.findViewById(R.id.list_item_tv_brief);
            tvDetial = itemView.findViewById(R.id.list_item_tv_detail);
            tvTag = itemView.findViewById(R.id.list_item_tv_tag);
            tvTime = itemView.findViewById(R.id.list_item_tv_time);
        }
    }
}
