package com.bai.chesscard;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.chesscard.dialog.LoginPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements PopInterfacer {


    @BindView(R.id.img_chess_left)
    ImageView imgChessLeft;
    @BindView(R.id.img_dice_top)
    ImageView imgDiceTop;
    @BindView(R.id.img_name)
    ImageView imgName;
    @BindView(R.id.img_loading)
    ImageView imgLoading;
    private LoginPop loginPop;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPop();
        noLogin();
    }


    private void initPop() {


    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (loginPop == null)
                loginPop = new LoginPop(context);
            loginPop.showPop(imgChessLeft);
            loginPop.setPopInterfacer(MainActivity.this, 0);
        }
    };

    private void noLogin() {
        new CountDownTimer(100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    @Override
    public void OnDismiss(int flag) {
        switch (flag) {
            case 0:
                loginPop = null;
                break;
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 0:
                if (bundle == null) return;
                if (bundle.getInt("type") == 0) {
                    //忘记密码
                }
                if (bundle.getInt("type") == 1) {
                    //注册
                }
                if (bundle.getInt("type") == 2) {
                    //登录成功
                }
                break;
        }
    }

    @Override
    public void OnCancle(int flag) {
        switch (flag) {
            case 0:
                //登录失败
                break;
        }
    }
}
