package com.wafa.android.pei.lib;

import android.app.Application;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wafa.android.pei.lib.utils.FileUtil;
import com.wafa.android.pei.lib.utils.ViewUtil;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

import java.io.File;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication instance;

    /**
     * 内存泄漏监测管理类
     */
    private RefWatcher refWatcher;

    static { //输入Rx的错误信息，供调试使用
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ViewUtil.init(this);
        initImage();
        //开启内存泄漏监测
        refWatcher = LeakCanary.install(this);
    }

    /**
     * 初始化ImageLoader
     */
    private void initImage() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(80 * 1024 * 1024) // 80 Mb
                .memoryCache(new LruMemoryCache(40 * 1024 * 1024)) // 40 Mb
                .diskCache(new UnlimitedDiskCache(new File(FileUtil.getPicDir(this))))
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatch() {
        return BaseApplication.getInstance().refWatcher;
    }
}
