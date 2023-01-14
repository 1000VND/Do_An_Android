package com.example.appbanhang.model;

public class HomeCategoryModel {
    String name;
    String imgurl;
    String type;

    public HomeCategoryModel() {
    }

    public HomeCategoryModel(String name, String imgurl, String type) {
        this.name = name;
        this.imgurl = imgurl;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
