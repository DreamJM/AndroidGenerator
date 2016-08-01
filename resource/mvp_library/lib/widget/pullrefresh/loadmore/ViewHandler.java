package com.dream.android.sample.lib.widget.pullrefresh.loadmore;

import android.view.View;
import android.view.View.OnClickListener;

public interface ViewHandler {

	/**
	 * 
	 * @param view
	 * @param adapter
	 * @param loadMoreView
	 * @param onClickListener
	 * @return 是否有 init ILoadMoreView
	 */
	public boolean handleSetAdapter(View contentView, ILoadViewMoreFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

}
