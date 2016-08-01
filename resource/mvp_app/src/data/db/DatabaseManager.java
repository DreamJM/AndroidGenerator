package com.dream.android.sample.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
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

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
