package com.wafa.android.pei.feature.main.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wafa.android.pei.R;
import com.wafa.android.pei.databinding.ItemTestBinding;
import com.wafa.android.pei.lib.widget.LoadingImageView;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;
import com.wafa.android.pei.model.Datum;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/5/31
 */
public class TestAdapter extends PtrRecyclerViewAdapter<Datum> {

    private LayoutInflater inflater;

    public TestAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ChildViewHolder holder = (ChildViewHolder) viewHolder;
        holder.binding.setDatum(datas.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        ItemTestBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_test, viewHolder, false);
        return new ChildViewHolder(binding);
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {

        ItemTestBinding binding;

        public ChildViewHolder(ItemTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
