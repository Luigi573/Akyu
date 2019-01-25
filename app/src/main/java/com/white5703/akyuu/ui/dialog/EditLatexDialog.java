package com.white5703.akyuu.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.white5703.akyuu.R;
import com.white5703.akyuu.util.CommonUtils;

public class EditLatexDialog extends AlertDialog {
    private EditText etLatexText;
    private ImageView ivLatexImage;
    private View view;

    private OnSaveListener onSaveListener;

    private String latex;

    public EditLatexDialog(@NonNull Context context, String latex) {
        super(context);
        this.latex = latex;

        initLayout();
        initData();
        initEvent();
    }

    private void initEvent() {
        setButton(AlertDialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if (onSaveListener != null) {
                    onSaveListener.onSave(etLatexText.getText().toString());
                }
            }
        });

        setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                initData();
                dismiss();
            }
        });

        ivLatexImage.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Picasso.with(getContext())
                    .load(CommonUtils.getLatextUrl(etLatexText.getText().toString()))
                    .into(ivLatexImage);
            }
        });
    }

    private void initData() {
        setView(view);
        setTitle("Edit Latex");
        etLatexText.setText(latex);

        Picasso.with(getContext())
            .load(CommonUtils.getLatextUrl(etLatexText.getText().toString()))
            .into(ivLatexImage);
        initEvent();
    }

    private void initLayout() {
        view = getLayoutInflater().inflate(R.layout.dialog_edit_latex, null);
        etLatexText = view.findViewById(R.id.dialog_edit_latex_et);
        ivLatexImage = view.findViewById(R.id.dialog_edit_latex_iv);
    }

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    public interface OnSaveListener {
        void onSave(String latex);
    }
}
