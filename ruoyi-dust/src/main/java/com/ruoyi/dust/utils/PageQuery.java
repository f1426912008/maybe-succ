package com.ruoyi.dust.utils;

import com.github.pagehelper.PageInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PageQuery<T> {
    @Valid
    @NotNull(message = "查询对象不能为空!")
    private T data;

    @NotNull(message = "分页对象不能为空!")
    private PageInfo<T> pageInfo;

    public PageQuery() {
    }

    public T getData() {
        return this.data;
    }

    public PageInfo<T> getPageInfo() {
        return this.pageInfo;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setPageInfo(final PageInfo<T> pageInfo) {
        this.pageInfo = pageInfo;
    }

}
