package com.example.fong_.homeserviceapplicationonandroid.Model;

public class User {

    String id, name, email, contact, address, userType;
    int eCoin;

    public User() {
    }

    public User(String id, String name, String email, String contact, String address, String userType, int eCoin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.userType = userType;
        this.eCoin = eCoin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int geteCoin() {
        return eCoin;
    }

    public void seteCoin(int eCoin) {
        this.eCoin = eCoin;
    }
}