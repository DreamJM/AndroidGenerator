package com.wafa.android.pei.base;

import android.os.Bundle;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.lib.base.BaseListFragment;
import com.wafa.android.pei.lib.base.HasComponent;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

/**
 * Created by snovajiang on 15/9/13.
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
