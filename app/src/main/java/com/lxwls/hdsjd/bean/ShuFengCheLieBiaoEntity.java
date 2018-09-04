package com.lxwls.hdsjd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/8/23.
 */

public class ShuFengCheLieBiaoEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":9,"tripno":"TRIP201808230001","sprovince":"山东省","scity":"济南市","scounty":"历城区","saddress":"山东省济南市历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","eaddress":"山东省济南市历城区","waytocitysshow":"济南市历城区,济南市历城区,济南市历城区","totalvehicle":111,"availablevehicle":22,"departuretime":"2018-11-23 10:38:00","createtime":"2018-08-23 10:37:54","state":1,"contactname":"sjsi","folder":"","autoname":""},{"id":8,"tripno":"TRIP201808230001","sprovince":"山东省","scity":"济南市","scounty":"历城区","saddress":"山东省济南市历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","eaddress":"山东省济南市历城区","waytocitysshow":"济南市历城区,济南市历城区,济南市历城区","totalvehicle":111,"availablevehicle":22,"departuretime":"2018-11-23 10:38:00","createtime":"2018-08-23 10:37:48","state":1,"contactname":"sjsi","folder":"","autoname":""}]
     */

    private int flag;
    private String msg;
    private List<ResponseBean> response;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 9
         * tripno : TRIP201808230001
         * sprovince : 山东省
         * scity : 济南市
         * scounty : 历城区
         * saddress : 山东省济南市历城区
         * eprovince : 山东省
         * ecity : 济南市
         * ecounty : 历城区
         * eaddress : 山东省济南市历城区
         * waytocitysshow : 济南市历城区,济南市历城区,济南市历城区
         * totalvehicle : 111
         * availablevehicle : 22
         * departuretime : 2018-11-23 10:38:00
         * createtime : 2018-08-23 10:37:54
         * state : 1
         * contactname : sjsi
         * folder :
         * autoname :
         */

        private int id;
        private String tripno;
        private String sprovince;
        private String scity;
        private String scounty;
        private String saddress;
        private String eprovince;
        private String ecity;
        private String ecounty;
        private String eaddress;
        private String waytocitysshow;
        private String totalvehicle;
        private String availablevehicle;
        private String departuretime;
        private String createtime;
        private int state;
        private String contactname;
        private String folder;
        private String autoname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTripno() {
            return tripno;
        }

        public void setTripno(String tripno) {
            this.tripno = tripno;
        }

        public String getSprovince() {
            return sprovince;
        }

        public void setSprovince(String sprovince) {
            this.sprovince = sprovince;
        }

        public String getScity() {
            return scity;
        }

        public void setScity(String scity) {
            this.scity = scity;
        }

        public String getScounty() {
            return scounty;
        }

        public void setScounty(String scounty) {
            this.scounty = scounty;
        }

        public String getSaddress() {
            return saddress;
        }

        public void setSaddress(String saddress) {
            this.saddress = saddress;
        }

        public String getEprovince() {
            return eprovince;
        }

        public void setEprovince(String eprovince) {
            this.eprovince = eprovince;
        }

        public String getEcity() {
            return ecity;
        }

        public void setEcity(String ecity) {
            this.ecity = ecity;
        }

        public String getEcounty() {
            return ecounty;
        }

        public void setEcounty(String ecounty) {
            this.ecounty = ecounty;
        }

        public String getEaddress() {
            return eaddress;
        }

        public void setEaddress(String eaddress) {
            this.eaddress = eaddress;
        }

        public String getWaytocitysshow() {
            return waytocitysshow;
        }

        public void setWaytocitysshow(String waytocitysshow) {
            this.waytocitysshow = waytocitysshow;
        }

        public String getTotalvehicle() {
            return totalvehicle;
        }

        public void setTotalvehicle(String totalvehicle) {
            this.totalvehicle = totalvehicle;
        }

        public String getAvailablevehicle() {
            return availablevehicle;
        }

        public void setAvailablevehicle(String availablevehicle) {
            this.availablevehicle = availablevehicle;
        }

        public String getDeparturetime() {
            return departuretime;
        }

        public void setDeparturetime(String departuretime) {
            this.departuretime = departuretime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContactname() {
            return contactname;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public String getFolder() {
            return folder;
        }

        public void setFolder(String folder) {
            this.folder = folder;
        }

        public String getAutoname() {
            return autoname;
        }

        public void setAutoname(String autoname) {
            this.autoname = autoname;
        }
    }
}
