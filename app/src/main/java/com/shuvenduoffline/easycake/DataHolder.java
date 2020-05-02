package com.shuvenduoffline.easycake;

import android.app.Application;

import java.util.List;

public class DataHolder {
    private List<Cake> data;

    public List<Cake> getData() {
        return data;
    }

    public void setData(List<Cake> data) {
        this.data = data;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}

