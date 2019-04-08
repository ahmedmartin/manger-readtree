package com.ahmed.martin.mang_readtree;

import java.io.Serializable;

public class post_details implements Serializable{

    private String book_name;
    private String sale;
    private String rent;
    private String category;
    private String part;
    private String description;
    private String advisor_name;
    private String advisor_phone;
    private String share_phone;
    private String address;
    private String uid;




    public post_details() {
    }

    public post_details(String book_name, String sale, String rent, String category, String part, String description, String advisor_name, String advisor_phone, String share_phone, String address, String uid) {
        this.book_name = book_name;
        this.sale = sale;
        this.rent = rent;
        this.category = category;
        this.part = part;
        this.description = description;
        this.advisor_name = advisor_name;
        this.advisor_phone = advisor_phone;
        this.share_phone = share_phone;
        this.address = address;
        this.uid = uid;
    }

    public post_details(String book_name, String category, String part, String description, String advisor_name, String advisor_phone, String address, String uid) {
        this.book_name = book_name;
        this.category = category;
        this.part = part;
        this.description = description;
        this.advisor_name = advisor_name;
        this.advisor_phone = advisor_phone;
        this.address = address;
        this.uid = uid;
    }

    public post_details(String description, String advisor_name, String advisor_phone, String address) {
        this.description = description;
        this.advisor_name = advisor_name;
        this.advisor_phone = advisor_phone;
        this.address = address;
    }


    public String getBook_name() {
        return book_name;
    }


    public String getSale() {
        return sale;
    }

    public String getRent() {
        return rent;
    }

    public String getDescription() {
        return description;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvisor_name() {
        return advisor_name;
    }

    public String getAdvisor_phone() {
        return advisor_phone;
    }

    public void setAdvisor_name(String advisor_name) {
        this.advisor_name = advisor_name;
    }

    public void setAdvisor_phone(String advisor_phone) {
        this.advisor_phone = advisor_phone;
    }

    public String getCategory() {
        return category;
    }

    public String getPart() {
        return part;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getShare_phone() {
        return share_phone;
    }

    public void setShare_phone(String share_phone) {
        this.share_phone = share_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
