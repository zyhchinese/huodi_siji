package com.lxwls.hdsjd.ui.activity;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackActivity;

public class MyCarActivity extends BackActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleIcon(R.mipmap.icon_title_car);
        setTitleText("我的车辆");
    }
}
