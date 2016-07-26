package com.wafa.android.pei.feature.main;

import android.os.Bundle;
import android.util.Log;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.PresenterListFragment;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.feature.main.adapter.TestAdapter;
import com.wafa.android.pei.feature.main.presenter.TestPresenter;
import com.wafa.android.pei.feature.main.view.ITestView;
import com.wafa.android.pei.model.Datum;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/6/6
 */
public class TestFragment extends PresenterListFragment<TestPresenter, Datum, TestAdapter> implements ITestView {

    private int index;

    public static TestFragment newInstance(int index) {
        TestFragment testFragment = new TestFragment();
        testFragment.index = index;
        return testFragment;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        presenter.init(this);
        Log.v("jm", String.valueOf(index));
    }

    @Override
    protected String getFragmentName() {
        return getString(R.string.app_name);
    }

    @Override
    public int getPtrViewId() {
        return R.id.ptr_view;
    }

    @Override
    public TestAdapter getAdapter() {
        return new TestAdapter(getActivity());
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_test;
    }
}
