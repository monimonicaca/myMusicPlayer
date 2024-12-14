package com.example.mymusicplayerapplication.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckPermissionUtil {
    public static boolean checkPermissions(Activity activity,String[] permissions,int requestCode){
        boolean check=true;
        /*如果版本号大于6.0再判断*/
        Log.d("检查权限", "checkPermissions: ");
        Log.d("当前版本", String.valueOf(Build.VERSION.SDK_INT));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int isPermission= PackageManager.PERMISSION_GRANTED;
            /*有一个没有权限就跳出循环*/
            for (String permission:permissions) {
                isPermission= ContextCompat.checkSelfPermission(activity,permission);
                if (isPermission==PackageManager.PERMISSION_DENIED)break;
            }
            if(isPermission!=PackageManager.PERMISSION_GRANTED){
                /*请求系统弹窗开启权限*/
                ActivityCompat.requestPermissions(activity,permissions,requestCode);
                check=false;
            }
        }
        return check;
    }
    public static boolean checkPermission(Activity activity,String permission,int requestCode) {
        return checkPermissions(activity,new String[]{permission},requestCode);}


    public static boolean checkResultCode(int [] resultCodes){
        boolean check=true;
        for (int resultCode:
             resultCodes) {
            if (resultCode == PackageManager.PERMISSION_DENIED) {
                check = false;
                break;
            }
        }
        return check;
    }

}
