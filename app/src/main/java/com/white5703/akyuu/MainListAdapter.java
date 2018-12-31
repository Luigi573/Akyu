package com.white5703.akyuu;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.InfoViewHolder> {

    private List<ItemInfo> itemInfoList;

    private ListInterface listInterface;

    public MainListAdapter(List<ItemInfo> itemInfoList){
        this.itemInfoList = itemInfoList;
    }

    public void listSetOnLongclick(ListInterface listInterface){
        this.listInterface = listInterface;
    }
    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, final int position){
        ItemInfo itemInfo = itemInfoList.get(position);

        holder.vPriority.setText(itemInfo.priority);
        holder.vText.setText(Html.fromHtml(itemInfo.text));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v("Akyuu","OnLongClick() "+listInterface.toString());
                if(listInterface != null){
                    listInterface.onLongClick(v,position);
                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount(){
        return itemInfoList.size();
    }

    public interface ListInterface{
        public void onLongClick(View v, int position);
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{
        protected CardView wCardView;
        protected TextView vPriority;
        protected TextView vText;
        public InfoViewHolder(View itemView){
            super(itemView);
            wCardView = itemView.findViewById(R.id.view_card);
            vPriority = itemView.findViewById(R.id.text_priority);
            vText = itemView.findViewById(R.id.my_text);

        }
    }
}
