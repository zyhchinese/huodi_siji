package com.lxwls.hdsjd.bean;

import java.util.List;

/**
 * Created by admin on 2018/3/12.（首页-货滴速运-同城小件单）
 */

public class TongChengXiaoJianDan {


    /**
     * total : 4
     * page_number : 1
     * rows : [{"id":187,"siji_money":8,"orderno":"ZDD201803190003","consigneename":"zhangxiao","consigneephone":"15069019010","pickuptime":"2018-03-19 15:56:00","createtime":"2018-03-19 15:57:12","startaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷","endaddress":"山东省济南市历城区港沟街道经十路辅路汉峪金谷","startlongitude":"117.14624717831613","startlatitude":"36.658319523395264","ordertotalprice":10,"autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"},{"id":186,"siji_money":8,"orderno":"ZDD201803190002","consigneename":"whl_123","consigneephone":"18615207831","pickuptime":"2018-03-19 09:23:00","createtime":"2018-03-19 09:24:38","startaddress":"山东省济南市历下区解放路街道历山路99-1号","endaddress":"山东省济南市历下区姚家街道绿地IFC中央公馆(建设中)","startlongitude":"117.042163","startlatitude":"36.669708","ordertotalprice":10,"autoname":"","folder":""},{"id":179,"siji_money":32,"orderno":"ZDD201803170001","consigneename":"zhangxiao","consigneephone":"15069019010","pickuptime":"2018-03-17 09:55:00","createtime":"2018-03-17 09:55:35","startaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷","endaddress":"山东省济南市历城区港沟街道华奥路567号德润·天玺","startlongitude":"117.14621365070346","startlatitude":"36.658319523395264","ordertotalprice":40,"autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"},{"id":178,"siji_money":32,"orderno":"ZDD201803160006","consigneename":"zhangxiao","consigneephone":"15069019010","pickuptime":"2018-03-16 17:56:00","createtime":"2018-03-16 17:56:50","startaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷","endaddress":"山东省济南市历城区港沟街道龙奥北路299号汉峪金融商务中心","startlongitude":"117.14616000652313","startlatitude":"36.658311992461066","ordertotalprice":40,"autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"}]
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
         * id : 187
         * siji_money : 8
         * orderno : ZDD201803190003
         * consigneename : zhangxiao
         * consigneephone : 15069019010
         * pickuptime : 2018-03-19 15:56:00
         * createtime : 2018-03-19 15:57:12
         * startaddress : 山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷
         * endaddress : 山东省济南市历城区港沟街道经十路辅路汉峪金谷
         * startlongitude : 117.14624717831613
         * startlatitude : 36.658319523395264
         * ordertotalprice : 10
         * autoname : 030839e19a1e4b44b5480c210f357c4b.png
         * folder : customerImages
         */

        private String id;
        private String siji_money;
        private String orderno;
        private String consigneename;
        private String consigneephone;
        private String pickuptime;
        private String createtime;
        private String startaddress;
        private String endaddress;
        private String startlongitude;
        private String startlatitude;
        private String ordertotalprice;
        private String autoname;
        private String folder;
        private String sprocitcou;
        private String eprocitcou;
        private String cust_num;
        private String cust_star;
        private String beizhu;
        private double licheng;

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getCust_num() {
            return cust_num;
        }

        public void setCust_num(String cust_num) {
            this.cust_num = cust_num;
        }

        public String getCust_star() {
            return cust_star;
        }

        public void setCust_star(String cust_star) {
            this.cust_star = cust_star;
        }

        public String getSprocitcou() {
            return sprocitcou;
        }

        public void setSprocitcou(String sprocitcou) {
            this.sprocitcou = sprocitcou;
        }

        public String getEprocitcou() {
            return eprocitcou;
        }

        public void setEprocitcou(String eprocitcou) {
            this.eprocitcou = eprocitcou;
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

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
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

        public String getStartaddress() {
            return startaddress;
        }

        public void setStartaddress(String startaddress) {
            this.startaddress = startaddress;
        }

        public String getEndaddress() {
            return endaddress;
        }

        public void setEndaddress(String endaddress) {
            this.endaddress = endaddress;
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

        public String getOrdertotalprice() {
            return ordertotalprice;
        }

        public void setOrdertotalprice(String ordertotalprice) {
            this.ordertotalprice = ordertotalprice;
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
