package com.wafa.android.pei.lib.widget.pullrefresh.recyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author snovajiang
 * @date 16/5/31
 */
public abstract class PtrRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> datas;

    public void initData(List<T> datas) {
        this.datas = datas;
    }
}
