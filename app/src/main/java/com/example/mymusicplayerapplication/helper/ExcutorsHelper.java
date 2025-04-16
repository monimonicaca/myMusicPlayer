package com.example.mymusicplayerapplication.helper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExcutorsHelper {
    private static volatile ExcutorsHelper excutorsHelper;
    public ExecutorService roomExecutorService;
    public ExecutorService netExecutorService;
    private ExcutorsHelper(){
       roomExecutorService=new ThreadPoolExecutor(1,1,30,TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        netExecutorService=new ThreadPoolExecutor(3,5,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
    }
    public static ExcutorsHelper getInstance(){
        if (excutorsHelper==null){
            synchronized (ExcutorsHelper.class){
                if(excutorsHelper==null){
                    excutorsHelper=new ExcutorsHelper();
                }
            }
        }
        return excutorsHelper;
    }
}
