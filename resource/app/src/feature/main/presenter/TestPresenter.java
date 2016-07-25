package com.wafa.android.pei.feature.main.presenter;

import com.wafa.android.pei.feature.main.view.ITestView;
import com.wafa.android.pei.interactor.TestCase;
import com.wafa.android.pei.lib.base.CollectionPresenter;
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
public class TestPresenter extends CollectionPresenter<Datum> {

    ITestView view;

    TestCase testCase;

    @Inject
    public TestPresenter(TestCase testCase) {
        this.testCase = testCase;
    }

    public void init(ITestView view) {
        this.view = view;
        initListView(view);
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
    public void destroy() {
        testCase.unsubscribe();
    }
}
