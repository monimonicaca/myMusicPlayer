package com.example.mymusicplayerapplication.service.impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson2.JSON;
import com.example.mymusicplayerapplication.entity.SongEntity;
import com.example.mymusicplayerapplication.service.IRecommendService;
import com.example.mymusicplayerapplication.utils.NetUtil;
import com.example.mymusicplayerapplication.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommendService implements IRecommendService {
    private Map params;
    private Context mContext;
    private static final String RECOMMEND_API = "http://mobilecdnbj.kugou.com/api/v5/special/recommend";
    private static RecommendService recommendService;
    private List<SongEntity> songList;
    private RecommendService(Context context) {
        mContext=context;
        songList=new ArrayList<>();
    }
    public static RecommendService getInstance(Context context){
        if (recommendService==null){
            recommendService=new RecommendService(context);
        }
        return recommendService;
    }
    @Override
    public List<SongEntity> getRecommendSongList(Map params) {
        this.params=params;
        try {
            String result= NetUtil.net(RECOMMEND_API,params,"GET");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray dataList=jsonObject.getJSONObject("data").getJSONArray("list");
            //Log.d("获取到的数据的数量", ""+dataList.length());
            transJsonToSongList(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast(2000,"网路请求失败",mContext);
        }
        return songList;
    }
    private List<SongEntity> transJsonToSongList(JSONArray jsonArray){
        //Log.d("传入的数据的数量", ""+jsonArray.length());
        for (int i=0;i<jsonArray.length();i++){
            JSONObject j= null;
            try {
                j = jsonArray.getJSONObject(i);
                JSONArray songs=j.getJSONArray("songs");
                List<SongEntity> songEntityList=JSON.parseArray(songs.toString(),SongEntity.class);
                //Log.d(j.getString("reason"), j.getJSONArray("songs").toString());
                songList.addAll(songEntityList);
                //Log.d("转化后实体集合的数量", songList.size()+"");
                //Log.d("songList", songList.toString());
            } catch (JSONException e) {
                Log.e("JSONToEntityError", "由json数据转换为实体集合出现错误", e);
            }
        }
        return songList;
    }

}

