package com.example.alok.banknoqueue;

/**
 * Created by alok on 6/5/17.
 */

public class GuestData {

    String email;
    String phone;
    String branch;
    String service;

    public GuestData() {
    }


    public GuestData(String email, String phone, String branch, String service) {
        this.email = email;
        this.phone = phone;
        this.branch = branch;
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBranch() {
        return branch;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
