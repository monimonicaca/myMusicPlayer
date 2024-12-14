package com.example.mymusicplayerapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson2.JSON;
import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.activities.MusicPlayerActivity;
import com.example.mymusicplayerapplication.adapter.RecommendMusicItemAdapter;
import com.example.mymusicplayerapplication.entity.SongEntity;
import com.example.mymusicplayerapplication.service.IRecommendService;
import com.example.mymusicplayerapplication.service.impl.MusicPlayService;
import com.example.mymusicplayerapplication.service.impl.RecommendService;
import com.example.mymusicplayerapplication.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendMusicFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM2 = "param2";
    private static final int RECOMMEND_MUSIC_WHAT=1;
    private Map params;
    private int page=1;
    private RecommendMusicItemAdapter recommendMusicItemAdapter;
    private IRecommendService iRecommendService;
    private List<SongEntity> songList;
    private MyHandler myHandler=new MyHandler();
    private ImageView recommend_iv;

    private ListView recommend_musics_lv;



    public RecommendMusicFragment() {
    }

    public static RecommendMusicFragment newInstance() {
        RecommendMusicFragment fragment = new RecommendMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRequestParams();
        iRecommendService=RecommendService.getInstance(getContext());
        RecommendMusicThread recommendMusicThread=new RecommendMusicThread();
        recommendMusicThread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommend_music, container, false);
        recommend_musics_lv=view.findViewById(R.id.recommend_musics_lv);
        recommend_iv=view.findViewById(R.id.recommend_iv);
        recommend_musics_lv.setOnItemClickListener(this);
        return view;
    }
    private void initRequestParams(){
        params=new HashMap();
        params.put("recommend_expire","0");
        params.put("sign","52186982747e1404d426fa3f2a1e8ee4");
        params.put("plat","0");
        params.put("uid","0");
        params.put("version","9108");
        params.put("page",page);
        params.put("area_code","1");
        params.put("appid","1005");
        params.put("mid","286974383886022203545511837994020015101");
        params.put("_t","1545746286");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(), MusicPlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("song",songList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("点击的是", songList.get(position).toString());
    }

    class RecommendMusicThread extends Thread{
        @Override
        public void run() {
            super.run();
            songList=iRecommendService.getRecommendSongList(params);
            Message message=new Message();
            message.what=RECOMMEND_MUSIC_WHAT;
            myHandler.sendMessage(message);
        }
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==RECOMMEND_MUSIC_WHAT){
                recommendMusicItemAdapter=new RecommendMusicItemAdapter(getContext(),songList);
                recommend_musics_lv.setAdapter(recommendMusicItemAdapter);
               // Log.d("songList", JSON.toJSONString(songList));
            }
        }
    }



}