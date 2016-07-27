package com.wafa.android.pei.lib.model;

import com.wafa.android.pei.lib.base.BaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:分页信息类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class Page<T> {

    /**
     * 页大小（每一页的数据量）
     */
    private int pageSize;

    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总数据量
     */
    private int totalCount;

    /**
     * 数据
     */
    private List<T> data;

    public Page() {
        currentPage = BaseConstants.INIT_PAGE;//设置为初始状态
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
     * 直接添加页码数据
     * @param page 指定页码数据
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

    /**
     * 删除指定位置的数据
     */
    public void removeData(int index) {
        if(index > 0 && index < data.size() - 1) {
            data.remove(index);
        }
    }

    /**
     * 删除数据
     */
    public void removeData(T t) {
        data.remove(t);
    }

    /**
     * 清空数据
     */
    public void clear() {
        this.pageSize = 0;
        this.currentPage = BaseConstants.INIT_PAGE;
        this.totalPage = 0;
        this.totalCount = 0;
        data.clear();
    }

    /**
     * 能否加载更多
     */
    public boolean canLoadMore() {
        return currentPage < totalPage - 1; //目前根据页码来判断（逻辑可变）
    }

}
