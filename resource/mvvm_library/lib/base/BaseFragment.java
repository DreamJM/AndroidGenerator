package com.dream.android.sample.lib.base;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.dream.android.sample.lib.BaseApplication;
import com.dream.android.sample.lib.R;

/**
 * Description:Base class for every Fragment in this application.
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class BaseFragment extends Fragment {

    protected Toolbar titleBar;

    protected TextView tvBarTitle;

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = prepareContentView(inflater, container);
            titleBar = (Toolbar) rootView.findViewById(R.id.tool_bar);
            tvBarTitle = (TextView) rootView.findViewById(R.id.tool_bar_title);
            if (titleBar != null) {
                setTitle(getFragmentName());
                ((BaseActivity)getActivity()).setSupportActionBar(titleBar);
                setHasOptionsMenu(true);
                if (getNavigationIcon() != 0) {
                    titleBar.setNavigationIcon(getNavigationIcon());
                }
                titleBar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigationClicked();
                    }
                });
            }
            onCreateView(savedInstanceState);
        }
        return rootView;
    }

    protected abstract View prepareContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract String getFragmentName();

    public void showLoadingToast(String message, boolean cancelable) {
        ((BaseActivity) getActivity()).showLoadingToast(message, cancelable);
    }

    public void hideLoadingToast() {
        ((BaseActivity) getActivity()).hideLoadingToast();
    }

    public void showLoadingDialog(String message) {
        ((BaseActivity) getActivity()).showLoadingDialog(message);
    }

    public void hideDialog() {
        ((BaseActivity) getActivity()).hideDialog();
    }

    public void showErrorToast(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showErrorToast(message);
        }
    }

    public void showErrorDialog(String title, String message, DialogInterface.OnDismissListener listener) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showErrorDialog(title, message, listener);
        }
    }

    public void showSuccessDialog(String title, String message, DialogInterface.OnDismissListener listener) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showSuccessDialog(title, message, listener);
        }
    }

    public void showAlertDialog(String title, String message, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, confirmClickListener);
        }
    }

    public void showAlertDialog(String title, String message, String btnConfirm, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, btnConfirm, confirmClickListener);
        }
    }

    protected void setTitle(String title) {
        if(tvBarTitle != null) {
            tvBarTitle.setText(title);
        }
    }

    protected int getNavigationIcon() {
        return 0;
    }

    protected void navigationClicked() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Fragment memory leak monitor.
        BaseApplication.getRefWatch().watch(this);
    }
}
