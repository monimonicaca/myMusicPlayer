package com.example.mymusicplayerapplication;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.mymusicplayerapplication.Config.MustInitConfig;
import com.tencent.qqmusic.innovation.common.util.DeviceUtils;
import com.tencent.qqmusic.openapisdk.core.InitConfig;
import com.tencent.qqmusic.openapisdk.core.OpenApiSDK;
import com.tencent.qqmusic.openapisdk.core.network.NetworkTimeoutConfig;

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
        initQQMusicSDK();
       // loginInfo=getSharedPreferences(LOGIN_INFO_FILE_NAME,MODE_PRIVATE);
       // checkIsLogin();
    }
    public void checkIsLogin(){
        boolean isLogin=loginInfo.getBoolean("isLogin",false);
        info.put("isLogin",isLogin);
        ISLOGIN=isLogin;
    }
    private void initQQMusicSDK(){
       //Log.d("LibraryPath", System.getProperty("java.library.path"));
        InitConfig initConfig = new InitConfig(
                getApplicationContext(),
                MustInitConfig.APP_ID,
                MustInitConfig.APP_KEY,
                DeviceUtils.getAndroidID()
        );
        // 配置属性
        initConfig.setUseForegroundService(true);
        // 设置崩溃配置
        InitConfig.CrashConfig crashConfig = new InitConfig.CrashConfig(true, true);
        initConfig.setCrashConfig(crashConfig);
        File logFileDir = new File(getFilesDir(), "logs");
        if (!logFileDir.exists()) {
            logFileDir.mkdirs();
        }
        // 设置日志路径
        initConfig.setLogFileDir(logFileDir.getAbsolutePath());
        // 设置网络超时配置
        NetworkTimeoutConfig timeoutConfig = new NetworkTimeoutConfig(100000L,100000L,100000L,100000L);  // 10秒连接超时，15秒读取超时和写入超时
        initConfig.setNetworkTimeoutConfig(timeoutConfig);
        // 初始化 SDK
        int result= OpenApiSDK.init(initConfig);
        Log.d("initQQMusicSDK: ",String.valueOf(result)) ;
    }
    public static SharedPreferences getLoginInfoSharedPreferences(){
        return loginInfo;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenApiSDK.destroy();
        Log.d("销毁SDK", "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
