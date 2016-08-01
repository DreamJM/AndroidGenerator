package com.dream.android.sample.feature.main.presenter;

import com.dream.android.sample.feature.main.view.IMainView;
import com.dream.android.sample.lib.base.PerActivity;
import com.dream.android.sample.lib.base.Presenter;

import javax.inject.Inject;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
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
