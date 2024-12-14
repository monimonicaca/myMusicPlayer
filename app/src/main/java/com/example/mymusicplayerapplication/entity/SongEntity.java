package com.example.mymusicplayerapplication.entity;

import com.alibaba.fastjson2.annotation.JSONField;

public class SongEntity {
    private String hash;
    private String sq_hash;
    @JSONField(name = "320_hash")
    private String ttzhash;
    private String filename;
    private String mv_hash;
    private int duration;
    private String extname;
    private String remark;
    private String brief;
    private int filesize;
    private int sqfilesize;
    @JSONField(name = "320filesize")
    private int ttzfilesize;
    private  int m4afilesize;
    private String topic_url;
    private int fail_process_sq;
    private int fail_process;
    private int audio_id;
    private int type;

    @Override
    public String toString() {
        return "SongEntity{" +
                "hash='" + hash + '\'' +
                ", sq_hash='" + sq_hash + '\'' +
                ", ttzhash='" + ttzhash + '\'' +
                ", filename='" + filename + '\'' +
                ", mv_hash='" + mv_hash + '\'' +
                ", duration=" + duration +
                ", extname='" + extname + '\'' +
                ", remark='" + remark + '\'' +
                ", brief='" + brief + '\'' +
                ", filesize=" + filesize +
                ", sqfilesize=" + sqfilesize +
                ", ttzfilesize=" + ttzfilesize +
                ", m4afilesize=" + m4afilesize +
                ", topic_url='" + topic_url + '\'' +
                ", fail_process_sq=" + fail_process_sq +
                ", fail_process=" + fail_process +
                ", audio_id=" + audio_id +
                ", type=" + type +
                '}';
    }

    public String getSq_hash() {
        return sq_hash;
    }

    public void setSq_hash(String sq_hash) {
        this.sq_hash = sq_hash;
    }

    public String getTtzhash() {
        return ttzhash;
    }

    public void setTtzhash(String ttzhash) {
        this.ttzhash = ttzhash;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMv_hash() {
        return mv_hash;
    }

    public void setMv_hash(String mv_hash) {
        this.mv_hash = mv_hash;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public int getSqfilesize() {
        return sqfilesize;
    }

    public void setSqfilesize(int sqfilesize) {
        this.sqfilesize = sqfilesize;
    }

    public int getTtzfilesize() {
        return ttzfilesize;
    }

    public void setTtzfilesize(int ttzfilesize) {
        this.ttzfilesize = ttzfilesize;
    }

    public int getM4afilesize() {
        return m4afilesize;
    }

    public void setM4afilesize(int m4afilesize) {
        this.m4afilesize = m4afilesize;
    }

    public String getTopic_url() {
        return topic_url;
    }

    public void setTopic_url(String topic_url) {
        this.topic_url = topic_url;
    }

    public int getFail_process_sq() {
        return fail_process_sq;
    }

    public void setFail_process_sq(int fail_process_sq) {
        this.fail_process_sq = fail_process_sq;
    }

    public int getFail_process() {
        return fail_process;
    }

    public void setFail_process(int fail_process) {
        this.fail_process = fail_process;
    }

    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SongEntity() {
    }



    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
