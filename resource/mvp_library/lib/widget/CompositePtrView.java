package com.dream.android.sample.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.dream.android.sample.lib.R;
import com.dream.android.sample.lib.widget.hint.ErrorView;
import com.dream.android.sample.lib.widget.hint.NoContentView;
import com.dream.android.sample.lib.widget.loading.LoadingView;
import com.dream.android.sample.lib.widget.pullrefresh.PtrCustomFrameLayout;
import com.dream.android.sample.lib.widget.pullrefresh.PtrFrameLayout;
import com.dream.android.sample.lib.widget.pullrefresh.PtrHandler;
import com.dream.android.sample.lib.widget.pullrefresh.loadmore.OnLoadMoreListener;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.FunctionalRecyclerView;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.RecyclerAdapterWithHF;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/1
 */
public class CompositePtrView extends FrameLayout implements OnLoadMoreListener, ErrorView.ErrorListener, NoContentView.EmptyListener{

    PtrCustomFrameLayout ptrLayout;

    FunctionalRecyclerView rvFunction;

    LoadingView loadingView;

    RecyclerAdapterWithHF mAdapter;

    private PtrListener listener;

    private boolean ptrEnable;

    private boolean loadMoreEnable;

    PtrHandler ptrHandler = new PtrHandler() {

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return ptrEnable && !canChildScrollUp(content);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            if(listener != null) {
                listener.onRefresh();
            }
        }

        public boolean canChildScrollUp(View view) {
            FunctionalRecyclerView frv = (FunctionalRecyclerView) view;
            if(frv.errorView.getVisibility() != View.VISIBLE && frv.noContentView.getVisibility() != View.VISIBLE) {
                if (android.os.Build.VERSION.SDK_INT < 14) {
                    return ViewCompat.canScrollVertically(frv.recyclerView, -1) || frv.recyclerView.getScrollY() > 0;
                } else {
                    return ViewCompat.canScrollVertically(frv.recyclerView, -1);
                }
            } else {
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
        ViewStub stubPtr = new ViewStub(context);
        stubPtr.setLayoutResource(R.layout.viewstub_ptr);
        addView(stubPtr, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ViewStub stubFrv = new ViewStub(context);
        stubFrv.setLayoutResource(R.layout.viewstub_frv);
        addView(stubFrv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ViewStub stubLoading = new ViewStub(context);
        stubLoading.setLayoutResource(R.layout.viewstub_loading);
        addView(stubLoading, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

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

        if(!ptrEnable && !loadMoreEnable) {
            rvFunction = (FunctionalRecyclerView) stubFrv.inflate();
        } else {
            ptrLayout = (PtrCustomFrameLayout) stubPtr.inflate();
            rvFunction = (FunctionalRecyclerView) ptrLayout.getContentView();
            ptrLayout.setOnLoadMoreListener(this);
        }
        rvFunction.setEmptyListener(this);
        rvFunction.setErrorListener(this);
        rvFunction.recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //默认使用线性垂直布局

        if(ncContent != null) {
            rvFunction.setNoContentHint(ncContent);
        }

        if(ncBtnText != null) {
            rvFunction.setNoContentAction(ncBtnText);
        }

        if(errContent != null) {
            rvFunction.setErrorContent(errContent);
        }

        if(errBtnText != null) {
            rvFunction.setErrorAction(errBtnText);
        }
        if(loadingEnable) {
            loadingView = (LoadingView) stubLoading.inflate();
            loadingView.setHint(loadingHint);
            loadingView.setVisibility(View.GONE);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        rvFunction.recyclerView.setLayoutManager(layoutManager);
    }

    public void showLoading() {
        if(loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading() {
        if(loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    public void showError() {
        rvFunction.showError();
    }

    public void showNoContent() {
        rvFunction.showNoContent();
    }

    public void showContent() {
        rvFunction.showContent();
    }

    public void setListener(PtrListener listener) {
        this.listener = listener;
    }

    public void setAdapter(RecyclerAdapterWithHF adapter) {
        mAdapter = adapter;
        rvFunction.recyclerView.setAdapter(mAdapter);
        if(ptrLayout != null) {
            ptrLayout.setPtrHandler(ptrHandler);
        }
    }

    public void loadMoreComplete(boolean hasMore) {
        if(loadMoreEnable && ptrLayout != null) {
            ptrLayout.setLoadMoreEnable(loadMoreEnable);
            ptrLayout.loadMoreComplete(hasMore);
        }
    }

    public void refreshComplete() {
        if(ptrLayout != null) {
            ptrLayout.refreshComplete();
        }
    }

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

        void loadMore();

        void onRefresh();

        void retry();

        void emptyClicked();
    }
}
