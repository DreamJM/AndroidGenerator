package com.wafa.android.pei.feature.main;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.DIActivity;
import com.wafa.android.pei.databinding.ActivitySplashBinding;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.feature.main.event.SplashEvent;
import com.wafa.android.pei.lib.utils.ActivityAnimator;
import com.wafa.android.pei.lib.utils.IntentUtil;
import com.wafa.android.pei.lib.widget.LoadingImageView;
import com.wafa.android.pei.model.LaunchItem;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class SplashActivity extends DIActivity implements SplashEvent {

    private static final long DELAY = 4000;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        new Handler().postDelayed(this::jumpToMain, DELAY);
        LaunchItem launchItem = new LaunchItem("test", "http://img3.douban.com/img/musician/large/4654.jpg", "http://www.baidu.com");
        binding.setLaunch(launchItem);
        binding.setEvent(this);
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

    /**
     * 跳转至主页面
     */
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        new ActivityAnimator().flipHorizontalAnimation(this);
        finish();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(LoadingImageView imageView, String url) {
        imageView.loadImage(url);
    }

    @Override
    public void showTargetUrl(LaunchItem item) {
        startActivity(IntentUtil.Media.newOpenWebBrowserIntent(item.getTargetUrl()));
    }
}
