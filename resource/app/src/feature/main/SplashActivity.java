package com.dream.android.sample.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import butterknife.BindView;
import com.dream.android.sample.R;
import com.dream.android.sample.base.DIActivity;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.utils.ActivityAnimator;
import com.dream.android.sample.lib.widget.LoadingImageView;
import com.dream.android.sample.model.LaunchItem;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class SplashActivity extends DIActivity {

    private static final long DELAY = 3000;

    @BindView(R.id.iv_splash)
    LoadingImageView ivSplash;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        }, DELAY);
        ivSplash.loadImage("http://img3.douban.com/img/musician/large/4654.jpg");
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

    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        new ActivityAnimator().flipHorizontalAnimation(this);
        finish();
    }
}
