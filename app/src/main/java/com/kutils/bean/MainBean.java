package com.kutils.bean;


import com.kutils.R;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public class MainBean {
    private String title;
    private int dwrableId= R.mipmap.aa;
    private Class activity;

    public MainBean(String title, Class activity) {
        this.title = title;
        this.activity = activity;
    }

    public MainBean(String title, int dwrableId, Class activity) {

        this.title = title;
        this.dwrableId = dwrableId;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "title='" + title + '\'' +
                ", dwrableId=" + dwrableId +
                ", activity=" + activity +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDwrableId() {
        return dwrableId;
    }

    public void setDwrableId(int dwrableId) {
        this.dwrableId = dwrableId;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

}
