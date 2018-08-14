package com.lxwls.hdsjd.ui.activity;

import android.widget.TextView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackActivity;

public class SysTextActivity extends BackActivity {
    private TextView text;

    @Override
    protected int getContentView() {
        return R.layout.activity_sys_text;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("扫码结果");
        text = (TextView) findViewById(R.id.text);
        text.setText(getIntent().getStringExtra("text"));
    }

}
