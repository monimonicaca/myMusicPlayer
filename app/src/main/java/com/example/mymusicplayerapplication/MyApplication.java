package com.example.mymusicplayerapplication;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
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
        String account=loginInfo.getString("account","00000");
        Log.d("isLogin", isLogin+"");
        info.put("isLogin",isLogin);
        info.put("account",account);
        ISLOGIN=isLogin;
    }

    public static SharedPreferences getLoginInfoSharedPreferences(){
        return loginInfo;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
