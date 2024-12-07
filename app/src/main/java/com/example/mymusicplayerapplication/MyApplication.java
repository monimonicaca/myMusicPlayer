package com.example.mymusicplayerapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public HashMap<String,Object> info=new HashMap<>();
    private static SharedPreferences loginInfo;
    private static final String LOGIN_INFO_FILE_NAME="loginInfo";
    public static boolean ISLOGIN;
    public static MyApplication getInstance(){
        if (myApplication==null)myApplication=new MyApplication();
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        Log.d("启动Application", "onCreate: ");
        loginInfo=getSharedPreferences(LOGIN_INFO_FILE_NAME,MODE_PRIVATE);
        checkIsLogin();
    }
    public void checkIsLogin(){
        boolean isLogin=loginInfo.getBoolean("isLogin",false);
        info.put("isLogin",isLogin);
        ISLOGIN=isLogin;
    }
    public static SharedPreferences getLoginInfoSharedPreferences(){
        return loginInfo;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
