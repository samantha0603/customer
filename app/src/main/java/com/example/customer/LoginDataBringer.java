package com.example.customer;

import com.google.gson.annotations.SerializedName;

public class LoginDataBringer  {
    @SerializedName("phone")
    private String phone;

    public LoginDataBringer(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {

        return phone;
    }

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginDataBringer(String username, String password) {

        this.username = username;
        this.password = password;
    }
}

