package com.lxwls.hdsjd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.SuYun_BanJiaDan_EWaiXuQiuAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.FeiLvEntity;
import com.lxwls.hdsjd.bean.PingYuEntity;
import com.lxwls.hdsjd.bean.SuYun_BanJiaDan_XiangQing;
import com.lxwls.hdsjd.bean.SuYun_XiaoJianDan_EWaiXuQiu;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lxwls.hdsjd.base.Constant.REQUEST_CODE;

/**
 * Created by admin on 2018/3/15.（速运-搬家单-订单详情）
 */
public class SuYunDan_BanJiaDan_XiangQingActivity extends BackCommonActivity implements View.OnClickListener {

    private TextView tv_diandanhao,tv_fhr,tv_address,tv_address1,tv_qidianjuli,tv_yongcheleixing,tv_luchengjuli,
            tv_yongcheshijian,tv_dingdanshijian,tv_money,tv_beizhu,tv_zhuangtai,tv_pingjia,tv_fhr111,tv_fangshi;
    private CircleImageView img_avatar;
    private ImageView iv_iphone,iv_iphone111;
    private String orderno;//订单id
    RequestOptions mOptions;//请求
    private LinearLayout ll_ewaixuqiu;//额外需求
    private RecyclerView act_recyclerView;//额外需求具体的列表信息
    private AlertDialog payalertDialog;//弹窗
    private List<SuYun_BanJiaDan_XiangQing> detailsEntityList;
    private List<SuYun_XiaoJianDan_EWaiXuQiu> eWaiEntityList;//额外需求
    private PingYuEntity pingYuEntity;
    private ImageView img_back;

    private LinearLayout linear_kaishi;
    private RelativeLayout relative;
    //private RelativeLayout relative1, relative2;
    private View view10;
    private ImageView img_type;
    private Button btn_quxiao, btn_jieshu, btn_kaishi;
    private EditText ed_content;
    private String line;
    private String content = "好评";
    private List<FeiLvEntity> feiLvEntityList;//
    private int pingjia = 5;
    private int call=0;
    private List<String> pingyulist=new ArrayList<>();
    private String xuanpingyu="";

    @Override
    protected int getContentView() {
        return R.layout.suyun_banjiadan_dingdanxiangqing;
    }
    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("同城搬家单详情");
        line = getIntent().getStringExtra("line");
        orderno = getIntent().getStringExtra("orderno");

        mOptions = new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .fitCenter();
        img_avatar = (CircleImageView) findViewById(R.id.img_avatar);//头像

        tv_diandanhao = (TextView) findViewById(R.id.tv_diandanhao);//订单号
        tv_fhr = (TextView) findViewById(R.id.tv_fhr);//发货人
        tv_address = (TextView) findViewById(R.id.tv_address);//起点
        tv_address1 = (TextView) findViewById(R.id.tv_address1);//终点
        iv_iphone = (ImageView) findViewById(R.id.iv_iphone);//电话
        tv_qidianjuli = (TextView) findViewById(R.id.tv_qidianjuli);//起点距离
        tv_yongcheleixing = (TextView) findViewById(R.id.tv_yongcheleixing);//用车类型
        tv_luchengjuli = (TextView) findViewById(R.id.tv_luchengjuli);//路程距离
        tv_yongcheshijian = (TextView) findViewById(R.id.tv_yongcheshijian);//用车时间
        tv_dingdanshijian = (TextView) findViewById(R.id.tv_dingdanshijian);//订单时间
        tv_money = (TextView) findViewById(R.id.tv_money);//总价
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);//备注
        tv_zhuangtai = (TextView) findViewById(R.id.tv_zhuangtai);//订单状态
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        tv_fhr111= (TextView) findViewById(R.id.tv_fhr111);
        iv_iphone111= (ImageView) findViewById(R.id.iv_iphone111);
        tv_fangshi= (TextView) findViewById(R.id.tv_fangshi);

        tv_pingjia= (TextView) findViewById(R.id.tv_pingjia);

        btn_quxiao = (Button) findViewById(R.id.btn_quxiao);//取消
        btn_jieshu = (Button) findViewById(R.id.btn_jieshu);//结束
        btn_kaishi = (Button) findViewById(R.id.btn_kaishi);//开始
        linear_kaishi= (LinearLayout) findViewById(R.id.linear_kaishi);
        relative= (RelativeLayout) findViewById(R.id.relative);
        img_type = (ImageView) findViewById(R.id.img_type);
        //relative1 = (RelativeLayout) findViewById(R.id.relative1);
        // relative2 = (RelativeLayout) findViewById(R.id.relative2);
        view10 = findViewById(R.id.view10);

        img_avatar.setOnClickListener(this);
        tv_diandanhao.setOnClickListener(this);
        tv_fhr.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        tv_address1.setOnClickListener(this);
        tv_qidianjuli.setOnClickListener(this);
        iv_iphone.setOnClickListener(this);
        iv_iphone111.setOnClickListener(this);
        tv_yongcheleixing.setOnClickListener(this);
        tv_luchengjuli.setOnClickListener(this);
        tv_yongcheshijian.setOnClickListener(this);
        tv_dingdanshijian.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_beizhu.setOnClickListener(this);
        act_recyclerView.setOnClickListener(this);

        tv_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(SuYunDan_BanJiaDan_XiangQingActivity.this);
                View view = LayoutInflater.from(SuYunDan_BanJiaDan_XiangQingActivity.this).inflate(R.layout.dialog_pingjiadingdan, null, false);
                dialog11.setView(view);
                final AlertDialog alertDialog = dialog11.create();
                alertDialog.setCancelable(false);
                final TagFlowLayout tagFlow= (TagFlowLayout) view.findViewById(R.id.tagFlow);
                tagFlow.setAdapter(new TagAdapter<String>(pingyulist) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv_name= (TextView) LayoutInflater.from(SuYunDan_BanJiaDan_XiangQingActivity.this).inflate(R.layout.pingyu_item,tagFlow,false);
                        tv_name.setText(pingyulist.get(position)+"("+pingYuEntity.getResponse().get(position).getUsenumber()+")");
                        return tv_name;
                    }

                });
                tagFlow.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        List<String> list=new ArrayList<String>();
                        if (selectPosSet.size()!=0){
                            for (int xuanzhong:selectPosSet){
                                list.add(pingYuEntity.getResponse().get(xuanzhong).getId()+"");

                            }
                            xuanpingyu="";
                            for (int i = 0; i < list.size(); i++) {
                                xuanpingyu=xuanpingyu+list.get(i)+",";
                            }
                        }
                    }
                });

                Button button = (Button) view.findViewById(R.id.btn_tijiao);
                Button btn_quxiao = (Button) view.findViewById(R.id.btn_quxiao);
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
                btn_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
            }
        });

        //加载订单详情信息
        initOrderDetails();
//        //请求费率
//        initFeiLv();

    }

    private void initPingjia() {
        if (!xuanpingyu.equals("")){
            xuanpingyu=xuanpingyu.substring(0,xuanpingyu.length()-1);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", detailsEntityList.get(0).getId() + "");
        map.put("driver_fraction", pingjia + "");
        map.put("note", content);
        map.put("driverid", detailsEntityList.get(0).getOwner_custid() + "");
        map.put("ordertype", line);
        map.put("evalute_type", "1");
        map.put("selevalueids", xuanpingyu);
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


    //获取信息并添加头像
    private void updateUserInfo(SuYun_BanJiaDan_XiangQing detailsEntityList) {
        if (detailsEntityList != null) {
            if (detailsEntityList.getDriver_orderstatus().equals("-1")){
                tv_zhuangtai.setText("已取消");
            }else if (detailsEntityList.getDriver_orderstatus().equals("0")){
                tv_zhuangtai.setText("前往中");
            }else if (detailsEntityList.getDriver_orderstatus().equals("1")){
                tv_zhuangtai.setText("开始订单");
            }else if (detailsEntityList.getDriver_orderstatus().equals("2")){
                tv_zhuangtai.setText("已完成");
                if (detailsEntityList.getIsevaluate_dri().equals("0")){
                    tv_pingjia.setVisibility(View.VISIBLE);
                }else {
                    tv_pingjia.setVisibility(View.GONE);
                }
            }
            tv_diandanhao.setText(detailsEntityList.getOwner_orderno());
            tv_fhr.setText(detailsEntityList.getFname());
            tv_fhr111.setText(detailsEntityList.getOwner_link_name());
            tv_address.setText(detailsEntityList.getOwner_address());
            tv_address1.setText(detailsEntityList.getOwner_send_address());
            tv_qidianjuli.setText("30" + "公里");//要改s
            tv_yongcheleixing.setText(detailsEntityList.getCar_type());
            tv_luchengjuli.setText(detailsEntityList.getTotal_mileage() + "公里");
            tv_yongcheshijian.setText(detailsEntityList.getOwner_sendtime());
            tv_dingdanshijian.setText(detailsEntityList.getOwner_createtime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(Double.parseDouble(detailsEntityList.getSiji_money()));
            tv_money.setText(format);
            if (detailsEntityList.getOwner_note().equals("")){
                tv_beizhu.setText("无");
            }else {
                tv_beizhu.setText(detailsEntityList.getOwner_note());
            }
            if (detailsEntityList.getPaytype().equals("0")){
                tv_fangshi.setText("支付宝");
            }else if (detailsEntityList.getPaytype().equals("1")){
                tv_fangshi.setText("微信");
            }else if (detailsEntityList.getPaytype().equals("2")){
                tv_fangshi.setText("余额");
            }else if (detailsEntityList.getPaytype().equals("3")){
                tv_fangshi.setText("线下");
            }else if (detailsEntityList.getPaytype().equals("4")){
                tv_fangshi.setText("签约用户");
            }else if (detailsEntityList.getPaytype().equals("20")){
                tv_fangshi.setText("未支付");
            }


            String userLogo = Constant.BASE_URL + detailsEntityList.getFolder() + "/" + detailsEntityList.getAutoname();
            Glide.with(this)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(img_avatar);
        }
    }

    private void initPingYu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("custid", detailsEntityList.get(0).getOwner_custid()+"");
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectEvaluationList(params)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson1=new Gson();
                                pingYuEntity=gson1.fromJson(body,PingYuEntity.class);

                                pingyulist.clear();
                                for (PingYuEntity.ResponseBean entity:pingYuEntity.getResponse()){
                                    pingyulist.add(entity.getContent());
                                }

                            }


                        } catch (IOException e) {
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

    //请求费率
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
    //额外需求
    private void initEWai() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", detailsEntityList.get(0).getId()+"");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderDetailServ(params)
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
                                eWaiEntityList = gson.fromJson(body, new TypeToken<List<SuYun_XiaoJianDan_EWaiXuQiu>>() {
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
    //额外需求适配器
    private void initEwaiRecyclerView() {
        SuYun_BanJiaDan_EWaiXuQiuAdapter adapter = new SuYun_BanJiaDan_EWaiXuQiuAdapter(this, eWaiEntityList,Integer.parseInt(detailsEntityList.get(0).getCust_orderstatus()));
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }
    //加载详情的基本信息
    private void initOrderDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchorder(data)
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
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<SuYun_BanJiaDan_XiangQing>>() {
                                }.getType());
                                updateUserInfo(detailsEntityList.get(0));
                                //加载额外需求信息
                                initEWai();
                                //initFuZhi();

                                initPingYu();

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
        String consigneephone="";
        if (call==1){
            consigneephone=detailsEntityList.get(0).getFphone();
        }else {
            consigneephone=detailsEntityList.get(0).getOwner_link_phone();
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_iphone:
                String consigneephone=detailsEntityList.get(0).getFphone();
                AlertDialog.Builder builder11=new AlertDialog.Builder(this)
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
                                call=1;
                                testCallPhone();
                            }
                        });
                builder11.show();
                break;
            case R.id.iv_iphone111:
                String consigneephone11=detailsEntityList.get(0).getOwner_link_phone();
                AlertDialog.Builder builder1122=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定拨打电话："+consigneephone11)
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
                                call=2;
                                testCallPhone();
                            }
                        });
                builder1122.show();
                break;
            case R.id.tv_quxiao1:
                final AlertDialog dialog1 = new AlertDialog.Builder(this).create();
                dialog1.setCancelable(false);
                dialog1.show();
                dialog1.getWindow().setContentView(R.layout.dialog_querenquxiao);
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                TextView textView1= (TextView) dialog1.getWindow().findViewById(R.id.tv_feilv);
                textView1.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()+"%的费用");
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
                textView.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()+"%的费用");
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
//            case R.id.tv_pingjia:
//                AlertDialog.Builder dialog11 = new AlertDialog.Builder(SuYunDan_BanJiaDan_XiangQingActivity.this);
//                View view = LayoutInflater.from(SuYunDan_BanJiaDan_XiangQingActivity.this).inflate(R.layout.kuaiyun_qiangdanchenggong, null, false);
//                dialog11.setView(view);
//                final AlertDialog alertDialog = dialog11.create();
//                alertDialog.setCancelable(false);
//                final TextView chenggong3 = (TextView) view.findViewById(R.id.chenggong3);
//                chenggong3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!ed_content.getText().toString().trim().equals("")) {
//                            content = ed_content.getText().toString().trim();
//                        }
//                        initQiangdan();
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
//                break;
        }
        //开始
    }

    private void initKaishi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", "");
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
        map.put("orderno", "");
        map.put("order_money", detailsEntityList.get(0).getOwner_totalprice() + "");
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
    //抢单
    private void initQiangdan() {
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
                                showToast("抢单失败");
                            } else {
                                showToast("抢单成功");

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
        map.put("orderno", "");
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
//        map.put("siji_findtime", detailsEntityList.get(0).getSiji_singletime());
        map.put("order_money", detailsEntityList.get(0).getOwner_totalprice() + "");
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
                                final AlertDialog dialog = new AlertDialog.Builder(SuYunDan_BanJiaDan_XiangQingActivity.this).create();
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
                                textView.setText("已扣除您"+feiLvEntityList.get(0).getFee()+"%费用");

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
                                Toast.makeText(SuYunDan_BanJiaDan_XiangQingActivity.this, "111", Toast.LENGTH_SHORT).show();
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

