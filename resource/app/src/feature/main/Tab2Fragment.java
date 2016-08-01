package com.dream.android.sample.feature.main;

import com.dream.android.sample.R;
import com.dream.android.sample.base.DIFragment;
import com.dream.android.sample.di.component.ActivityComponent;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/7/28
 */
public class Tab2Fragment extends DIFragment {

    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_tab2;
    }

    @Override
    protected String getFragmentName() {
        return null;
    }
}
