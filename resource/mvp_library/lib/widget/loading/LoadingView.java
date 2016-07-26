package com.wafa.android.pei.lib.widget.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.wafa.android.pei.lib.R;

/**
 * Description: 三色球旋转效果的加载控件
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class LoadingView extends FrameLayout {

    public LoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_loading, this);
        setOnClickListener(this::block);
    }

    public void setHint(String hint) {

    }

    private void block(View v) {}
}
