package com.bai.chesscard;

import android.app.Application;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ChessCardApplication extends Application {
    private ChessCardApplication instance;
    public ChessCardApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
