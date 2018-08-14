package com.lxwls.hdsjd.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.KaiPiaoAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.KaiPiaoEntity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class KaiPiaoActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private CheckBox img_xuanshang;
    private TextView tv_total_money;
    private Button btn_next;
    private String type;
    private List<KaiPiaoEntity> kaiPiaoEntityList;

    @Override
    protected int getContentView() {
        return R.layout.activity_kai_piao;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("按行程开票");
        type = getIntent().getStringExtra("type");
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        img_xuanshang= (CheckBox) findViewById(R.id.img_xuanshang);
        tv_total_money= (TextView) findViewById(R.id.tv_total_money);
        btn_next= (Button) findViewById(R.id.btn_next);


        initShuju();

    }

    private void initShuju() {
        PileApi.instance.searchInvoiceOrder(Integer.parseInt(type))
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
                            if (body.length()<10){
                                Toast.makeText(KaiPiaoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                            }else {
                                Gson gson=new Gson();
                                kaiPiaoEntityList=gson.fromJson(body,new TypeToken<List<KaiPiaoEntity>>(){}.getType());
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

    private void initRecyclerView() {
        KaiPiaoAdapter adapter=new KaiPiaoAdapter(this,kaiPiaoEntityList,img_xuanshang,tv_total_money,btn_next,type);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }
}
