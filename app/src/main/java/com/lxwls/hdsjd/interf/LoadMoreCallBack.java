package com.lxwls.hdsjd.interf;

import com.lxwls.hdsjd.bean.PrimaryNews;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public interface LoadMoreCallBack {

    void loadData(List<PrimaryNews> primaryNewsList);
}
