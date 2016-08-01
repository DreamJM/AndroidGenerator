package com.dream.android.sample.feature.main;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dream.android.sample.R;
import com.dream.android.sample.base.DIFragment;
import com.dream.android.sample.databinding.FragmentTab3Binding;
import com.dream.android.sample.di.component.ActivityComponent;

public class Tab3Fragment extends DIFragment {

    FragmentTab3Binding binding;

    public static Tab3Fragment newInstance() {
        return new Tab3Fragment();
    }

    @Override
    protected View prepareContentView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab3, container, false);
        return binding.getRoot();
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected String getFragmentName() {
        return null;
    }
}

