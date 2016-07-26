package com.wafa.android.pei.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description:数据库总管理类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
@Singleton
public class DatabaseManager {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Inject
    public DatabaseManager(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "buyer-pei", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * @return 数据库会话对象（通过DaoSession可以取得DAO）
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
