package com.wafa.android.pei.lib.widget.loading;

import android.app.Dialog;
import android.content.Context;
import com.wafa.android.pei.lib.R;

/**
 * Description: 三色球旋转效果的加载对话框
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.TransparentDialog);
        init();
    }

    private void init() {
        setContentView(new LoadingView(getContext()));
    }
}
