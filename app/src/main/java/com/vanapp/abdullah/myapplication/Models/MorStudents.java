package com.vanapp.abdullah.myapplication.Models;

public class MorStudents {

    private int id;
    private  String name;
    private  String img;
    private  String van_no;

    public MorStudents(int id, String name, String img, String van_no) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.van_no = van_no;
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

    public String getVan_no() {
        return van_no;
    }

    public void setVan_no(String van_no) {
        this.van_no = van_no;
    }
}
