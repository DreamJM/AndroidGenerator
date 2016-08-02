package com.dream.android.sample.feature.main;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import com.dream.android.sample.R;
import com.dream.android.sample.base.DIActivity;
import com.dream.android.sample.databinding.ActivitySplashBinding;
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

    private static final long DELAY = 4000;

    private ActivitySplashBinding binding;

    private LaunchItem launchItem;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        }, DELAY);
        launchItem = new LaunchItem("test", "http://img3.douban.com/img/musician/large/4654.jpg", "http://www.google.com");
        binding.setLaunch(launchItem);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchItem.setTitle("hello");
                launchItem.setUrl("http://img2.mtime.cn/up/831/1086831/6C225652-309B-462C-A289-5A23BC0DC2C4_500.jpg");
                launchItem.setTargetUrl("http://www.sina.com.cn");
            }
        }, 2000);
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void prepareContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
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

    @BindingAdapter({"imageUrl"})
    public static void loadImage(LoadingImageView imageView, String url) {
        imageView.loadImage(url);
    }
}
