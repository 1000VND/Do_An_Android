package com.example.appbanhang.model;

public class AccountModel {
    private int Id;
    private String UserName;
    private String Email;
    private String Phone;
    private String PassWord;

    public AccountModel() {
    }

    public AccountModel(int id, String userName, String email, String phone, String passWord) {
        Id = id;
        UserName = userName;
        Email = email;
        Phone = phone;
        PassWord = passWord;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
