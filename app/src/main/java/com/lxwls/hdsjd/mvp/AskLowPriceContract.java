package com.lxwls.hdsjd.mvp;

import com.lxwls.hdsjd.bean.CarType;
import com.lxwls.hdsjd.mvp.base.BasePresenter;
import com.lxwls.hdsjd.mvp.base.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */
@SuppressWarnings("unused")
public interface AskLowPriceContract {
    interface View extends BaseView<Presenter>{
        void showCarType(List<CarType> carTypes);
        void showConfirmResult(boolean b);
    }

    interface Presenter extends BasePresenter{
        void getCarType();
        void toConfirm(HashMap<String,String> map);
    }
}
