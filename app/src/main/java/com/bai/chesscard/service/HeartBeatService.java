package com.bai.chesscard.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.mina.MinaThread;

/**
 * Created by Administrator on 2016/11/25.
 */

public class HeartBeatService extends Service {
    private MinaThread minaThread;
    private GameDataListener gameDataListener;

    public void setGameDataListener(GameDataListener gameDataListener) {
        this.gameDataListener = gameDataListener;
        if (minaThread == null)
            minaThread = new MinaThread();
        minaThread.setGameDataListener(gameDataListener);
        minaThread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public HeartBeatService getService(){
            return  HeartBeatService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
