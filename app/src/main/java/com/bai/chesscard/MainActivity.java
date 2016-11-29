package com.bai.chesscard;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.chesscard.activity.Home;
import com.bai.chesscard.dialog.GetCodePop;
import com.bai.chesscard.dialog.InputPwdPop;
import com.bai.chesscard.dialog.LoginPop;
import com.bai.chesscard.dialog.RegisterPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.utils.AppPrefrence;

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
    @BindView(R.id.txt_loading)
    TextView txtLoading;
    private LoginPop loginPop;
    private RegisterPop registerPop;
    private InputPwdPop inputPwdPop;
    private GetCodePop getCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        if (AppPrefrence.getIsLogin(context)) {
            countDown();
            txtLoading.setVisibility(View.VISIBLE);
            imgLoading.setVisibility(View.GONE);
        } else {
            txtLoading.setVisibility(View.GONE);
            imgLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cardClick(View v) {
        super.cardClick(v);
        if (loginPop == null)
            loginPop = new LoginPop(context);
        loginPop.showPop(imgChessLeft);
        loginPop.setPopInterfacer(MainActivity.this, 0);
    }

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (AppPrefrence.getIsLogin(context)) {
//                if (loginPop == null)
//                    loginPop = new LoginPop(context);
//                loginPop.showPop(imgChessLeft);
//                loginPop.setPopInterfacer(MainActivity.this, 0);
//            } else countDown();
//        }
//    };

    private void countDown() {
        new CountDownTimer(2000, 2000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(context, Home.class));
                finish();
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

    String phone, man, pwd;

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
                    if (registerPop == null)
                        registerPop = new RegisterPop(context);
                    registerPop.showPop(txtLoading);
                    registerPop.setPopInterfacer(this, 1);
                }
                if (bundle.getInt("type") == 2) {
                    //登录成功
                    AppPrefrence.setIsLogin(context, true);
                }
                break;
            case 1:
                phone = bundle.getString("phone");
                man = bundle.getString("man");
                if (getCode == null)
                    getCode = new GetCodePop(context);
                getCode.setPhone(phone);
                getCode.showPop(txtLoading);
                getCode.setPopInterfacer(this, 2);
                break;
            case 2:
                //填写密码
                if (bundle.getInt("type") == 1) {
                    //用户协议

                    return;
                }
                if (inputPwdPop == null)
                    inputPwdPop = new InputPwdPop(context);
                inputPwdPop.showPop(txtLoading);
                inputPwdPop.setPopInterfacer(this, 3);
                break;
            case 3:
                //注册
                pwd = bundle.getString("pwd");
                register();
                break;
        }
    }

    private void register() {

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
