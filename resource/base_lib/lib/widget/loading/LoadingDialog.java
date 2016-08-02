package com.dream.android.sample.lib.widget.loading;

import android.app.Dialog;
import android.content.Context;
import com.dream.android.sample.lib.R;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
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
