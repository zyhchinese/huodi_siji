package com.lxwls.hdsjd.ui.activity;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

public class CommonQuestionActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_common_question;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("常见问题");
    }
}
