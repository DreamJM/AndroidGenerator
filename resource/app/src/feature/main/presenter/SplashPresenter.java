package com.wafa.android.pei.feature.main.presenter;

import android.os.Handler;
import com.wafa.android.pei.feature.main.view.ISplashView;
import com.wafa.android.pei.lib.base.PerActivity;
import com.wafa.android.pei.lib.base.Presenter;

import javax.inject.Inject;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
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
        new Handler().postDelayed(view::jumpToMain, DELAY_TIME);
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
