package com.example.mymusicplayerapplication.manager.service;

import com.example.mymusicplayerapplication.data.model.PlayInfoEntity;
import com.example.mymusicplayerapplication.data.model.SongEntity;

public interface IMusicPlayService {
    public PlayInfoEntity getPlayInfo(String hash);
    public int getPlayIndex(SongEntity song);
}
