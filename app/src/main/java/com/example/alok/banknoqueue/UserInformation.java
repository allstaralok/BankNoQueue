package com.example.alok.banknoqueue;

/**
 * Created by alok on 7/5/17.
 */

public class UserInformation {

    private String Branch;
    private String Name;
    private String Service;
    private String TokenInd;
    private String TokenNo;
    private String Time;

    public UserInformation() {
    }


    public UserInformation(String branch, String name, String service, String tokenInd, String tokenNo, String time) {
        Branch = branch;
        Name = name;
        Service = service;
        TokenInd = tokenInd;
        TokenNo = tokenNo;
        Time = time;
    }


    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getTokenInd() {
        return TokenInd;
    }

    public void setTokenInd(String tokenInd) {
        TokenInd = tokenInd;
    }

    public String getTokenNo() {
        return TokenNo;
    }

    public void setTokenNo(String tokenNo) {
        TokenNo = tokenNo;
    }
}
