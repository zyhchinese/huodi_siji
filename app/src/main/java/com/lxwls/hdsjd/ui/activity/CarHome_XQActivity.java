package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.CarPP_XQAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.PageBean;
import com.lxwls.hdsjd.bean.carhometopBean;
import com.lxwls.hdsjd.mvp.CarDetialActivity;
import com.lxwls.hdsjd.widgets.LoadListView.OnLoaderListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarHome_XQActivity extends BackCommonActivity implements OnLoaderListener {
    private com.lxwls.hdsjd.widgets.LoadListView listview;
    private CarPP_XQAdapter adt;
    private ImageView shoushuo;
    private String id;
    private EditText et_input_pwd;
    List<carhometopBean> listdata;
    int page1 = 1;
    LinearLayout linearLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_xqhome;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText(getIntent().getStringExtra("name"));
        //   String id = getIntent().getStringExtra("id");
        id = getIntent().getStringExtra("id");
        listview = (com.lxwls.hdsjd.widgets.LoadListView) findViewById(R.id.car_pp);
        listview.setLoaderListener(this);
        shoushuo = (ImageView) findViewById(R.id.shoushuo);
        et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
        linearLayout = (LinearLayout) findViewById(R.id.lly_empty_view);
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
//        temp.add(new CarPP("1", "宝马",28.7));
        //  listview.setAdapter(new CarPP_XQAdapter(this, temp));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it1 = new Intent(CarHome_XQActivity.this, CarDetialActivity.class);
                it1.putExtra("id", adt.getItem(position).getId() + "");
                startActivity(it1);
            }
        });
        shoushuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitDialog("正在刷新信息...");
                if (listdata != null) {
                    listdata.clear();
                }
                page1 = 1;
                getCarHomeListData(et_input_pwd.getText().toString(), page1, 8);
            }
        });
        showWaitDialog("正在刷新信息...");
        getCarHomeListData("", 1, 8);
    }

    private void updateInfo(List<carhometopBean> CarPP) {
        if (CarPP != null) {
            if (adt == null) {
                listdata = CarPP;
                adt = new CarPP_XQAdapter(CarHome_XQActivity.this, listdata);
                listview.setAdapter(adt);
                listview.loadComplete();
            } else {
                listdata.addAll(CarPP);
                adt.setMarkerData(listdata);
                adt.notifyDataSetChanged();
                listview.loadComplete();
            }
        }
    }

    private void getCarHomeListData(String groupname, int page, int rows) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("groupname", groupname);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getCarSeries(params, page, rows)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageBean<carhometopBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PageBean<carhometopBean> responseBody) {

                        try {
                            if (responseBody == null || responseBody.getRows().size() == 0) {
                                //   if(body.equals("\"false\""))

                                page1 = page1 - 1;
                                listview.loadComplete();
                                if (listdata == null) {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    hideWaitDialog();
                                }
                                if (listdata.size() == 0) {
                                    linearLayout.setVisibility(View.VISIBLE);
                                } else {
                                    showToast("暂无更多数据");
                                }
                            } else {
                                linearLayout.setVisibility(View.GONE);
                                List<carhometopBean> fd = responseBody.getRows();
                                updateInfo(fd);
                            }
                            hideWaitDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        page1 = page1 - 1;
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page1 = page1 + 1;
                getCarHomeListData(et_input_pwd.getText().toString(), page1, 8);
            }
        }, 2000);


    }
}

