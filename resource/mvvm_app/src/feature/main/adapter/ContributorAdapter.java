package com.dream.android.sample.feature.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.dream.android.sample.R;
import com.dream.android.sample.databinding.ItemContributorBinding;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;
import com.dream.android.sample.model.Contributor;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/31
 */
public class ContributorAdapter extends PtrRecyclerViewAdapter<Contributor> {

    private LayoutInflater inflater;

    public ContributorAdapter(Context context) {
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
        holder.binding.setContributor(datas.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        ItemContributorBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_contributor, viewHolder, false);
        return new ChildViewHolder(binding);
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {

        ItemContributorBinding binding;

        public ChildViewHolder(ItemContributorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
