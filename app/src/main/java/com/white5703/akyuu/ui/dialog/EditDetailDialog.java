package com.white5703.akyuu.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.ui.adapter.LatexListAdapter;
import com.white5703.akyuu.util.ResourceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDetailDialog extends AlertDialog {

    private OnSaveListener mOnSaveListener;

    private EditLatexDialog dialogLatex;

    private View view;
    private EditText etText;
    private SeekBar barSize;
    private EditText etSize;
    private RadioGroup rgGravity;
    private TextView tvLatexTitle;
    private Button btnLatexAdd;
    private RecyclerView rvLatex;

    private LatexListAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private Note note;
    private List<String> mTempLatexs = new ArrayList<>();

    public EditDetailDialog(@NonNull Context context, long noteId) {
        super(context);

        note = DbManager.getNote(noteId);

        initLayout();
        initData();
        initEvent();
    }

    private void initLayout() {
        view = getLayoutInflater().inflate(R.layout.dialog_edit_detail, null);
        etText = view.findViewById(R.id.dialog_edit_detail_et_text);
        barSize = view.findViewById(R.id.dialog_edit_detail_bar_size);
        etSize = view.findViewById(R.id.dialog_edit_detail_et_size);
        rgGravity = view.findViewById(R.id.dialog_edit_detail_radio_gravity);
        tvLatexTitle = view.findViewById(R.id.dialog_edit_detail_tv_latex_title);
        btnLatexAdd = view.findViewById(R.id.dialog_edit_detail_btn_latex_add);
        rvLatex = view.findViewById(R.id.dialog_edit_detail_rv_latex);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLatex.setLayoutManager(mLayoutManager);
    }

    private void initData() {
        setView(view);
        setTitle(ResourceUtils.getString(R.string.dialog_edit_detail_title));
        etText.setText(note.getDetail());
        barSize.setMax(72);
        barSize.setProgress(note.getDetailfontsize());

        etSize.setText(String.format(Locale.CHINA, "%d", note.getDetailfontsize()));

        switch (note.getDetailgravity()) {
            case Gravity.START:
                rgGravity.check(R.id.dialog_edit_detail_radio_gravity_start);
                break;
            case Gravity.CENTER:
                rgGravity.check(R.id.dialog_edit_detail_radio_gravity_center);
                break;
            default:
                break;
        }

        mTempLatexs.clear();
        if (note.getLatexs() != null) {
            mTempLatexs.addAll(note.getLatexs());
        }

        adapter = new LatexListAdapter(getContext(), note.getLatexs());
        rvLatex.setAdapter(adapter);
        initEvent();
    }

    private void initEvent() {
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.v("Akyuu",s.toString());
                String str = s.toString();
                Pattern pattern = Pattern.compile("ppp");
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    int selection = matcher.end() - "ppp".length() + 1;
                    str = matcher.replaceAll("★");
                    etText.setText(str);
                    etText.setSelection(selection);
                }
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
        setButton(AlertDialog.BUTTON_POSITIVE, ResourceUtils.getString(R.string.dialog_edit_btn_ok),
            new OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    if (mOnSaveListener != null) {
                        if (rgGravity.getCheckedRadioButtonId()
                            == R.id.dialog_edit_detail_radio_gravity_center) {
                            mOnSaveListener.onSave(etText.getText().toString(),
                                barSize.getProgress(), Gravity.CENTER, mTempLatexs);
                        } else {
                            mOnSaveListener.onSave(etText.getText().toString(),
                                barSize.getProgress(), Gravity.START, mTempLatexs);
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

        btnLatexAdd.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTempLatexs.add("DEFAULT");
                adapter = new LatexListAdapter(getContext(), mTempLatexs);
                rvLatex.setAdapter(adapter);
                initEvent();
                dialogLatex = new EditLatexDialog(getContext(),
                    mTempLatexs.get(mTempLatexs.size() - 1));
                dialogLatex.show();
            }
        });

        adapter.setOnClickInterface(new LatexListAdapter.OnClickInterface() {
            @Override
            public void onTextClick(final int position) {
                dialogLatex = new EditLatexDialog(getContext(), mTempLatexs.get(position));
                dialogLatex.setOnSaveListener(new EditLatexDialog.OnSaveListener() {
                    @Override public void onSave(String latex) {
                        mTempLatexs.set(position, latex);
                        adapter = new LatexListAdapter(getContext(), mTempLatexs);
                        rvLatex.setAdapter(adapter);
                        initEvent();
                        dialogLatex.dismiss();
                    }
                });
                dialogLatex.show();
            }

            @Override
            public void onButtonClick(int position) {
                mTempLatexs.remove(position);
                adapter = new LatexListAdapter(getContext(), mTempLatexs);
                rvLatex.setAdapter(adapter);
                initEvent();
            }
        });
    }

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        mOnSaveListener = onSaveListener;
    }

    public interface OnSaveListener {
        void onSave(String text, int size, int gravity, List<String> latex);
    }
}
