package com.lxwls.hdsjd.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.account.AccountHelper;
import com.lxwls.hdsjd.account.LoginActivity;
import com.lxwls.hdsjd.adapter.KuaiYunDan_DingDan_LieBiaoAdapter;
import com.lxwls.hdsjd.adapter.RecyclerViewKuaiYunAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BackActivity;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.HuoDKuaiYun_liebiao;
import com.lxwls.hdsjd.bean.KuaiYunEntity;
import com.lxwls.hdsjd.bean.User;
import com.lxwls.hdsjd.utils.DialogHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/14.（快运-订单列表）
 */

public class KuaiYunDan_DingDan_LieBiaoActivity extends BackCommonActivity implements View.OnClickListener{

    private ImageView img_back;
    private RecyclerView act_recyclerView;//数据列表
    private List<KuaiYunEntity> auditOrderEntityList;//实体类
    private KuaiYunDan_DingDan_LieBiaoAdapter adapter;
    private String city = "";

    @Override
    protected int getContentView() {
        return R.layout.kuaiyun_dingdanliebiao;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("快运单订单");
        city=getIntent().getStringExtra("cs");
        //初始化控件
        initView();
    }

    private void initView() {
//        img_back= (ImageView) findViewById(R.id.go_back);
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);//列表数据

//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        //加载快运数据
        initCarInfo();
    }
    //加载快运数据
    private void initCarInfo() {
        PileApi.instance.searchfindKuaiyunorder()
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

                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                Toast.makeText(KuaiYunDan_DingDan_LieBiaoActivity.this, "暂无订单信息", Toast.LENGTH_SHORT).show();
                                if (auditOrderEntityList!=null){
                                    auditOrderEntityList.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                Gson gson = new Gson();
                                auditOrderEntityList = gson.fromJson(body, new TypeToken<List<KuaiYunEntity>>() {
                                }.getType());

                                //加载列表
                                initRecyclerView();
                            }
                        } catch (IOException e) {
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
    //快运详情
    private void initRecyclerView() {

        adapter=new KuaiYunDan_DingDan_LieBiaoAdapter(this,auditOrderEntityList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new KuaiYunDan_DingDan_LieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(KuaiYunDan_DingDan_LieBiaoActivity.this,KuaiYunDan_DingDan_XiangQingActivity.class);
                intent.putExtra("orderno",auditOrderEntityList.get(position).getOrderno()+"");
                intent.putExtra("line","0");
                startActivity(intent);
            }
        });
        adapter.setOnClickItemHang(new KuaiYunDan_DingDan_LieBiaoAdapter.OnClickItemHang() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
