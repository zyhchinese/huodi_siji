package com.lxwls.hdsjd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.account.LoginActivity;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.User;
import com.lxwls.hdsjd.utils.DialogHelper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/***
 * 我的钱包余额页面activity
 * @author tb
 * 2017/8/28
 */
public class MywalletActivity extends BackCommonActivity {
    private TextView tvUserName;
    private TextView tBalance;
    private TextView tvTickets;
    private TextView tvScores;
    private CardView pinion1;
    private CardView pinion2;
    private CardView pinion3;
    private CardView pinion4;
    private CardView pinion5;
    boolean isLogin = false;
    RequestOptions mOptions;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("我的钱包");
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        circleImageView = (CircleImageView) findViewById(R.id.img_avatar);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tBalance = (TextView) findViewById((R.id.tBalance));
        tvTickets = (TextView) findViewById((R.id.tvTickets));
        tvScores = (TextView) findViewById(R.id.tvScores);
        pinion1 = (CardView) findViewById(R.id.pinion);
        pinion2 = (CardView) findViewById(R.id.pinion1);
        pinion3 = (CardView) findViewById(R.id.pinion2);
        pinion4 = (CardView) findViewById(R.id.pinion3);
        pinion5 = (CardView) findViewById(R.id.pinion4);
        pinion1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent1 = new Intent(MywalletActivity.this, BalanceActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent1 = new Intent(MywalletActivity.this, CouponActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
//                                           Intent intent1 = new Intent(MywalletActivity.this, IntegralActivity.class);
//
//                                           startActivity(intent1);

                                           Intent intent1 = new Intent(MywalletActivity.this, QianDaoActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intent=new Intent(MywalletActivity.this,KaiJuFaPiaoActivity.class);
                                            startActivity(intent);
                                        }
                                   }
        );
        pinion5.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           Intent intent=new Intent(MywalletActivity.this,EtcChongZhiActivity.class);
                                           startActivity(intent);
                                       }
                                   }
        );
        getUserInfo();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initData() {
        super.initData();
        // 可以进行定位、检测更新等操作
    }

    private void updateUserInfo(User user) {
        if (user != null) {
            tvUserName.setText(user.getCustname());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            tBalance.setText(decimalFormat.format(user.getBalance()) + "元");
            tvTickets.setText(user.getCount() + "张");
            tvScores.setText(decimalFormat.format(user.getScores()) + "积分");
            String userLogo= Constant.BASE_URL+user.getFolder()+"/"+user.getAutoname();
            Glide.with(MywalletActivity.this)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(circleImageView);
        }
    }

    private void getUserInfo() {
        showWaitDialog("正在获取个人信息");
        PileApi.instance.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("获取个人信息失败，请登录后重试");
                            } else {
                                Gson gson = new Gson();
                                List<User> userList = gson.fromJson(body, new TypeToken<List<User>>() {
                                }.getType());
                                updateUserInfo(userList.get(0));

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        PileApi.instance.mCheckLoginState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                getUserInfo();
                                isLogin = true;
                            } else {
                                isLogin = false;
                                DialogHelper.getConfirmDialog(MywalletActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(MywalletActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
