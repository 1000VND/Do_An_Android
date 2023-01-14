package com.example.appbanhang.model;

public class CartModel {
    String currentDate;
    String currentTime;
    String description;
    String namePopular;
    Integer price;
    String imgUrl;
    Integer quantity;

    public CartModel() {
    }

    public CartModel(String currentDate, String currentTime, String description, String namePopular, Integer price, String imgUrl, Integer quantity) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.namePopular = namePopular;
        this.price = price;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNamePopular() {
        return namePopular;
    }

    public void setNamePopular(String namePopular) {
        this.namePopular = namePopular;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
