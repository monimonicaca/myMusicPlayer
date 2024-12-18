package com.example.mymusicplayerapplication.manager;

import androidx.annotation.Nullable;

import com.example.mymusicplayerapplication.data.model.SongEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayListManager {
    /**
     * 共享的数据
     */
    private List<SongEntity> playList;
    /**
     * 当前正在播放的歌曲*/
    private int index;
    private static PlayListManager playListManager;
    private PlayListManager(){
        /*先从数据库中读取数据，如果没有就直接new一个*/
        playList=new ArrayList<>();
    }
    public static PlayListManager getInstance(){
        if (playListManager==null)playListManager=new PlayListManager();
        return playListManager;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public SongEntity getSong(int position){
        return playList.get(position);
    }
    public List<SongEntity> getSongList() {
        return playList;
    }
    public void addSong(SongEntity song,int position){
        playList.add(position,song);
    }
    public void addSong(SongEntity song){
        playList.add(song);
    }
    public void removeSong(int position){
        playList.remove(position);
    }
    public void setSongList(List<SongEntity> songList) {
        this.playList = songList;
    }
    public int getIndex(SongEntity song){
        for (int i=0;i<playList.size();i++){
            if (playList.get(i).getHash().equals(song.getHash()))return i;
        }
        return -1;
    }

}
