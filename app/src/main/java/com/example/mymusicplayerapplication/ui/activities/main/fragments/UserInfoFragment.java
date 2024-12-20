package com.example.mymusicplayerapplication.ui.activities.main.fragments;

import static com.example.mymusicplayerapplication.MyApplication.ISLOGIN;
import static com.example.mymusicplayerapplication.MyApplication.getInstance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymusicplayerapplication.R;

public class UserInfoFragment extends Fragment {
    private TextView account_tv;
    private TextView song_report_tv;
    public UserInfoFragment() {
    }


    public static UserInfoFragment newInstance() {
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return new UserInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_info, container, false);
        account_tv=view.findViewById(R.id.account_tv);
        song_report_tv=view.findViewById(R.id.song_report_tv);
        if (ISLOGIN){
            account_tv.setText(getInstance().info.get("account").toString());
        }
        return view;
    }
}