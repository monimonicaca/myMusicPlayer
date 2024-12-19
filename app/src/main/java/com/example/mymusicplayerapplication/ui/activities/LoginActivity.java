package com.example.mymusicplayerapplication.ui.activities;

import static com.example.mymusicplayerapplication.MyApplication.ISLOGIN;
import static com.example.mymusicplayerapplication.MyApplication.getInstance;
import static com.example.mymusicplayerapplication.MyApplication.getLoginInfoSharedPreferences;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.ui.activities.main.MainActivity;
import com.example.mymusicplayerapplication.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private ImageView login_back_iv;
    private TextView login_account_tv;
    private Button login_btn;
    private LinearLayout item_login_account_layout;
    private EditText login_account_et;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        isLogin= ISLOGIN;
        initializeView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initializeView(){
        login_back_iv=findViewById(R.id.login_back_iv);
        login_account_tv=findViewById(R.id.login_account_tv);
        login_btn=findViewById(R.id.login_btn);
        login_account_et=findViewById(R.id.login_account_et);
        item_login_account_layout=findViewById(R.id.item_login_account_layout);
        if(isLogin)login_account_et.setText((CharSequence) getInstance().info.get("account"));
        login_back_iv.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        login_account_et.setOnFocusChangeListener(this);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.login_back_iv){
            finish();
        }else if (id==R.id.login_btn) {
            login_btn.setBackgroundColor(getResources().getColor(R.color.grey,null));
            if (login_account_et.getText().length()!=0){
                String account=login_account_et.getText().toString();
                SharedPreferences loginInfoSharedPreferences =getLoginInfoSharedPreferences();
                SharedPreferences.Editor editor=loginInfoSharedPreferences.edit();
                editor.putString("account",account);
                editor.putBoolean("isLogin",true);
                getInstance().info.put("isLogin",true);
                ISLOGIN=true;
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                ToastUtil.showToast(1000,"请输入账号",this);
            }
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId()==R.id.login_account_et){
            if (hasFocus){
                item_login_account_layout.setBackgroundResource(R.color.white);
            }else if (!hasFocus){
                item_login_account_layout.setBackgroundResource(R.drawable.shape_square_round_default);
            }
        }
    }
}