package com.lxwls.hdsjd.account;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.api.PileApi;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RegisterTwoActivity extends AccountBaseActivity {

    private EditText etUserName, etPwd, etConfimPwd;
    private TextView tvSubmit;
    private String mUserName = "", mPwd = "", mCheckPwd = "", mPhone = "", mAuthCode = "";
    private ImageView goback;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_two;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mPhone = getIntent().getStringExtra("phone");
        mAuthCode = getIntent().getStringExtra("authcode");
        etUserName = (EditText) findViewById(R.id.et_username);
        goback = (ImageView) findViewById(R.id.goback);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etConfimPwd = (EditText) findViewById(R.id.et_confirm_pwd);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = etUserName.getText().toString().trim();
                mPwd = etPwd.getText().toString().trim();
                mCheckPwd = etConfimPwd.getText().toString().trim();
                if (mUserName.equals("")) {
                    showToast("用户名不能为空");
                    return;
                }
                if (mPwd.equals("")) {
                    showToast("密码不能为空");
                    return;
                }

                if (mPwd.length() < 6) {
                    showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
                    return;
                }
                if (mPwd.length() > 15) {
                    showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
                    return;
                }
                if (!mPwd.equals(mCheckPwd)) {
                    showToast("两次密码输入不一致");
                    return;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", mPhone);
                Gson gson = new Gson();
                String params = gson.toJson(map);

                PileApi.instance.mCheckPhone(params)
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
                                    if (body.equals("\"false\"")) {
                                        mRegister();
                                    } else {
                                        showToast("该手机已注册");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });
    }

    private void mRegister() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("custname", mUserName);
        map.put("custphone", mPhone);
        map.put("password", mPwd);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        showWaitDialog("正在注册...");
        PileApi.instance.mRegister(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        hideWaitDialog();
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                showToast("注册成功");
                                if (RegisterOneActivity.instance != null) {
                                    RegisterOneActivity.instance.finish();
                                }
                                finish();
                            } else {
                                showToast("注册失败，请稍后重试");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        showToast("注册失败，请稍后重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
