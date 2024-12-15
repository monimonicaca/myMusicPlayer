package com.example.mymusicplayerapplication.data.model;

import java.util.Arrays;

public class PlayInfoEntity {
    private String url;
    private String signerName;
    private String author_name;
    private String extName;
    private String error;
    private int audio_id;
    private int signerId;
    private int errcode;
    private String[] back_url;
    private String songName;
    private String mvhash;
    private String hash;

    public PlayInfoEntity() {
    }

    @Override
    public String toString() {
        return "PlayInfoEntity{" +
                "url='" + url + '\'' +
                ", signerName='" + signerName + '\'' +
                ", author_name='" + author_name + '\'' +
                ", extName='" + extName + '\'' +
                ", error='" + error + '\'' +
                ", audio_id=" + audio_id +
                ", signerId=" + signerId +
                ", errcode=" + errcode +
                ", back_url=" + Arrays.toString(back_url) +
                ", songName='" + songName + '\'' +
                ", mvhash='" + mvhash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }

    public int getSignerId() {
        return signerId;
    }

    public void setSignerId(int signerId) {
        this.signerId = signerId;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String[] getBack_url() {
        return back_url;
    }

    public void setBack_url(String[] back_url) {
        this.back_url = back_url;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getMvhash() {
        return mvhash;
    }

    public void setMvhash(String mvhash) {
        this.mvhash = mvhash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
