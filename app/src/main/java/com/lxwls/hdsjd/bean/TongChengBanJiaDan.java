package com.lxwls.hdsjd.bean;

import java.util.List;

/**
 * Created by admin on 2018/3/13.（同城搬家单）
 */

public class TongChengBanJiaDan {


    /**
     * total : 2
     * page_number : 1
     * rows : [{"id":386,"siji_money":16,"owner_orderno":"SDD201803190001","owner_link_name":"whl_123","owner_link_phone":"18615207831","owner_sendtime":"2018-03-19 09:47:00","owner_createtime":"2018-03-19 09:47:42","owner_address":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷","owner_send_address":"山东省济南市历城区华山街道家和苑","longitude":117.14643,"latitude":36.657991,"owner_totalprice":20,"car_type":"小货车-平板","autoname":"","folder":""},{"id":384,"siji_money":16,"owner_orderno":"SDD201803170001","owner_link_name":"zhangxiao","owner_link_phone":"15069019010","owner_sendtime":"2018-03-17 12:15:00","owner_createtime":"2018-03-17 12:16:02","owner_address":"山东省济南市历城区港沟街道龙奥北路301号汉峪金融商务中心","owner_send_address":"山东省济南市历城区港沟街道舜奥华府舜奥华府北区","longitude":117.146416,"latitude":36.656907,"owner_totalprice":20,"car_type":"小货车-平板","autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"}]
     */

    private String total;
    private String page_number;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage_number() {
        return page_number;
    }

    public void setPage_number(String page_number) {
        this.page_number = page_number;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 386
         * siji_money : 16
         * owner_orderno : SDD201803190001
         * owner_link_name : whl_123
         * owner_link_phone : 18615207831
         * owner_sendtime : 2018-03-19 09:47:00
         * owner_createtime : 2018-03-19 09:47:42
         * owner_address : 山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷
         * owner_send_address : 山东省济南市历城区华山街道家和苑
         * longitude : 117.14643
         * latitude : 36.657991
         * owner_totalprice : 20
         * car_type : 小货车-平板
         * autoname :
         * folder :
         */

        private String id;
        private String siji_money;
        private String owner_orderno;
        private String owner_link_name;
        private String owner_link_phone;
        private String owner_sendtime;
        private String owner_createtime;
        private String owner_address;
        private String owner_send_address;
        private String longitude;
        private String latitude;
        private String owner_totalprice;
        private String car_type;
        private String autoname;
        private String folder;
        private String cust_star;
        private String cust_num;
        private String beizhu;
        private double licheng;

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getCust_star() {
            return cust_star;
        }

        public void setCust_star(String cust_star) {
            this.cust_star = cust_star;
        }

        public String getCust_num() {
            return cust_num;
        }

        public void setCust_num(String cust_num) {
            this.cust_num = cust_num;
        }

        public double getLicheng() {
            return licheng;
        }

        public void setLicheng(double licheng) {
            this.licheng = licheng;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSiji_money() {
            return siji_money;
        }

        public void setSiji_money(String siji_money) {
            this.siji_money = siji_money;
        }

        public String getOwner_orderno() {
            return owner_orderno;
        }

        public void setOwner_orderno(String owner_orderno) {
            this.owner_orderno = owner_orderno;
        }

        public String getOwner_link_name() {
            return owner_link_name;
        }

        public void setOwner_link_name(String owner_link_name) {
            this.owner_link_name = owner_link_name;
        }

        public String getOwner_link_phone() {
            return owner_link_phone;
        }

        public void setOwner_link_phone(String owner_link_phone) {
            this.owner_link_phone = owner_link_phone;
        }

        public String getOwner_sendtime() {
            return owner_sendtime;
        }

        public void setOwner_sendtime(String owner_sendtime) {
            this.owner_sendtime = owner_sendtime;
        }

        public String getOwner_createtime() {
            return owner_createtime;
        }

        public void setOwner_createtime(String owner_createtime) {
            this.owner_createtime = owner_createtime;
        }

        public String getOwner_address() {
            return owner_address;
        }

        public void setOwner_address(String owner_address) {
            this.owner_address = owner_address;
        }

        public String getOwner_send_address() {
            return owner_send_address;
        }

        public void setOwner_send_address(String owner_send_address) {
            this.owner_send_address = owner_send_address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getOwner_totalprice() {
            return owner_totalprice;
        }

        public void setOwner_totalprice(String owner_totalprice) {
            this.owner_totalprice = owner_totalprice;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
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
}
