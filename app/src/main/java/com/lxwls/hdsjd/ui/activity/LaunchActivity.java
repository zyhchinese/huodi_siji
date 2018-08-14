package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.lxwls.hdsjd.MainActivity;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BaseActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(r, 0);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {

            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            finish();
        }
    };
}
