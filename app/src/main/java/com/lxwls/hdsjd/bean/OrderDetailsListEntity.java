package com.lxwls.hdsjd.bean;

/**
 * Created by Administrator on 2017/10/21.
 */

public class OrderDetailsListEntity {
    private int id;

    private int orderid;

    private int proid;

    private String proname;

    private String specification;

    private double price;

    private int count;

    private double money;

    private int type;

    private String note;

    private String color;

    private String productdes;

    private String folder;

    private String autoname;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOrderid(int orderid){
        this.orderid = orderid;
    }
    public int getOrderid(){
        return this.orderid;
    }
    public void setProid(int proid){
        this.proid = proid;
    }
    public int getProid(){
        return this.proid;
    }
    public void setProname(String proname){
        this.proname = proname;
    }
    public String getProname(){
        return this.proname;
    }
    public void setSpecification(String specification){
        this.specification = specification;
    }
    public String getSpecification(){
        return this.specification;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setMoney(double money){
        this.money = money;
    }
    public double getMoney(){
        return this.money;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return this.color;
    }
    public void setProductdes(String productdes){
        this.productdes = productdes;
    }
    public String getProductdes(){
        return this.productdes;
    }
    public void setFolder(String folder){
        this.folder = folder;
    }
    public String getFolder(){
        return this.folder;
    }
    public void setAutoname(String autoname){
        this.autoname = autoname;
    }
    public String getAutoname(){
        return this.autoname;
    }
}
