package com.example.mymusicplayerapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.service.IRecommendService;
import com.example.mymusicplayerapplication.service.impl.RecommendService;
import com.example.mymusicplayerapplication.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendMusicFragment extends Fragment {

    private Map params;
    private IRecommendService iRecommendService;
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RecommendMusicFragment() {
        // Required empty public constructor
    }

    public static RecommendMusicFragment newInstance() {
        RecommendMusicFragment fragment = new RecommendMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initRequestParams();
        iRecommendService=RecommendService.getInstance(getContext());
        iRecommendService.getRecommendSongList(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend_music, container, false);
    }
    private void initRequestParams(){
        params=new HashMap();
        params.put("recommend_expire","0");
        params.put("sign","52186982747e1404d426fa3f2a1e8ee4");
        params.put("plat","0");
        params.put("uid","0");
        params.put("version","9108");
        params.put("page","1");
        params.put("area_code","1");
        params.put("appid","1005");
        params.put("mid","286974383886022203545511837994020015101");
        params.put("_t","1545746286");
    }

}