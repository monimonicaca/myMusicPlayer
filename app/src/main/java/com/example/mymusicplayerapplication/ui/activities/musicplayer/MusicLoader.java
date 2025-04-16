package com.example.mymusicplayerapplication.ui.activities.musicplayer;

import android.content.Context;
import android.telecom.Call;
import android.util.LruCache;

import com.example.mymusicplayerapplication.data.model.PlayInfoEntity;
import com.example.mymusicplayerapplication.helper.ExcutorsHelper;
import com.example.mymusicplayerapplication.manager.service.IMusicPlayService;
import com.example.mymusicplayerapplication.manager.service.impl.MusicPlayService;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MusicLoader  {
   private LruCache<String, PlayInfoEntity> musicCache;
   private ExcutorsHelper excutorsHelper;
   private IMusicPlayService iMusicPlayService;
   private PlayInfoEntity playInfoEntity;
   private static MusicLoader musicLoader;
   public static MusicLoader getInstance(Context context){
       if(musicLoader==null){
           synchronized (MusicLoader.class){
               if(musicLoader==null){
                   musicLoader=new MusicLoader(context);
               }
           }
       }
       return musicLoader;
   }
    private MusicLoader(Context context) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        musicCache = new LruCache<String, PlayInfoEntity>(cacheSize) {
            @Override
            protected int sizeOf(String key, PlayInfoEntity value) {
                return getCacheSize(key,value);
            }
        };
        iMusicPlayService=new MusicPlayService(context);
        excutorsHelper=ExcutorsHelper.getInstance();
    }
    public PlayInfoEntity loadSong(String hashid){
        //从缓存中找
        playInfoEntity=musicCache.get(hashid);
        //找不到
        if(playInfoEntity==null){
            Callable<PlayInfoEntity> loadTask=()->{
                PlayInfoEntity playInfo=iMusicPlayService.getPlayInfo(hashid);
                return playInfo;
            };
           Future<PlayInfoEntity> res= excutorsHelper.netExecutorService.submit(loadTask);
            try {
                playInfoEntity=res.get();
            } catch (ExecutionException e) {
                ExceptionHandleUtil.logException(e);
            } catch (InterruptedException e) {
                ExceptionHandleUtil.logException(e);
            }
        }
        return playInfoEntity;
    }
    private int getCacheSize(String key,PlayInfoEntity value){
        int size = key.getBytes().length +
                value.getHash().getBytes().length+
                value.getAudio_id()+value.getSignerId()+
                value.getErrcode()+value.getAudio_id()+
                value.getError().getBytes().length+
                value.getAuthor_name().getBytes().length+
                value.getBack_url().length+
                value.getExtName().getBytes().length+
                value.getBack_url().length+
                value.getUrl().getBytes().length+
                value.getSignerName().getBytes().length+
                value.getSignerId()+
                value.getSongName().getBytes().length
                +value.getMvhash().getBytes().length;
        return size +16;
    }
}
