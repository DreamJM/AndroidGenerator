package com.wafa.android.pei.base;

import android.os.Bundle;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.lib.base.BaseFragment;
import com.wafa.android.pei.lib.base.HasComponent;

/**
 * Created by snovajiang on 15/9/13.
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
