package com.dream.android.sample.feature.main;

import com.dream.android.sample.R;
import com.dream.android.sample.base.PresenterFragment;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.EmptyPresenter;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/7/29
 */
public class Tab1Fragment extends PresenterFragment<EmptyPresenter> {

    public static Tab1Fragment newInstance() {
        return new Tab1Fragment();
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {}

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_tab1;
    }

    @Override
    protected String getFragmentName() {
        return null;
    }
}
