package com.lxwls.hdsjd.ui.activity;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

public class NewFeatureIntroduceActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_new_feature_introduce;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("新功能介绍");
    }
}
