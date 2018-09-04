package com.lxwls.hdsjd.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FaBuShunFengCheActivity extends BackCommonActivity implements View.OnClickListener, RouteSearch.OnRouteSearchListener {

    private TextView tv_time,tv_qidian,tv_zhongdian,tv_tujingchengshi,tv_fabuxingcheng;
    private LinearLayout linear_time,linear_qidian,linear_zhongdian,linear_tujingchengshi;
    private EditText ed_zongchechang,ed_keyongchechang,ed_name,ed_phone;
    private String qidianLatitude="",qidianLongitude="",zhongdianLatitude="",zhongdianLongitude="";
    private ArrayList<HashMap<String,String>> tujingchenshi=new ArrayList<>();
    private RouteSearch mRouteSearch;
    private double licheng;
    private String sdwsheng="",sdwshi="",sdwxian="",edwsheng="",edwshi="",edwxian="";

    @Override
    protected int getContentView() {
        return R.layout.activity_fa_bu_shun_feng_che;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货滴顺风车发布");

        initView();

    }

    private void initView() {
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_qidian= (TextView) findViewById(R.id.tv_qidian);
        tv_zhongdian= (TextView) findViewById(R.id.tv_zhongdian);
        tv_tujingchengshi= (TextView) findViewById(R.id.tv_tujingchengshi);
        tv_fabuxingcheng= (TextView) findViewById(R.id.tv_fabuxingcheng);
        linear_time= (LinearLayout) findViewById(R.id.linear_time);
        linear_qidian= (LinearLayout) findViewById(R.id.linear_qidian);
        linear_zhongdian= (LinearLayout) findViewById(R.id.linear_zhongdian);
        linear_tujingchengshi= (LinearLayout) findViewById(R.id.linear_tujingchengshi);
        ed_zongchechang= (EditText) findViewById(R.id.ed_zongchechang);
        ed_keyongchechang= (EditText) findViewById(R.id.ed_keyongchechang);
        ed_name= (EditText) findViewById(R.id.ed_name);
        ed_phone= (EditText) findViewById(R.id.ed_phone);

        linear_time.setOnClickListener(this);
        linear_qidian.setOnClickListener(this);
        linear_zhongdian.setOnClickListener(this);
        linear_tujingchengshi.setOnClickListener(this);
        tv_fabuxingcheng.setOnClickListener(this);
        //线路规划 计算距离
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        initJianCeSiji();
    }

    private void initJianCeSiji() {
        PileApi.instance.selDriverInfo()
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
                            System.out.println("body = " + body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            String msg = jsonObject.getString("msg");
                            if (flag.equals("200")) {
                                JSONArray response = jsonObject.getJSONArray("response");
                                JSONObject string = response.getJSONObject(0);
                                String dname = string.getString("dname");
                                String dcall = string.getString("dcall");
                                ed_name.setText(dname);
                                ed_phone.setText(dcall);
                            }else {
                                AlertDialog.Builder builder=new AlertDialog.Builder(FaBuShunFengCheActivity.this)
                                        .setTitle("提示：")
                                        .setCancelable(false)
                                        .setMessage(msg)
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                builder.show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(FaBuShunFengCheActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_time:
                initShijiantanchuan();
                break;
            case R.id.linear_qidian:
                //获取问题
                String question = "qidian";
                //包装数据
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                bundle.putString("x", qidianLatitude);
                bundle.putString("y", qidianLongitude);
                Intent intent = new Intent(FaBuShunFengCheActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
                break;
            case R.id.linear_zhongdian:
                //获取问题
                String question1 = "zhongdian";
                //包装数据
                Bundle bundle1 = new Bundle();
                bundle1.putString("question", question1);
                bundle1.putString("x", zhongdianLatitude);
                bundle1.putString("y", zhongdianLongitude);
                Intent intent1 = new Intent(FaBuShunFengCheActivity.this, DeliverMapActivity.class);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 001);
                break;
            case R.id.linear_tujingchengshi:
                if (qidianLatitude.equals("")&&qidianLongitude.equals("")){
                    showToast("请选择起点");
                    return;
                }
                if (zhongdianLongitude.equals("")&&zhongdianLatitude.equals("")){
                    showToast("请选择终点");
                    return;
                }
                //获取问题
                String question2 = "tujing";
                //包装数据
                Bundle bundle2 = new Bundle();
                bundle2.putString("question", question2);
                bundle2.putString("x", qidianLatitude);
                bundle2.putString("y", qidianLongitude);
                bundle2.putString("p1", zhongdianLatitude);
                bundle2.putString("p2", zhongdianLongitude);

                Intent intent2 = new Intent(FaBuShunFengCheActivity.this, DeliverMapActivity.class);
                intent2.putExtras(bundle2);
                startActivityForResult(intent2, 001);
                break;
            case R.id.tv_fabuxingcheng:
                if (ed_name.getText().toString().trim().equals("")){
                    showToast("请输入姓名");
                    return;
                }
                if (ed_phone.getText().toString().trim().equals("")){
                    showToast("请输入电话");
                    return;
                }
                if (ed_phone.getText().toString().trim().length()!=11){
                    showToast("请输入正确的电话号码");
                    return;
                }
//                if (tv_qidian.getText().toString().trim().equals("")){
//                    showToast("请选择起点");
//                    return;
//                }
//                if (tv_zhongdian.getText().toString().trim().equals("")){
//                    showToast("请选择终点");
//                    return;
//                }
                if (qidianLatitude.equals("")&&qidianLongitude.equals("")){
                    showToast("请选择起点");
                    return;
                }
                if (zhongdianLongitude.equals("")&&zhongdianLatitude.equals("")){
                    showToast("请选择终点");
                    return;
                }
                if (ed_zongchechang.getText().toString().trim().equals("")){
                    showToast("请输入总车长");
                    return;
                }
                if (ed_keyongchechang.getText().toString().trim().equals("")){
                    showToast("请输入可用车长");
                    return;
                }
                if (tv_time.getText().toString().trim().equals("请选择出发时间")){
                    showToast("请选择出发时间");
                    return;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(FaBuShunFengCheActivity.this)
                        .setTitle("提示")
                        .setMessage("确认发布行程吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initFaBuShuFengChe();
                                dialog.dismiss();
                            }
                        });
                builder.show();

                break;
        }
    }

    private void initFaBuShuFengChe() {
        HashMap<String, String> map = new HashMap<>();
        map.put("slongitude", qidianLongitude);
        map.put("slatitude", qidianLatitude);
        map.put("sprovince", sdwsheng);
        map.put("scity", sdwshi);
        map.put("scounty", sdwxian);
        map.put("saddress", tv_qidian.getText().toString());

        map.put("elongitude", zhongdianLongitude);
        map.put("elatitude", zhongdianLatitude);
        map.put("eprovince", edwsheng);
        map.put("ecity", edwshi);
        map.put("ecounty", edwxian);
        map.put("eaddress", tv_zhongdian.getText().toString());

        String showname="";
        for (int i = 0; i < tujingchenshi.size(); i++) {
            showname=showname+tujingchenshi.get(i).get("douhao");
        }
        if (showname.equals("")){
            map.put("waytocitys", "");
        }else {
            map.put("waytocitys", showname.substring(0,showname.length()-1));
        }

        String showname1="";
        for (int i = 0; i < tujingchenshi.size(); i++) {
            showname1=showname1+tujingchenshi.get(i).get("xinghaodouhao");
        }
        if (showname1.equals("")){
            map.put("waytocitystemp", "");
        }else {
            map.put("waytocitystemp", showname1.substring(0,showname1.length()-1));
        }

        String showname2="";
        for (int i = 0; i < tujingchenshi.size(); i++) {
            showname2=showname2+tujingchenshi.get(i).get("douhao1");
        }
        if (showname2.equals("")){
            map.put("waytocitysshow", "");
        }else {
            map.put("waytocitysshow", showname2.substring(0,showname2.length()-1));
        }


        map.put("distance", licheng+"");
        map.put("totalvehicle", ed_zongchechang.getText().toString().trim());
        map.put("availablevehicle", ed_keyongchechang.getText().toString().trim());
        map.put("departuretime", tv_time.getText().toString());
        map.put("contactname", ed_name.getText().toString().trim());
        map.put("contactphone", ed_phone.getText().toString().trim());
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.addFreeRideTrip(data)
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
                                showToast("发布成功");
                                finish();
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

    private void initShijiantanchuan() {
        TimePickerView pvTime = new TimePickerView.Builder(FaBuShunFengCheActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调
                String time = getTime(date2);
                Date date = new Date(System.currentTimeMillis());
                String time1 = getTime(date);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date parse = format.parse(time);
                    Date parse1 = format.parse(time1);
                    if (parse.getTime()<=parse1.getTime()){
                        showToast("必须大于当前时间");
                    }else {
                        tv_time.setText(time);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//默认全部显示
//                        .setTitleText("订单时间")
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setTitleColor(Color.parseColor("#ffb400"))//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == 002) {
            tv_qidian.setText(data.getStringExtra("myresuly"));
            qidianLatitude=data.getStringExtra("x");
            qidianLongitude=data.getStringExtra("y");
            sdwsheng=data.getStringExtra("dwsheng");
            sdwshi=data.getStringExtra("dwshi");
            sdwxian=data.getStringExtra("dwxian");
        } else if (requestCode == 001 && resultCode == 003) {
            tv_zhongdian.setText(data.getStringExtra("myresuly"));
            zhongdianLongitude=data.getStringExtra("y");
            zhongdianLatitude=data.getStringExtra("x");
            edwsheng=data.getStringExtra("dwsheng");
            edwshi=data.getStringExtra("dwshi");
            edwxian=data.getStringExtra("dwxian");
        }else if (requestCode == 001 && resultCode == 10000){
            tujingchenshi= (ArrayList<HashMap<String, String>>) data.getSerializableExtra("tujingchenshi");
            String showname="";
            for (int i = 0; i < tujingchenshi.size(); i++) {
                showname=showname+tujingchenshi.get(i).get("douhao1");
            }
            tv_tujingchengshi.setText(showname.substring(0,showname.length()-1));

        }

        if (!qidianLatitude.equals("")&&!qidianLongitude.equals("")&&!zhongdianLongitude.equals("")&&!zhongdianLatitude.equals("")){
            searchRouteResult(0,RouteSearch.DRIVING_SINGLE_DEFAULT,new LatLonPoint(Double.parseDouble(qidianLatitude),Double.parseDouble(qidianLongitude)),new LatLonPoint(Double.parseDouble(zhongdianLatitude),Double.parseDouble(zhongdianLongitude)));
        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint startpoint, LatLonPoint endporint) {

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startpoint, endporint);
        if (routeType == 0) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            showWaitDialog("正在加载...").setCancelable(false);
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

        if (i==1000){
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    double dis = drivePath.getDistance();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("###0.00");
                    BigDecimal b = new BigDecimal(dis / 1000);


                    licheng = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    System.out.println(licheng+"");
                }
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
