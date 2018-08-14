package com.lxwls.hdsjd.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.DriverZhaoHuoAdapter;
import com.lxwls.hdsjd.adapter.RecyclerViewSuYunAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.AuditOrderEntity;
import com.lxwls.hdsjd.bean.AuditOrderEntity1;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengEntity;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengImgEntity;
import com.lxwls.hdsjd.bean.TongChengBanJiaDan;
import com.lxwls.hdsjd.bean.TongChengXiaoJianDan;
import com.lxwls.hdsjd.ui.activity.DriverCertificationActivity;
import com.lxwls.hdsjd.ui.activity.DriverCertificationStateActivity;
import com.lxwls.hdsjd.ui.activity.DriverZhaoHuoActivity;
import com.lxwls.hdsjd.ui.activity.DriverZhaoHuoDetailsActivity;
import com.lxwls.hdsjd.ui.activity.HuoDKuaiyun1Activity;
import com.lxwls.hdsjd.ui.activity.HuoDSuyun1Activity;
import com.lxwls.hdsjd.ui.activity.LogisticsOrderDetailsActivity;
import com.lxwls.hdsjd.ui.activity.LogisticsOrderDetailsActivity1;
import com.lxwls.hdsjd.ui.activity.SuYun_BanJiaDan_XiangQingActivity;
import com.lxwls.hdsjd.ui.activity.SuYun_XiaoJianDan_XiangQiangAvtivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lxwls.hdsjd.base.Constant.REQUEST_CODE;

/**
 * Created by admin on 2018/3/12.（首页-货滴速运-列表）
 */

public class HuoDSuYun1Fragment extends Fragment implements OnRefreshListener, OnLoadmoreListener, AMapLocationListener, RouteSearch.OnRouteSearchListener {

    private RecyclerView act_recyclerView;
    private SmartRefreshLayout smart;
    private int type = 0;
    private TongChengXiaoJianDan tongChengXiaoJianDan;
    private TongChengXiaoJianDan tongChengXiaoJianDan1;
    private TongChengBanJiaDan tongChengBanJiaDan;
    private TongChengBanJiaDan tongChengBanJiaDan1;
    private RecyclerView.LayoutManager manager;
    private RecyclerViewSuYunAdapter adapter;
    private final int ROUTE_TYPE_DRIVE = 0;
    private RouteSearch mRouteSearch;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    private double licheng;

    private double latitude;//纬度
    private double longitude;//精度
    private String city = "";
    private int page = 1;
    private int i1 = 0;
    private int type11 = 0;

    private int si1 = 0;
    private int stype11 = 0;

    private int position;
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.huodisuyun_liebiao, container, false);

        city = getActivity().getIntent().getStringExtra("cs");
        act_recyclerView = (RecyclerView) view.findViewById(R.id.act_recyclerView);
        smart = (SmartRefreshLayout) view.findViewById(R.id.smart);



        mRouteSearch = new RouteSearch(getContext());
        mRouteSearch.setRouteSearchListener(this);

        smart.setOnRefreshListener(this);
        smart.setOnLoadmoreListener(this);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
            }
        });
        smart.setDisableContentWhenRefresh(true);
        smart.setDisableContentWhenLoading(true);

//        //加载租赁订单列表数据
//        initDingDanSearch();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        location();
    }

    public void location() {
        type11 = 0;
        i1 = 0;

        stype11 = 0;
        si1 = 0;
        page = 1;

        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    public void initDingDanSearch1() {
        page = 1;
        if (type == 0) {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectSuyunOrderList(page, 5, params)
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
                                System.out.println(body.length() + "");
                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    if (tongChengXiaoJianDan!=null&&adapter!=null){
                                        if (tongChengXiaoJianDan.getRows() != null) {
                                            tongChengXiaoJianDan.getRows().clear();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengXiaoJianDan = gson.fromJson(body, TongChengXiaoJianDan.class);

                                    LatLonPoint start = new LatLonPoint(latitude, longitude);
                                    LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengXiaoJianDan.getRows().get(i1).getStartlatitude()), Double.parseDouble(tongChengXiaoJianDan.getRows().get(i1).getStartlongitude()));
                                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

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
        } else {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectLogisticsOrderList(page, 5, params)
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
//                            System.out.println(body);


                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    if (tongChengBanJiaDan!=null&&adapter!=null){
                                        if (tongChengBanJiaDan.getRows() != null) {
                                            tongChengBanJiaDan.getRows().clear();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengBanJiaDan = gson.fromJson(body, TongChengBanJiaDan.class);

                                    LatLonPoint start = new LatLonPoint(latitude, longitude);
                                    LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengBanJiaDan.getRows().get(i1).getLatitude()), Double.parseDouble(tongChengBanJiaDan.getRows().get(i1).getLongitude()));
                                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

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

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint startpoint, LatLonPoint endporint) {

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startpoint, endporint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            showWaitDialog("正在加载...").setCancelable(false);
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }


    public void initDingDanSearch() {
        page = 1;
        if (type == 0) {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectSuyunOrderList(page, 5, params)
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
                                System.out.println(body.length() + "");
                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    if (tongChengXiaoJianDan!=null&&adapter!=null){
                                        if (tongChengXiaoJianDan.getRows() != null) {
                                            tongChengXiaoJianDan.getRows().clear();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengXiaoJianDan = gson.fromJson(body, TongChengXiaoJianDan.class);
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
        } else {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectLogisticsOrderList(page, 5, params)
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
//                            System.out.println(body);


                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    if (tongChengBanJiaDan!=null&&adapter!=null){
                                        if (tongChengBanJiaDan.getRows() != null) {
                                            tongChengBanJiaDan.getRows().clear();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengBanJiaDan = gson.fromJson(body, TongChengBanJiaDan.class);
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

    }

    private void initRecyclerView() {

        if (type == 0) {
            adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengXiaoJianDan.getRows(), type);
        } else {
            adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengBanJiaDan.getRows(), type, 0);
        }

        manager = new LinearLayoutManager(getContext());
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickChild(new RecyclerViewSuYunAdapter.OnClickChild() {
            @Override
            public void onClick(int position) {
                if (type == 0) {
                    initPanduan(position);

//                    Intent intent = new Intent(getContext(), SuYun_XiaoJianDan_XiangQiangAvtivity.class);
//                    intent.putExtra("id", tongChengXiaoJianDan.getRows().get(position).getId() + "");
//                    intent.putExtra("juli", tongChengXiaoJianDan.getRows().get(position).getLicheng() + "");
//                    intent.putExtra("cs", city);
//                    startActivity(intent);
                } else {
                    initPanduan(position);

//                    Intent intent = new Intent(getContext(), SuYun_BanJiaDan_XiangQingActivity.class);
//                    intent.putExtra("id", tongChengBanJiaDan.getRows().get(position).getId() + "");
//                    intent.putExtra("juli", tongChengBanJiaDan.getRows().get(position).getLicheng() + "");
//                    intent.putExtra("cs", city);
//                    startActivity(intent);
                }
            }
        });

        adapter.setOnCallPhone(new RecyclerViewSuYunAdapter.OnCallPhone() {
            @Override
            public void onClick(int position) {
                HuoDSuYun1Fragment.this.position=position;
                String consigneephone="";
                if (type==0){
                    consigneephone = tongChengXiaoJianDan.getRows().get(position).getConsigneephone();
                }else {
                    consigneephone = tongChengBanJiaDan.getRows().get(position).getOwner_link_phone();
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定拨打电话："+consigneephone)
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
                builder.show();

            }
        });
    }

    private void initPanduan(final int position) {
        PileApi.instance.checkDriverSPStatus()
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
                            String substring = body.substring(1, body.length() - 1);

                            if (body.indexOf("司机已被停用") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                        .setTitle("提示")
                                        .setMessage(body)
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                                return;
                            }
                            if (substring.equals("-1")) {
//                                startActivity(new Intent(getContext(), DriverCertificationActivity.class));
                                initTishi(1);
                            } else {

                                Gson gson = new Gson();
                                checkSiJiRenZhengEntityList = gson.fromJson(body, new TypeToken<List<CheckSiJiRenZhengEntity>>() {
                                }.getType());
                                String imglist = checkSiJiRenZhengEntityList.get(0).getImglist();
                                Gson gson1 = new Gson();
                                checkSiJiRenZhengImgEntityList = gson1.fromJson(imglist, new TypeToken<List<CheckSiJiRenZhengImgEntity>>() {
                                }.getType());
                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 0) {
                                    //审核中
//                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
                                    initTishi(2);

                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
                                    //审核通过
                                    if (type==0){
                                        Intent intent = new Intent(getContext(), SuYun_XiaoJianDan_XiangQiangAvtivity.class);
                                        intent.putExtra("id", tongChengXiaoJianDan.getRows().get(position).getId() + "");
                                        intent.putExtra("juli", tongChengXiaoJianDan.getRows().get(position).getLicheng() + "");
                                        intent.putExtra("cs", city);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getContext(), SuYun_BanJiaDan_XiangQingActivity.class);
                                        intent.putExtra("id", tongChengBanJiaDan.getRows().get(position).getId() + "");
                                        intent.putExtra("juli", tongChengBanJiaDan.getRows().get(position).getLicheng() + "");
                                        intent.putExtra("cs", city);
                                        startActivity(intent);
                                    }
                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
                                    //审核拒绝
//                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
                                    initTishi(2);
                                }
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

    private void initTishi(final int line) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
                .setTitle("您还没通过司机认证")
                .setMessage("请到 我的-司机认证 提交相应信息")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (line==1){
                            startActivity(new Intent(getContext(), DriverCertificationActivity.class));
                        }else if (line==2){
                            startActivity(new Intent(getContext(), DriverCertificationStateActivity.class));
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    public void type(int type) {
        this.type = type;
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (type == 0) {
            type11 = 0;
            i1 = 0;
        } else {
            stype11 = 0;
            si1 = 0;
        }
        page = 1;
        initDingDanSearch1();
        if (smart.isRefreshing()) {
            smart.finishRefresh();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (type == 0) {
            type11 = 1;
            i1 = 0;
        } else {
            stype11 = 1;
            si1 = 0;
        }

        page++;
        if (type == 0) {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectSuyunOrderList(page, 5, params)
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
//                            System.out.println(body);
                                if (body.indexOf("false") != -1 || body.length() < 41) {
//                                    if (tongChengXiaoJianDan.getRows() != null) {
//                                        tongChengXiaoJianDan.getRows().clear();
//                                        adapter.notifyDataSetChanged();
//                                    }
                                    if (smart.isLoading()) {
                                        smart.finishLoadmore();
                                    }
                                    Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengXiaoJianDan1 = gson.fromJson(body, TongChengXiaoJianDan.class);
                                    if (!tongChengXiaoJianDan1.getRows().isEmpty()) {
                                        LatLonPoint start = new LatLonPoint(latitude, longitude);
                                        LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengXiaoJianDan1.getRows().get(i1).getStartlatitude()), Double.parseDouble(tongChengXiaoJianDan1.getRows().get(i1).getStartlongitude()));
                                        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
                                    }

//                                    tongChengXiaoJianDan.getRows().addAll(tongChengXiaoJianDan1.getRows());
//                                    adapter.notifyDataSetChanged();
                                    if (smart.isLoading()) {
                                        smart.finishLoadmore();
                                    }

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
        } else {
            HashMap map = new HashMap();
            map.put("city", city);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectLogisticsOrderList(page, 5, params)
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
//                            System.out.println(body);


                                if (body.indexOf("false") != -1 || body.length() < 41) {
//                                    if (tongChengBanJiaDan.getRows() != null) {
//                                        tongChengBanJiaDan.getRows().clear();
//                                        adapter.notifyDataSetChanged();
//                                    }
                                    if (smart.isLoading()) {
                                        smart.finishLoadmore();
                                    }
                                    Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    tongChengBanJiaDan1 = gson.fromJson(body, TongChengBanJiaDan.class);
                                    if (!tongChengBanJiaDan1.getRows().isEmpty()) {
                                        LatLonPoint start = new LatLonPoint(latitude, longitude);
                                        LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengBanJiaDan1.getRows().get(i1).getLatitude()), Double.parseDouble(tongChengBanJiaDan1.getRows().get(i1).getLongitude()));
                                        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
                                    }

//                                    tongChengBanJiaDan.getRows().addAll(tongChengBanJiaDan1.getRows());
//                                    adapter.notifyDataSetChanged();
                                    if (smart.isLoading()) {
                                        smart.finishLoadmore();
                                    }

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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                latitude = aMapLocation.getLatitude();//获取纬度
                longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                initDingDanSearch1();

//                Toast.makeText(this, aMapLocation.getLatitude() + "    " + aMapLocation.getLongitude() + "", Toast.LENGTH_SHORT).show();

//                initQiangDanLieBiao1();

//                for (DriverZhaohuoEntity driverZhaohuoEntity:driverZhaohuoEntityList){
////                    Toast.makeText(this, "00000000", Toast.LENGTH_SHORT).show();
//                    LatLonPoint start = new LatLonPoint(latitude, longitude);
//                    LatLonPoint end = new LatLonPoint(driverZhaohuoEntity.getLatitude(), driverZhaohuoEntity.getLongitude());
//                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
//
//                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_LONG).show();

                initDingDanSearch();
//                initQiangDanLieBiao();

//                adapter = new DriverZhaoHuoAdapter(this, driverZhaohuoEntity1.getRows(), latitude, longitude);
//                manager = new LinearLayoutManager(this);
//                act_recyclerView.setLayoutManager(manager);
//                act_recyclerView.setAdapter(adapter);
//
//                adapter.setOnClickItem(new DriverZhaoHuoAdapter.OnClickItem() {
//                    @Override
//                    public void onClick(int position) {
//                        Intent intent = new Intent(DriverZhaoHuoActivity.this, DriverZhaoHuoDetailsActivity.class);
//                        intent.putExtra("id", driverZhaohuoEntity1.getRows().get(position).getId() + "");
//                        startActivity(intent);
//                    }
//                });
//                adapter.setOnClickItem1(new DriverZhaoHuoAdapter.OnClickItem1() {
//                    @Override
//                    public void onClick(int position) {
//                        DriverZhaoHuoActivity.this.position = position;
//                        testCallPhone();
//                    }
//                });

            }
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == 1000) {
            if (type == 0) {
                i1++;
                if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                    if (driveRouteResult.getPaths().size() > 0) {
                        final DrivePath drivePath = driveRouteResult.getPaths()
                                .get(0);
                        double dis = drivePath.getDistance();
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                        BigDecimal b = new BigDecimal(dis / 1000);


                        licheng = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                    DriverZhaohuoEntity driverZhaohuoEntity=new DriverZhaohuoEntity();
//                    driverZhaohuoEntity.setLicheng(licheng);
//                    Toast.makeText(this, licheng + "", Toast.LENGTH_SHORT).show();
                        if (type11 == 0) {
                            tongChengXiaoJianDan.getRows().get(i1 - 1).setLicheng(licheng);

                            if (i1 < tongChengXiaoJianDan.getRows().size()) {

                                LatLonPoint start = new LatLonPoint(latitude, longitude);
                                LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengXiaoJianDan.getRows().get(i1).getStartlatitude()), Double.parseDouble(tongChengXiaoJianDan.getRows().get(i1).getStartlongitude()));
                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                            }


                            if (i1 == tongChengXiaoJianDan.getRows().size()) {
                                if (type == 0) {
                                    adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengXiaoJianDan.getRows(), type);
                                } else {
                                    adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengBanJiaDan.getRows(), type, 0);
                                }

                                manager = new LinearLayoutManager(getContext());
                                act_recyclerView.setLayoutManager(manager);
                                act_recyclerView.setAdapter(adapter);
                                adapter.setOnClickChild(new RecyclerViewSuYunAdapter.OnClickChild() {
                                    @Override
                                    public void onClick(int position) {
                                        if (type == 0) {
                                            initPanduan(position);
//                                            Intent intent = new Intent(getContext(), SuYun_XiaoJianDan_XiangQiangAvtivity.class);
//                                            intent.putExtra("id", tongChengXiaoJianDan.getRows().get(position).getId() + "");
//                                            intent.putExtra("juli", tongChengXiaoJianDan.getRows().get(position).getLicheng() + "");
//                                            intent.putExtra("cs", city);
//                                            startActivity(intent);
                                        } else {
                                            initPanduan(position);
//                                            Intent intent = new Intent(getContext(), SuYun_BanJiaDan_XiangQingActivity.class);
//                                            intent.putExtra("id", tongChengBanJiaDan.getRows().get(position).getId() + "");
//                                            intent.putExtra("juli", tongChengBanJiaDan.getRows().get(position).getLicheng() + "");
//                                            intent.putExtra("cs", city);
//                                            startActivity(intent);
                                        }
                                    }
                                });
                                adapter.setOnCallPhone(new RecyclerViewSuYunAdapter.OnCallPhone() {
                                    @Override
                                    public void onClick(int position) {
                                        HuoDSuYun1Fragment.this.position=position;
                                        String consigneephone="";
                                        if (type==0){
                                            consigneephone = tongChengXiaoJianDan.getRows().get(position).getConsigneephone();
                                        }else {
                                            consigneephone = tongChengBanJiaDan.getRows().get(position).getOwner_link_phone();
                                        }
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
                                                .setTitle("提示")
                                                .setMessage("确定拨打电话："+consigneephone)
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
                                        builder.show();
                                    }
                                });
                            }
                        } else if (type11 == 1) {
                            tongChengXiaoJianDan1.getRows().get(i1 - 1).setLicheng(licheng);

                            if (i1 < tongChengXiaoJianDan1.getRows().size()) {

                                LatLonPoint start = new LatLonPoint(latitude, longitude);
                                LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengXiaoJianDan1.getRows().get(i1).getStartlatitude()), Double.parseDouble(tongChengXiaoJianDan1.getRows().get(i1).getStartlongitude()));
                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                            }


                            if (i1 == tongChengXiaoJianDan1.getRows().size()) {
                                tongChengXiaoJianDan.getRows().addAll(tongChengXiaoJianDan1.getRows());
                                adapter.notifyDataSetChanged();
                            }
                        }


                        //showToast(zlc + "");

                    }
                }
            } else {
                si1++;
                if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                    if (driveRouteResult.getPaths().size() > 0) {
                        final DrivePath drivePath = driveRouteResult.getPaths()
                                .get(0);
                        double dis = drivePath.getDistance();
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                        BigDecimal b = new BigDecimal(dis / 1000);


                        licheng = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                    DriverZhaohuoEntity driverZhaohuoEntity=new DriverZhaohuoEntity();
//                    driverZhaohuoEntity.setLicheng(licheng);
//                    Toast.makeText(this, licheng + "", Toast.LENGTH_SHORT).show();
                        if (stype11 == 0) {
                            tongChengBanJiaDan.getRows().get(si1 - 1).setLicheng(licheng);

                            if (si1 < tongChengBanJiaDan.getRows().size()) {

                                LatLonPoint start = new LatLonPoint(latitude, longitude);
                                LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengBanJiaDan.getRows().get(si1).getLatitude()), Double.parseDouble(tongChengBanJiaDan.getRows().get(si1).getLongitude()));
                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                            }


                            if (si1 == tongChengBanJiaDan.getRows().size()) {
                                if (type == 0) {
                                    adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengXiaoJianDan.getRows(), type);
                                } else {
                                    adapter = new RecyclerViewSuYunAdapter(getContext(), tongChengBanJiaDan.getRows(), type, 0);
                                }

                                manager = new LinearLayoutManager(getContext());
                                act_recyclerView.setLayoutManager(manager);
                                act_recyclerView.setAdapter(adapter);
                                adapter.setOnClickChild(new RecyclerViewSuYunAdapter.OnClickChild() {
                                    @Override
                                    public void onClick(int position) {
                                        if (type == 0) {
                                            initPanduan(position);
//                                            Intent intent = new Intent(getContext(), SuYun_XiaoJianDan_XiangQiangAvtivity.class);
//                                            intent.putExtra("id", tongChengXiaoJianDan.getRows().get(position).getId() + "");
//                                            intent.putExtra("juli", tongChengXiaoJianDan.getRows().get(position).getLicheng() + "");
//                                            intent.putExtra("cs", city);
//                                            startActivity(intent);
                                        } else {
                                            initPanduan(position);
//                                            Intent intent = new Intent(getContext(), SuYun_BanJiaDan_XiangQingActivity.class);
//                                            intent.putExtra("id", tongChengBanJiaDan.getRows().get(position).getId() + "");
//                                            intent.putExtra("juli", tongChengBanJiaDan.getRows().get(position).getLicheng() + "");
//                                            intent.putExtra("cs", city);
//                                            startActivity(intent);
                                        }
                                    }
                                });
                                adapter.setOnCallPhone(new RecyclerViewSuYunAdapter.OnCallPhone() {
                                    @Override
                                    public void onClick(int position) {
                                        HuoDSuYun1Fragment.this.position=position;
                                        String consigneephone="";
                                        if (type==0){
                                            consigneephone = tongChengXiaoJianDan.getRows().get(position).getConsigneephone();
                                        }else {
                                            consigneephone = tongChengBanJiaDan.getRows().get(position).getOwner_link_phone();
                                        }
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
                                                .setTitle("提示")
                                                .setMessage("确定拨打电话："+consigneephone)
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
                                        builder.show();
                                    }
                                });
                            }
                        } else if (stype11 == 1) {
                            tongChengBanJiaDan1.getRows().get(si1 - 1).setLicheng(licheng);

                            if (si1 < tongChengBanJiaDan1.getRows().size()) {

                                LatLonPoint start = new LatLonPoint(latitude, longitude);
                                LatLonPoint end = new LatLonPoint(Double.parseDouble(tongChengBanJiaDan1.getRows().get(si1).getLatitude()), Double.parseDouble(tongChengBanJiaDan1.getRows().get(si1).getLongitude()));
                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                            }


                            if (si1 == tongChengBanJiaDan1.getRows().size()) {
                                tongChengBanJiaDan.getRows().addAll(tongChengBanJiaDan1.getRows());
                                adapter.notifyDataSetChanged();
                            }
                        }


                        //showToast(zlc + "");

                    }
                }
            }

        } else {
            Toast.makeText(getContext(), "路线信息拉取失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }

    private void callPhone(String phoneNum) {
        String consigneephone="";
        if (type==0){
            consigneephone = tongChengXiaoJianDan.getRows().get(position).getConsigneephone();
        }else {
            consigneephone = tongChengBanJiaDan.getRows().get(position).getOwner_link_phone();
        }
        //直接拨号
        Uri uri = Uri.parse("tel:" + consigneephone );
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(getContext(), "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
