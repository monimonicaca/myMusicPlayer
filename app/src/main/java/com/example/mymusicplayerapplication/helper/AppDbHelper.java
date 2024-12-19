package com.example.mymusicplayerapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mymusicplayerapplication.data.model.SongEntity;

import java.util.ArrayList;
import java.util.List;

public class AppDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="app.db";
    private static final int VERSION=2;
    private static final String SONG_TABLE_NAME="playlist";
    private static final String USER_TABLE_NAME="user";
    private static AppDbHelper appDbHelper=null;
    private static SQLiteDatabase wdb=null;
    private static SQLiteDatabase rdb=null;

    public AppDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    public static AppDbHelper getInstance(Context context){
        if (appDbHelper ==null)appDbHelper=new AppDbHelper(context);
        return appDbHelper;
    }
    public SQLiteDatabase openWriteLink(){
        if(wdb==null|| !wdb.isOpen()) wdb=appDbHelper.getWritableDatabase();
        return wdb;
    }
    public SQLiteDatabase openReadLink(){
        if(rdb==null|| !rdb.isOpen()) rdb=appDbHelper.getReadableDatabase();
        return rdb;
    }
    public  void closeLink(){
        if (wdb != null && wdb.isOpen()) {
            wdb.close();
            wdb = null;
        }
        if (rdb != null && rdb.isOpen()) {
            rdb.close();
            rdb = null;
        }
    }
    public boolean insertSong(SongEntity song){
        ContentValues values=new ContentValues();
        values.put("hash", song.getHash());
        values.put("sqhash", song.getSqhash());
        values.put("`320hash`", song.getTtzhash());
        values.put("filename",song.getFilename());
        values.put("mvhash", song.getMvhash());
        values.put("duration", song.getDuration());
        values.put("extname", song.getExtname());
        values.put("remark", song.getRemark());
        values.put("brief", song.getBrief());
        values.put("filesize", song.getFilesize());
        values.put("sqfilesize", song.getSqfilesize());
        values.put("`320filesize`", song.getTtzfilesize());
        values.put("m4afilesize", song.getM4afilesize());
        values.put("topic_url", song.getTopic_url());
        values.put("fail_process_sq", song.getFail_process_sq());
        values.put("fail_process", song.getFail_process());
        values.put("audio_id", song.getAudio_id());
        values.put("type", song.getType());
        long rowId=wdb.insert(SONG_TABLE_NAME,null,values);
        if (rowId>0)return true;
        return false;
    }
    public List<SongEntity> queryAllSong(){
        List<SongEntity> playlist=new ArrayList<>();
        String sql="select * from "+SONG_TABLE_NAME;
        Cursor cursor=rdb.rawQuery(sql,null);
        SongEntity song;
        while (cursor.moveToNext()){
            song=new SongEntity();
            song.setHash(cursor.getString(1));
            song.setSqhash(cursor.getString(2));
            song.setTtzhash(cursor.getString(3));
            song.setFilename(cursor.getString(4));
            song.setMvhash(cursor.getString(5));
            song.setDuration(cursor.getInt(6));
            song.setExtname(cursor.getString(7));
            song.setRemark(cursor.getString(8));
            song.setBrief(cursor.getString(9));
            song.setFilesize(cursor.getInt(10));
            song.setSqfilesize(cursor.getInt(11));
            song.setTtzfilesize(cursor.getInt(12));
            song.setM4afilesize(cursor.getInt(13));
            song.setTopic_url(cursor.getString(14));
            song.setFail_process_sq(cursor.getInt(15));
            song.setFail_process(cursor.getInt(16));
            song.setAudio_id(cursor.getInt(17));
            song.setType(cursor.getInt(18));
            playlist.add(song);
        }
        return playlist;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "
                +SONG_TABLE_NAME
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"hash VARCHAR UNIQUE NOT NULL,"
                +"sqhash VARCHAR,"
                + "`320hash` VARCHAR," +
                "filename VARCHAR," +
                "mvhash VARCHAR(255)," +
                "duration INTEGER," +
                "extname VARCHAR(50)," +
                "remark VARCHAR(255)," +
                "brief VARCHAR(255)," +
                "filesize INTEGER," +
                "sqfilesize INTEGER," +
                "`320filesize` INTEGER," +
                "m4afilesize INTEGER," +
                "topic_url VARCHAR(255)," +
                "fail_process_sq INTEGER," +
                "fail_process INTEGER," +
                "audio_id INTEGER," +
                "type INTEGER);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public static void dropTable(){
        String sql="DROP TABLE IF EXISTS "+SONG_TABLE_NAME;
        rdb.execSQL(sql);
    }
}
