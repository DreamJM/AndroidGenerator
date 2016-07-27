package com.wafa.android.pei.base;

import android.os.Bundle;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.lib.base.BaseFragment;
import com.wafa.android.pei.lib.base.BaseListFragment;
import com.wafa.android.pei.lib.base.HasComponent;
import com.wafa.android.pei.lib.base.Presenter;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Created by snovajiang on 15/9/13.
 */
public abstract class PresenterListFragment<T extends Presenter, D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListFragment<D, RV> {

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
