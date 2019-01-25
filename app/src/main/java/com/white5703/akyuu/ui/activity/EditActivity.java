package com.white5703.akyuu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.white5703.akyuu.R;
import com.white5703.akyuu.app.Constant;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.manager.DbManager;
import com.white5703.akyuu.ui.dialog.EditBriefDialog;
import com.white5703.akyuu.ui.dialog.EditDetailDialog;
import com.white5703.akyuu.ui.dialog.EditExtraDialog;
import com.white5703.akyuu.util.ResourceUtils;
import com.white5703.akyuu.util.TextSpanUtil;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    FloatingActionButton fabEditExtra;
    FloatingActionButton fabConfirm;
    FloatingActionButton fabDelete;
    TextView tvBrief;
    TextView tvDetail;

    EditBriefDialog dialogBrief;
    EditDetailDialog dialogDetail;
    EditExtraDialog dialogExtra;
    Intent intent;
    private Note mNote;
    private Note mTempNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLightGrey));
        }
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = getIntent();
        mNote = DbManager.getNote(intent.getLongExtra(Constant.INTENT_KEY_ID, 1));
        mTempNote = mNote.getTempCopy();

        initLayout();
        initEvent();
        initData(mTempNote);
    }

    @Override
    public void onBackPressed() {
        if (mTempNote.equal(mNote)) {
            finish();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(ResourceUtils.getString(R.string.dialog_edit_title_exit))
                .setMessage(ResourceUtils.getString(R.string.dialog_edit_msg_exit))
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_edit_btn_save),
                    new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            saveNote();
                            finish();
                        }
                    })
                .setNegativeButton(ResourceUtils.getString(R.string.dialog_edit_btn_discard),
                    new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                .setNeutralButton(ResourceUtils.getString(R.string.dialog_edit_btn_cancel),
                    new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                .create();
            dialog.show();
        }
    }

    private void saveNote() {
        mNote.setBrief(mTempNote.getBrief());
        mNote.setBrieffontsize(mTempNote.getBrieffontsize());
        mNote.setBriefgravity(mTempNote.getBriefgravity());
        mNote.setDetail(mTempNote.getDetail());
        mNote.setDetailfontsize(mTempNote.getDetailfontsize());
        mNote.setDetailgravity(mTempNote.getDetailgravity());
        mNote.setTag(mTempNote.getTag());
        mNote.setPriority(mTempNote.getPriority());
        mNote.setReference(mTempNote.getReference());
        mNote.setLatexs(mTempNote.getLatexs());
        mNote.setUpdatetime(new Date());
        DbManager.updateNote(mNote);
    }

    private void initData(Note note) {
        tvBrief.setGravity(note.getBriefgravity());
        tvBrief.setText(TextSpanUtil.buildBriefText(note));
        tvDetail.setGravity(note.getDetailgravity());
        tvDetail.setText(TextSpanUtil.buildDetailText(this, note, tvDetail));
    }

    private void initEvent() {
        tvBrief.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialogBrief.show();
            }
        });

        dialogBrief.setOnSaveListener(new EditBriefDialog.OnSaveListener() {
            @Override public void onSave(String text, int size, int gravity) {
                //Log.v("Akyuu","OnSave()");
                mTempNote.setBrief(text);
                mTempNote.setBrieffontsize(size);
                mTempNote.setBriefgravity(gravity);
                initData(mTempNote);
            }
        });

        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialogDetail.show();
            }
        });

        dialogDetail.setOnSaveListener(new EditDetailDialog.OnSaveListener() {
            @Override
            public void onSave(String text, int size, int gravity, List<String> latex) {
                mTempNote.setDetail(text);
                mTempNote.setDetailfontsize(size);
                mTempNote.setDetailgravity(gravity);
                mTempNote.setLatexs(latex);
                initData(mTempNote);
            }
        });

        fabEditExtra.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialogExtra.show();
            }
        });

        dialogExtra.setOnSaveListener(new EditExtraDialog.OnSaveListener() {
            @Override public void onSave(int priority, String tag, String reference) {
                mTempNote.setPriority(priority);
                mTempNote.setTag(tag);
                mTempNote.setReference(reference);
                initData(mTempNote);
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showDeleteConfirmDialog();
            }
        });

        fabConfirm.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initLayout() {
        fabEditExtra = findViewById(R.id.edit_fab_edit_extra);
        fabConfirm = findViewById(R.id.edit_fab_confirm);
        fabDelete = findViewById(R.id.edit_fab_delete);
        tvBrief = findViewById(R.id.edit_tv_brief);
        tvDetail = findViewById(R.id.edit_tv_detail);

        dialogBrief = new EditBriefDialog(EditActivity.this, mNote.getId());
        dialogDetail = new EditDetailDialog(EditActivity.this, mNote.getId());
        dialogExtra = new EditExtraDialog(EditActivity.this, mNote.getId());
    }

    private void showDeleteConfirmDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Really delete it?")
            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    DbManager.deleteNote(mNote.getId());
                    dialog.dismiss();
                    finish();
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create();
        dialog.show();
    }
}
