package com.dgk.mvpdemo.model;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:53
 * @des ${TODO}
 */
public class Model implements IModel {


    @Override
    public void getDataFromNet() {
        // 4.model层获取数据
        SystemClock.sleep(500);
        Log.i("Model", "getDataFromNet");
    }
}
