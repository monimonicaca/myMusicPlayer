package com.example.mymusicplayerapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.PlayListManager;

public class PlayListItemAdapter extends RecyclerView.Adapter<PlayListItemAdapter.ViewHolder> {
    private PlayListManager playListManager;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public PlayListItemAdapter(PlayListManager playListManager, Context context) {
        this.playListManager = playListManager;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return playListManager.getSongList().size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.pw_item_playlist,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongEntity song=playListManager.getSong(position);
        holder.song_tv.setText(song.getFilename());
        if (playListManager.getIndex()==position){
            holder.song_tv.setTextColor(ContextCompat.getColor(context,R.color.color_Active));
        }
        int currentPosition=holder.getAbsoluteAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(currentPosition);
            }
        });
    }
    public interface OnItemClickListener{
        void OnItemClick(int index);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView song_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song_tv=(TextView) itemView.findViewById(R.id.song_tv);
        }
    }
}
