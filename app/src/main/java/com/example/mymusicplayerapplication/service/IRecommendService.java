package com.example.mymusicplayerapplication.service;

import com.example.mymusicplayerapplication.entity.SongEntity;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface IRecommendService {
    public List<SongEntity> getRecommendSongList(Map params);
}
