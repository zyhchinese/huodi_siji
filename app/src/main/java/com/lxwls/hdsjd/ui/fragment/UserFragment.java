package com.lxwls.hdsjd.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
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
import com.lxwls.hdsjd.account.AccountHelper;
import com.lxwls.hdsjd.account.LoginActivity;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.base.fragment.BaseFragment;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengEntity;
import com.lxwls.hdsjd.bean.CheckSiJiRenZhengImgEntity;
import com.lxwls.hdsjd.bean.User;
import com.lxwls.hdsjd.ui.activity.BalanceActivity;
import com.lxwls.hdsjd.ui.activity.CouponActivity;
import com.lxwls.hdsjd.ui.activity.CustomerPositionActivity;
import com.lxwls.hdsjd.ui.activity.DZapplyActivity;
import com.lxwls.hdsjd.ui.activity.DriverCertificationActivity;
import com.lxwls.hdsjd.ui.activity.DriverCertificationStateActivity;
import com.lxwls.hdsjd.ui.activity.HuoDKuaiyun1Activity;
import com.lxwls.hdsjd.ui.activity.HuoDSuyun1Activity;
import com.lxwls.hdsjd.ui.activity.IntegralActivity;
import com.lxwls.hdsjd.ui.activity.MyCar1Activity;
import com.lxwls.hdsjd.ui.activity.MywalletActivity;
import com.lxwls.hdsjd.ui.activity.OrderActivity;
import com.lxwls.hdsjd.ui.activity.PersonalInformationActivity;
import com.lxwls.hdsjd.ui.activity.SetupActivity;
import com.lxwls.hdsjd.ui.activity.ShopActivity;
import com.lxwls.hdsjd.ui.activity.SiJiPingJiaActivity;
import com.lxwls.hdsjd.ui.activity.WeiZhangChaXunActivity;
import com.lxwls.hdsjd.utils.DialogHelper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lxwls.hdsjd.base.Constant.REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener{
    RequestOptions mOptions;
    private CardView cvChargeOrder,cvPileApply,cvMyCar,cvSetting,cardview11,cardView111;

    private RelativeLayout rlyWalletRoot;
    private LinearLayout linear;
    private ImageView img1,img2,img3,img4,img5,img6,img_call;
    private CircleImageView mAvatar;
    private TextView tvUserName,tBalance,tvScores,tvTickets;
    private LinearLayout llyBalance,llyScores,llyTickets;
    boolean isLogin=false;
    public static final String HOLD_USERNAME_KEY = "holdUsernameKey";
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
              //  .override(80,80);
        mAvatar=findView(R.id.img_avatar);
        tvUserName=findView(R.id.tv_user_name);
        tBalance=findView(R.id.tv_balance);
        tvTickets=findView(R.id.tv_tickets);
        tvScores=findView(R.id.tv_scores);
        rlyWalletRoot=findView(R.id.rly_root_wallet);
        llyBalance=findView(R.id.lly_balance_root);
        llyScores=findView(R.id.lly_tickets_root);
        llyTickets=findView(R.id.lly_scores_root);

        cvChargeOrder=findView(R.id.cardView0);
        cvPileApply=findView(R.id.cardView1);
        cvMyCar=findView(R.id.cardView2);
        cvSetting=findView(R.id.cardView3);
        cardview11=findView(R.id.cardView11);
        cardView111=findView(R.id.cardView111);

        linear=findView(R.id.linear);
        img1=findView(R.id.img1);
        img2=findView(R.id.img2);
        img3=findView(R.id.img3);
        img4=findView(R.id.img4);
        img5=findView(R.id.img5);
        img6=findView(R.id.img6);
        img_call=findView(R.id.img_call);

        rlyWalletRoot.setOnClickListener(this);
        cvChargeOrder.setOnClickListener(this);
        cvPileApply.setOnClickListener(this);
        cvMyCar.setOnClickListener(this);
        cvSetting.setOnClickListener(this);
        cardview11.setOnClickListener(this);
        cardView111.setOnClickListener(this);
        llyBalance.setOnClickListener(this);
        llyScores.setOnClickListener(this);
        llyTickets.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        linear.setOnClickListener(this);
        img_call.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {

        if(isLogin){
            switch (v.getId()){
                case R.id.img_avatar:
                    startActivity(new Intent(getContext(), PersonalInformationActivity.class));
                    break;
                case R.id.lly_balance_root:
                    startActivity(new Intent(getContext(), BalanceActivity.class));
                    break;
                case R.id.lly_tickets_root:
                    startActivity(new Intent(getContext(), CouponActivity.class));
                    break;
                case R.id.lly_scores_root:
                    startActivity(new Intent(getContext(), IntegralActivity.class));
                    break;
                case R.id.rly_root_wallet:
                    startActivity(new Intent(getContext(), MywalletActivity.class));
                    break;
                case R.id.cardView0:
//                    startActivity(new Intent(getContext(), OrderActivity.class));
//                    startActivity(new Intent(getContext(), ChargeOrderActivity.class));
//                        startActivity(new Intent(getContext(), ModifyPwdActivity.class));
                    initPanduan();
                    break;
                case R.id.cardView1:
                    startActivity(new Intent(getContext(), ShopActivity.class));
                    break;
                case R.id.cardView2:
                    startActivity(new Intent(getContext(), WeiZhangChaXunActivity.class));
                    break;
                case R.id.cardView3:
                    startActivity(new Intent(getContext(), SetupActivity.class));
                    break;
                case R.id.cardView11:
                    startActivity(new Intent(getContext(), CustomerPositionActivity.class));
                    break;
                case R.id.cardView111:
                    startActivity(new Intent(getContext(), MywalletActivity.class));
                    break;
                case R.id.linear:
                    startActivity(new Intent(getContext(), SiJiPingJiaActivity.class));
                    break;
                case R.id.img_call:
                    String consigneephone="0531-88807916";
                    AlertDialog.Builder builder11=new AlertDialog.Builder(getContext())
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
                    builder11.show();
                    break;
            }
        }else {
            DialogHelper.getConfirmDialog(getContext(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }, null).show();
        }
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
        String consigneephone="0531-88807916";
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

    private void initPanduan() {
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
                                startActivity(new Intent(getContext(), DriverCertificationActivity.class));
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
                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
//                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
//                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
                                    startActivity(intent);
                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
                                    //审核通过
//                                    if (type==1){
//                                        Intent intent1 = new Intent(getContext(), HuoDSuyun1Activity.class);
//                                        intent1.putExtra("cs", tvLocation.getText().toString());
//                                        startActivity(intent1);
//                                    }else if (type==2){
//                                        Intent intent1 = new Intent(getContext(), HuoDKuaiyun1Activity.class);
//                                        intent1.putExtra("cs", tvLocation.getText().toString());
//                                        startActivity(intent1);
//                                    }

                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
//                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
//                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
                                    startActivity(intent);
                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
                                    //审核拒绝
                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
//                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
//                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
                                    startActivity(intent);
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

    private void updateUserInfo(User user){
        if(user!=null){
            tvUserName.setText(user.getCustname());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            tBalance.setText(decimalFormat.format(user.getBalance()));
            tvTickets.setText(user.getCount()+"");
            DecimalFormat decimalFormat1=new DecimalFormat("###0.00");
            tvScores.setText(decimalFormat1.format(user.getScores()));
            String userLogo=Constant.BASE_URL+user.getFolder()+"/"+user.getAutoname();
            Glide.with(getContext())
                    .load(userLogo)
                    .apply(mOptions)
                    .into(mAvatar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
                            String body=responseBody.string();
                            if(body.equals("\"true\"")){
                                getUserInfo();
                                isLogin=true;
                            }else{
//                                tvUserName.setText("麦巴特用户");
//                                tBalance.setText("0.00");
//                                tvTickets.setText("0");
//                                tvScores.setText("0");
//                                Glide.with(getContext())
//                                        .load("")
//                                        .apply(mOptions)
//                                        .into(mAvatar);
                                isLogin=false;

                                SharedPreferences sharedPreferences=getContext().getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                String name = sharedPreferences.getString("name", "");
                                String password = sharedPreferences.getString("password", "");
                                if (name.equals("")||password.equals("")){
                                    DialogHelper.getConfirmDialog(getContext(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getContext(), LoginActivity.class));
                                        }
                                    }, null).show();
                                }else {
                                    requestLogin(name,password);
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLogin=false;
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
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
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
                                        getUserInfo();
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
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    private void getUserInfo(){
        showWaitDialog("正在获取个人信息");
        PileApi.instance.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body=responseBody.string();
                            if(body.length()<10){
                                //   if(body.equals("\"false\""))
                                showToast("获取个人信息失败，请登录后重试");
                            }else {
                                Gson gson=new Gson();
                                List<User> userList=gson.fromJson(body,new TypeToken<List<User>>() {
                                }.getType());
                                updateUserInfo(userList.get(0));
                                if (userList.get(0).getIsdriver()==1){
                                    initDriverPingjia();
                                }else {
                                    linear.setVisibility(View.GONE);
                                }

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

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

    private void initDriverPingjia() {
        PileApi.instance.selectDriverEvaluationTotal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body=responseBody.string();
                            if(body.indexOf("false") != -1){
                                //   if(body.equals("\"false\""))
//                                showToast("获取个人信息失败，请登录后重试");
                                linear.setVisibility(View.GONE);
                            }else {
                                linear.setVisibility(View.VISIBLE);

                                if (body.indexOf("0")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia0);
                                    img2.setImageResource(R.mipmap.img_pingjia_false);
                                    img3.setImageResource(R.mipmap.img_pingjia_false);
                                    img4.setImageResource(R.mipmap.img_pingjia_false);
                                    img5.setImageResource(R.mipmap.img_pingjia_false);
                                    img6.setImageResource(R.mipmap.img_pingjia_false);
                                }else if (body.indexOf("1")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia1);
                                    img2.setImageResource(R.mipmap.img_pingjia_true);
                                    img3.setImageResource(R.mipmap.img_pingjia_false);
                                    img4.setImageResource(R.mipmap.img_pingjia_false);
                                    img5.setImageResource(R.mipmap.img_pingjia_false);
                                    img6.setImageResource(R.mipmap.img_pingjia_false);
                                }else if (body.indexOf("2")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia2);
                                    img2.setImageResource(R.mipmap.img_pingjia_true);
                                    img3.setImageResource(R.mipmap.img_pingjia_true);
                                    img4.setImageResource(R.mipmap.img_pingjia_false);
                                    img5.setImageResource(R.mipmap.img_pingjia_false);
                                    img6.setImageResource(R.mipmap.img_pingjia_false);
                                }else if (body.indexOf("3")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia3);
                                    img2.setImageResource(R.mipmap.img_pingjia_true);
                                    img3.setImageResource(R.mipmap.img_pingjia_true);
                                    img4.setImageResource(R.mipmap.img_pingjia_true);
                                    img5.setImageResource(R.mipmap.img_pingjia_false);
                                    img6.setImageResource(R.mipmap.img_pingjia_false);
                                }else if (body.indexOf("4")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia4);
                                    img2.setImageResource(R.mipmap.img_pingjia_true);
                                    img3.setImageResource(R.mipmap.img_pingjia_true);
                                    img4.setImageResource(R.mipmap.img_pingjia_true);
                                    img5.setImageResource(R.mipmap.img_pingjia_true);
                                    img6.setImageResource(R.mipmap.img_pingjia_false);
                                }else if (body.indexOf("5")!=-1){
                                    img1.setImageResource(R.mipmap.img_pingjia5);
                                    img2.setImageResource(R.mipmap.img_pingjia_true);
                                    img3.setImageResource(R.mipmap.img_pingjia_true);
                                    img4.setImageResource(R.mipmap.img_pingjia_true);
                                    img5.setImageResource(R.mipmap.img_pingjia_true);
                                    img6.setImageResource(R.mipmap.img_pingjia_true);
                                }

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

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
}
