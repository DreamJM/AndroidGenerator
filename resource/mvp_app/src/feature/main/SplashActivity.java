package com.wafa.android.pei.feature.main;

import android.content.Intent;
import android.os.Bundle;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.PresenterActivity;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.feature.main.presenter.SplashPresenter;
import com.wafa.android.pei.feature.main.view.ISplashView;
import com.wafa.android.pei.lib.utils.ActivityAnimator;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class SplashActivity extends PresenterActivity<SplashPresenter> implements ISplashView {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        presenter.init(this);
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String getActivityName() {
        return null;
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        new ActivityAnimator().flipHorizontalAnimation(this);
        finish();
    }
}
