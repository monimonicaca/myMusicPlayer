package com.example.mymusicplayerapplication.service;

import com.example.mymusicplayerapplication.entity.SongEntity;

import org.json.JSONArray;

import java.util.List;

public interface IRecommendService {
    public List<SongEntity> getRecommendSongList();
}
