package com.example.mymusicplayerapplication.activities;


import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.fragments.RecommendMusicFragment;
import com.example.mymusicplayerapplication.fragments.UserInfoFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView title_tv;
    private FrameLayout monitor_frame;
    private RadioGroup tab_rg;
    private RadioButton tab_recommend_rb;
    private RadioButton tab_user_rb;
    private FragmentManager fragmentManager;
    private RecommendMusicFragment recommendMusicFragment;
    private UserInfoFragment userInfoFragment;
    private static final int MONITOR_FRAME_ID=R.id.monitor_frame;
    private static final String RECOMMEND_FRAGMENT_TAG="Recommend";
    private static final String USER_FRAGMENT_TAG="user";
    private FragmentTransaction fTransaction;
    private void initView(){
        title_tv=findViewById(R.id.title_tv);
        monitor_frame= findViewById(R.id.monitor_frame);
        tab_rg=findViewById(R.id.tab_rg);
        tab_recommend_rb=findViewById(R.id.tab_recommend_rb);
        tab_user_rb=findViewById(R.id.tab_user_rb);
        recommendMusicFragment=RecommendMusicFragment.newInstance();
        userInfoFragment=UserInfoFragment.newInstance();
        /*
         * 设置页面启动的显示的fragment*/
        fragmentManager=getSupportFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        fTransaction.add(MONITOR_FRAME_ID,recommendMusicFragment,RECOMMEND_FRAGMENT_TAG);
        fTransaction.commit();
        /*
        * 给激活的tab设置颜色区别*/
        setTabActiveStatus(tab_recommend_rb.getId());
    }

    private void initEvent(){
        tab_rg.setOnCheckedChangeListener(this);
    }
    private void setTabActiveStatus(int id){
        /*tab_rg.check(id);*/
        if (id==tab_recommend_rb.getId()){
            title_tv.setText(R.string.recommend_fragment_active_title);
            tab_recommend_rb.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tab_recommend_action,0,0);
            tab_recommend_rb.setTextColor(getResources().getColor(R.color.color_Active,null));
        }else if(id==tab_user_rb.getId()){
            title_tv.setText(R.string.user_fragment_active_title);
            tab_user_rb.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tab_user_action,0,0);
            tab_user_rb.setTextColor(getResources().getColor(R.color.color_Active,null));
        }
    }
    private void setTabInactiveStatus(int id){
        if (id!=tab_recommend_rb.getId()){
            tab_recommend_rb.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tab_recommend,0,0);
            tab_recommend_rb.setTextColor(getResources().getColor(R.color.black,null));
        }
        if(id!=tab_user_rb.getId()){
            tab_user_rb.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tab_user,0,0);
            tab_user_rb.setTextColor(getResources().getColor(R.color.black,null));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d("选择的tab", checkedId==tab_recommend_rb.getId()?"推荐":"用户");
        setTabActiveStatus(checkedId);
        setTabInactiveStatus(checkedId);
        if (checkedId==tab_recommend_rb.getId()){
            if (fragmentManager.findFragmentByTag(RECOMMEND_FRAGMENT_TAG)==null){
                recommendMusicFragment=RecommendMusicFragment.newInstance();
                fTransaction=fragmentManager.beginTransaction();
                fTransaction.add(MONITOR_FRAME_ID,recommendMusicFragment,RECOMMEND_FRAGMENT_TAG);
                fTransaction.commit();
            }
            if (fragmentManager.findFragmentByTag(RECOMMEND_FRAGMENT_TAG)!=null)fragmentManager.beginTransaction().show(recommendMusicFragment).commit();
            if (fragmentManager.findFragmentByTag(USER_FRAGMENT_TAG)!=null)fragmentManager.beginTransaction().hide(userInfoFragment).commit();
        } else if (checkedId==tab_user_rb.getId()) {
            if (fragmentManager.findFragmentByTag(USER_FRAGMENT_TAG)==null){
                userInfoFragment=UserInfoFragment.newInstance();
                fTransaction=fragmentManager.beginTransaction();
                fTransaction.add(MONITOR_FRAME_ID,userInfoFragment,USER_FRAGMENT_TAG);
                fTransaction.commit();
            }
            if (fragmentManager.findFragmentByTag(USER_FRAGMENT_TAG)!=null)fragmentManager.beginTransaction().show(userInfoFragment).commit();
            if (fragmentManager.findFragmentByTag(RECOMMEND_FRAGMENT_TAG)!=null)fragmentManager.beginTransaction().hide(recommendMusicFragment).commit();
        }
    }
}