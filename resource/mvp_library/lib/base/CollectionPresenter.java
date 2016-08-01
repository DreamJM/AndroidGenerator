package com.dream.android.sample.lib.base;

import com.dream.android.sample.lib.BaseApplication;
import com.dream.android.sample.lib.R;
import com.dream.android.sample.lib.model.Page;
import com.dream.android.sample.lib.widget.CompositePtrView;
import com.dream.android.sample.lib.widget.pullrefresh.PtrDefaultHandler;
import com.dream.android.sample.lib.widget.pullrefresh.PtrFrameLayout;
import com.dream.android.sample.lib.widget.pullrefresh.loadmore.OnLoadMoreListener;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class CollectionPresenter<T> implements Presenter, CompositePtrView.PtrListener {

    IListView<T> view;

    Page<T> mData = new Page<>();

    public void initListView(IListView<T> view) {
        this.view = view;
        view.initCallbacks(this);
        view.initData(mData.getData());
        view.showLoading();
        loadData(BaseConstants.START_PAGE);
    }

    public abstract void loadData(int page);

    @Override
    public void onRefresh() {
        loadData(BaseConstants.START_PAGE);
    }

    @Override
    public void loadMore() {
        loadData(mData.getCurrentPage() + 1);
    }

    @Override
    public void retry() {
        view.showLoading();
        loadData(BaseConstants.START_PAGE);
    }

    @Override
    public void emptyClicked() {

    }

    public void refreshResult(Page<T> pageResult) {
        if (pageResult.getCurrentPage() == BaseConstants.START_PAGE) {
            if (mData.getCurrentPage() == BaseConstants.INIT_PAGE) {
                view.hideLoading();
            }
            view.refreshComplete();
            mData.clear();
            mData.addPage(pageResult);
            view.notifyDataSetChanged();
            view.loadMoreComplete(mData.canLoadMore());
        } else {
            mData.addPage(pageResult);
            view.notifyDataSetChanged();
            view.loadMoreComplete(mData.canLoadMore() && pageResult.getData().size() >= pageResult.getPageSize());
        }
    }

    protected void showErrorResult() {
        view.refreshComplete();
        if (mData.getCurrentPage() == BaseConstants.INIT_PAGE) { //初始化时失败，则显示错误页面
            view.hideLoading();
            view.showError();
        } else {
            view.loadMoreComplete(mData.canLoadMore());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

}
