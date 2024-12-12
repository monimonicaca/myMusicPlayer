package com.example.mymusicplayerapplication.activities;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.fragments.RecommendMusicFragment;
import com.example.mymusicplayerapplication.fragments.UserInfoFragment;
import com.example.mymusicplayerapplication.utils.CheckPermissionUtil;
import com.example.mymusicplayerapplication.utils.ToastUtil;
import com.tencent.qqmusic.openapisdk.core.OpenApiSDK;
import com.tencent.qqmusic.openapisdk.core.openapi.OpenApiResponse;
import com.tencent.qqmusic.openapisdk.model.Folder;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
    private static String PERMISSION_READ_PHONE_STATE= Manifest.permission.READ_PHONE_STATE;

    private static String PERMISSION_ACCESS_NETWORK_STATE= Manifest.permission.ACCESS_NETWORK_STATE;
    private static String PERMISSION_INTERNET= Manifest.permission.INTERNET;
    /*private static String [] PERMISSION_ACCESS_NETWORK_STATE=new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private static String [] PERMISSION_ACCESS_NETWORK_STATE=new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private static String [] PERMISSION_ACCESS_NETWORK_STATE=new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private static String [] PERMISSION_ACCESS_NETWORK_STATE=new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
    };*/


    public static final int  READ_PHONE_STATE_CODE=1;
    public static final int ACCESS_NETWORK_STATE_CODE=2;
    public static final int INTERNET_CODE=3;
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
        CheckPermissionUtil.checkPermission(this,PERMISSION_READ_PHONE_STATE,READ_PHONE_STATE_CODE);
        CheckPermissionUtil.checkPermission(this,PERMISSION_INTERNET,INTERNET_CODE);
        CheckPermissionUtil.checkPermission(this,PERMISSION_ACCESS_NETWORK_STATE,ACCESS_NETWORK_STATE_CODE);
        /*OpenApiSDK.getOpenApi().fetchFolderDetail("123456", new Function1<OpenApiResponse<Folder>, Unit>() {
            @Override
            public Unit invoke(OpenApiResponse<Folder> folderOpenApiResponse) {
                if (folderOpenApiResponse.isSuccess()) {
                    Folder folder = folderOpenApiResponse.getData();
                    Log.e("TAG", "获取歌单详情: " + folder);
                } else {
                    Log.e("TAG", "获取歌单详情失败: " + folderOpenApiResponse.getErrorMsg());
                }
                return Unit.INSTANCE; // Kotlin 的 Unit 需要返回实例
            }

        });*/
        OpenApiSDK.getOpenApi().fetchFolderDetail("123456", response -> {
            if (response.isSuccess()) {
                Log.e("TAG", "获取歌单详情: " + response.getData());
            } else {
                Log.e("TAG", "获取歌单详情失败: " + response.getErrorMsg());
            }
            return Unit.INSTANCE;
        });



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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_PHONE_STATE_CODE:
                boolean check=CheckPermissionUtil.checkResultCode(grantResults);
                if (!check){
                    ToastUtil.showToast(1000,"打开READ_PHONE_STATE权限失败,即将跳转到设置界面",this);
                    jumpToSettings();
                }
                break;
            case ACCESS_NETWORK_STATE_CODE:
                boolean check2=CheckPermissionUtil.checkResultCode(grantResults);
                if (!check2){
                    ToastUtil.showToast(1000,"打开ACCESS_NETWORK_STATE权限失败,即将跳转到设置界面",this);
                    jumpToSettings();
                }
                break;
            case INTERNET_CODE:
                boolean check3=CheckPermissionUtil.checkResultCode(grantResults);
                if (!check3){
                    ToastUtil.showToast(1000,"打开INTERNET_CODE权限失败,即将跳转到设置界面",this);
                    jumpToSettings();
                }
                break;
        }

    }
    private void jumpToSettings(){
        Intent intent=new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package",getPackageName(),null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//开启新的任务栈
        startActivity(intent);
    }
}