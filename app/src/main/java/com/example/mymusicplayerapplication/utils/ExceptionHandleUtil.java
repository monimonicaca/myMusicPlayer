package com.example.mymusicplayerapplication.utils;

import android.content.Context;
import android.util.Log;

public class ExceptionHandleUtil {

    public static void logException(Exception e) {
        // 使用 Logger 或 Android 的 Log 类
        Log.e(e.getClass().getSimpleName(), formatExceptionMessage(e), e);
    }

    // 格式化异常信息
    public static String formatExceptionMessage(Exception e) {
        return "Error: " + e.getClass().getSimpleName() + " - " + e.getMessage();
    }
    public static void showException(Context c,String content){
        ToastUtil.showToast(2000,content,c);
    }
}
