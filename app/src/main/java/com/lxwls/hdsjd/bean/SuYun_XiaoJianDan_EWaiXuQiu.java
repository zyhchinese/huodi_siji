package com.lxwls.hdsjd.bean;

/**
 * Created by admin on 2018/3/14.（速运-小件单-额外需求）
 */

public class SuYun_XiaoJianDan_EWaiXuQiu {

    private double owner_service_price;//服务价格 0为免费
    private String service_name;//额外需求服务名

    public double getOwner_service_price() {
        return owner_service_price;
    }

    public void setOwner_service_price(double owner_service_price) {
        this.owner_service_price = owner_service_price;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}
