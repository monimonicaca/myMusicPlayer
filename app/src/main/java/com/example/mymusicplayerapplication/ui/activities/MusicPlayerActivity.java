package com.example.mymusicplayerapplication.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.service.impl.MusicPlayService;

import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity {
    private SongEntity playSong;
    private List<SongEntity> play_list_song;
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
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);
        playSong=getPlaySong();
        new PlayInfoThread().start();
        initView();
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
    private SongEntity getPlaySong(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        return  (SongEntity) bundle.get("song");
    }

    private class PlayInfoThread extends Thread{
        @Override
        public void run() {
            super.run();
            new MusicPlayService(MusicPlayerActivity.this).getMusicUrl(playSong.getHash());
        }
    }
}