package com.dream.android.sample.lib.base;

import android.content.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.dream.android.sample.lib.R;
import com.umeng.analytics.MobclickAgent;
import com.dream.android.sample.lib.widget.loading.LoadingDialog;

/**
 * Description:Base class for every Activity in this application.
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar titleBar;

    protected TextView tvBarTitle;

    protected SweetAlertDialog dialog;

    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFeature();
        setContentView(getRootViewId());
        titleBar = (Toolbar) findViewById(R.id.tool_bar);
        tvBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (needViewInjection()) {
            // Enable View Injection with ButterKnife
            ButterKnife.bind(this);
        }
        if (titleBar != null) { //if layout xml file has include tool_bar.xml for header，customize title and navigation button
            setTitle(getActivityName());
            setSupportActionBar(titleBar);
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

    @Override
    protected void onResume() {
        super.onResume();
        //introduce umeng sdk for Mobile Analytics
        if(getActivityName() != null) {
            MobclickAgent.onPageStart(getActivityName());
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getActivityName() != null) {
            MobclickAgent.onPageEnd(getActivityName());
        }
        MobclickAgent.onPause(this);
    }

    /**
     * @return whether use ButterKnife for ViewInjection
     */
    protected boolean needViewInjection() {
        return true;
    }

    /**
     * Set Window feature for activity（should be set before "setContentView"）
     */
    protected void setWindowFeature() {}

    /**
     * @param savedInstanceState
     */
    protected abstract void onCreateView(Bundle savedInstanceState);

    /**
     * @return xml layout resource id
     */
    protected abstract int getRootViewId();

    /**
     * @return activity name
     */
    protected abstract String getActivityName();

    public void showLoadingToast(String message, boolean cancelable) {
        if(loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    public void hideLoadingToast() {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void showLoadingDialog(String message) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new SweetAlertDialog(this).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.setContentText(message).setTitleText("").setCancelText(getString(R.string.cancel)).showCancelButton(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setOnDismissListener(null);
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showErrorDialog(String title, String message, DialogInterface.OnDismissListener listener) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new SweetAlertDialog(this).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        dialog.setContentText(message).setTitleText(title).showCancelButton(false).setConfirmText(getString(R.string.ensure)).setConfirmClickListener(null);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setOnDismissListener(listener);
    }

    public void showSuccessDialog(String title, String message, DialogInterface.OnDismissListener listener) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new SweetAlertDialog(this).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setContentText(message).setTitleText(title).showCancelButton(false).setConfirmClickListener(null);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setOnDismissListener(listener);
    }

    public void showAlertDialog(String title, String message, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        showAlertDialog(title, message, getString(R.string.ensure), confirmClickListener);
    }

    public void showAlertDialog(String title, String message, String confirmBtn, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new SweetAlertDialog(this).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText(message).setTitleText(title)
                .setConfirmText(confirmBtn)
                .setConfirmClickListener(confirmClickListener);
        if(confirmClickListener != null) {
            dialog.setCancelText(getString(R.string.cancel)).showCancelButton(true);
        } else {
            dialog.showCancelButton(false);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setOnDismissListener(null);
    }

    /**
     * @param title: activity title
     */
    protected void setTitle(String title) {
        if(tvBarTitle != null) {
            tvBarTitle.setText(title);
        }
    }

    /**
     * @return drawable resource id for navigation button
     */
    protected int getNavigationIcon() {
        return R.drawable.back;
    }

    /**
     * click event for navigation button(finish activity by default)
     */
    protected void navigationClicked() {
        finish();
    }
}
