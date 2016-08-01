package com.dream.android.sample.feature.main.presenter;

import android.os.Handler;
import com.dream.android.sample.feature.main.view.ISplashView;
import com.dream.android.sample.lib.base.PerActivity;
import com.dream.android.sample.lib.base.Presenter;
import com.dream.android.sample.model.LaunchItem;

import javax.inject.Inject;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
@PerActivity
public class SplashPresenter implements Presenter {

    private static final int DELAY_TIME = 3000;

    ISplashView view;

    @Inject
    public SplashPresenter() {

    }

    public void init(ISplashView view) {
        this.view = view;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashPresenter.this.view.jumpToMain();
            }
        }, DELAY_TIME);
        view.showLaunch(new LaunchItem("hello", "http://img3.douban.com/img/musician/large/4654.jpg", "http://www.google.com"));
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
