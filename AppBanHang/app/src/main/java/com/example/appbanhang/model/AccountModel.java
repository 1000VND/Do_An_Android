package com.example.appbanhang.model;

import com.google.android.material.textfield.TextInputEditText;

public class AccountModel {
    private String UserName;
    private String Email;
    private String Phone;
    private String PassWord;

    public AccountModel() {
    }

    public AccountModel(String userName, String email, String phone, String passWord) {
        UserName = userName;
        Email = email;
        Phone = phone;
        PassWord = passWord;
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
