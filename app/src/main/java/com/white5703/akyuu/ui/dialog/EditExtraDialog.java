package com.white5703.akyuu.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.util.CommonUtils;
import com.xw.repo.BubbleSeekBar;

public class EditExtraDialog extends AlertDialog {
    private OnSaveListener onSaveListener;

    private View view;
    private BubbleSeekBar barPriority;
    private EditText etTag;
    private EditText etReference;

    private Note note;

    public EditExtraDialog(@NonNull Context context, long noteId) {
        super(context);

        note = DbManager.getNote(noteId);

        initLayout();
        initData();
        initEvent();
    }

    private void initEvent() {
        setButton(AlertDialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if (onSaveListener != null) {
                    onSaveListener.onSave(barPriority.getProgress(), etTag.getText().toString(),
                        etReference.getText().toString());
                }
            }
        });

        setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                initData();
                dismiss();
            }
        });

        barPriority.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress,
                float progressFloat, boolean fromUser) {
                setBarColor(bubbleSeekBar);
            }

            @Override public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress,
                float progressFloat) {
            }

            @Override public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress,
                float progressFloat, boolean fromUser) {
            }
        });
    }

    private void initData() {
        setView(view);
        setTitle(getContext().getString(R.string.dialog_edit_extra_title));

        barPriority.getConfigBuilder().max(5).min(1).sectionCount(5).touchToSeek()
            .showSectionMark().build();
        barPriority.setProgress(note.getPriority());
        setBarColor(barPriority);

        etTag.setText(note.getTag());
        etReference.setText(note.getTag());
    }

    private void initLayout() {
        view = getLayoutInflater().inflate(R.layout.dialog_edit_extra, null);
        barPriority = view.findViewById(R.id.dialog_edit_extra_bar_priority);
        etTag = view.findViewById(R.id.dialog_edit_extra_tag);
        etReference = view.findViewById(R.id.dialog_edit_extra_reference);
    }

    private void setBarColor(BubbleSeekBar seekBar) {
        int color = CommonUtils.getPriorityColor(seekBar.getProgress());
        seekBar.setTrackColor(color);
        seekBar.setSecondTrackColor(color);
        seekBar.setBubbleColor(color);
        seekBar.setThumbColor(color);
    }

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    public interface OnSaveListener {
        void onSave(int priority, String tag, String reference);
    }
}
