package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.BaseFragment;
import com.dream.android.sample.lib.base.HasComponent;
import com.dream.android.sample.lib.base.Presenter;

import javax.inject.Inject;

/**
 * Description:base presenter manager class for fragment(inject presenter automatically)
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class PresenterFragment<T extends Presenter> extends BaseFragment {

    @Inject
    protected T presenter;

    protected boolean isHidden;

    protected abstract void injectComponent(ActivityComponent activityComponent);

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        injectComponent(getComponent(ActivityComponent.class));
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter != null && !isHidden) presenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) presenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(presenter != null && !isHidden) presenter.pause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isHidden = hidden;
        if(presenter != null) {
            if(hidden) {
                presenter.pause();
            } else {
                presenter.resume();
            }
        }
    }
}
