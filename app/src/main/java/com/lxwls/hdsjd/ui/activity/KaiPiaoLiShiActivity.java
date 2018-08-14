package com.lxwls.hdsjd.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.KaiPiaoLiShiAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.KaiPiaoLiShiEntity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class KaiPiaoLiShiActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private List<KaiPiaoLiShiEntity> list;

    @Override
    protected int getContentView() {
        return R.layout.activity_kai_piao_li_shi;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("开票历史");
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);

        initShuju();
    }

    private void initShuju() {

        PileApi.instance.searchInvoice()
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
                            Gson gson = new Gson();
                            list = gson.fromJson(body, new TypeToken<List<KaiPiaoLiShiEntity>>() {
                            }.getType());

                            initRecyclerView();


                        } catch (IOException e) {
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
        KaiPiaoLiShiAdapter adapter = new KaiPiaoLiShiAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }
}
