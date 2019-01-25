package com.white5703.akyuu.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import com.white5703.akyuu.dao.DaoMaster;
import com.white5703.akyuu.dao.DaoSession;
import com.white5703.akyuu.manager.DbManager;
import java.util.ArrayList;
import java.util.List;

public class AkyuuApplication extends Application {

    private static AkyuuApplication instance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initDao();

        DbManager.clearTableNote();

        DbManager.insertDefaultNote();
        List<String> latexs = new ArrayList<>();
        latexs.add("$$\\int_a^b f(x)\\mathrm{d}x$$");
        latexs.add("\\sum_{i=1}^{n} c_{i}");

        DbManager.insertNote("测试", "测试★\n★", "测试", 5, "测试",
            Gravity.START, 24, Gravity.CENTER, 18, latexs);
    }



    private void initDao() {
        mHelper = new DaoMaster.DevOpenHelper(this,"akyuu2_db",null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public static AkyuuApplication getInstance() {
        return instance;
    }
}
