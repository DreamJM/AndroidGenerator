package com.wafa.android.pei.lib.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.umeng.analytics.MobclickAgent;
import com.wafa.android.pei.lib.R;
import com.wafa.android.pei.lib.widget.loading.LoadingDialog;


/**
 * Description:Base class for every Activity in this application.
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 界面Header
     */
    protected Toolbar titleBar;

    /**
     * 界面标题栏
     */
    protected TextView tvBarTitle;

    /**
     * 信息提示框
     */
    protected SweetAlertDialog dialog;

    /**
     * 加载等待框
     */
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareContentView();
        titleBar = (Toolbar) findViewById(R.id.tool_bar);
        tvBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (titleBar != null) { //如果xml中有定义header，则进行标题和返回按钮定制
            setTitle(getActivityName());
            setSupportActionBar(titleBar);
            if (getNavigationIcon() != 0) {
                titleBar.setNavigationIcon(getNavigationIcon());
            }
            titleBar.setNavigationOnClickListener(v -> navigationClicked());
        }
        onCreateView(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        if(getActivityName() != null) {
            MobclickAgent.onPageStart(getActivityName());
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        if(getActivityName() != null) {
            MobclickAgent.onPageEnd(getActivityName());
        }
        MobclickAgent.onPause(this);
    }

    /**
     * 在此准备content view
     */
    protected abstract void prepareContentView();

    /**
     * 界面创建（ContentView已经设置）
     * @param savedInstanceState
     */
    protected abstract void onCreateView(Bundle savedInstanceState);

    /**
     * @return 界面标题
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
            dialog = new SweetAlertDialog(this).setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss());
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
            dialog = new SweetAlertDialog(this).setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss());
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
            dialog = new SweetAlertDialog(this).setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss());
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
            dialog = new SweetAlertDialog(this).setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss());
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
     * 设置界面标题
     * @param title
     */
    protected void setTitle(String title) {
        if(tvBarTitle != null) {
            tvBarTitle.setText(title);
        }
    }

    /**
     * @return 定制返回按钮（默认使用返回）
     */
    protected int getNavigationIcon() {
        return R.drawable.back;
    }

    /**
     * navigation按钮（左上）点击时间（默认关闭界面）
     */
    protected void navigationClicked() {
        finish();
    }
}
