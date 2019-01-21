package com.white5703.akyuu.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.white5703.akyuu.dao.DaoMaster;
import com.white5703.akyuu.dao.DaoSession;

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

        //DbManager.insertNote("测试","测试","测试",5,"中文测试1111");
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
