package com.example.appbanhang.model;

import java.io.Serializable;

public class PopularModel implements Serializable {
    String namePopular;
    String descriptionPopular;
    Integer discount;
    Integer price;
    String type;
    String imgUrl;
    String detail;

    public PopularModel() {
    }

    public PopularModel(String namePopular, String descriptionPopular, Integer discount, Integer price, String type, String imgUrl, String detail) {
        this.namePopular = namePopular;
        this.descriptionPopular = descriptionPopular;
        this.discount = discount;
        this.price = price;
        this.type = type;
        this.imgUrl = imgUrl;
        this.detail = detail;
    }

    public String getNamePopular() {
        return namePopular;
    }

    public void setNamePopular(String namePopular) {
        this.namePopular = namePopular;
    }

    public String getDescriptionPopular() {
        return descriptionPopular;
    }

    public void setDescriptionPopular(String descriptionPopular) {
        this.descriptionPopular = descriptionPopular;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
