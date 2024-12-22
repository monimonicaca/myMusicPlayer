package com.example.mymusicplayerapplication.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.adapter.PlayListItemAdapter;
import com.example.mymusicplayerapplication.data.model.PlayInfoEntity;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.manager.service.IMusicPlayService;
import com.example.mymusicplayerapplication.manager.service.impl.MusicPlayService;
import com.example.mymusicplayerapplication.utils.DurationTransUtil;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;
import com.example.mymusicplayerapplication.utils.ToastUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int PLAY_MUSIC_WHAT=1;
    private static final int UI_UPDATE_WHAT=2;
    private SongEntity playSong;
    private PlayInfoEntity playInfo;
    private PlayListManager playListManager;
    private ImageView close_music_view_iv;
    private ImageView music_iv;
    private TextView song_tv;
    private TextView singer_tv;
    private SeekBar seekBar;
    private TextView runtime_tv;
    private TextView total_time_tv;
    private ImageButton previous_music_ib;
    private ImageButton play_stop_ib;
    private ImageButton next_music_ib;
    private ImageView play_list_iv;
    private PopupWindow playListPopupWindow;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private PlayInfoThread playInfoThread;
    private PlayMusicHandler playMusicHandler;
    private IMusicPlayService iMusicPlayService;
    private ExecutorService executorService;
    private PlayListItemAdapter playListItemAdapter;
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);
        initView();
        initEvent();
        iMusicPlayService = new MusicPlayService(this);
        playListManager = PlayListManager.getInstance();
        initPlayListPopupWindow();
        playSong = getPlaySong();
        Log.d("playSong", playSong.toString());
        song_tv.setText(playSong.getFilename().split("-")[1]);
        singer_tv.setText(playSong.getFilename().split("-")[0]);
        total_time_tv.setText(DurationTransUtil.formatTotalTime(playSong.getDuration()));
        playInfoThread = new PlayInfoThread();
        playMusicHandler = new PlayMusicHandler(Looper.getMainLooper());
        playInfoThread.start();
        executorService= Executors.newSingleThreadExecutor();
        startUiUpdateTask();
    }
    private void initView(){
        close_music_view_iv=findViewById(R.id.close_music_view_iv);
        music_iv=findViewById(R.id.music_iv);
        song_tv=findViewById(R.id.song_tv);
        singer_tv=findViewById(R.id.singer_tv);
        seekBar=findViewById(R.id.seekBar);
        runtime_tv=findViewById(R.id.runtime_tv);
        total_time_tv=findViewById(R.id.total_time_tv);
        previous_music_ib=findViewById(R.id.previous_music_ib);
        next_music_ib=findViewById(R.id.next_music_ib);
        play_stop_ib=findViewById(R.id.play_stop_ib);
        play_list_iv=findViewById(R.id.play_list_iv);
        seekBar.setMax(100);
    }
    private void initEvent(){
        close_music_view_iv.setOnClickListener(this);
        previous_music_ib.setOnClickListener(this);
        play_stop_ib.setOnClickListener(this);
        next_music_ib.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        play_list_iv.setOnClickListener(this);
    }
    private SongEntity getPlaySong(){
        //Log.d("获取歌曲信息", "getPlaySong: ");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        //Log.d("待播放歌曲", bundle.get("song").toString());
        return  (SongEntity) bundle.get("song");
    }
    private void startUiUpdateTask() {
       // Log.d("startUiUpdateTask", "startUiUpdateTask: ");
        executorService.execute(() -> {
            while (flag) {
                // 循环更新进度
                while (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                    try {
                        final int currentPosition = mediaPlayer.getCurrentPosition();
                        final int duration = mediaPlayer.getDuration();
                        // 计算播放进度的百分比
                        int percent = Math.round((float) currentPosition / duration * 100);
                        // 计算显示的时间
                        String runtime = DurationTransUtil.formatRemainingTime(currentPosition);
                        //Log.d("percent", percent + "");
                        //Log.d("runtime", runtime);
                        Message updateUIMessage = new Message();
                        updateUIMessage.what = UI_UPDATE_WHAT;
                        Bundle uiBundle = new Bundle();
                        uiBundle.putString("runtime", runtime);
                        uiBundle.putInt("currentPercent", percent);
                        updateUIMessage.setData(uiBundle);
                        playMusicHandler.sendMessage(updateUIMessage);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        ExceptionHandleUtil.logException(e);
                    }
                }
            }
        });
    }
    private void playNewSong(int index){
        playSong=playListManager.getSong(index);
        playListManager.setIndex(index);
        if(playInfoThread.isAlive())playInfoThread.interrupt();
        playInfoThread=new PlayInfoThread();
        playInfoThread.start();
    }
    private void playNextSong(){
        int index=playListManager.getIndex(playSong)+1<playListManager.getSongList().size()-1?playListManager.getIndex(playSong)+1:playListManager.getSongList().size()-1;
        playNewSong(index);
    }
    private void playOrStopMusic(){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            play_stop_ib.setImageResource(R.drawable.ic_play);
        }else{
            assert mediaPlayer != null;
            mediaPlayer.start();
            play_stop_ib.setImageResource(R.drawable.ic_stop);
        }
    }
    private void playPreviousSong(){
        int index=playListManager.getIndex(playSong)-1>0?playListManager.getIndex(playSong)-1:0;
        playNewSong(index);
    }
    private void closView(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        flag=false;
        finish();
    }
    private void resetUI(){
        song_tv.setText(playSong.getFilename().split("-")[1]);
        singer_tv.setText(playSong.getFilename().split("-")[0]);
        runtime_tv.setText(DurationTransUtil.formatTotalTime(0));
        total_time_tv.setText(DurationTransUtil.formatTotalTime(playSong.getDuration()));
        seekBar.setProgress(0);
    }
    private void play()  {
        if (playInfo!=null) {
            if (!playInfo.getError().isEmpty()) {
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                    play_stop_ib.setImageResource(R.drawable.ic_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                ToastUtil.showToast(500, playInfo.getError(), MusicPlayerActivity.this);
            } else {
                String path = playInfo.getUrl();
                //Log.d("TAG", path);
                try {
                    //更新播放器
                    if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
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
    }
    private void showPlayListPopupWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_music_player, null);
        // 设置弹窗位置
        playListPopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        // 使得背景亮度变暗
    }
    private void initPlayListPopupWindow(){
        View pwView = LayoutInflater.from(this).inflate(R.layout.pw_playlist, null, false);
        // 实例化 PopupWindow
        playListPopupWindow=new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT,1500);
        // 初始化弹窗列表
        RecyclerView recyclerView = pwView.findViewById(R.id.playlist_rv);
        playListItemAdapter=new PlayListItemAdapter(playListManager,this);
        playListItemAdapter.setOnItemClickListener(index -> {
            int oldPosition=playListManager.getIndex();
            playNewSong(index);
            playListItemAdapter.notifyItemChanged(oldPosition);
            playListItemAdapter.notifyItemChanged(index);
        });
        recyclerView.setAdapter(playListItemAdapter);
        // 设置 popupWindow
        playListPopupWindow.setOutsideTouchable(true);
        playListPopupWindow.setTouchable(true);
        playListPopupWindow.setFocusable(true);
        playListPopupWindow.setAnimationStyle(R.style.pw_bottom_anim_style);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==close_music_view_iv.getId()){
            closView();
        } else if (id==previous_music_ib.getId()) {
            playPreviousSong();
        }else if (id==play_stop_ib.getId()){
            playOrStopMusic();
        }else if (id==next_music_ib.getId()){
            playNextSong();
        } else if (id==play_list_iv.getId()) {
            showPlayListPopupWindow();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // 关闭线程池
        if (executorService != null) {
            executorService.shutdown();
            executorService=null;
        }
        if (playInfoThread!=null&&playInfoThread.isAlive()){
            playInfoThread.interrupt();
            playInfoThread = null;
        }
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(mediaPlayer!=null&&fromUser){
            int duration=mediaPlayer.getDuration();
            mediaPlayer.seekTo(Math.round(duration * (progress / 100.0f)));
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
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
    private class PlayMusicHandler extends Handler{
        public PlayMusicHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==PLAY_MUSIC_WHAT){
                //Log.d("PLAY_MUSIC_WHAT", Thread.currentThread().getName());
                resetUI();
                play();
            } else if (msg.what==UI_UPDATE_WHAT) {
                    seekBar.setProgress(msg.getData().getInt("currentPercent"));
                    runtime_tv.setText(msg.getData().getString("runtime"));
                //Log.d("UI_UPDATE_WHAT", Thread.currentThread().getName());
            }
        }
    }
}