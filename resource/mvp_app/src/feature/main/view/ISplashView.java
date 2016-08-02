package com.dream.android.sample.feature.main.view;

import com.dream.android.sample.lib.base.IBaseView;
import com.dream.android.sample.model.LaunchItem;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public interface ISplashView extends IBaseView {

    void jumpToMain();

    void showLaunch(LaunchItem item);

}
