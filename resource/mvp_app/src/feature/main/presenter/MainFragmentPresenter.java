package com.dream.android.sample.feature.main.presenter;

import com.dream.android.sample.base.Constants;
import com.dream.android.sample.feature.main.view.IMainFragmentView;
import com.dream.android.sample.interactor.GetContributorsCase;
import com.dream.android.sample.lib.base.CollectionPresenter;
import com.dream.android.sample.lib.executor.DefaultObserver;
import com.dream.android.sample.lib.model.Page;
import com.dream.android.sample.model.Contributor;

import javax.inject.Inject;
import java.util.List;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/6
 */
public class MainFragmentPresenter extends CollectionPresenter<Contributor> {

    IMainFragmentView view;

    GetContributorsCase getContributorsCase;

    @Inject
    public MainFragmentPresenter(GetContributorsCase getContributorsCase) {
        this.getContributorsCase = getContributorsCase;
    }

    public void init(IMainFragmentView view) {
        this.view = view;
        initListView(view);
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
    public void destroy() {
        getContributorsCase.unsubscribe();
    }
}
