package com.dream.android.sample.lib.base;

import com.dream.android.sample.lib.widget.CompositePtrView;

import java.util.List;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/30
 */
public interface IListView<T> extends IBaseView {

    /**
     * init list data
     */
    void initData(List<T> data);

    /**
     * load more
     * @param hasMore
     */
    void loadMoreComplete(boolean hasMore);

    void refreshComplete();

    void notifyDataSetChanged();

    void showError();

    void showLoading();

    void hideLoading();

    void initCallbacks(CompositePtrView.PtrListener ptrListener);

}
