package com.example.mymusicplayerapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.entity.SongEntity;

import java.util.List;

public class RecommendMusicItemAdapter extends BaseAdapter {
    private Context context;
    private List<SongEntity> songList;

    public RecommendMusicItemAdapter(Context context, List<SongEntity> songList) {
        this.context = context;
        this.songList = songList;
    }

    public void setSongList(List<SongEntity> songList) {
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item_song,null);
            viewHolder.add_ib=convertView.findViewById(R.id.add_ib);
            viewHolder.more_ib=convertView.findViewById(R.id.more_ib);
            viewHolder.song_name_tv=convertView.findViewById(R.id.song_name_tv);
            viewHolder.song_tv=convertView.findViewById(R.id.song_tv);
            viewHolder.sq_tv=convertView.findViewById(R.id.sq_tv);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        SongEntity songEntity=songList.get(position);
        viewHolder.song_name_tv.setText(songEntity.getFilename().split("-")[1]);
        viewHolder.song_tv.setText(songEntity.getFilename());
        if (!songEntity.getSqhash().equals("")){
            viewHolder.sq_tv.setText("SQ");
        }else {
            viewHolder.sq_tv.setVisibility(View.GONE);
        }
        return convertView;
    }
    public final class ViewHolder{
        public TextView song_name_tv;
        public TextView sq_tv;
        public TextView song_tv;
        public ImageButton add_ib;
        public ImageButton more_ib;
    }
}
