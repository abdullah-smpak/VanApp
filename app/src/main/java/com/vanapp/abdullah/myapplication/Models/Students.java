package com.vanapp.abdullah.myapplication.Models;

public class Students {

    private int id;
    private  String name;
    private  String img;
    private  String cls_id;


    public Students(int id, String name, String img, String cls_id) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.cls_id = cls_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCls_id() {
        return cls_id;
    }

    public void setCls_id(String cls_id) {
        this.cls_id = cls_id;
    }
}
