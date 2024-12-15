package com.example.mymusicplayerapplication.manager.service;

import com.example.mymusicplayerapplication.data.model.SongEntity;

import java.util.List;
import java.util.Map;

public interface IRecommendService {
    public List<SongEntity> getRecommendSongList(Map params);
}
