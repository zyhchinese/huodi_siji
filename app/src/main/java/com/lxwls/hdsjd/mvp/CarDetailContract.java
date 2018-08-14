package com.lxwls.hdsjd.mvp;

import com.lxwls.hdsjd.bean.CarInfo;
import com.lxwls.hdsjd.mvp.base.BasePresenter;
import com.lxwls.hdsjd.mvp.base.BaseView;

import java.util.List;

@SuppressWarnings("unused")
interface CarDetailContract {

    interface View extends BaseView<Presenter> {
        void showBanner(List<String> banners);
        void showCarInfo(List<CarInfo> carInfos);
        void showWebView(String content);

    }

    interface Presenter extends BasePresenter {

        void getBanner(String id);

        void getCarInfo(String id);//获得详情

        void getWebView();
    }
}
