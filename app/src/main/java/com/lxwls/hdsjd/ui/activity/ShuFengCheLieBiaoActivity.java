package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.ShuFengCheLieBiaoAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.KuYunCheChangEntity;
import com.lxwls.hdsjd.bean.ShuFengCheLieBiaoEntity;
import com.lxwls.hdsjd.widgets.duoxuandialog.ShuJuEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ShuFengCheLieBiaoActivity extends BackCommonActivity implements View.OnClickListener {

    private RecyclerView act_recyclerView;
    private TextView tv_fabuxingcheng;
    private ShuFengCheLieBiaoEntity shuFengCheLieBiaoEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_shu_feng_che_lie_biao;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货滴顺风车行程列表");

        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        tv_fabuxingcheng= (TextView) findViewById(R.id.tv_fabuxingcheng);

        tv_fabuxingcheng.setOnClickListener(this);




    }

    @Override
    protected void onResume() {
        super.onResume();
        initShuJu();
    }

    private void initShuJu() {
        PileApi.instance.selectSelfFreeRideList()
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
                            System.out.println("body = " + body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                shuFengCheLieBiaoEntity = new Gson().fromJson(body, ShuFengCheLieBiaoEntity.class);

                                initRecyclerView();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(ShuFengCheLieBiaoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRecyclerView() {
        ShuFengCheLieBiaoAdapter adapter=new ShuFengCheLieBiaoAdapter(this,shuFengCheLieBiaoEntity.getResponse());
        act_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ShuFengCheLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(ShuFengCheLieBiaoActivity.this,ShuFengCheXiangQingActivity.class).putExtra("id",shuFengCheLieBiaoEntity.getResponse().get(position).getId()+""));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_fabuxingcheng:
                startActivity(new Intent(this, FaBuShunFengCheActivity.class));
                break;
        }
    }


}
