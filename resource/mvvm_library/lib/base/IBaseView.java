package com.wafa.android.pei.lib.base;

import android.content.DialogInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Description:Base View Interface
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public interface IBaseView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoadingToast(String message, boolean cancelable);

    /**
     * Hide a loading toast view.
     */
    void hideLoadingToast();

    /**
     * Show a progress dialog
     */
    void showLoadingDialog(String message);

    /**
     * Hide a sweet dialog
     */
    void hideDialog();

    /**
     * Show an error message
     *
     */
    void showErrorToast(String message);

    /**
     * Show a dialog with error message
     */
    void showErrorDialog(String title, String message, DialogInterface.OnDismissListener listener);

    /**
     * Show an alert message
     */
    void showAlertDialog(String title, String message, SweetAlertDialog.OnSweetClickListener confirmClickListener);
    void showAlertDialog(String title, String message, String confirmBtn, SweetAlertDialog.OnSweetClickListener confirmClickListener);

    /**
     * Show success dialog
     */
    void showSuccessDialog(String title, String message, DialogInterface.OnDismissListener listener);

}
