package com.lxwls.hdsjd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.HuoZhuLieBiaoAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.ShuFengCheXiangQingEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lxwls.hdsjd.base.Constant.REQUEST_CODE;

public class ShuFengCheXiangQingActivity extends BackCommonActivity {

    private TextView tv_qidian,tv_zhongdian,tv_tujingchengshi,tv_zongchechang,tv_keyongchechang,tv_time,tv_createtime,tv_fabuxingcheng;
    private RecyclerView act_recyclerView;
    private ShuFengCheXiangQingEntity entity;
    private LinearLayout linear_huozhuliebiao;
    private int position;

    @Override
    protected int getContentView() {
        return R.layout.activity_shu_feng_che_xiang_qing;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货滴顺风车详情");

        initView();

        initShuJu();
    }

    private void initView() {
        tv_qidian= (TextView) findViewById(R.id.tv_qidian);
        tv_zhongdian= (TextView) findViewById(R.id.tv_zhongdian);
        tv_tujingchengshi= (TextView) findViewById(R.id.tv_tujingchengshi);
        tv_zongchechang= (TextView) findViewById(R.id.tv_zongchechang);
        tv_keyongchechang= (TextView) findViewById(R.id.tv_keyongchechang);
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_createtime= (TextView) findViewById(R.id.tv_createtime);
        tv_fabuxingcheng= (TextView) findViewById(R.id.tv_fabuxingcheng);
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        linear_huozhuliebiao= (LinearLayout) findViewById(R.id.linear_huozhuliebiao);

        tv_fabuxingcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ShuFengCheXiangQingActivity.this)
                        .setTitle("提示")
                        .setMessage("确认取消行程吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initQuXiaoXingCheng();
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });
    }

    private void initQuXiaoXingCheng() {
        HashMap<String, String> map = new HashMap<>();
        map.put("frid", getIntent().getStringExtra("id"));
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.cancelFreeRideTrip(data)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            String msg = jsonObject.getString("msg");
                            if (flag.equals("200")) {

                                showToast("取消成功");
                                finish();


                            }else {
                                showToast(msg);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.selectSelfFreeRideDet(data)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {

                                entity=new Gson().fromJson(body,ShuFengCheXiangQingEntity.class);

                                initFuZhi();


                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initFuZhi() {
        if (entity.getResponse().get(0).getFreerideJoin().size()==0){
            linear_huozhuliebiao.setVisibility(View.GONE);
        }else {
            linear_huozhuliebiao.setVisibility(View.VISIBLE);
        }
        if (entity.getResponse().get(0).getFreerideDet().getState()==0){
            tv_fabuxingcheng.setVisibility(View.GONE);
        }else {
            tv_fabuxingcheng.setVisibility(View.VISIBLE);
        }
        tv_qidian.setText(entity.getResponse().get(0).getFreerideDet().getScity());
        tv_zhongdian.setText(entity.getResponse().get(0).getFreerideDet().getEcity());
        tv_tujingchengshi.setText(entity.getResponse().get(0).getFreerideDet().getWaytocitysshow());
        tv_zongchechang.setText(entity.getResponse().get(0).getFreerideDet().getTotalvehicle());
        tv_keyongchechang.setText(entity.getResponse().get(0).getFreerideDet().getAvailablevehicle());
        tv_time.setText(entity.getResponse().get(0).getFreerideDet().getDeptime());
        tv_createtime.setText(entity.getResponse().get(0).getFreerideDet().getCretime());

        HuoZhuLieBiaoAdapter adapter=new HuoZhuLieBiaoAdapter(this,entity);
        LinearLayoutManager manager=new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickCall(new HuoZhuLieBiaoAdapter.OnClickCall() {
            @Override
            public void onClick(int position) {
                ShuFengCheXiangQingActivity.this.position=position;
                AlertDialog.Builder builder11=new AlertDialog.Builder(ShuFengCheXiangQingActivity.this)
                        .setTitle("提示")
                        .setMessage("确定拨打电话："+entity.getResponse().get(0).getFreerideJoin().get(position).getConsignorphone())
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                    testCallPhone();
                            }
                        });
                builder11.show();
            }
        });

    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }
    private void callPhone(String phoneNum) {
        String consigneephone=entity.getResponse().get(0).getFreerideJoin().get(position).getConsignorphone();
        //直接拨号
        Uri uri = Uri.parse("tel:" + consigneephone );
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }

    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
