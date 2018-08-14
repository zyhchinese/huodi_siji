package com.lxwls.hdsjd.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.account.AccountHelper;
import com.lxwls.hdsjd.alipay.OrderInfoUtil2_0;
import com.lxwls.hdsjd.alipay.PayResult;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BaseActivity;
import com.lxwls.hdsjd.bean.KuaiYunTableBean;
import com.lxwls.hdsjd.bean.cxbean;
import com.lxwls.hdsjd.bean.hwlxBean;
import com.lxwls.hdsjd.widgets.duoxuandialog.MyAdapter;
import com.lxwls.hdsjd.widgets.duoxuandialog.ShuJuEntity;
import com.lxwls.hdsjd.widgets.wheelview.ChangeDatePopwindow3;
import com.lxwls.paylib.PayAPI;
import com.lxwls.paylib.WechatPayReq;
import com.lxwls.paylib.wxutils.WXPayUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
* 快运
* */
public class HuoDKuaiyunActivity extends BaseActivity {
    private ImageView go_back;
    private RelativeLayout qhsj, qd, zd;
    private RelativeLayout main;
    private EditText ed_password;
    private RelativeLayout cx, hwlx;
    private TextView qhtimetext, qdtext, zdtext, tv_cx_text, tv_hwlx, submit, shipmenttime;
    private boolean isok = false;
    private AlertDialog payalertDialog;
    protected LocalBroadcastManager mManager1;
    private EditText tv_qtlx, weight, volume, remarks, contactname, contactphone, settheprice;
    private BroadcastReceiver mReceiver1;
    private String cs = "";
    private boolean ispayok = false;
    private AlertDialog alertDialog;
    private double i;
    private static final int SDK_PAY_FLAG = 1;
    private KuaiYunTableBean tabledata;
    private Dialog dialog;
    private List<ShuJuEntity> duoxuanlist = new ArrayList<>();
    //显示单选或多选Dialog  true为单选，false为多选
    private boolean isOk = true;
    private TextView tv_ssx1, tv_ssx2;
    private String str;

    @Override
    protected int getContentView() {
        return R.layout.activity_huodikuaiyun;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tabledata = new KuaiYunTableBean();
        weight = (EditText) findViewById(R.id.weight);
        volume = (EditText) findViewById(R.id.volume);
        remarks = (EditText) findViewById(R.id.remarks);
        contactname = (EditText) findViewById(R.id.contactname);
        contactphone = (EditText) findViewById(R.id.contactphone);
        settheprice = (EditText) findViewById(R.id.settheprice);
        qhsj = (RelativeLayout) findViewById(R.id.zcsj);
        submit = (TextView) findViewById(R.id.submit);
        shipmenttime = (TextView) findViewById(R.id.shipmenttime);
        shipmenttime = (TextView) findViewById(R.id.shipmenttime);
        tv_cx_text = (TextView) findViewById(R.id.cx_text);
        tv_hwlx = (TextView) findViewById(R.id.hwlx_text);
        go_back = (ImageView) findViewById(R.id.go_back);
        qhtimetext = (TextView) findViewById(R.id.qhtimetext);
        main = (RelativeLayout) findViewById(R.id.main);
        qd = (RelativeLayout) findViewById(R.id.qd);
        zd = (RelativeLayout) findViewById(R.id.zd);
        cs = Constant.city;
        qdtext = (TextView) findViewById(R.id.qdtext);
        zdtext = (TextView) findViewById(R.id.zdtext);
        tv_ssx1 = (TextView) findViewById(R.id.tv_ssx1);
        tv_ssx2 = (TextView) findViewById(R.id.tv_ssx2);
        cx = (RelativeLayout) findViewById(R.id.cx);
        hwlx = (RelativeLayout) findViewById(R.id.hwlx);
        cs = getIntent().getStringExtra("cs");
        SharedPreferences sp = getSharedPreferences("userxinxi", Context.MODE_PRIVATE);
        contactname.setText(sp.getString("custname", ""));
        contactphone.setText(sp.getString("custphone", ""));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择起点".equals(qdtext.getText().toString())) {
                    showToast("请选择起点");
                    return;
                }
                if ("请选择终点".equals(zdtext.getText().toString())) {
                    showToast("请选择终点");
                    return;
                }
                if ("请选择装车时间".equals(shipmenttime.getText().toString())) {
                    showToast("请选择装车时间");
                    return;
                }
                if ("请选择".equals(tv_cx_text.getText().toString())) {
                    showToast("请选择车型");
                    return;
                }
                if ("".equals(weight.getText().toString()) && "".equals(volume.getText().toString())) {
                    showToast("请填写货物重量/体积");
                    return;
                }
                if ("".equals(contactname.getText().toString())) {
                    showToast("请填写联系人姓名");
                    return;
                }
                if ("".equals(contactphone.getText().toString())) {
                    showToast("请填写联系人电话");
                    return;
                }
                if (!phone(contactphone.getText().toString())){
                    showToast("联系人电话格式不正确");
                    return;
                }
                if ("".equals(settheprice.getText().toString())) {
                    showToast("请填写金额");
                    return;
                }

                tabledata.setContactname(contactname.getText().toString());
                tabledata.setCargotypenames(tv_hwlx.getText().toString());
                tabledata.setCartypenames(tv_cx_text.getText().toString());
                tabledata.setContactphone(contactphone.getText().toString());
                tabledata.setStartaddress(qdtext.getText().toString());
                tabledata.setEndaddress(zdtext.getText().toString());
                tabledata.setShipmenttime(shipmenttime.getText().toString());
                if (weight.getText().toString().equals("")) {
                    tabledata.setWeight("0");
                } else {
                    tabledata.setWeight(weight.getText().toString());
                }
                if (volume.getText().toString().equals("")) {
                    tabledata.setVolume("0");
                } else {
                    tabledata.setVolume(volume.getText().toString());
                }

                tabledata.setRemarks(remarks.getText().toString());
                tabledata.setSettheprice(settheprice.getText().toString());
                upload();

            }
        });
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadcx();
            }
        });
        hwlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadhwlx();
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        str = formatter.format(curDate);
        qhsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDatePopwindow3 mChangeBirthDialog = new ChangeDatePopwindow3(HuoDKuaiyunActivity.this);
                mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow3.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day, String day1) {
                        // TODO Auto-generated method stub
                        String yydate = year + " " + day1.replace("点", "") + ":" + day.replace("分", "");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分-秒
                        try {
                            Date date1 = dateFormat.parse(yydate);
                            Date date2 = dateFormat.parse(str);
                            if (date1.getTime() <= date2.getTime()) {
                                showToast("选择时间需大于当前时间");
                            } else {
                                shipmenttime.setText(yydate);
                                //  showToast(yydate);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取问题
                String question = "qidian";
                //包装数据
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                bundle.putString("x", tabledata.getStartlatitude());
                bundle.putString("y", tabledata.getStartlongitude());
                bundle.putString("type", "kuaiyun");
                Intent intent = new Intent(HuoDKuaiyunActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        zd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取问题
                String question = "zhongdian";
                //包装数据
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                bundle.putString("x", tabledata.getEndlatitude());
                bundle.putString("y", tabledata.getEndlongitude());
                bundle.putString("type", "kuaiyun");
                Intent intent = new Intent(HuoDKuaiyunActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, 001);
            }
        });
        getyeData();
    }
    private boolean phone(String mobiles){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * activity回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 001 && resultCode == 002) {

//            tv_ssx1.setVisibility(View.VISIBLE);
//            tv_ssx1.setText(data.getStringExtra("ssx1"));
            qdtext.setText(data.getStringExtra("myresuly") + " " + data.getStringExtra("address"));
            tabledata.setStartlongitude(data.getStringExtra("y"));
            tabledata.setStartlatitude(data.getStringExtra("x"));
            tabledata.setSprovince(data.getStringExtra("dwsheng"));
            tabledata.setScity(data.getStringExtra("dwshi"));
            tabledata.setScounty(data.getStringExtra("dwxian"));
        } else if (requestCode == 001 && resultCode == 003) {
//            tv_ssx2.setVisibility(View.VISIBLE);
//            tv_ssx2.setText(data.getStringExtra("ssx1"));
            zdtext.setText(data.getStringExtra("myresuly") + " " + data.getStringExtra("address"));
            tabledata.setEprovince(data.getStringExtra("dwsheng"));
            tabledata.setEcity(data.getStringExtra("dwshi"));
            tabledata.setEcounty(data.getStringExtra("dwxian"));
            tabledata.setEndlongitude(data.getStringExtra("y"));
            tabledata.setEndlatitude(data.getStringExtra("x"));
        }
    }

    private void upload() {
        showWaitDialog("正在刷新信息...");
        Gson gson = new Gson();
        String params = gson.toJson(tabledata);
        PileApi.instance.addKuaiyunOrder(params)
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
                            // showToast(body);
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("提交订单失败，请检查信息后重试");
                            } else {
                                showpaydialog(Float.parseFloat(tabledata.getSettheprice()), body.replace("\"", ""));

//                                Gson gson = new Gson();
//                                List<CarPP> CarPPList = gson.fromJson(body, new TypeToken<List<CarPP>>() {
//                                }.getType());
//                                updateInfo(CarPPList);
                            }
                            hideWaitDialog();
                        } catch (IOException e) {
                            showToast(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                        //   startActivity(new Intent(ConfirmOrderActivity.this, CustomerPositionActivity.class));
//                        sendPayLocalReceiver(GoodsDetialActivity.class.getName());
//                        sendPayLocalReceiver(ShoppingActivity.class.getName());
//                        sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
//                        sendPayLocalReceiver(myOrderActivity.class.getName());
//                        Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
//                        startActivity(it);
//                        finish();

                        final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
                        ispayok = true;
                        payalertDialog.dismiss();
                        dialog.show();
                        dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                        WindowManager windowManager = getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                        p.width = (int) (display.getWidth() * 0.6);
                        dialog.getWindow().setAttributes(p);

                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isok = false;
                                dialog.dismiss();
                                finish();
                                sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                intent.putExtra("type", 2);
                                startActivity(intent);

//                                        finish();
                            }
                        });


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                        // showToast(resultStatus);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    protected boolean sendPayLocalReceiver(String className) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.putExtra("className", className);
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    private void showpaydialog(final float summoney, final String orderid) {

        payalertDialog = new AlertDialog.Builder(this).create();
        payalertDialog.show();
        payalertDialog.getWindow().setContentView(R.layout.layout_pay2);
        payalertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = payalertDialog.getWindow().getAttributes();
        p.width = (int) (display.getWidth() * 0.8);
        payalertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!ispayok) {
                    deletepay(orderid);
                }
            }
        });
        payalertDialog.getWindow().setAttributes(p);
        payalertDialog.getWindow()
                .findViewById(R.id.rb1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 0, orderid);
//                                        deletepay(orderid);
                                        showToast("正在开发中");
//                                        startPay(0.01f, 0, orderid);
                                        dialog.dismiss();

                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                });
        payalertDialog.getWindow()
                .findViewById(R.id.rb2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 1, orderid);
//                                        deletepay(orderid);
                                        showToast("正在开发中");
//                                        startPay(0.01f, 1, orderid);
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                });
        if (Double.parseDouble(tabledata.getSettheprice()) < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
                            .setTitle("提示")
                            .setMessage("确定要支付吗")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  showToast(Float.parseFloat(tabledata.getOwner_totalprice()) + "");
                                    //orderYuEZhiFu(orderid, Float.parseFloat(tabledata.getSettheprice()));

                                    //检查支付密码
                                    initCheckPassword(orderid);


                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            });

        } else {

            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
                            .setTitle("提示")
                            .setMessage("余额不足，请充值再进行支付")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder1.show();
                }
            });

        }
        TextView textView = (TextView) payalertDialog.getWindow().findViewById(R.id.tv_yue);
        textView.setText(i + "");

    }

    private void initCheckPassword(final String orderid) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("", "");
//        Gson gson = new Gson();
        PileApi.instance.checkZhiFuPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("000000000000开始");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            System.out.println("000000000000" + body);
                            if (body.indexOf("false") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
                                        .setTitle("提示")
                                        .setMessage("请到：个人中心-设置-安全中心-设置支付密码中进行设置")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                            } else {
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(HuoDKuaiyunActivity.this);
                                View view = LayoutInflater.from(HuoDKuaiyunActivity.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(HuoDKuaiyunActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderid, Float.parseFloat(tabledata.getSettheprice()));
//                                            alertDialog.dismiss();
                                        }

                                    }
                                });
                                Button button1 = (Button) view.findViewById(R.id.btn_quxiao);
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        hintKeyboard();
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();

//                                    Button button = (Button) view.findViewById(R.id.btn_tijiao);
//                                    ed_content = (EditText) view.findViewById(R.id.ed_content);
                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("000000000000++++" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void startPay(final float confirmMoney, final int payType, final String orderid) {
        PileApi.instance.mCheckLoginState()
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
                            if (body.equals("\"true\"")) {

//                                                        return;

                                //支付宝支付
                                if (payType == 0) {
                                    // showToast(confirmMoney + "-" + orderid);
                                    /**
                                     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                                     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                                     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                                     *
                                     * orderInfo的获取必须来自服务端；
                                     */
                                    boolean rsa2 = (Constant.RSA_PRIVATE.length() > 0);
                                    Map<String, String> params = OrderInfoUtil2_0.kuaiyunzhifu(Constant.APPID, rsa2, confirmMoney + "", orderid + "_" + AccountHelper.getUserId());
                                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                    String privateKey = Constant.RSA_PRIVATE;  //私钥
                                    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                    final String orderInfo = orderParam + "&" + sign;

                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(HuoDKuaiyunActivity.this);
                                            Map<String, String> result = alipay.payV2(orderInfo, true);
                                            Log.i("msp", result.toString());
                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };

                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();

                                } else {     //微信支付
//                                                                if(!isWXAppInstalledAndSupported()){
//                                                                   showToast("请安装微信客户端后重试");
//                                                                    return;
//                                                                }
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("wx_appid", Constant.APP_ID);
                                    map.put("wx_mch_id", Constant.MCH_ID);
                                    map.put("wx_key", Constant.API_KEY);
                                    map.put("orderNo", orderid + "_" + AccountHelper.getUserId());                //订单号
                                    map.put("orderMoney", confirmMoney * 100);//支付金额
                                    //     map.put("orderMoney",1);
                                    map.put("notify_url", "http://www.maibat.com/maibate/shipperWeChat");
                                    map.put("body", "商品描述");
                                    new WXPayUtils().init(HuoDKuaiyunActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(HuoDKuaiyunActivity.this) //activity instance
                                                            .setAppId(Constant.APP_ID) //wechat pay AppID
                                                            .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                            .setPrepayId(prepayId)//pre pay id
                                                            .setNonceStr("")
                                                            .setTimeStamp("")//time stamp
                                                            .setSign(sign)//sign
                                                            .create();
                                                    //2. send the request with wechat pay
                                                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                    Constant.WXPAY_STARTNAME = HuoDKuaiyunActivity.class.getName();
//                                                                                wechatPayReq.setOnWechatPayListener(new WechatPayReq.OnWechatPayListener() {
//                                                                                    @Override
//                                                                                    public void onPaySuccess(int errorCode) {
//
////                                                                                        if(PayMethodActivity.instance!=null){
////                                                                                            PayMethodActivity.instance.finish();
////                                                                                        }
////                                                                                        startActivity(new Intent(PayActivity.this,CustomerPositionActivity.class));
////                                                                                        finish();
//                                                                                    }
//
//                                                                                    @Override
//                                                                                    public void onPayFailure(int errorCode) {
//
//                                                                                    }
//                                                                                });
                                                }
                                            });
                                }

                            } else {
                                showToast("当前用户未登录,无法获取单号");
                            }
                        } catch (IOException e) {
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

    //请求余额信息
    private void getyeData() {

        PileApi.instance.loadCustomerBalance()
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
                                showToast("获取信息失败，请重试");
                            } else {
                                body = body.substring(1, body.length() - 1);
                                i = Double.parseDouble(body);


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

    //余额支付
    private void orderYuEZhiFu(final AlertDialog alertDialog, String password, String orderno, float totalmoney) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("ordertype", "2");
        map.put("uuid", orderno + "");
        map.put("paypassword", password);
        map.put("zhifuprice", totalmoney + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.shipperBalancePay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            //       showToast(body);
                            if ("nologin".equals(body)) {
                                showToast("当前帐号未登录，请检查登录状态");
                            } else if ("false".equals(body) || "".equals(body)) {
                                showToast("支付失败");
                            } else if ("true".equals(body)) {
                                showToast("支付成功");
//                                sendPayLocalReceiver(GoodsDetialActivity.class.getName());
//                                sendPayLocalReceiver(ShoppingActivity.class.getName());
//                                sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
//                                sendPayLocalReceiver();
//                                Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
//                                startActivity(it);

                                final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
                                ispayok = true;
                                payalertDialog.dismiss();
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                p.width = (int) (display.getWidth() * 0.6);
                                dialog.getWindow().setAttributes(p);

                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog.dismiss();
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 2);
                                        startActivity(intent);


//                                        finish();
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                    }
                                });

                            } else {
                                showToast(body);
                            }
                            hideWaitDialog();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //余额支付
    private void deletepay(String orderno) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", orderno + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.deleteKuaiyunOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            System.err.println(body);
                            hideWaitDialog();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 微信支付广播
     */
    private void registerWXPayLocalReceiver() {
        if (mManager1 == null)
            mManager1 = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("MicroMsg.SDKSample.WXPayEntryActivity");
        if (mReceiver1 == null)
            mReceiver1 = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    //showToast("广播内容：" + action);
                    if ("MicroMsg.SDKSample.WXPayEntryActivity".equals(action)) {
                        if (intent != null) {
                            int result = intent.getIntExtra("result", -1);
                            switch (result) {
                                case 0:
                                    // showToast("支付成功******");
                                    if (Constant.WXPAY_STARTNAME.equals(HuoDKuaiyunActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
                                        ispayok = true;
                                        payalertDialog.dismiss();
                                        dialog.show();
                                        dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                        WindowManager windowManager = getWindowManager();
                                        Display display = windowManager.getDefaultDisplay();
                                        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                        p.width = (int) (display.getWidth() * 0.6);
                                        dialog.getWindow().setAttributes(p);

                                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                        textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                isok = false;
                                                dialog.dismiss();
                                                finish();
                                                sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                                Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                                intent.putExtra("type", 2);
                                                startActivity(intent);

//                                        finish();
                                            }
                                        });
                                    }
                                    break;
                                case -2:
                                    //     showToast("取消支付");
                                    break;
                                case -1:
                                    showToast("支付失败");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            };
        mManager1.registerReceiver(mReceiver1, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager1 != null) {
            if (mReceiver1 != null)
                mManager1.unregisterReceiver(mReceiver1);
        }

        SharedPreferences sp = getSharedPreferences("chengshi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        SharedPreferences sp1 = getSharedPreferences("shengshixian1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.clear();
        editor1.apply();
        SharedPreferences sp2 = getSharedPreferences("shengshixian2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp2.edit();
        editor2.clear();
        editor2.apply();
    }

    //加载车型
    public void loadcx() {

        PileApi.instance.searchKuaiyunCartype()
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
                            ArrayList<cxbean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<cxbean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(HuoDKuaiyunActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ArrayList<ShuJuEntity> datas = new ArrayList<ShuJuEntity>();
                            for (cxbean data : temp) {
                                datas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getCartypename(), false));
                            }
                            //    showdialog((TextView) v, temp, type);
                            showDuoXuanDiaLog(datas, false, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //加载货物类型
    public void loadhwlx() {
        PileApi.instance.searchKuaiyunCargotype()
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
                            ArrayList<hwlxBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<hwlxBean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(HuoDKuaiyunActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ArrayList<ShuJuEntity> datas = new ArrayList<ShuJuEntity>();
                            for (hwlxBean data : temp) {
                                datas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getCargotypename(), false));
                            }
                            //    showdialog((TextView) v, temp, type);
                            showDuoXuanDiaLog(datas, true, 2);
                            //    showdialog((TextView) v, temp, type);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void showDuoXuanDiaLog(final List<ShuJuEntity> list, boolean isOk, final int type) {
        //初始化Dialog
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_item, null);
        //初始化控件
        TextView tv_dismiss = (TextView) inflate.findViewById(R.id.tv_dismiss);
        TextView tv_tltle = (TextView) inflate.findViewById(R.id.tv_tltle);
        TextView cx_bz = (TextView) inflate.findViewById(R.id.cx_bz);
        tv_qtlx = (EditText) inflate.findViewById(R.id.tv_qtlx);
        RelativeLayout qtlx = (RelativeLayout) inflate.findViewById(R.id.qtlx);
        if (type == 1) {
            tv_tltle.setText("选择车型");
            tv_cx_text.setText("请选择");
            cx_bz.setVisibility(View.VISIBLE);
            qtlx.setVisibility(View.GONE);
        } else if (type == 2) {
            qtlx.setVisibility(View.VISIBLE);
            cx_bz.setVisibility(View.GONE);
            tv_qtlx.setText("");
            tv_hwlx.setText("请选择");
            tv_tltle.setText("选择货物类型");
        }
        TextView tv_queren = (TextView) inflate.findViewById(R.id.tv_queren);
        RecyclerView act_recyclerView = (RecyclerView) inflate.findViewById(R.id.act_recyclerView);
        //初始化列表
        MyAdapter adapter = new MyAdapter(this, list, isOk);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        //取消按钮的点击事件
        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                dialog = null;
            }


        });
        //确认按钮的点击事件
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duoxuanlist.clear();
                duoxuanlist = list;
                for (ShuJuEntity temp : duoxuanlist) {
                    if (!tv_qtlx.getText().toString().equals("")) {
                        String text = tv_qtlx.getText().toString();
                        tv_hwlx.setText(tv_qtlx.getText());
                        break;
                    }
                    if (temp.isType()) {
                        if (type == 2) {
                            tv_hwlx.setText(temp.getName());
                            break;
                        } else if (type == 1) {
                            if (tv_cx_text.getText().toString().equals("请选择")) {
                                tv_cx_text.setText(temp.getName());

                            } else {
                                tv_cx_text.setText(tv_cx_text.getText().toString() + "," + temp.getName());

                            }
                        }
                    }
                }
                dialog.dismiss();
                dialog = null;
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);

        WindowManager windowManager = getWindowManager();
        //为获取屏幕宽、高
        Display display = windowManager.getDefaultDisplay();
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        lp.y = 0;
        //设置Dialog宽度
        lp.width = display.getWidth();
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        dialog.show();

    }


}
