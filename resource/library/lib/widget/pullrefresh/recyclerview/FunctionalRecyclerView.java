package com.wafa.android.pei.lib.widget.pullrefresh.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.wafa.android.pei.lib.R;
import com.wafa.android.pei.lib.widget.hint.ErrorView;
import com.wafa.android.pei.lib.widget.hint.NoContentView;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author snovajiang
 * @date 16/6/1
 */
public class FunctionalRecyclerView extends FrameLayout {

    public RecyclerView recyclerView;

    public NoContentView noContentView;

    public ErrorView errorView;

    public FunctionalRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public FunctionalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FunctionalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        recyclerView = new RecyclerView(context);
        addView(recyclerView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        noContentView = new NoContentView(context);
        addView(noContentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        noContentView.setVisibility(View.GONE);

        errorView = new ErrorView(context);
        addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        errorView.setVisibility(View.GONE);

        String ncContent = null;
        String ncBtnText = null;
        String errContent = null;
        String errBtnText = null;

        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FunctionalRecyclerView);
            ncContent = ta.getString(R.styleable.FunctionalRecyclerView_frv_ncContent);
            ncBtnText = ta.getString(R.styleable.FunctionalRecyclerView_frv_ncBtnText);

            errContent = ta.getString(R.styleable.FunctionalRecyclerView_frv_errorContent);
            errBtnText = ta.getString(R.styleable.FunctionalRecyclerView_frv_errorBtnText);
            ta.recycle();
        }
        if(ncContent == null) ncContent = getContext().getResources().getString(R.string.default_no_content_hint);
        if(errContent == null) errContent = getContext().getResources().getString(R.string.default_error_hint);
        if(errBtnText == null) errBtnText = getContext().getResources().getString(R.string.click_retry);

        noContentView.setContent(ncContent);
        noContentView.setBtnText(ncBtnText);

        errorView.setErrorContent(errContent);
        errorView.setErrorButtonText(errBtnText);
    }

    public void setNoContentHint(String ncContent) {
        noContentView.setContent(ncContent);
    }

    public void setNoContentAction(String ncBtnText) {
        noContentView.setBtnText(ncBtnText);
    }

    public void setErrorContent(String errorContent) {
        errorView.setErrorContent(errorContent);
    }

    public void setErrorAction(String errBtnText) {
        errorView.setErrorButtonText(errBtnText);
    }

    public void showError() {
        recyclerView.setVisibility(View.GONE);
        noContentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void showNoContent() {
        recyclerView.setVisibility(View.GONE);
        noContentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        noContentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void setEmptyListener(NoContentView.EmptyListener emptyListener) {
        noContentView.setListener(emptyListener);
    }

    public void setErrorListener(ErrorView.ErrorListener errorListener) {
        errorView.setListener(errorListener);
    }
}
