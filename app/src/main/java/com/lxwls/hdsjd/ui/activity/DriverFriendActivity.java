package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

public class DriverFriendActivity extends BackCommonActivity implements View.OnClickListener {

    private CardView pinion1,pinion2,pinion3,pinion5,pinion6;

    @Override
    protected int getContentView() {
        return R.layout.activity_driver_friend;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("司机之家");
        initView();
    }

    private void initView() {
        pinion1= (CardView) findViewById(R.id.pinion1);
        pinion2= (CardView) findViewById(R.id.pinion2);
        pinion3= (CardView) findViewById(R.id.pinion3);
        pinion5= (CardView) findViewById(R.id.pinion5);
        pinion6= (CardView) findViewById(R.id.pinion6);
        pinion1.setOnClickListener(this);
        pinion2.setOnClickListener(this);
        pinion3.setOnClickListener(this);
        pinion5.setOnClickListener(this);
        pinion6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pinion1:
                startActivity(new Intent(this,ChaoXianChaoZaiShouCeActivity.class));
                break;
            case R.id.pinion2:
                startActivity(new Intent(this,WeiZhangChaXunActivity.class));
                break;
            case R.id.pinion3:
                startActivity(new Intent(this,YongHuZhiNanActivity.class));
                break;
            case R.id.pinion5:
                startActivity(new Intent(this,ChangJianWenTiActivity.class));
                break;
            case R.id.pinion6:
                startActivity(new Intent(this,ErShouCheShouYeActivity.class));
                break;
        }
    }
}
