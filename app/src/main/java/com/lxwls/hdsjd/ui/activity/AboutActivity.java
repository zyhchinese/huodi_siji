package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

import cn.sharesdk.onekeyshare.share.OthreShare;

public class AboutActivity extends BackCommonActivity implements View.OnClickListener{

    private LinearLayout llyNewFeature,llyCommonQuestion,llyShareToFriends,llyLikeMbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("关于我们");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_about_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        llyNewFeature=(LinearLayout)findViewById(R.id.lly_new_feature);
        llyCommonQuestion=(LinearLayout)findViewById(R.id.lly_common_question);
        llyShareToFriends=(LinearLayout)findViewById(R.id.lly_share_friends);
        llyLikeMbt=(LinearLayout)findViewById(R.id.lly_like_mbt);

        llyNewFeature.setOnClickListener(this);
        llyCommonQuestion.setOnClickListener(this);
        llyShareToFriends.setOnClickListener(this);
        llyLikeMbt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_new_feature:
                startActivity(new Intent(AboutActivity.this,NewFeatureIntroduceActivity.class));
                break;
            case R.id.lly_common_question:
                startActivity(new Intent(AboutActivity.this,ChangJianWenTiActivity.class));
                break;
            case R.id.lly_share_friends:
                OthreShare.showShare(AboutActivity.this,"货滴司机","欢迎使用货滴司机，您身边的物流平台", "http://www.huodiwulian.com/logo_sj.png","http://www.huodiwulian.com/hdsywz/weixin/page/xiazai.html");
              //  startActivity(new Intent(AboutActivity.this,CommonQuestionActivity.class));
                break;
            case R.id.lly_like_mbt:
                startActivity(new Intent(AboutActivity.this,LikeMbtActivity.class));
                break;
        }
    }
}
