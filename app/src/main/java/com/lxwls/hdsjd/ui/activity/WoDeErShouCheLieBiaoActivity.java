package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.ErShouCheShouYeLieBiaoAdapter111;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.WoDeErShouCheLieBiaoEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class WoDeErShouCheLieBiaoActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private WoDeErShouCheLieBiaoEntity woDeErShouCheLieBiaoEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_wo_de_er_shou_che_lie_biao;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("我的二手车");

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initShuJu();
    }

    private void initShuJu() {
        PileApi.instance.searchSelfSecondhandcar()
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson=new Gson();
                                woDeErShouCheLieBiaoEntity = gson.fromJson(body, WoDeErShouCheLieBiaoEntity.class);

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
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }

    private void initRecyclerView() {
        ErShouCheShouYeLieBiaoAdapter111 adapter=new ErShouCheShouYeLieBiaoAdapter111(this,woDeErShouCheLieBiaoEntity.getResponse());
        GridLayoutManager manager=new GridLayoutManager(this,2);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ErShouCheShouYeLieBiaoAdapter111.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(WoDeErShouCheLieBiaoActivity.this,ErShouCheShouYeXiangQingActivity.class);
                intent.putExtra("id",woDeErShouCheLieBiaoEntity.getResponse().get(position).getId()+"");
                intent.putExtra("line","1");
                startActivity(intent);
            }
        });
    }

    private void initView() {
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);

    }
}
