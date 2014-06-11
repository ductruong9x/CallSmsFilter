package com.truongtvd.callsmsfilter.model;

/**
 * Created by truongtvd on 6/11/14.
 */
public class SmsFilter {

    private int time;
    private String phone;
    private String content;
    private String title;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
