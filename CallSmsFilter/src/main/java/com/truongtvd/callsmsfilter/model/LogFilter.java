package com.truongtvd.callsmsfilter.model;

/**
 * Created by truongtvd on 6/12/14.
 */
public class LogFilter {

    private int time;
    private String phone;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
