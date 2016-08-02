package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.BaseListFragment;
import com.dream.android.sample.lib.base.HasComponent;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class DIListFragment<D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListFragment<D, RV> {

    protected abstract void injectComponent(ActivityComponent activityComponent);

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        injectComponent(getComponent(ActivityComponent.class));
        super.onCreateView(savedInstanceState);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
