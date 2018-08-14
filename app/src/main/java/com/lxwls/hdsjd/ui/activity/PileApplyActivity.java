package com.lxwls.hdsjd.ui.activity;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackActivity;

public class PileApplyActivity extends BackActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_pile_apply;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleIcon(R.mipmap.icon_title_pile);
        setTitleText("电桩申请");
    }

}
