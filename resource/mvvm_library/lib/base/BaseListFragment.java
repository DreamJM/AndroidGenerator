package com.wafa.android.pei.lib.base;

import android.os.Bundle;
import com.wafa.android.pei.lib.model.Page;
import com.wafa.android.pei.lib.widget.CompositePtrView;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.RecyclerAdapterWithHF;

import java.util.List;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author snovajiang
 * @date 16/5/30
 */
public abstract class BaseListFragment<D, RV extends PtrRecyclerViewAdapter<D>> extends BaseFragment implements CompositePtrView.PtrListener{

    CompositePtrView ptrView;

    RecyclerAdapterWithHF mAdapter;

    Page<D> mData = new Page<>();

    RV adapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        ptrView = (CompositePtrView) rootView.findViewById(getPtrViewId());
        ptrView.setLastUpdateTimeKey(getClass().getName());
        adapter = getAdapter();
        mAdapter = new RecyclerAdapterWithHF(adapter);
        ptrView.setAdapter(mAdapter);
        ptrView.setListener(this);
        adapter.initData(mData.getData());
        //加载显示中
        ptrView.showLoading();
        loadData(BaseConstants.START_PAGE);
    }

    public void notifyDataSetChanged() {
        if(mAdapter.getItemCountHF() == 0) {
            ptrView.showNoContent();
        } else {
            ptrView.showContent();
        }
        mAdapter.notifyDataSetChanged();
    }

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
        ptrView.showLoading();
        loadData(BaseConstants.START_PAGE);
    }

    @Override
    public void emptyClicked() {

    }

    /**
     * 刷新数据信息
     * @param pageResult 分页的数据信息
     */
    protected void refreshResult(Page<D> pageResult) {
        if (pageResult.getCurrentPage() == BaseConstants.START_PAGE) {
            if (mData.getCurrentPage() == BaseConstants.INIT_PAGE) { //如果是初始化获取数据（非刷新情况），则隐藏初始化加载框
                ptrView.hideLoading();
            }
            ptrView.refreshComplete();
            mData.clear();
            mData.addPage(pageResult);
            notifyDataSetChanged();
            ptrView.loadMoreComplete(mData.canLoadMore());
        } else {
            mData.addPage(pageResult);
            notifyDataSetChanged();
            ptrView.loadMoreComplete(mData.canLoadMore() && pageResult.getData().size() >= pageResult.getPageSize());
        }
    }

    protected void showErrorResult() {
        ptrView.refreshComplete();
        if (mData.getCurrentPage() == BaseConstants.INIT_PAGE) { //初始化时失败，则显示错误页面
            ptrView.hideLoading();
            ptrView.showError();
        } else {
            ptrView.loadMoreComplete(mData.canLoadMore());
        }
    }

    public abstract void loadData(int page);

    public abstract int getPtrViewId();

    public abstract RV getAdapter();
}
