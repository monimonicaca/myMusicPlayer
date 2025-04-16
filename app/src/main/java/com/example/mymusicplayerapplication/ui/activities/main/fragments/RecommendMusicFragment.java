package com.example.mymusicplayerapplication.ui.activities.main.fragments;

import android.content.Context;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.database.AppDatabase;
import com.example.mymusicplayerapplication.helper.AppDbHelper;
import com.example.mymusicplayerapplication.helper.ExcutorsHelper;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.ui.activities.musicplayer.MusicPlayerActivity;
import com.example.mymusicplayerapplication.ui.activities.main.adapter.RecommendMusicItemAdapter;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.service.IRecommendService;
import com.example.mymusicplayerapplication.manager.service.impl.RecommendService;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;
import com.example.mymusicplayerapplication.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RecommendMusicFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private static final int RECOMMEND_MUSIC_WHAT=1;
    private static final int RECOMMEND_MORE_MUSIC_WHAT=2;
    /**
     * 请求的参数
     * */
    private Map<String,Object> params;
    /**
     * 请求第几页的数据
     * */
    private int page=1;
    /**
     * ListView的适配器
     * */
    private RecommendMusicItemAdapter recommendMusicItemAdapter;
    /**
     * 用于获取网络请求
     * */
    private IRecommendService iRecommendService;
    /**
     * ListView的数据
     * */
    private List<SongEntity> songList;
    /**
     * 用于管理当前正在播放的数据
     * */
    private PlayListManager playListManager;
    /**
     * 解决message
     * */
    private MyHandler myHandler;
    /**
     * 显示图片
     * */
    private ImageView recommend_iv;
    /**
     * ListView
     * */
    private ListView recommend_musics_lv;
    /**
     * 加载动画
     * */
    private LottieAnimationView animation_loading;
    /**
     * 是否触底加载
     * */
    private boolean isBottom=false;
    /**
     * 请求网络资源的线程
     * */
    //private RecommendMusicThread recommendMusicThread;
    /**
     * 操作数据库的Helper
     * */
    private AppDbHelper appDbHelper;
    private AppDatabase appDatabase;
    private ExcutorsHelper excutorsHelper;
    public RecommendMusicFragment() {
    }
    public static RecommendMusicFragment newInstance() {
        return new RecommendMusicFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //appDbHelper=AppDbHelper.getInstance(getContext());
        playListManager=PlayListManager.getInstance();
        excutorsHelper=ExcutorsHelper.getInstance();
        appDatabase=AppDatabase.getInstance(getActivity());
        initPlayList();
        initRequestParams();
        iRecommendService=RecommendService.getInstance(getContext());
        //recommendMusicThread=new RecommendMusicThread();
        myHandler=new MyHandler(Looper.getMainLooper());
        getMoreMusic();
        //recommendMusicThread.start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommend_music, container, false);
        recommend_musics_lv=view.findViewById(R.id.recommend_musics_lv);
        recommend_iv=view.findViewById(R.id.recommend_iv);
        animation_loading=view.findViewById(R.id.animation_loading);
        recommend_musics_lv.setOnItemClickListener(this);
        recommend_musics_lv.setOnScrollListener(this);
        animation_loading.setVisibility(View.VISIBLE);
        return view;
    }
    private void initRequestParams(){
        params=new HashMap<>();
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
    private void initPlayList(){
        //appDbHelper.openReadLink();
        //playListManager.setSongList(appDbHelper.queryAllSong());
        excutorsHelper.roomExecutorService.execute(()->{
            List<SongEntity> songs=appDatabase.SongDao().loadAll();
            playListManager.setSongList(songs);
        });

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addSong(position);
        Intent intent=new Intent(getContext(), MusicPlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("song",songList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        //Log.d("点击的是", songList.get(position).toString());
    }
    public void addSong(int position){
       //appDbHelper.openWriteLink();
       //boolean result=appDbHelper.insertSong(songList.get(position));
        Callable<Long> addSongTask=()->{
            long result=  appDatabase.SongDao().insertSongs(songList.get(position));
            return result;
        };
       Future<Long> resultFutrue= excutorsHelper.roomExecutorService.submit(addSongTask);
        long result= -1;
        try {
            result = resultFutrue.get();
        } catch (ExecutionException e) {
            ExceptionHandleUtil.logException(e);
        } catch (InterruptedException e) {
            ExceptionHandleUtil.logException(e);
        }
        if (result!=-1){
            playListManager.addSong(songList.get(position));
            playListManager.setIndex(playListManager.getSongList().size()-1);
            ToastUtil.showToast(500,getString(R.string.add_song_success),getActivity().getApplicationContext());
        }else {
            ToastUtil.showToast(500,getString(R.string.add_song_fail),getActivity().getApplicationContext());
        }
        //Log.d("playList", playListManager.getSongList().toString());
    }
    public void more(int position){
        Log.d("more",songList.get(position).toString());
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE ) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                isBottom=true;
               // recommendMusicThread=new RecommendMusicThread();
              //  recommendMusicThread.start();
                getMoreMusic();
            }
        }
    }
    private void getMoreMusic(){
        excutorsHelper.netExecutorService.execute(()->{
            songList=iRecommendService.getRecommendSongList(params);
            Message message=new Message();
            if(isBottom){
                message.what=RECOMMEND_MORE_MUSIC_WHAT;
            }else {
                message.what = RECOMMEND_MUSIC_WHAT;
            }
            myHandler.sendMessage(message);
        });

    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!excutorsHelper.netExecutorService.isShutdown()){
            excutorsHelper.netExecutorService.shutdown();
        }
        /*if (recommendMusicThread!=null&&recommendMusicThread.isAlive()){
            recommendMusicThread.interrupt();
            try {
                recommendMusicThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
        /*if (appDbHelper!=null){
            appDbHelper.closeLink();
            appDbHelper.close();
        }*/
        if (myHandler!=null){
            myHandler.removeCallbacksAndMessages(null);
        }
    }
    class RecommendMusicThread extends Thread{
        @Override
        public void run() {
            super.run();
            songList=iRecommendService.getRecommendSongList(params);
            Message message=new Message();
            if(isBottom){
                message.what=RECOMMEND_MORE_MUSIC_WHAT;
            }else {
                message.what = RECOMMEND_MUSIC_WHAT;
            }
            myHandler.sendMessage(message);
        }
    }
    class MyHandler extends Handler{
        public MyHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==RECOMMEND_MUSIC_WHAT){
                if (songList.isEmpty()){
                    ExceptionHandleUtil.showException(getContext(),"网络请求数据失败");
                }
                recommendMusicItemAdapter=new RecommendMusicItemAdapter(getContext(),songList);
                recommend_musics_lv.setAdapter(recommendMusicItemAdapter);
                recommendMusicItemAdapter.notifyDataSetChanged();
                recommendMusicItemAdapter.setOnAddSongListener(position -> addSong(position));
                recommendMusicItemAdapter.setMoreOperationListener(position -> {
                    more(position);
                    //Log.d("OnMoreOperationClick",songList.get(position).toString());
                });
                animation_loading.setVisibility(View.GONE);
               // Log.d("songList", JSON.toJSONString(songList));
            } else if (msg.what==RECOMMEND_MORE_MUSIC_WHAT) {
                recommendMusicItemAdapter.setSongList(songList);
                recommendMusicItemAdapter.notifyDataSetChanged();
                //Log.d("songlist count", songList.size()+"");
                isBottom=false;
            }
        }
    }

}