package com.lxwls.hdsjd.ui.activity;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

public class SetEncryptedActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_set_encrypted;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("设置密保");
    }
}
