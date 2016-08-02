package com.dream.android.sample.feature.main;

import com.dream.android.sample.R;
import com.dream.android.sample.base.Constants;
import com.dream.android.sample.base.DIListFragment;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.feature.main.adapter.ContributorAdapter;
import com.dream.android.sample.interactor.GetContributorsCase;
import com.dream.android.sample.lib.executor.DefaultObserver;
import com.dream.android.sample.lib.model.Page;
import com.dream.android.sample.model.Contributor;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/6
 */
public class MainFragment extends DIListFragment<Contributor, ContributorAdapter> {

    @Inject
    GetContributorsCase getContributorsCase;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void loadData(final int pageIndex) {
        getContributorsCase.execute(Constants.OWNER, Constants.REPO, observer);
    }

    DefaultObserver<List<Contributor>> observer = new DefaultObserver<List<Contributor>>() {
        @Override
        public void onNext(List<Contributor> data) {
            refreshResult(new Page<>(data));
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            showErrorResult();
        }
    };

    @Override
    protected String getFragmentName() {
        return getString(R.string.app_name);
    }

    @Override
    public int getPtrViewId() {
        return R.id.ptr_view;
    }

    @Override
    public ContributorAdapter getAdapter() {
        return new ContributorAdapter(getActivity());
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContributorsCase.unsubscribe();
    }
}
