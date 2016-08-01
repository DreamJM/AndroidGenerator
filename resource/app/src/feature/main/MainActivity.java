package com.dream.android.sample.feature.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dream.android.sample.R;
import com.dream.android.sample.base.DIActivity;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.lib.base.BaseFragment;

import java.util.List;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class MainActivity extends DIActivity {

    private static final String[] FG_TAGS = new String[]{"fgHome", "fgOrder", "fgCarParts", "fgConversation"};

    private static final int[] TAB_IDS = new int[]{R.id.btn_home, R.id.btn_tab1, R.id.btn_msg, R.id.btn_tab2};

    private static final String KEY_INDEX = "key_index";

    private BaseFragment[] fragments;

    @BindViews({R.id.btn_home, R.id.btn_tab1, R.id.btn_msg, R.id.btn_tab2})
    List<Button> tabButtons;

    private int currentIndex = 0;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            fragments = new BaseFragment[]{MainFragment.newInstance(), Tab1Fragment.newInstance(), Tab2Fragment.newInstance(), Tab3Fragment.newInstance()};
            for (int i = 0; i < fragments.length; i++) {
                transaction.add(R.id.fragment_container, fragments[i], FG_TAGS[i]);
                if (i != currentIndex) {
                    transaction.hide(fragments[i]);
                }
            }
        } else {
            fragments = new BaseFragment[]{(BaseFragment) fm.findFragmentByTag(FG_TAGS[0]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[1]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[2]), (BaseFragment) fm.findFragmentByTag(FG_TAGS[3])};
            for (BaseFragment fragment : fragments) {
                transaction.hide(fragment);
                fragment.onHiddenChanged(true);
            }
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            transaction.show(fragments[currentIndex]);
        }
        transaction.commit();
        tabButtons.get(currentIndex).setSelected(true);
    }

    public void changeFragment(int index) {
        if (currentIndex != index) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.hide(fragments[currentIndex]);
            transaction.show(fragments[index]).commit();
            currentIndex = index;
            ButterKnife.apply(tabButtons, new ButterKnife.Action<Button>() {
                @Override
                public void apply(@NonNull Button button, int i) {
                    button.setSelected(false);
                }
            });
            tabButtons.get(currentIndex).setSelected(true);
        }
    }

    @OnClick({R.id.btn_home, R.id.btn_tab1, R.id.btn_msg, R.id.btn_tab2})
    public void onTabClicked(View view) {
        if (!view.isSelected()) {
            int index;
            for (index = 0; index < TAB_IDS.length; index++) {
                if (TAB_IDS[index] == view.getId()) {
                    break;
                }
            }
            changeFragment(index);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    protected void injectComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.app_name);
    }

}
