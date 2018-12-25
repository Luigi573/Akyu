package com.white5703.akyuu;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daquexian.flexiblerichtextview.FlexibleRichTextView;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.InfoViewHolder> {

    public static final int BUTTON_TYPE_DOWN = 0;
    public static final int BUTTON_TYPE_UP = 1;
    public static final int BUTTON_TYPE_DELETE = 2;
    private List<ItemInfo> itemInfoList;

    private ButtonInterface buttonInterface;

    public MainListAdapter(List<ItemInfo> itemInfoList){
        this.itemInfoList = itemInfoList;
    }

    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface = buttonInterface;
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
        holder.vText.setText(itemInfo.text);

        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null)
                {
                    buttonInterface.onClick(v,position,MainListAdapter.BUTTON_TYPE_DOWN);
                }
            }
        });

        holder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null)
                {
                    buttonInterface.onClick(v,position,MainListAdapter.BUTTON_TYPE_UP);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null)
                {
                    buttonInterface.onClick(v,position,MainListAdapter.BUTTON_TYPE_DELETE);
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return itemInfoList.size();
    }

    public interface ButtonInterface{
        public void onClick(View view, int position, int btnType);
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPriority;
        protected FlexibleRichTextView vText;
        protected Button btnUp;
        protected Button btnDown;
        protected Button btnDelete;
        public InfoViewHolder(View itemView){
            super(itemView);
            vPriority = itemView.findViewById(R.id.text_priority);
            vText = itemView.findViewById(R.id.my_text);
            btnUp = itemView.findViewById(R.id.button_up);
            btnDown = itemView.findViewById(R.id.button_down);
            btnDelete = itemView.findViewById(R.id.button_delete);

        }
    }
}
