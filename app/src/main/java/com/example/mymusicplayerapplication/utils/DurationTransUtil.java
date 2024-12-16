package com.example.mymusicplayerapplication.utils;

public class DurationTransUtil {
public static String formatTotalTime(int seconds){
    int minutes=seconds/60;
    int remainingSeconds=seconds%60;
    return String.format("%02d:%02d", minutes, remainingSeconds);
}
public static String formatRemainingTime(int currentPosition){
    int seconds=currentPosition/1000%60;
    int minutes=currentPosition/1000/60;
    return String.format("%02d:%02d", minutes, seconds);
}
}
