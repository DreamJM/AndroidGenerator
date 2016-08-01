package com.dream.android.sample.base;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/5/30
 */

import android.os.Bundle;
import com.dream.android.sample.MainApplication;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.di.component.ApplicationComponent;
import com.dream.android.sample.di.component.DaggerActivityComponent;
import com.dream.android.sample.di.module.ActivityModule;
import com.dream.android.sample.lib.base.BaseListActivity;
import com.dream.android.sample.lib.base.HasComponent;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

/**
 * Description:base dependency injection manager class
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class DIListActivity<D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListActivity<D, RV> implements HasComponent<ActivityComponent> {

    protected ActivityComponent activityComponent;

    protected abstract void injectComponent(ActivityComponent activityComponent);
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        injectComponent(getComponent());
        super.onCreateView(savedInstanceState);
    }

    @Override
    public ActivityComponent getComponent() {
        if(activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build();
        }
        return activityComponent;
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
