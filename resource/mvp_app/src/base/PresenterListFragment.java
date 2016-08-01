package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.*;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Description:base presenter manager for list-style fragment(inject presenter automatically)
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class PresenterListFragment<T extends CollectionPresenter, D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListFragment<D, RV> {

    @Inject
    protected T presenter;

    protected boolean isHidden;

    protected abstract void injectComponent(ActivityComponent activityComponent);

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
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
