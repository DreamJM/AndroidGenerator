package com.dream.android.sample.lib.base;

import javax.inject.Inject;

/**
 * Description:Empty Presenter for DI
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
@PerActivity
public class EmptyPresenter implements Presenter {
    @Inject
    public EmptyPresenter() {};

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
