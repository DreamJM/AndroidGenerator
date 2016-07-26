package com.wafa.android.pei.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.wafa.android.pei.lib.R;
import com.wafa.android.pei.lib.widget.hint.ErrorView;
import com.wafa.android.pei.lib.widget.hint.NoContentView;
import com.wafa.android.pei.lib.widget.loading.LoadingView;
import com.wafa.android.pei.lib.widget.pullrefresh.PtrCustomFrameLayout;
import com.wafa.android.pei.lib.widget.pullrefresh.PtrFrameLayout;
import com.wafa.android.pei.lib.widget.pullrefresh.PtrHandler;
import com.wafa.android.pei.lib.widget.pullrefresh.loadmore.OnLoadMoreListener;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.FunctionalRecyclerView;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.RecyclerAdapterWithHF;

/**
 * Description:复合功能的列表控件，可以进行下拉刷新、上拉加载更多、
 * 加载中表示、加载失败表示、空列表提示表示。
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/6/1
 */
public class CompositePtrView extends FrameLayout implements OnLoadMoreListener, ErrorView.ErrorListener, NoContentView.EmptyListener{

    /**
     * 下拉刷新、上拉加载控件
     */
    PtrCustomFrameLayout ptrLayout;

    /**
     * 带有加载失败和空列表提示的列表控件
     */
    FunctionalRecyclerView rvFunction;

    /**
     * 加载中控件
     */
    LoadingView loadingView;

    /**
     * 带有Header和Footer定义的Adpater
     */
    RecyclerAdapterWithHF mAdapter;

    /**
     * 本控件的监听器
     */
    private PtrListener listener;

    /**
     * 是否启用下拉刷新
     */
    private boolean ptrEnable;

    /**
     * 是否启用上拉加载更多
     */
    private boolean loadMoreEnable;

    /**
     * 自定义下拉刷新的处理类
     */
    PtrHandler ptrHandler = new PtrHandler() {

        /**
         * 是否可以进行下拉刷新
         */
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return ptrEnable && !canChildScrollUp(content);
        }

        /**
         * 刷新开始
         */
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            if(listener != null) {
                listener.onRefresh();
            }
        }

        /**
         * 判断自控件是否可以向上滚动
         */
        public boolean canChildScrollUp(View view) {
            FunctionalRecyclerView frv = (FunctionalRecyclerView) view;
            if(frv.errorView.getVisibility() != View.VISIBLE && frv.noContentView.getVisibility() != View.VISIBLE) {
                //如果显示列表信息，则需要判断列表是否可以向上滚动
                if (android.os.Build.VERSION.SDK_INT < 14) {
                    return ViewCompat.canScrollVertically(frv.recyclerView, -1) || frv.recyclerView.getScrollY() > 0;
                } else {
                    return ViewCompat.canScrollVertically(frv.recyclerView, -1);
                }
            } else { //如果显示出错或空列表信息，则控件必然不可向上滚动
                return false;
            }
        }
    };

    public CompositePtrView(Context context) {
        super(context);
        init(context, null);
    }

    public CompositePtrView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CompositePtrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //使用Stub并通过属性来决定哪些控件将被加载
        ViewStub stubPtr = new ViewStub(context);
        stubPtr.setLayoutResource(R.layout.viewstub_ptr); //刷新控件
        addView(stubPtr, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ViewStub stubFrv = new ViewStub(context);
        stubFrv.setLayoutResource(R.layout.viewstub_frv); //列表空间
        addView(stubFrv, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ViewStub stubLoading = new ViewStub(context);
        stubLoading.setLayoutResource(R.layout.viewstub_loading); //加载中控件
        addView(stubLoading, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        String loadingHint = null;
        ptrEnable = true;
        loadMoreEnable = true;
        boolean loadingEnable = true;
        String ncContent = null;
        String ncBtnText = null;
        String errContent = null;
        String errBtnText = null;

        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CompositePtrView);
            ncContent = ta.getString(R.styleable.CompositePtrView_cptr_ncContent);
            ncBtnText = ta.getString(R.styleable.CompositePtrView_cptr_ncBtnText);

            errContent = ta.getString(R.styleable.CompositePtrView_cptr_errorContent);
            errBtnText = ta.getString(R.styleable.CompositePtrView_cptr_errorBtnText);

            loadingHint = ta.getString(R.styleable.CompositePtrView_cptr_loading);

            ptrEnable = ta.getBoolean(R.styleable.CompositePtrView_cptr_ptr_enable, true);
            loadMoreEnable = ta.getBoolean(R.styleable.CompositePtrView_cptr_loadmore_enable, true);
            loadingEnable = ta.getBoolean(R.styleable.CompositePtrView_cptr_loading_enable, true);
            ta.recycle();
        }

        if(!ptrEnable && !loadMoreEnable) { //如果不需要下拉刷新和上拉加载，则直接使用recyclerview
            rvFunction = (FunctionalRecyclerView) stubFrv.inflate();
        } else {
            ptrLayout = (PtrCustomFrameLayout) stubPtr.inflate();
            rvFunction = (FunctionalRecyclerView) ptrLayout.getContentView();
            ptrLayout.setOnLoadMoreListener(this);//加载更多监听
        }
        rvFunction.setEmptyListener(this); //空列表事件监听
        rvFunction.setErrorListener(this); //错误重试事件监听
        rvFunction.recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //默认使用线性垂直布局

        if(ncContent != null) {
            rvFunction.setNoContentHint(ncContent); //设置空列表提示语
        }

        if(ncBtnText != null) {
            rvFunction.setNoContentAction(ncBtnText); //空列表按钮
        }

        if(errContent != null) {
            rvFunction.setErrorContent(errContent); //错误提示语
        }

        if(errBtnText != null) {
            rvFunction.setErrorAction(errBtnText); //错误按钮
        }
        if(loadingEnable) {
            loadingView = (LoadingView) stubLoading.inflate();
            loadingView.setHint(loadingHint);
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置RecyclerView的布局管理器
     * @param layoutManager 布局管理器
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        rvFunction.recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        if(loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏加载中
     */
    public void hideLoading() {
        if(loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示错误页面
     */
    public void showError() {
        rvFunction.showError();
    }

    /**
     * 显示空列表提示页面
     */
    public void showNoContent() {
        rvFunction.showNoContent();
    }

    /**
     * 显示列表内容
     */
    public void showContent() {
        rvFunction.showContent();
    }

    /**
     * 设置此控件的监听器
     */
    public void setListener(PtrListener listener) {
        this.listener = listener;
    }

    /**
     * 设置RecyclerView的监听器
     */
    public void setAdapter(RecyclerAdapterWithHF adapter) {
        mAdapter = adapter;
        rvFunction.recyclerView.setAdapter(mAdapter);
        if(ptrLayout != null) {
            ptrLayout.setPtrHandler(ptrHandler);
        }
    }

    /**
     * 加载更多完成
     * @param hasMore 是否有更多数据
     */
    public void loadMoreComplete(boolean hasMore) {
        if(loadMoreEnable && ptrLayout != null) {
            ptrLayout.setLoadMoreEnable(loadMoreEnable);
            ptrLayout.loadMoreComplete(hasMore);
        }
    }

    /**
     * 下拉刷新完成
     */
    public void refreshComplete() {
        if(ptrLayout != null) {
            ptrLayout.refreshComplete();
        }
    }

    /**
     * 下拉刷新的时间显示设置
     */
    public void setLastUpdateTimeKey(String key) {
        ptrLayout.setLastUpdateTimeKey(key);
    }

    @Override
    public void loadMore() {
        if(listener != null) listener.loadMore();
    }

    @Override
    public void onEmptyClicked() {
        if(listener != null) listener.emptyClicked();
    }

    @Override
    public void onErrorClicked() {
        if(listener != null) listener.retry();
    }

    public interface PtrListener {

        /**
         * 加载更多
         */
        void loadMore();

        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 错误重试
         */
        void retry();

        /**
         * 空列表事件
         */
        void emptyClicked();
    }
}
