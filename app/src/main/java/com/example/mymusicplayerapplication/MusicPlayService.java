package com.example.mymusicplayerapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicPlayService extends Service {
    public MusicPlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}