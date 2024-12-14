package com.example.mymusicplayerapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.entity.SongEntity;
import com.example.mymusicplayerapplication.service.impl.MusicPlayService;

public class MusicPlayerActivity extends AppCompatActivity {
    private SongEntity playSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);
        playSong=getPlaySong();
        new PlayInfoThread().start();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private SongEntity getPlaySong(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        return  (SongEntity) bundle.get("song");
    }
    class PlayInfoThread extends Thread{
        @Override
        public void run() {
            super.run();
            new MusicPlayService(MusicPlayerActivity.this).getMusicUrl(playSong.getHash());
        }
    }
}