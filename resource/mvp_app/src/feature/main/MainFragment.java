package com.dream.android.sample.feature.main;

import android.os.Bundle;
import com.dream.android.sample.R;
import com.dream.android.sample.base.PresenterListFragment;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.feature.main.adapter.ContributorAdapter;
import com.dream.android.sample.feature.main.presenter.MainFragmentPresenter;
import com.dream.android.sample.feature.main.view.IMainFragmentView;
import com.dream.android.sample.model.Contributor;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/6
 */
public class MainFragment extends PresenterListFragment<MainFragmentPresenter, Contributor, ContributorAdapter> implements IMainFragmentView {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        presenter.init(this);
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
}
