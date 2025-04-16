package com.example.mymusicplayerapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mymusicplayerapplication.data.model.Song;
import com.example.mymusicplayerapplication.data.model.SongEntity;

import java.util.List;

@Dao
public interface SongDao {
    static final String SONG_TABLE_NAME="playlist";
    @Insert(onConflict = OnConflictStrategy.ABORT)
     Long insertSongs(SongEntity song);
    @Query("Select * from "+SONG_TABLE_NAME)
    List<SongEntity> loadAll();
}
