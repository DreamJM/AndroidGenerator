package com.dream.android.sample.lib.widget.pullrefresh.loadmore;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.FunctionalRecyclerView;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.RecyclerAdapterWithHF;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/1
 */
public class CustomFunctionViewHandler implements ViewHandler {

    @Override
    public boolean handleSetAdapter(View contentView, ILoadViewMoreFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        final FunctionalRecyclerView functionView = (FunctionalRecyclerView) contentView;
        boolean hasInit = false;
        final RecyclerView recyclerView = (RecyclerView) functionView.getChildAt(0);
        final RecyclerAdapterWithHF adapter = (RecyclerAdapterWithHF) recyclerView.getAdapter();
        if (loadMoreView != null) {
            final Context context = recyclerView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadViewMoreFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    adapter.addFooter(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
        }
        return hasInit;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final FunctionalRecyclerView functionView = (FunctionalRecyclerView) contentView;
        RecyclerView recyclerView = (RecyclerView) functionView.getChildAt(0);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScollBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        private boolean isScollBottom(RecyclerView recyclerView) {
            FunctionalRecyclerView parent = (FunctionalRecyclerView)recyclerView.getParent();
            if(parent.errorView.getVisibility() != View.GONE || parent.recyclerView.getVisibility() != View.GONE) {
                return !isCanScollVertically(recyclerView);
            } else {
                return false;
            }
        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            if (android.os.Build.VERSION.SDK_INT < 14) {
                return ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
            } else {
                return ViewCompat.canScrollVertically(recyclerView, 1);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

    }

}
