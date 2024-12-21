package com.example.mymusicplayerapplication.ui.activities.main.fragments;

import static com.example.mymusicplayerapplication.MyApplication.ISLOGIN;
import static com.example.mymusicplayerapplication.MyApplication.getInstance;

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
import android.widget.TextView;


import com.example.mymusicplayerapplication.R;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.manager.PlayListManager;
import com.example.mymusicplayerapplication.manager.PreferenceAnalyze;
import com.example.mymusicplayerapplication.utils.ExceptionHandleUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfoFragment extends Fragment {
    private static final int RESPONSE_SUCCESS_WHAT=1;
    private static final int RESPONSE_ERROR_WHAT=2;
    private TextView account_tv;
    private TextView song_report_tv;
    private Responsethread responsethread;
    private ResponseHandler responseHandler;

    public UserInfoFragment() {
    }
    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseHandler=new ResponseHandler(Looper.getMainLooper());
        responsethread= new Responsethread();
        responsethread.start();
       //Log.d("mainthread", Thread.currentThread().getName());
        //Log.d("responsethread", responsethread.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_info, container, false);
        account_tv=view.findViewById(R.id.account_tv);
        song_report_tv=view.findViewById(R.id.song_report_tv);
        if (ISLOGIN){
            account_tv.setText(getInstance().info.get("account").toString());
        }
        return view;
    }
    class Responsethread extends Thread{
        @Override
        public void run() {
            super.run();
            List<String> songNames=new ArrayList<>();
            for (SongEntity s: PlayListManager.getInstance().getSongList()) {
                songNames.add(s.getFilename());
            }
            String response=PreferenceAnalyze.getAnalyzeResult(songNames);
            try {
                JSONObject responseJsonObject=new JSONObject(response);
                if (responseJsonObject.has("error")){
                    sendErrorMessage(responseJsonObject.toString());
                }else {
                   sendSuccessMessage(responseJsonObject.toString());
                }
            } catch (JSONException e) {
                ExceptionHandleUtil.logException(e);
            }
        }
        public void sendSuccessMessage(String response){
            Message message=new Message();
            message.what=RESPONSE_SUCCESS_WHAT;
            Bundle bundle=new Bundle();
            bundle.putString("response", response);
            message.setData(bundle);
            responseHandler.handleMessage(message);
        }
        public void sendErrorMessage(String response){
            Message message=new Message();
            message.what=RESPONSE_ERROR_WHAT;
            Bundle bundle=new Bundle();
            bundle.putString("response", response);
            message.setData(bundle);
            responseHandler.handleMessage(message);
        }
    }
    class ResponseHandler extends Handler{
        public ResponseHandler(@NonNull Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
           if (msg.what==RESPONSE_SUCCESS_WHAT){
               handleSuccessMessage(msg);
           }else if (msg.what==RESPONSE_ERROR_WHAT){
               Log.d("error", msg.getData().getString("response"));
           }
        }
        public void handleSuccessMessage(Message msg){
            JSONObject response= null;
            try {
                response = new JSONObject(msg.getData().getString("response"));
                String  content=response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                String data=content.replaceAll("```json\\n|\\n```", "").trim();
                String result=new JSONObject(data).getJSONObject("data").getString("result");
                //Log.d("ResponseHandlerThread", Thread.currentThread().getName());
                //Log.d("Looper.getMainLooper()", Looper.getMainLooper().toString());
                post(() -> song_report_tv.setText(result));
                Log.d("success", result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}