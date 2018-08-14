package com.lxwls.hdsjd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.KuaiYunAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.bean.KuaiYunEntity;
import com.lxwls.hdsjd.ui.activity.KuaiYunDan_DingDan_XiangQingActivity;
import com.lxwls.hdsjd.ui.activity.LogisticsOrderDetailsActivity2;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/2/27.
 */

public class KuaiYunOrderFragment extends Fragment{

    private RecyclerView act_recyclerView;
    private List<KuaiYunEntity> auditOrderEntityList;
    private KuaiYunAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kuaiyun_order, container, false);
        act_recyclerView= (RecyclerView) view.findViewById(R.id.act_recyclerView);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        initKuaiyunshuju();
    }

    private void initKuaiyunshuju() {
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
                                if (auditOrderEntityList != null) {
                                    auditOrderEntityList.clear();
                                    adapter.notifyDataSetChanged();
                                }
//                                Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                auditOrderEntityList = gson.fromJson(body, new TypeToken<List<KuaiYunEntity>>() {
                                }.getType());
                                //加载列表
                                initView();

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

    private void initView() {

        adapter=new KuaiYunAdapter(getContext(),auditOrderEntityList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new KuaiYunAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(), KuaiYunDan_DingDan_XiangQingActivity.class);
                intent.putExtra("orderno",auditOrderEntityList.get(position).getOrderno());
                intent.putExtra("gettype",auditOrderEntityList.get(position).getGettype());
                intent.putExtra("line","2");
                startActivity(intent);
            }
        });

    }
}
