package com.example.mymusicplayerapplication.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.data.model.PlayInfoEntity;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.manager.service.IMusicPlayService;
import com.example.mymusicplayerapplication.manager.service.impl.MusicPlayService;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;
import com.example.mymusicplayerapplication.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PLAY_MUSIC_WHAT=1;
    private SongEntity playSong;
    private PlayInfoEntity playInfo;
    private PlayListManager playListManager;
    private ImageView close_music_view_iv;
    private ImageView music_being_iv;
    private TextView song_tv;
    private TextView singer_tv;
    private SeekBar seekBar;
    private TextView start_time_tv;
    private TextView last_time_tv;
    private ImageButton previous_music_ib;
    private ImageButton play_stop_ib;
    private ImageButton next_music_ib;
    private ImageView play_list_iv;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private PlayInfoThread playInfoThread;
    private PlayMusicHandler playMusicHandler;
    private IMusicPlayService iMusicPlayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);
        initView();
        initEvent();
        iMusicPlayService=new MusicPlayService(this);
        playListManager=PlayListManager.getInstance();
        playSong=getPlaySong();
        playInfoThread=new PlayInfoThread();
        playMusicHandler=new PlayMusicHandler(Looper.getMainLooper());
        playInfoThread.start();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initView(){
        close_music_view_iv=findViewById(R.id.close_music_view_iv);
        music_being_iv=findViewById(R.id.music_being_iv);
        song_tv=findViewById(R.id.song_tv);
        singer_tv=findViewById(R.id.singer_tv);
        seekBar=findViewById(R.id.seekBar);
        start_time_tv=findViewById(R.id.start_time_tv);
        last_time_tv=findViewById(R.id.last_time_tv);
        previous_music_ib=findViewById(R.id.previous_music_ib);
        play_stop_ib=findViewById(R.id.play_stop_ib);
        next_music_ib=findViewById(R.id.next_music_ib);
        play_list_iv=findViewById(R.id.play_list_iv);
    }
    private void initEvent(){
        close_music_view_iv.setOnClickListener(this);
        previous_music_ib.setOnClickListener(this);
        play_stop_ib.setOnClickListener(this);
    }
    private SongEntity getPlaySong(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        return  (SongEntity) bundle.get("song");
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==close_music_view_iv.getId()){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            finish();
        } else if (id==previous_music_ib.getId()) {

        }else if (id==play_stop_ib.getId()){
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                play_stop_ib.setImageResource(R.drawable.ic_play);
            }else{
                assert mediaPlayer != null;
                mediaPlayer.start();
                play_stop_ib.setImageResource(R.drawable.ic_stop);
            }
        }
    }
    private void play()  {
        if (!playInfo.getError().isEmpty()){
            play_stop_ib.setImageResource(R.drawable.ic_play);
            ToastUtil.showToast(1000,playInfo.getError(),MusicPlayerActivity.this);
        }else {
            String path=playInfo.getUrl();
            //Log.d("TAG", path);
            try {
                //更新播放器
                if(mediaPlayer.isPlaying()) {
                    play_stop_ib.setImageResource(R.drawable.ic_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                play_stop_ib.setImageResource(R.drawable.ic_stop);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setOnPreparedListener(mp -> mp.start());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                ExceptionHandleUtil.logException(e);
            }
        }
    }
    private class PlayInfoThread extends Thread{
        @Override
        public void run() {
            super.run();
            playInfo=iMusicPlayService.getPlayInfo(playSong.getHash());
            Message message=new Message();
            message.what=PLAY_MUSIC_WHAT;
            playMusicHandler.sendMessage(message);
        }
    }
    class PlayMusicHandler extends Handler{
        public PlayMusicHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==PLAY_MUSIC_WHAT){
                play();
            }
        }
    }
}