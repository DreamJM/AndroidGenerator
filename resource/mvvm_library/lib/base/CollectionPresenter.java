package com.wafa.android.pei.lib.base;

import com.wafa.android.pei.lib.BaseApplication;
import com.wafa.android.pei.lib.R;
import com.wafa.android.pei.lib.model.Page;
import com.wafa.android.pei.lib.widget.CompositePtrView;
import com.wafa.android.pei.lib.widget.pullrefresh.PtrDefaultHandler;
import com.wafa.android.pei.lib.widget.pullrefresh.PtrFrameLayout;
import com.wafa.android.pei.lib.widget.pullrefresh.loadmore.OnLoadMoreListener;

/**
 * Description:列表的表现层基类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
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
        //初始化数据
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

    /**
     * 刷新数据信息
     * @param pageResult 分页的数据信息
     */
    public void refreshResult(Page<T> pageResult) {
        if (pageResult.getCurrentPage() == BaseConstants.START_PAGE) {
            if (mData.getCurrentPage() == BaseConstants.INIT_PAGE) { //如果是初始化获取数据（非刷新情况），则隐藏初始化加载框
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
