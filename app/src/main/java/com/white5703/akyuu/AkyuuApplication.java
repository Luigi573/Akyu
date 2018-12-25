package com.white5703.akyuu;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.white5703.akyuu.Dao.DaoMaster;
import com.white5703.akyuu.Dao.DaoSession;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

import io.github.kbiakov.codeview.classifier.CodeProcessor;

public class AkyuuApplication extends Application {
    private static AkyuuApplication instance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSeesion;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;

        initDao();
//        initRichTextView();
//        Toast.makeText(this,"application created",Toast.LENGTH_SHORT).show();
    }



    private void initDao() {
        mHelper = new DaoMaster.DevOpenHelper(this,"akyuu_db",null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSeesion = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSeesion;
    }

    public SQLiteDatabase getDb(){
        return db;
    }

    public static AkyuuApplication getInstance(){
        return instance;
    }
}
