package com.lxwls.hdsjd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class AuditOrderEntity implements Serializable{


    /**
     * id : 192
     * orderno : ZDD201803200004
     * owner_custid : 88
     * driver_custid : 62
     * pickuptime : 2018-03-20 12:38:00
     * createtime : 2018-03-20 12:42:17
     * startlongitude : 117.14619755744934
     * startlatitude : 36.658319523395264
     * startaddress : 山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷
     * endlongitude : 117.15588703751567
     * endlatitude : 36.67100379834226
     * endaddress : 山东省济南市历下区智远街道志远实业总公司
     * weightofgoods : 0-20kg
     * expectedarrivaltime : 2018-03-23 00:00:00
     * consigneename : zhangxiao
     * consigneephone : 15069019010
     * floorhousenumber :
     * remarks :
     * ordertotalprice : 10
     * ispay : 1
     * paymethod : 2
     * pay_no : ZDD201803200004
     * pay_time : 2018-03-20 12:42:24
     * pingtai_money : 2
     * siji_money : 8
     * siji_singletime : 2018-03-20 12:43:01
     * cancelmoney1 :
     * uuid : 0cb8bacc75894517a0a6
     * cust_orderstatus : 0
     * driver_orderstatus : 0
     * money_status : 0
     * isinvoice : 0
     * isevaluate : 0
     * sprovinceid : 1
     * scityid : 36
     * scountyid : 47
     * sprocitcou : 浙江省 湖州市 吴兴区
     * eprovinceid : 1
     * ecityid : 36
     * ecountyid : 47
     * eprocitcou : 浙江省 湖州市 吴兴区
     * cancelmoney : 0
     * autoname : 030839e19a1e4b44b5480c210f357c4b.png
     * folder : customerImages
     */

    private String id;
    private String orderno;
    private String owner_custid;
    private String driver_custid;
    private String pickuptime;
    private String createtime;
    private String startlongitude;
    private String startlatitude;
    private String startaddress;
    private String endlongitude;
    private String endlatitude;
    private String endaddress;
    private String weightofgoods;
    private String expectedarrivaltime;
    private String consigneename;
    private String consigneephone;
    private String floorhousenumber;
    private String remarks;
    private String ordertotalprice;
    private String ispay;
    private String paymethod;
    private String pay_no;
    private String pay_time;
    private String pingtai_money;
    private String siji_money;
    private String siji_singletime;
    private String cancelmoney1;
    private String uuid;
    private String cust_orderstatus;
    private String driver_orderstatus;
    private String money_status;
    private String isinvoice;
    private String isevaluate;
    private String sprovinceid;
    private String scityid;
    private String scountyid;
    private String sprocitcou;
    private String eprovinceid;
    private String ecityid;
    private String ecountyid;
    private String eprocitcou;
    private String cancelmoney;
    private String autoname;
    private String folder;
    private String fname;
    private String fphone;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOwner_custid() {
        return owner_custid;
    }

    public void setOwner_custid(String owner_custid) {
        this.owner_custid = owner_custid;
    }

    public String getDriver_custid() {
        return driver_custid;
    }

    public void setDriver_custid(String driver_custid) {
        this.driver_custid = driver_custid;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(String startlongitude) {
        this.startlongitude = startlongitude;
    }

    public String getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(String startlatitude) {
        this.startlatitude = startlatitude;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(String endlongitude) {
        this.endlongitude = endlongitude;
    }

    public String getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(String endlatitude) {
        this.endlatitude = endlatitude;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }

    public String getWeightofgoods() {
        return weightofgoods;
    }

    public void setWeightofgoods(String weightofgoods) {
        this.weightofgoods = weightofgoods;
    }

    public String getExpectedarrivaltime() {
        return expectedarrivaltime;
    }

    public void setExpectedarrivaltime(String expectedarrivaltime) {
        this.expectedarrivaltime = expectedarrivaltime;
    }

    public String getConsigneename() {
        return consigneename;
    }

    public void setConsigneename(String consigneename) {
        this.consigneename = consigneename;
    }

    public String getConsigneephone() {
        return consigneephone;
    }

    public void setConsigneephone(String consigneephone) {
        this.consigneephone = consigneephone;
    }

    public String getFloorhousenumber() {
        return floorhousenumber;
    }

    public void setFloorhousenumber(String floorhousenumber) {
        this.floorhousenumber = floorhousenumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrdertotalprice() {
        return ordertotalprice;
    }

    public void setOrdertotalprice(String ordertotalprice) {
        this.ordertotalprice = ordertotalprice;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPingtai_money() {
        return pingtai_money;
    }

    public void setPingtai_money(String pingtai_money) {
        this.pingtai_money = pingtai_money;
    }

    public String getSiji_money() {
        return siji_money;
    }

    public void setSiji_money(String siji_money) {
        this.siji_money = siji_money;
    }

    public String getSiji_singletime() {
        return siji_singletime;
    }

    public void setSiji_singletime(String siji_singletime) {
        this.siji_singletime = siji_singletime;
    }

    public String getCancelmoney1() {
        return cancelmoney1;
    }

    public void setCancelmoney1(String cancelmoney1) {
        this.cancelmoney1 = cancelmoney1;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCust_orderstatus() {
        return cust_orderstatus;
    }

    public void setCust_orderstatus(String cust_orderstatus) {
        this.cust_orderstatus = cust_orderstatus;
    }

    public String getDriver_orderstatus() {
        return driver_orderstatus;
    }

    public void setDriver_orderstatus(String driver_orderstatus) {
        this.driver_orderstatus = driver_orderstatus;
    }

    public String getMoney_status() {
        return money_status;
    }

    public void setMoney_status(String money_status) {
        this.money_status = money_status;
    }

    public String getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(String isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getIsevaluate() {
        return isevaluate;
    }

    public void setIsevaluate(String isevaluate) {
        this.isevaluate = isevaluate;
    }

    public String getSprovinceid() {
        return sprovinceid;
    }

    public void setSprovinceid(String sprovinceid) {
        this.sprovinceid = sprovinceid;
    }

    public String getScityid() {
        return scityid;
    }

    public void setScityid(String scityid) {
        this.scityid = scityid;
    }

    public String getScountyid() {
        return scountyid;
    }

    public void setScountyid(String scountyid) {
        this.scountyid = scountyid;
    }

    public String getSprocitcou() {
        return sprocitcou;
    }

    public void setSprocitcou(String sprocitcou) {
        this.sprocitcou = sprocitcou;
    }

    public String getEprovinceid() {
        return eprovinceid;
    }

    public void setEprovinceid(String eprovinceid) {
        this.eprovinceid = eprovinceid;
    }

    public String getEcityid() {
        return ecityid;
    }

    public void setEcityid(String ecityid) {
        this.ecityid = ecityid;
    }

    public String getEcountyid() {
        return ecountyid;
    }

    public void setEcountyid(String ecountyid) {
        this.ecountyid = ecountyid;
    }

    public String getEprocitcou() {
        return eprocitcou;
    }

    public void setEprocitcou(String eprocitcou) {
        this.eprocitcou = eprocitcou;
    }

    public String getCancelmoney() {
        return cancelmoney;
    }

    public void setCancelmoney(String cancelmoney) {
        this.cancelmoney = cancelmoney;
    }

    public String getAutoname() {
        return autoname;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
