package com.example.mymusicplayerapplication.manager.service.impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson2.JSON;
import com.example.mymusicplayerapplication.data.model.PlayInfoEntity;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.manager.service.IMusicPlayService;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;
import com.example.mymusicplayerapplication.utils.NetUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayService implements IMusicPlayService {
    private final static String PLAY_INFO_URL="https://m.kugou.com/app/i/getSongInfo.php";
    private final static String PARAM_CMD_KEY="cmd";
    private final static String PARAM_CMD_VALUE="playInfo";
    private final static String PARAM_HASH_KEY="hash";
    private Context mContext;

    public MusicPlayService(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public PlayInfoEntity getPlayInfo(String hash) {
        Map params=new HashMap();
        params.put(PARAM_CMD_KEY,PARAM_CMD_VALUE);
        params.put(PARAM_HASH_KEY,hash);
        PlayInfoEntity playInfoEntity=null;
        try {
            String playinfo= NetUtil.net(PLAY_INFO_URL,params,"GET");
            playInfoEntity= JSON.parseObject(playinfo,PlayInfoEntity.class);
            //Log.d("音乐信息", playInfoEntity.toString());
        } catch (IOException e) {
            ExceptionHandleUtil.logException(e);
            ExceptionHandleUtil.showException(mContext,"歌曲获取失败");
        }
        return playInfoEntity;
    }

    @Override
    public int getPlayIndex(SongEntity song) {

        return PlayListManager.getInstance().getSongList().indexOf(song);
    }
}

