package com.lxwls.hdsjd.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.EWaiAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.EWaiEntity;
import com.lxwls.hdsjd.bean.FeiLvEntity;
import com.lxwls.hdsjd.bean.LogisticsOrderDetailsEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LogisticsOrderDetailsActivity extends BackCommonActivity implements View.OnClickListener {

    private TextView tv_dingdanhao, tv_type, tv_name, tv_phone, tv_car_type, tv_yongchetime, tv_dingdantime,
            tv_address, tv_address1, tv_juli, tv_money, tv_siji_name, tv_siji_phone, tv_info;
    private TextView tv_quxiao,tv_kaishi,tv_quxiao1,tv_jieshu,tv_pingjia,tv_yuji_daoda,tv_beizhu;
    private TextView tv_xingming,tv_lianxi,tv_huowu11,tv_shijian,tv_shijian1,tv_shijian2,
            tv_xuqiu,tv_beizhumingzi,tv_jiehuoinfo,tv111,tv222,tv_zongji,tv_yuan;
    private ImageView img_dizhi_t,img_dizhi_f;
    private LinearLayout linear_kaishi;
    private RelativeLayout relative;

    private RelativeLayout relative1, relative2;
    private View view10;
    private RecyclerView act_recyclerView;
    private ImageView img_type;
    private Button btn_quxiao, btn_jieshu, btn_kaishi;
    private EditText ed_content;
    private String orderno;
    private String line;
    private String content = "好评";
    private List<LogisticsOrderDetailsEntity> detailsEntityList;
    private List<EWaiEntity> eWaiEntityList;
    private List<FeiLvEntity> feiLvEntityList;
    private int pingjia = 5;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_order_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("同城小件单详情");
        orderno = getIntent().getStringExtra("orderno");
        line = getIntent().getStringExtra("line");

        btn_quxiao = (Button) findViewById(R.id.btn_quxiao);
        btn_jieshu = (Button) findViewById(R.id.btn_jieshu);
        btn_kaishi = (Button) findViewById(R.id.btn_kaishi);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_dingdanhao);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);
        tv_yongchetime = (TextView) findViewById(R.id.tv_yongchetime);
        tv_dingdantime = (TextView) findViewById(R.id.tv_dingdantime);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address1 = (TextView) findViewById(R.id.tv_address1);
        tv_juli = (TextView) findViewById(R.id.tv_juli);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_siji_name = (TextView) findViewById(R.id.tv_siji_name);
        tv_siji_phone = (TextView) findViewById(R.id.tv_siji_phone);
        tv_info = (TextView) findViewById(R.id.tv_info);

        tv_xingming = (TextView) findViewById(R.id.tv_xingming);
        tv_lianxi = (TextView) findViewById(R.id.tv_lianxi);
        tv_huowu11 = (TextView) findViewById(R.id.tv_huowu11);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
        tv_shijian1 = (TextView) findViewById(R.id.tv_shijian1);
        tv_shijian2 = (TextView) findViewById(R.id.tv_shijian2);
        tv_xuqiu = (TextView) findViewById(R.id.tv_xuqiu);
        tv_beizhumingzi = (TextView) findViewById(R.id.tv_beizhumingzi);
        tv_jiehuoinfo = (TextView) findViewById(R.id.tv_jiehuoinfo);
        tv111 = (TextView) findViewById(R.id.tv111);
        tv222 = (TextView) findViewById(R.id.tv222);
        tv_zongji = (TextView) findViewById(R.id.tv_zongji);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        img_dizhi_t = (ImageView) findViewById(R.id.img_dizhi_t);
        img_dizhi_f = (ImageView) findViewById(R.id.img_dizhi_f);

        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_kaishi = (TextView) findViewById(R.id.tv_kaishi);
        tv_quxiao1 = (TextView) findViewById(R.id.tv_quxiao1);
        tv_jieshu = (TextView) findViewById(R.id.tv_jieshu);
        tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);
        tv_yuji_daoda = (TextView) findViewById(R.id.tv_yuji_daoda);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        linear_kaishi= (LinearLayout) findViewById(R.id.linear_kaishi);
        relative= (RelativeLayout) findViewById(R.id.relative);

        img_type = (ImageView) findViewById(R.id.img_type);
        relative1 = (RelativeLayout) findViewById(R.id.relative1);
        relative2 = (RelativeLayout) findViewById(R.id.relative2);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        view10 = findViewById(R.id.view10);
        tv_quxiao.setOnClickListener(this);
        tv_quxiao1.setOnClickListener(this);
        tv_kaishi.setOnClickListener(this);
        tv_jieshu.setOnClickListener(this);
        tv_pingjia.setOnClickListener(this);

        //加载订单详情信息
        initOrderDetails();
        initFeiLv();


    }

    private void initFeiLv() {
        PileApi.instance.searchfee()
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
                                showToast("暂无地址，请添加");
                            } else {
                                Gson gson = new Gson();
                                feiLvEntityList = gson.fromJson(body, new TypeToken<List<FeiLvEntity>>() {
                                }.getType());

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

    private void initEWai() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", detailsEntityList.get(0).getId() + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectSuyunOrderDetail(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
//                                showToast("订单详情暂无数据");
                            } else {
                                Gson gson = new Gson();
                                eWaiEntityList = gson.fromJson(body, new TypeToken<List<EWaiEntity>>() {
                                }.getType());

                                initEwaiRecyclerView();


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

    private void initEwaiRecyclerView() {
        EWaiAdapter adapter = new EWaiAdapter(this, eWaiEntityList,detailsEntityList.get(0).getCust_orderstatus());
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    private void initOrderDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchSuyunOrderDetail(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("订单详情暂无数据");
                            } else {
                                Gson gson = new Gson();
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<LogisticsOrderDetailsEntity>>() {
                                }.getType());
                                //加载额外需求信息
                                initEWai();

                                initFuZhi();

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

    private void initFuZhi() {
        tv_dingdanhao.setText(detailsEntityList.get(0).getOrderno());
        if (detailsEntityList.get(0).getCust_orderstatus() == -1) {
            tv_type.setText("已取消");
            tv_quxiao.setVisibility(View.GONE);
            linear_kaishi.setVisibility(View.GONE);
            tv_jieshu.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.GONE);


            tv_dingdanhao.setTextColor(Color.parseColor("#B9B9B9"));
            tv_type.setTextColor(Color.parseColor("#B9B9B9"));
            tv_xingming.setTextColor(Color.parseColor("#B9B9B9"));
            tv_name.setTextColor(Color.parseColor("#B9B9B9"));
            tv_lianxi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_phone.setTextColor(Color.parseColor("#B9B9B9"));
            tv_huowu11.setTextColor(Color.parseColor("#B9B9B9"));
            tv_car_type.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yongchetime.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian1.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yuji_daoda.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian2.setTextColor(Color.parseColor("#B9B9B9"));
            tv_dingdantime.setTextColor(Color.parseColor("#B9B9B9"));
            tv_xuqiu.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address1.setTextColor(Color.parseColor("#B9B9B9"));
            tv_beizhumingzi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_beizhu.setTextColor(Color.parseColor("#B9B9B9"));
            tv_jiehuoinfo.setTextColor(Color.parseColor("#B9B9B9"));
            tv111.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_name.setTextColor(Color.parseColor("#B9B9B9"));
            tv222.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_phone.setTextColor(Color.parseColor("#B9B9B9"));
            tv_info.setTextColor(Color.parseColor("#B9B9B9"));
            tv_zongji.setTextColor(Color.parseColor("#B9B9B9"));
            tv_money.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yuan.setTextColor(Color.parseColor("#B9B9B9"));
            img_dizhi_t.setImageResource(R.mipmap.img_huodi_weizhi_f);
            img_dizhi_f.setImageResource(R.mipmap.img_huodi_weizhi_f);
        } else if (detailsEntityList.get(0).getCust_orderstatus() == 0) {
            tv_type.setText("等待接货");
            if (detailsEntityList.get(0).getDriver_orderstatus() == 0){
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.VISIBLE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }else {
                tv_quxiao.setVisibility(View.VISIBLE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }
        } else if (detailsEntityList.get(0).getCust_orderstatus() == 1) {
            tv_type.setText("服务开始");
            tv_quxiao.setVisibility(View.GONE);
            linear_kaishi.setVisibility(View.GONE);
            tv_jieshu.setVisibility(View.VISIBLE);
            tv_pingjia.setVisibility(View.GONE);
        } else {
            tv_type.setText("已完成");
            if (detailsEntityList.get(0).getIsevaluate().equals("0")){
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.VISIBLE);
            }else {
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }

        }

        tv_name.setText(detailsEntityList.get(0).getConsigneename());
        tv_phone.setText(detailsEntityList.get(0).getConsigneephone());
        tv_car_type.setText(detailsEntityList.get(0).getWeightofgoods());
        tv_yongchetime.setText(detailsEntityList.get(0).getPickuptime());
        tv_yuji_daoda.setText(detailsEntityList.get(0).getExpectedarrivaltime());
        tv_dingdantime.setText(detailsEntityList.get(0).getCreatetime());
        tv_address.setText(detailsEntityList.get(0).getStartaddress());
        tv_address1.setText(detailsEntityList.get(0).getEndaddress());
        if (detailsEntityList.get(0).getRemarks().equals("")){
            relative.setVisibility(View.GONE);
        }else {
            relative.setVisibility(View.VISIBLE);
            tv_beizhu.setText(detailsEntityList.get(0).getRemarks());
        }
//        tv_juli.setText(detailsEntityList.get(0).getTotal_mileage() + "公里");
        tv_money.setText(detailsEntityList.get(0).getOrdertotalprice()+"");
        if (detailsEntityList.get(0).getDriver_name().equals("")) {
            relative1.setVisibility(View.GONE);
            view10.setVisibility(View.GONE);
            relative2.setVisibility(View.GONE);
            tv_info.setVisibility(View.VISIBLE);
        } else {
            relative1.setVisibility(View.VISIBLE);
            view10.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            tv_info.setVisibility(View.GONE);
            tv_siji_name.setText(detailsEntityList.get(0).getDriver_name());
            tv_siji_phone.setText(detailsEntityList.get(0).getDriver_phone());
        }

//        if (detailsEntityList.get(0).getCust_orderstatus() == -1) {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.GONE);
//        } else if (detailsEntityList.get(0).getCust_orderstatus() == 0) {
//            if (detailsEntityList.get(0).getDriver_orderstatus() == 0) {
//                btn_quxiao.setVisibility(View.VISIBLE);
//                btn_kaishi.setVisibility(View.VISIBLE);
//                btn_jieshu.setVisibility(View.GONE);
//            } else {
//                btn_quxiao.setVisibility(View.VISIBLE);
//                btn_kaishi.setVisibility(View.GONE);
//                btn_jieshu.setVisibility(View.GONE);
//            }
//
//
//        } else if (detailsEntityList.get(0).getCust_orderstatus() == 1) {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.VISIBLE);
//        } else {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.GONE);
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quxiao1:
                final AlertDialog dialog1 = new AlertDialog.Builder(this).create();
                dialog1.setCancelable(false);
                dialog1.show();
                dialog1.getWindow().setContentView(R.layout.dialog_querenquxiao);
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                TextView textView1= (TextView) dialog1.getWindow().findViewById(R.id.tv_feilv);
                textView1.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()*100+"%的费用");
                dialog1.getWindow().findViewById(R.id.btn_jixudingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                dialog1.getWindow().findViewById(R.id.btn_quxiaodingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                initQuxiao();

                                dialog1.dismiss();

                            }
                        });
                break;
            case R.id.tv_quxiao:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setContentView(R.layout.dialog_querenquxiao);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                TextView textView= (TextView) dialog.getWindow().findViewById(R.id.tv_feilv);
                textView.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()*100+"%的费用");
                dialog.getWindow().findViewById(R.id.btn_jixudingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                dialog.getWindow().findViewById(R.id.btn_quxiaodingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                initQuxiao();

                                dialog.dismiss();

                            }
                        });
                break;
            case R.id.tv_jieshu:

                AlertDialog.Builder builder1=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定结束订单吗")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //结束订单
                                initJieshu();

                            }
                        });
                builder1.show();



                break;
            case R.id.tv_kaishi:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定开始订单吗")
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
                                initKaishi();
                            }
                        });
                builder.show();
                break;
            case R.id.tv_pingjia:
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(LogisticsOrderDetailsActivity.this);
                View view = LayoutInflater.from(LogisticsOrderDetailsActivity.this).inflate(R.layout.dialog_pingjiadingdan, null, false);
                dialog11.setView(view);
                final AlertDialog alertDialog = dialog11.create();
                alertDialog.setCancelable(false);
                Button button = (Button) view.findViewById(R.id.btn_tijiao);
                final ImageView imageView1 = (ImageView) view.findViewById(R.id.img1);
                final ImageView imageView2 = (ImageView) view.findViewById(R.id.img2);
                final ImageView imageView3 = (ImageView) view.findViewById(R.id.img3);
                final ImageView imageView4 = (ImageView) view.findViewById(R.id.img4);
                final ImageView imageView5 = (ImageView) view.findViewById(R.id.img5);
                ed_content = (EditText) view.findViewById(R.id.ed_content);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ed_content.getText().toString().trim().equals("")) {
                            content = ed_content.getText().toString().trim();
                        }
                        initPingjia();
                        alertDialog.dismiss();
                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 1;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_f);
                        imageView3.setImageResource(R.mipmap.img_pingjia_f);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 2;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_f);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pingjia = 3;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 4;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_t);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 5;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_t);
                        imageView5.setImageResource(R.mipmap.img_pingjia_t);
                    }
                });
                alertDialog.show();
                break;
        }
    }

    private void initKaishi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.startSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("订单开始失败");
                            } else {
                                showToast("订单已开始");
                                //加载订单详情信息
                                initOrderDetails();

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

    //结束订单
    private void initJieshu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        map.put("order_money", detailsEntityList.get(0).getOrdertotalprice() + "");
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
//        map.put("siji_findtime", detailsEntityList.get(0).getSiji_findtime());
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.finishSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("结束订单失败");
                            } else {
                                showToast("订单结束成功");
                                initOrderDetails();

                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        System.out.println(e + "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initPingjia() {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", detailsEntityList.get(0).getId() + "");
        map.put("driver_fraction", pingjia + "");
        map.put("note", content);
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        map.put("ordertype", line);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.evlateOrder(data)
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
                            //加载订单详情信息
                            initOrderDetails();

                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("评价失败");
                            } else {
                                showToast("评价成功");

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

    //取消订单
    private void initQuxiao() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
        map.put("siji_findtime", detailsEntityList.get(0).getSiji_singletime());
        map.put("order_money", detailsEntityList.get(0).getOrdertotalprice() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.cancelSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 1) {
                                showToast("订单取消失败");
                            } else if (body.indexOf("true") != -1){
                                showToast("订单取消成功");
                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivity.this).create();
                                dialog.setCancelable(false);
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_quxiaochenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                                TextView textView= (TextView) dialog.getWindow().findViewById(R.id.tv_feilv1);
                                if (body.substring(1,body.length()-1).equals("trueTimeout")){
                                    textView.setVisibility(View.VISIBLE);
                                }else if (body.substring(1,body.length()-1).equals("true")){
                                    textView.setVisibility(View.INVISIBLE);
                                }
                                textView.setText("已扣除您"+feiLvEntityList.get(0).getFee()*100+"%费用");

                                dialog.getWindow().findViewById(R.id.img_close)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                //加载订单详情信息
                                initOrderDetails();

                            }else {
                                Toast.makeText(LogisticsOrderDetailsActivity.this, "111", Toast.LENGTH_SHORT).show();
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
}
