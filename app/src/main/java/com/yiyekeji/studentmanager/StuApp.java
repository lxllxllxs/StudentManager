package com.yiyekeji.studentmanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by lxl on 2016/10/26.
 */
public class StuApp extends Application {
    public static  boolean isLogin;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
