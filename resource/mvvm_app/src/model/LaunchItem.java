package com.wafa.android.pei.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.wafa.android.pei.BR;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/7/25
 */
public class LaunchItem extends BaseObservable {

    private String title;

    private String url;

    private String targetUrl;

    public LaunchItem(String title, String url, String targetUrl) {
        this.title = title;
        this.url = url;
        this.targetUrl = targetUrl;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
        notifyPropertyChanged(BR.targetUrl);
    }
}
