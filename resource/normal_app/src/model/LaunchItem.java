package com.dream.android.sample.model;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/7/25
 */
public class LaunchItem {

    private String title;

    private String url;

    private String targetUrl;

    public LaunchItem(String title, String url, String targetUrl) {
        this.title = title;
        this.url = url;
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
