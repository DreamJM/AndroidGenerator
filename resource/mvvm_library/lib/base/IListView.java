package com.wafa.android.pei.lib.base;

import com.wafa.android.pei.lib.widget.CompositePtrView;

import java.util.List;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/30
 */
public interface IListView<T> extends IBaseView {

    /**
     * 初始化数据
     */
    void initData(List<T> data);

    /**
     * 加载更多完成
     * @param hasMore 是否有更多数据
     */
    void loadMoreComplete(boolean hasMore);

    /**
     * 下拉刷新完成
     */
    void refreshComplete();

    /**
     * 界面数据更新
     */
    void notifyDataSetChanged();

    /**
     * 显示错误页面
     */
    void showError();

    /**
     * 显示加载中页面
     */
    void showLoading();

    /**
     * 隐藏加载中页面
     */
    void hideLoading();

    /**
     * 设置回调
     */
    void initCallbacks(CompositePtrView.PtrListener ptrListener);

}
