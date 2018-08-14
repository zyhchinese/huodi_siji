package com.lxwls.hdsjd;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.account.AccountHelper;
import com.lxwls.hdsjd.account.LoginActivity;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.BaseApplication;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BaseActivity;
import com.lxwls.hdsjd.bean.CarInfoEntity;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengEntity;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengImgEntity;
import com.lxwls.hdsjd.bean.User;
import com.lxwls.hdsjd.bean.VersionBean;
import com.lxwls.hdsjd.interf.OnTabReselectListener;
import com.lxwls.hdsjd.nav.NavFragment;
import com.lxwls.hdsjd.nav.NavigationButton;
import com.lxwls.hdsjd.ui.activity.CarAudit1Activity;
import com.lxwls.hdsjd.ui.activity.HuoDKuaiyun1Activity;
import com.lxwls.hdsjd.ui.activity.HuoDKuaiyunActivity;
import com.lxwls.hdsjd.ui.activity.HuoDSuyun1Activity;
import com.lxwls.hdsjd.ui.activity.HuoDSuyunActivity;
import com.lxwls.hdsjd.update.CheckUpdateManager;
import com.lxwls.hdsjd.utils.BroadCastManager;
import com.lxwls.hdsjd.utils.DialogHelper;
import com.lxwls.hdsjd.utils.TDevice;
import com.lxwls.hdsjd.utils.map.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener, AMapLocationListener,
        EasyPermissions.PermissionCallbacks, CheckUpdateManager.RequestPermissions {
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private static final int REQUEST_CODE_CAMERA = 0x03;//相机权限
    private static final int REQUEST_CODE_LOCTION = 0x02;//相机权限
    public static final String ACTION_NOTICE = "ACTION_NOTICE";
    private long mBackPressedTime;
    private LinearLayout llyBottomScanner;
    private NavFragment mNavBar;
    private double x = 0.0;
    private double y = 0.0;
    private String[] strMsg;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    ImageView imageView;
    private VersionBean mVersion;
    RequestOptions mOptions;
    boolean isLogin = false;
    private TextView tvChargeState;
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lxwls.hdsjd.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private AlertDialog payalertDialog;
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    private int type;
    private String location="";
    private String location11="";
    private String driverid="";

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        SharedPreferences sharedPreferences=getSharedPreferences("userxinxi",Context.MODE_PRIVATE);
        driverid = sharedPreferences.getString("driverid", "0");
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);
        imageView = (ImageView) findViewById(R.id.img_scanner_bottom);
        tvChargeState = (TextView) findViewById(R.id.tv_charge_state);
        tvChargeState.setText("找货");
        float w = TDevice.getScreenWidth();
        float h = TDevice.getScreenHeight();
        float fW = 50 / 540 * w;
        float fH = 50 / 960 * h;
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.img_sijizhaohuo)
                .error(R.mipmap.img_sijizhaohuo)
                .fitCenter()
                .override((int) fW, (int) fH);
        Glide.with(this)
                .load(R.mipmap.img_sijizhaohuo)
                .apply(mOptions)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payalertDialog = new AlertDialog.Builder(MainActivity.this).create();
                payalertDialog.show();
                payalertDialog.getWindow().setContentView(R.layout.layout_main_dialog_show);
                payalertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams p = payalertDialog.getWindow().getAttributes();
                p.width = (int) (display.getWidth() * 0.8);
                payalertDialog.getWindow().setAttributes(p);
                payalertDialog.getWindow()
                        .findViewById(R.id.dialog_goback)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payalertDialog.dismiss();

                            }
                        });
                payalertDialog.getWindow()
                        .findViewById(R.id.dialog_sy)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Intent intent1 = new Intent(MainActivity.this, HuoDSuyun1Activity.class);
//                                intent1.putExtra("cs", "济南市");
//                                startActivity(intent1);

//                                type=1;
//                                initPanduan();

                                Intent intent1 = new Intent(MainActivity.this, HuoDSuyun1Activity.class);
                                intent1.putExtra("cs", location);
                                startActivity(intent1);
                                payalertDialog.dismiss();
                            }
                        });
                payalertDialog.getWindow()
                        .findViewById(R.id.dialog_ky)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Intent intent1 = new Intent(MainActivity.this, HuoDKuaiyun1Activity.class);
//                                intent1.putExtra("cs", "济南市");
//                                startActivity(intent1);

//                                type=2;
//                                initPanduan();

                                Intent intent1 = new Intent(MainActivity.this, HuoDKuaiyun1Activity.class);
                                intent1.putExtra("cs", location);
                                intent1.putExtra("cs11", location11);
                                startActivity(intent1);
                                payalertDialog.dismiss();
                            }
                        });
            }
        });

        llyBottomScanner = (LinearLayout) findViewById(R.id.lly_bottom_scanner);
//        initLogin();

        llyBottomScanner.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    //二维码扫描
//                    requestCamral();
                } else {
                    DialogHelper.getConfirmDialog(MainActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }, null).show();
                }
            }
        });
        //首次启动应用执行
        if (AppContext.get("isFirstComing", true))

        {

            AppContext.set("isFirstComing", false);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doNewIntent(getIntent(), true);
        requestLocation();

    }

    private void initPanduan() {
        PileApi.instance.checkDriverSPStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            String substring = body.substring(1, body.length() - 1);

                            if (body.indexOf("司机已被停用") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
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
                                initTishi();
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
                                    initTishi();

                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
                                    //审核通过
                                    if (type==1){
                                        Intent intent1 = new Intent(MainActivity.this, HuoDSuyun1Activity.class);
                                        intent1.putExtra("cs", location);
                                        startActivity(intent1);
                                    }else if (type==2){
                                        Intent intent1 = new Intent(MainActivity.this, HuoDKuaiyun1Activity.class);
                                        intent1.putExtra("cs", location);
                                        startActivity(intent1);
                                    }
                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
                                    //审核拒绝
//                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
                                    initTishi();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }
    private void initTishi() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("您还没通过司机认证")
                .setMessage("请到 我的-司机认证 提交相应信息")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLogin();
        PileApi.instance.mCheckLoginState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                isLogin = true;
//                                PileApi.instance.mCheckChargeState()
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Observer<ResponseBody>() {
//                                            @Override
//                                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                                            }
//
//                                            @Override
//                                            public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
//                                                try {
//                                                    String body = responseBody.string();
//                                                    if (body.equals("\"true\"")) {
//                                                        tvChargeState.setText("正在充电");
//                                                        Glide.with(MainActivity.this)
//                                                                .load(R.mipmap.nav_center_button_charging)
//                                                                .apply(mOptions)
//                                                                .into(imageView);
//                                                        llyBottomScanner.setOnClickListener(new View.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(View v) {
//                                                                if (isLogin) {
//                                                                    startActivity(new Intent(MainActivity.this, ChargeViewActivity.class));
//
//                                                                } else {
//                                                                    DialogHelper.getConfirmDialog(MainActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//                                                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                                                        }
//                                                                    }, null).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    } else {
//                                                        tvChargeState.setText("发货");
//                                                        Glide.with(MainActivity.this)
//                                                                .load(R.mipmap.nav_center_button)
//                                                                .apply(mOptions)
//                                                                .into(imageView);
//                                                        llyBottomScanner.setOnClickListener(new View.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(View v) {
//                                                                if (isLogin) {
//                                                                    requestCamral();
//                                                                } else {
//                                                                    DialogHelper.getConfirmDialog(MainActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//                                                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                                                        }
//                                                                    }, null).show();
//                                                                }
//                                                            }
//                                                        });
//
//
//                                                    }
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//
//                                            }
//
//                                            @Override
//                                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                                            }
//
//                                            @Override
//                                            public void onComplete() {
//
//                                            }
//                                        });

                            } else {

                                isLogin = false;
//                                tvChargeState.setText("发货");
//                                Glide.with(MainActivity.this)
//                                        .load(R.mipmap.nav_center_button)
//                                        .apply(mOptions)
//                                        .into(imageView);
//                                llyBottomScanner.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        if (isLogin) {
//                                            requestCamral();
//                                        } else {
//                                            DialogHelper.getConfirmDialog(MainActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                                }
//                                            }, null).show();
//                                        }
//
//                                    }
//                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doNewIntent(intent, false);
    }

    @SuppressWarnings("unused")
    private void doNewIntent(Intent intent, boolean isCreate) {
        if (intent == null || intent.getAction() == null)
            return;
        String action = intent.getAction();
        if (action.equals(ACTION_NOTICE)) {
            NavFragment bar = mNavBar;
            if (bar != null) {
                bar.select(3);
            }
        }
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null
                && fragment instanceof OnTabReselectListener) {
            OnTabReselectListener listener = (OnTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        // 可以进行定位、检测更新等操作
        checkUpdate();
    }

    @Override
    public void onBackPressed() {

        boolean isDoubleClick = BaseApplication.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true);
        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                //定位完成
                case Utils.MSG_LOCATION_FINISH:
                    String result = "";
                    try {
                        AMapLocation loc = (AMapLocation) msg.obj;
                        result = Utils.getLocationStr(loc, 1);
                        strMsg = result.split(",");
                        if (strMsg[0].equals("0")) {
                            //    textView.setText("地址：" + strMsg[0] + "\n" + "经    度：" + strMsg[1] + "\n" + "纬    度：" + strMsg[2]);
                            x = Double.valueOf(strMsg[3]);
                            y = Double.valueOf(strMsg[2]);
                            location=strMsg[4];
                            location11=loc.getProvince();
                            Constant.KUAIYUN_SHENGFEN=loc.getProvince();
                            Constant.HUOYUAN_SHENG=loc.getProvince();
                            //发送广播
                            Intent intent2 = new Intent();
                            intent2.putExtra("location", strMsg[4]);
                            intent2.putExtra("sheng11", loc.getProvince());
                            intent2.setAction("location");
                            BroadCastManager.getInstance().sendBroadCast(MainActivity.this, intent2);
                            System.err.print("定位成功！");
                        } else {
                            System.err.print("GPS信号弱或者无权限,定位失败");
                        }
                    } catch (Exception e) {
                        System.err.print("GPS信号弱或者无权限,定位失败");
                    }
                    break;
                default:
                    break;
            }
        }

        ;

    };


    public void Location() {
        // TODO Auto-generated method stub
        try {
            locationClient = new AMapLocationClient(this);
            locationOption = new AMapLocationClientOption();
            // 设置定位模式为低功耗模式
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            // 设置定位监听
            locationClient.setLocationListener(this);
             locationOption.setOnceLocation(true);//设置为单次定位
            locationClient.setLocationOption(locationOption);// 设置定位参数
            // 启动定位
            locationClient.startLocation();
            mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "GPS信号弱或者无权限,定位失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("driverid", driverid);
        map.put("longitude", loc.getLongitude()+"");
        map.put("lattitude", loc.getLatitude()+"");
        map.put("gpsadd", loc.getAddress());
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.saveGps(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);

                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void call(VersionBean version) {
        this.mVersion = version;
        requestExternalStorage();
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //    DownloadService.startService(this, mVersion.getApp_url());
        } else {
            EasyPermissions.requestPermissions(this, "存储权限未授权,是否去授权", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    public void requestCamral() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MainActivity.this, com.lxwls.hdsjd.ui.activity.ScannerActivity.class);
            startActivityForResult(intent, Constant.REQUEST_CODE_CAMERA);
        } else {
            EasyPermissions.requestPermissions(this, "相机权限未授权,是否去授权", REQUEST_CODE_CAMERA, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_LOCTION)
    public void requestLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            Location();
        } else {
            EasyPermissions.requestPermissions(this, "定位权限未获取", REQUEST_CODE_LOCTION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE);
        }
    }

    /**
     * proxy request permission
     */
//    @AfterPermissionGranted(NearbyActivity.LOCATION_PERMISSION)
//    private void requestLocationPermission() {
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE)) {
//            startLbs();
//        } else {
//            EasyPermissions.requestPermissions(this, getString(R.string.need_lbs_permission_hint), LOCATION_PERMISSION,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE);
//        }
//    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        for (String perm : perms) {
            if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                DialogHelper.getConfirmDialog(this, "温馨提示", "需要开启货滴对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();

            } else {
                //  Setting.updateLocationPermission(getApplicationContext(), false);
            }

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void checkUpdate() {
        if (!AppContext.get(AppConfig.KEY_CHECK_UPDATE, true)) {
            return;
        }
        CheckUpdateManager manager = new CheckUpdateManager(this, false);
        manager.setCaller(this);
        manager.checkUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.city = "";
    }

    //是否登录
    private void initLogin() {
        PileApi.instance.mCheckLoginState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                isLogin = true;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (;;){
                                            try {
                                                Thread.sleep(1000*60*30);
                                                Location();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).start();
                            } else {
                                isLogin = false;

                                SharedPreferences sharedPreferences=getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                String name = sharedPreferences.getString("name", "");
                                String password = sharedPreferences.getString("password", "");
                                if (name.equals("")||password.equals("")){
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }else {
                                    requestLogin(name,password);
                                }


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void requestLogin(String tempUsername, String tempPwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", tempUsername);
        map.put("password", tempPwd);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.mLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
                        //  if (AccountHelper.login(user, headers)) {
                        try {
                            String body = responseBody.string();
                            if (body.length() < 10) {
                                if (body.equals("\"false\"")){
//                                    showToast("用户名或密码错误");
                                }

                            } else {
                                Gson gson = new Gson();
                                try {
                                    List<User> userList = gson.fromJson(body, new TypeToken<List<User>>() {
                                    }.getType());
                                    if (AccountHelper.login(userList.get(0))) {
                                        isLogin=true;

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (;;){
                                                    try {
                                                        Thread.sleep(1000*60*30);
                                                        Location();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }).start();

//                                        MyJPushMessageReceiver myJPushMessageReceiver=new MyJPushMessageReceiver();
//                                        JPushMessage jPushMessage=new JPushMessage();
//                                        jPushMessage.setAlias(userList.get(0).getCustphone());
//                                        myJPushMessageReceiver.onAliasOperatorResult(LoginActivity.this,jPushMessage);

//                                        JPushInterface.init(LoginActivity.this);
//                                        if (JPushInterface.isPushStopped(LoginActivity.this)){
//                                            JPushInterface.resumePush(LoginActivity.this);
//                                        }
//                                        JPushInterface.setAlias(LoginActivity.this,userList.get(0).getCustphone(),null);


                                    } else {
                                        showToast("账号异常");
                                    }
                                } catch (Exception e) {
                                    showToast("用户信息获取异常，请重新登录");
                                }

                            }

                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

}
