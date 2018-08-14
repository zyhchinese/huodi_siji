package com.lxwls.hdsjd.bean;

/**
 * Created by Administrator on 2018/1/11.
 */

public class CheckSiJiRenZhengEntity {

    private int id;

    private int custid;

    private int driver_car_type;

    private String driver_car_number;

    private String driver_phone;

    private int driver_checkno;

    private String driver_name;

    private int driver_info_status;

    private String note;

    private String submit_time;

    private int driver_star;

    private int isvalid;

    private int noagree_count;

    private int driver_level;

    private String uuid;

    private int isrecord;

    private String imglist;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setCustid(int custid){
        this.custid = custid;
    }
    public int getCustid(){
        return this.custid;
    }
    public void setDriver_car_type(int driver_car_type){
        this.driver_car_type = driver_car_type;
    }
    public int getDriver_car_type(){
        return this.driver_car_type;
    }
    public void setDriver_car_number(String driver_car_number){
        this.driver_car_number = driver_car_number;
    }
    public String getDriver_car_number(){
        return this.driver_car_number;
    }
    public void setDriver_phone(String driver_phone){
        this.driver_phone = driver_phone;
    }
    public String getDriver_phone(){
        return this.driver_phone;
    }
    public void setDriver_checkno(int driver_checkno){
        this.driver_checkno = driver_checkno;
    }
    public int getDriver_checkno(){
        return this.driver_checkno;
    }
    public void setDriver_name(String driver_name){
        this.driver_name = driver_name;
    }
    public String getDriver_name(){
        return this.driver_name;
    }
    public void setDriver_info_status(int driver_info_status){
        this.driver_info_status = driver_info_status;
    }
    public int getDriver_info_status(){
        return this.driver_info_status;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setSubmit_time(String submit_time){
        this.submit_time = submit_time;
    }
    public String getSubmit_time(){
        return this.submit_time;
    }
    public void setDriver_star(int driver_star){
        this.driver_star = driver_star;
    }
    public int getDriver_star(){
        return this.driver_star;
    }
    public void setIsvalid(int isvalid){
        this.isvalid = isvalid;
    }
    public int getIsvalid(){
        return this.isvalid;
    }
    public void setNoagree_count(int noagree_count){
        this.noagree_count = noagree_count;
    }
    public int getNoagree_count(){
        return this.noagree_count;
    }
    public void setDriver_level(int driver_level){
        this.driver_level = driver_level;
    }
    public int getDriver_level(){
        return this.driver_level;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public String getUuid(){
        return this.uuid;
    }
    public void setIsrecord(int isrecord){
        this.isrecord = isrecord;
    }
    public int getIsrecord(){
        return this.isrecord;
    }
    public void setImglist(String imglist){
        this.imglist = imglist;
    }
    public String getImglist(){
        return this.imglist;
    }
}
