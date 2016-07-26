package com.wafa.android.pei.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.DIActivity;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.lib.utils.ActivityAnimator;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class SplashActivity extends DIActivity {

    private static final long DELAY = 3000;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        new Handler().postDelayed(this::jumpToMain, DELAY);
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

    /**
     * 跳转至主页面
     */
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        new ActivityAnimator().flipHorizontalAnimation(this);
        finish();
    }
}
