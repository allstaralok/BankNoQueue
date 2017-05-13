package com.example.alok.banknoqueue;

/**
 * Created by Ayushi Patel on 7/5/17.
 */

public class Branch {

    private String Time;
    private String TokenNo;

    public Branch() {
    }

    public Branch(String time, String tokenNo) {
        Time = time;
        TokenNo = tokenNo;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTokenNo() {
        return TokenNo;
    }

    public void setTokenNo(String tokenNo) {
        TokenNo = tokenNo;
    }
}
