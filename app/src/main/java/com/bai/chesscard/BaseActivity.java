package com.bai.chesscard;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BaseActivity extends AppCompatActivity {
    public Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }

    public void cardClick(View v) {
        ChessCardApplication.getInstance().playBtnSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ChessCardApplication.getInstance().playBack();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        ChessCardApplication.getInstance().stopBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
