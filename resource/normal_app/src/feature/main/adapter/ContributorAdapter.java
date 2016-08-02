package com.dream.android.sample.feature.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dream.android.sample.R;
import com.dream.android.sample.lib.widget.LoadingImageView;
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
        ContributorViewHolder holder = (ContributorViewHolder) viewHolder;
        holder.tvName.setText(datas.get(position).getLogin());
        holder.ivAvatar.loadImage(datas.get(position).getAvatar_url());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_contributor, viewHolder, false);
        return new ContributorViewHolder(view);
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.iv_avatar)
        LoadingImageView ivAvatar;

        public ContributorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
