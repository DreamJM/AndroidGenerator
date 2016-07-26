package com.wafa.android.pei.feature.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wafa.android.pei.R;
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
        holder.itemTv.setText(datas.get(position).getName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_test, viewHolder, false);
        return new ChildViewHolder(view);
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTv;

        public ChildViewHolder(View view) {
            super(view);
            itemTv = (TextView) view;
        }
    }
}
