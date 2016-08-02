package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.BaseFragment;
import com.dream.android.sample.lib.base.HasComponent;

/**
 * Description:base dependency injection manager class
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class DIFragment extends BaseFragment {

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
}
