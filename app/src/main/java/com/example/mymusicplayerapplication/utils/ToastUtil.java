package com.example.mymusicplayerapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast(int duration, String content, Context context){
        Toast.makeText(context,content,duration).show();
    }
}
