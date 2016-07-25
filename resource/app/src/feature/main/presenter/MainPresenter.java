package com.wafa.android.pei.feature.main.presenter;

import com.wafa.android.pei.feature.main.view.IMainView;
import com.wafa.android.pei.lib.base.PerActivity;
import com.wafa.android.pei.lib.base.Presenter;

import javax.inject.Inject;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
@PerActivity
public class MainPresenter implements Presenter {

    IMainView view;

    @Inject
    public MainPresenter() {
    }

    public void init(IMainView view) {
        this.view = view;
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
