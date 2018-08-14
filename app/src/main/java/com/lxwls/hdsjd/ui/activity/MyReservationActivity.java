package com.lxwls.hdsjd.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.MyReservationAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.MyReservation;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyReservationActivity extends BackCommonActivity {
    private RecyclerView act_recyclerView;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_reservation;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("我的预约");

        initView();
    }

    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);

        initHttp();

    }

    //加载我的预约列表
    private void initHttp() {
        PileApi.instance.getMyApply()
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

                            if (body.indexOf("false") != -1 || body.length() < 3) {
                                showToast("暂无预约，请添加");
                            } else {
                                Gson gson = new Gson();
                                List<MyReservation> myReservationList = gson.fromJson(body, new TypeToken<List<MyReservation>>() {
                                }.getType());

                                MyReservationAdapter adapter=new MyReservationAdapter(MyReservationActivity.this,myReservationList);
                                LinearLayoutManager manager=new LinearLayoutManager(MyReservationActivity.this);
                                act_recyclerView.setLayoutManager(manager);
                                act_recyclerView.setAdapter(adapter);
                                adapter.setClickItemChild(new MyReservationAdapter.ClickItemChild1() {
                                    @Override
                                    public void onClick(int position) {
//                                        Intent intent=new Intent(MyReservationActivity.this,BannerDetailsActivity.class);
//                                        startActivity(intent);
                                    }
                                });

                            }
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

}
