package com.wafa.android.pei.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.DIListFragment;
import com.wafa.android.pei.databinding.FragmentTestBinding;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.feature.main.adapter.TestAdapter;
import com.wafa.android.pei.interactor.TestCase;
import com.wafa.android.pei.lib.base.PerActivity;
import com.wafa.android.pei.lib.executor.DefaultObserver;
import com.wafa.android.pei.lib.model.Page;
import com.wafa.android.pei.model.Datum;

import javax.inject.Inject;
import java.util.List;

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
public class TestFragment extends DIListFragment<Datum, TestAdapter> {

    private int index;

    @Inject
    TestCase testCase;

    private FragmentTestBinding binding;

    public static TestFragment newInstance(int index) {
        TestFragment testFragment = new TestFragment();
        testFragment.index = index;
        return testFragment;
    }

    @Override
    public void loadData(final int pageIndex) {
        testCase.execute(observer);
    }

    DefaultObserver<List<Datum>> observer = new DefaultObserver<List<Datum>>() {
        @Override
        public void onNext(List<Datum> data) {
            refreshResult(new Page<>(data));
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            showErrorResult();
        }
    };

    @Override
    protected View prepareContentView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false);
        return binding.getRoot();
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
    public void onDestroy() {
        super.onDestroy();
        testCase.unsubscribe();
    }
}
