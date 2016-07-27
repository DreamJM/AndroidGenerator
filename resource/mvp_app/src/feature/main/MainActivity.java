package com.wafa.android.pei.feature.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wafa.android.pei.R;
import com.wafa.android.pei.base.PresenterActivity;
import com.wafa.android.pei.base.PresenterListActivity;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.feature.main.adapter.TestAdapter;
import com.wafa.android.pei.feature.main.presenter.MainPresenter;
import com.wafa.android.pei.feature.main.view.IMainView;
import com.wafa.android.pei.lib.base.BaseFragment;
import com.wafa.android.pei.model.Datum;

import java.util.List;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class MainActivity extends PresenterActivity<MainPresenter> implements IMainView {

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
        presenter.init(this);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            fragments = new BaseFragment[]{TestFragment.newInstance(0), TestFragment.newInstance(1), TestFragment.newInstance(2), TestFragment.newInstance(3)};
            for (int i = 0; i < fragments.length; i++) {
                transaction.add(R.id.fragment_container, fragments[i], FG_TAGS[i]);
                if (i != currentIndex) {
                    transaction.hide(fragments[i]);
                }
            }
        } else { //异常销毁后重建,防止fragment重叠
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
            ButterKnife.apply(tabButtons, (button, position) -> button.setSelected(false));
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
