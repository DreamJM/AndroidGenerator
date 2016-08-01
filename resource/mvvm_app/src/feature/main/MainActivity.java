package com.dream.android.sample.feature.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;
import com.dream.android.sample.R;
import com.dream.android.sample.base.DIActivity;
import com.dream.android.sample.databinding.ActivityMainBinding;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.feature.main.event.TabEvent;
import com.dream.android.sample.lib.base.BaseFragment;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class MainActivity extends DIActivity implements TabEvent{

    private static final String[] FG_TAGS = new String[]{"fgHome", "fgOrder", "fgCarParts", "fgConversation"};

    private static final String KEY_INDEX = "key_index";

    private BaseFragment[] fragments;

    private ObservableInt currentIndex = new ObservableInt(0);

    private ActivityMainBinding binding;

    @Override
    protected void prepareContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setIndex(currentIndex);
        binding.setTabEvent(this);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            fragments = new BaseFragment[]{MainFragment.newInstance(), Tab1Fragment.newInstance(), Tab2Fragment.newInstance(), Tab3Fragment.newInstance()};
            for (int i = 0; i < fragments.length; i++) {
                transaction.add(R.id.fragment_container, fragments[i], FG_TAGS[i]);
                if (i != currentIndex.get()) {
                    transaction.hide(fragments[i]);
                }
            }
        } else {
            fragments = new BaseFragment[]{(BaseFragment) fm.findFragmentByTag(FG_TAGS[0]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[1]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[2]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[3])};
            for (BaseFragment fragment : fragments) {
                transaction.hide(fragment);
                fragment.onHiddenChanged(true);
            }
            currentIndex.set(savedInstanceState.getInt(KEY_INDEX));
            transaction.show(fragments[currentIndex.get()]);
        }
        transaction.commit();
    }

    @BindingAdapter({"buttonSelect"})
    public static void selectButton(View view, boolean selected) {
        view.setSelected(selected);
    }

    @Override
    public void showIndex(int index) {
        if (currentIndex.get() != index) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.hide(fragments[currentIndex.get()]);
            transaction.show(fragments[index]).commit();
            currentIndex.set(index);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex.get());
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.app_name);
    }

}
