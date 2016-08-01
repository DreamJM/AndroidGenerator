package com.dream.android.sample.lib;

import android.app.Application;
import com.dream.android.sample.lib.utils.FileUtil;
import com.dream.android.sample.lib.utils.ViewUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

import java.io.File;

public abstract class BaseApplication extends Application {

    private static BaseApplication instance;

    /**
     * Memory Leak monitor
     */
    private RefWatcher refWatcher;

    static { //output error information of RxJava for debug
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
