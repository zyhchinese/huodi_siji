package com.lxwls.hdsjd;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mob.MobSDK;
import com.lxwls.hdsjd.account.AccountHelper;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.cookie.CookieJarImpl;
import com.lxwls.hdsjd.cookie.SPCookieStore;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class PilesApplication extends AppContext {
    private static final String CONFIG_READ_STATE_PRE = "CONFIG_READ_STATE_PRE_";
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        // 初始化操作
        init();
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
     //   Beta.installTinker();
    }
    public static void reInit() {
        ((PilesApplication) PilesApplication.getInstance()).init();
    }

    private void init() {

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new SPCookieStore(this)))
                .retryOnConnectionFailure(true); // 失败重发
           //     .addInterceptor(new CustomInterceptor());

        PileApi.getInstance(builder.build());
        CrashReport.initCrashReport(getApplicationContext(), "497eba14e6", false);
        // 初始化账户基础信息
        AccountHelper.init(this);
        MobSDK.init(this);
    }


}
