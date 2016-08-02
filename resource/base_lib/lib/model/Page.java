package com.dream.android.sample.lib.model;

import com.dream.android.sample.lib.base.BaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:Page info
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class Page<T> {

    private int pageSize;

    private int currentPage;

    private int totalPage;

    private int totalCount;

    private List<T> data;

    public Page() {
        currentPage = BaseConstants.INIT_PAGE;
        data = new ArrayList<>();
    }

    public Page(List<T> datas) {
        if(datas != null) {
            this.pageSize = datas.size();
            this.currentPage = 0;
            this.totalPage = 1;
            this.totalCount = datas.size();
            this.data = datas;
        }
    }

    public Page(int pageSize, int currentPage, int totalPage, int totalCount) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        if(data == null) {
            return new ArrayList<>();
        } else {
            return data;
        }
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * add page data
     * @param page data by page
     */
    public void addPage(Page<T> page) {
        this.pageSize = page.pageSize;
        this.currentPage = page.currentPage;
        this.totalPage = page.totalPage;
        this.totalCount = page.totalCount;
        if(page.getData() != null) {
            data.addAll(page.getData());
        }
    }

    public void removeData(int index) {
        if(index > 0 && index < data.size() - 1) {
            data.remove(index);
        }
    }

    public void removeData(T t) {
        data.remove(t);
    }

    /**
     * clear all datas
     */
    public void clear() {
        this.pageSize = 0;
        this.currentPage = BaseConstants.INIT_PAGE;
        this.totalPage = 0;
        this.totalCount = 0;
        data.clear();
    }

    /**
     * @return whether load more action is available or not
     */
    public boolean canLoadMore() {
        return currentPage < totalPage - 1;
    }

}
