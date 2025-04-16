package com.example.mymusicplayerapplication.data.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alibaba.fastjson2.annotation.JSONField;


@Entity(tableName = "playlist",indices = {@Index(value = {"hash"},
        unique = true)})
public class Song {
    @PrimaryKey(autoGenerate=true)
    public int id;
    public String hash;
    public String sqhash;
    @JSONField(name = "320hash")
    public String ttzhash;
    public String filename;
    public String mvhash;
    public int duration;
    public String extname;
    public String remark;
    public String brief;
    public int filesize;
    public int sqfilesize;
    @JSONField(name = "320filesize")
    public int ttzfilesize;
    public  int m4afilesize;
    public String topic_url;
    public int fail_process_sq;
    public int fail_process;
    public int audio_id;
    public int type;
}
