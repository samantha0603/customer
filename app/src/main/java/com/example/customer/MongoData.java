package com.example.customer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MongoData {
    @SerializedName("productname")
    private String  productname;
    @SerializedName("issue")
    private String issue;
    @SerializedName("description")
    private String description;
    @SerializedName("customerName")
    private String customername;
    @SerializedName("customerPhno")
    private String phno;
    @SerializedName("address")
    private String address;
    @SerializedName("Assigned Engineer")
    private String Assigned_Engineer;

    public void setAssigned_Engineer(String assigned_Engineer) {
        Assigned_Engineer = assigned_Engineer;
    }

    public String getAssigned_Engineer() {

        return Assigned_Engineer;
    }

    public MongoData(String productname, String issue, String description, String customername, String phno, String address, String status , String Assigned_Engineer) {
        this.productname = productname;
        this.issue = issue;
        this.description = description;
        this.customername = customername;
        this.phno = phno;
        this.address = address;
        this.Assigned_Engineer = Assigned_Engineer;

    }


    public String getIssue() {
        return issue;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomername() {
        return customername;
    }

    public String getPhno() {
        return phno;
    }

    public String getAddress() {
        return address;
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
