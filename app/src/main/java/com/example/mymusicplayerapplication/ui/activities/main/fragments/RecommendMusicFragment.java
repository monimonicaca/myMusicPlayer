package com.example.mymusicplayerapplication.ui.activities.main.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.ui.activities.MusicPlayerActivity;
import com.example.mymusicplayerapplication.adapter.RecommendMusicItemAdapter;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.service.IRecommendService;
import com.example.mymusicplayerapplication.manager.service.impl.RecommendService;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendMusicFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM2 = "param2";
    private static final int RECOMMEND_MUSIC_WHAT=1;
    /**
     * @param params 请求的参数
     * @param page 请求第几页的数据
     * @param recommendMusicItemAdapter ListView的适配器
     * @param iRecommendService 用于获取网络请求
     * @param songList ListView的数据
     * @param playListManager 用于管理当前正在播放的数据
     * @param myHandler 解决message
     * @param recommend_musics_lv ListView
     * @param recommend_iv 显示图片
     */
    private Map params;
    private int page=1;
    private RecommendMusicItemAdapter recommendMusicItemAdapter;
    private IRecommendService iRecommendService;
    private List<SongEntity> songList;
    private PlayListManager playListManager;
    private MyHandler myHandler;
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
        playListManager=PlayListManager.getInstance();
        initRequestParams();
        iRecommendService=RecommendService.getInstance(getContext());
        RecommendMusicThread recommendMusicThread=new RecommendMusicThread();
        myHandler=new MyHandler();
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
        /*这里有可能需要将数据写入数据库*/
        playListManager.addSong(songList.get(position));
        Intent intent=new Intent(getContext(), MusicPlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("song",songList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        //Log.d("点击的是", songList.get(position).toString());
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
                if (songList.isEmpty()){
                    ExceptionHandleUtil.showException(getContext(),"网络请求数据失败");
                }
                recommendMusicItemAdapter=new RecommendMusicItemAdapter(getContext(),songList);
                recommend_musics_lv.setAdapter(recommendMusicItemAdapter);
               // Log.d("songList", JSON.toJSONString(songList));
            }
        }
    }

}