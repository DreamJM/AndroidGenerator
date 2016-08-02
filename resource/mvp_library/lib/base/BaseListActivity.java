package com.dream.android.sample.lib.base;

import android.os.Bundle;
import com.dream.android.sample.lib.widget.CompositePtrView;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.RecyclerAdapterWithHF;

import java.util.List;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/30
 */
public abstract class BaseListActivity<D, RV extends PtrRecyclerViewAdapter<D>> extends BaseActivity {

    CompositePtrView ptrView;

    RecyclerAdapterWithHF mAdapter;

    RV adapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        ptrView = (CompositePtrView) findViewById(getPtrViewId());
        ptrView.setLastUpdateTimeKey(getClass().getName());
        adapter = getAdapter();
        mAdapter = new RecyclerAdapterWithHF(adapter);
        ptrView.setAdapter(mAdapter);
    }

    public void initData(List<D> datas) {
        adapter.initData(datas);
    }

    public void loadMoreComplete(boolean hasMore) {
        ptrView.loadMoreComplete(hasMore);
    }

    public void refreshComplete() {
        ptrView.refreshComplete();
    }

    public void notifyDataSetChanged() {
        if(mAdapter.getItemCountHF() == 0) {
            ptrView.showNoContent();
        } else {
            ptrView.showContent();
        }
        mAdapter.notifyDataSetChanged();
    }

    public void showError() {
        ptrView.showError();
    }

    public void showLoading() {
        ptrView.showLoading();
    }

    public void hideLoading() {
        ptrView.hideLoading();
    }

    public void initCallbacks(CompositePtrView.PtrListener ptrListener) {
        ptrView.setListener(ptrListener);
    }

    public abstract int getPtrViewId();

    public abstract RV getAdapter();
}
