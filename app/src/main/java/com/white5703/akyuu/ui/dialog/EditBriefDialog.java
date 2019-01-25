package com.white5703.akyuu.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.util.ResourceUtils;
import java.util.Locale;

public class EditBriefDialog extends AlertDialog {
    private EditText etText;
    private SeekBar barSize;
    private EditText etSize;
    private RadioGroup rgGravity;
    private View view;
    private OnSaveListener mOnSaveListener;

    private Note note;

    public EditBriefDialog(@NonNull Context context, long noteId) {
        super(context);

        note = DbManager.getNote(noteId);

        initLayout();
        initData();
        initEvent();
    }

    private void initLayout() {
        view = getLayoutInflater().inflate(R.layout.dialog_edit_brief, null);
        etText = view.findViewById(R.id.dialog_edit_brief_et_text);
        barSize = view.findViewById(R.id.dialog_edit_brief_bar_size);
        etSize = view.findViewById(R.id.dialog_edit_brief_et_size);
        rgGravity = view.findViewById(R.id.dialog_edit_brief_radio_gravity);
    }

    private void initData() {
        setView(view);
        setTitle(ResourceUtils.getString(R.string.dialog_edit_brief_title));
        etText.setText(note.getBrief());
        barSize.setMax(36);
        barSize.setProgress(note.getBrieffontsize());

        etSize.setText(String.format(Locale.CHINA, "%d", note.getBrieffontsize()));

        switch (note.getBriefgravity()) {
            case Gravity.START:
                rgGravity.check(R.id.dialog_edit_brief_radio_gravity_start);
                break;
            case Gravity.CENTER:
                rgGravity.check(R.id.dialog_edit_brief_radio_gravity_center);
                break;
            default:
                break;
        }
    }

    private void initEvent() {
        setButton(AlertDialog.BUTTON_POSITIVE, ResourceUtils.getString(R.string.dialog_edit_btn_ok),
            new OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    if (mOnSaveListener != null) {
                        if (rgGravity.getCheckedRadioButtonId()
                            == R.id.dialog_edit_brief_radio_gravity_center) {
                            mOnSaveListener.onSave(etText.getText().toString(),
                                barSize.getProgress(), Gravity.CENTER);
                        } else {
                            mOnSaveListener.onSave(etText.getText().toString(),
                                barSize.getProgress(), Gravity.START);
                        }
                    }
                }
            });

        setButton(AlertDialog.BUTTON_NEGATIVE,
            ResourceUtils.getString(R.string.dialog_edit_btn_cancel),
            new OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    initData();
                    dismiss();
                }
            });

        barSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etSize.setText(String.format(Locale.CHINA, "%d", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        mOnSaveListener = onSaveListener;
    }

    public interface OnSaveListener {
        void onSave(String text, int size, int gravity);
    }
}
