package com.white5703.akyuu.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.white5703.akyuu.R;
import com.white5703.akyuu.util.TextSpanUtil;
import java.util.ArrayList;
import java.util.List;

public class LatexListAdapter extends RecyclerView.Adapter<LatexListAdapter.LatexViewHolder> {

    private List<String> latexList = new ArrayList<>();
    private Context context;

    private OnClickInterface onClickInterface;

    public LatexListAdapter(Context context, List<String> list) {
        this.context = context;
        if (list != null) {
            latexList.addAll(list);
        }
    }

    public List<String> getLatexList() {
        return latexList;
    }

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public LatexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_latex,
            parent, false);
        return new LatexViewHolder(itemView);
    }

    @Override public void onBindViewHolder(@NonNull final LatexViewHolder holder, int position) {
        String latex = latexList.get(holder.getAdapterPosition());
        holder.tvLatex.setText(TextSpanUtil.buildLatexImageText(context, holder.tvLatex, latex));
        holder.tvLatex.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onClickInterface.onTextClick(holder.getAdapterPosition());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onClickInterface.onButtonClick(holder.getAdapterPosition());
            }
        });
    }

    @Override public int getItemCount() {
        return latexList.size();
    }

    public interface OnClickInterface {
        void onTextClick(int position);

        void onButtonClick(int position);
    }

    class LatexViewHolder extends RecyclerView.ViewHolder {
        TextView tvLatex;
        Button btnDelete;

        public LatexViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLatex = itemView.findViewById(R.id.dialog_edit_detail_latex_tv);
            btnDelete = itemView.findViewById(R.id.dialog_edit_detail_latex_btn);
        }
    }
}
