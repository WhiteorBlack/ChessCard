package com.bai.chesscard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.chesscard.activity.Home;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_Login;
import com.bai.chesscard.dialog.FindPwdPop;
import com.bai.chesscard.dialog.GetCodePop;
import com.bai.chesscard.dialog.InputPwdPop;
import com.bai.chesscard.dialog.LoginPop;
import com.bai.chesscard.dialog.RegisterPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
    private ProgressDialog progressDilaog;
    private FindPwdPop findPwdPop;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        if (AppPrefrence.getIsLogin(context)) {
//            countDown();
            txtLoading.setVisibility(View.VISIBLE);
            imgLoading.setVisibility(View.GONE);
        } else {
            txtLoading.setVisibility(View.GONE);
            imgLoading.setVisibility(View.VISIBLE);
        }
        progressDilaog = Tools.getDialog(context, "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AppPrefrence.getIsLogin(context)) {
            login();
        }
    }

    private void login() {
        progressDilaog.setMessage("登录中...");
        progressDilaog.show();
        Map<String, String> params = new HashMap<>();
        params.put("phone", AppPrefrence.getUserPhone(context));
        params.put("password", AppPrefrence.getUserPwd(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (Tools.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                if (login.status) {
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setUserPhone(context, login.data.mobile);
                    AppPrefrence.setToken(context, login.token);
                    AppPrefrence.setReferrer(context, login.data.referrer);
                    AppPrefrence.setAvatar(context, CommonUntilities.PIC_URL + login.data.avatar);
                    AppPrefrence.setAmount(context, login.data.amount);
                    AppPrefrence.setUserNo(context, login.data.id);
                    AppPrefrence.setUserName(context, login.data.nick_name);
                    startActivity(new Intent(context, Home.class));
                    finish();
                } else Tools.toastMsgCenter(context, login.msg);

            }

            @Override
            public void onAfter() {
                super.onAfter();
                progressDilaog.dismiss();
            }
        });
    }

    @Override
    public void cardClick(View v) {
        super.cardClick(v);
        if (loginPop == null)
            loginPop = new LoginPop(context);
        loginPop.showPop(imgChessLeft);
        loginPop.setPopInterfacer(MainActivity.this, 0);
    }

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
            case 1:
                registerPop = null;
                break;
            case 2:
                getCode = null;
                break;
        }
    }

    String phone = "", man = "", pwd = "", code = "";

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 0:
                if (bundle == null) return;
                if (bundle.getInt("type") == 0) {
                    //忘记密码
                    if (getCode == null)
                        getCode = new GetCodePop(context);
                    getCode.setPhone(AppPrefrence.getUserPhone(context));
                    getCode.showPop(txtLoading);
                    getCode.setPopInterfacer(this, 2);
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
                    if (bundle.getBoolean("statue")) {
                        dismissPop();
                        startActivity(new Intent(context, Home.class));
                        finish();
                    }
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
                if (bundle == null)
                    return;
                if (bundle.getInt("type") == 1) {
                    //用户协议

                    return;
                }
                if (bundle.getInt("type") == 2) {
                    code = bundle.getString("code");
                }
                if (TextUtils.isEmpty(code))
                    return;
                if (bundle.getInt("putType") == 0) {
                    if (inputPwdPop == null)
                        inputPwdPop = new InputPwdPop(context);
                    inputPwdPop.showPop(txtLoading);
                    inputPwdPop.setPopInterfacer(this, 3);
                } else {
                    if (findPwdPop == null)
                        findPwdPop = new FindPwdPop(context);
                    findPwdPop.showPop(txtLoading);
                    findPwdPop.setPopInterfacer(this, 4);
                }

                break;
            case 3:
                //注册
                pwd = bundle.getString("pwd");
                register();
                break;
            case 4:
                pwd = bundle.getString("pwd");
                findPwd();
                break;
        }
    }

    private void findPwd() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", AppPrefrence.getUserPhone(context));
        params.put("code", code);
        params.put("password", pwd);
        PostTools.postData(CommonUntilities.MAIN_URL, params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, "");
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    AppPrefrence.setUserPwd(context, pwd);
                    dismissPop();
                    if (loginPop == null)
                        loginPop = new LoginPop(context);
                    loginPop.showPop(imgChessLeft);
                    loginPop.setPopInterfacer(MainActivity.this, 0);
                }
                Tools.toastMsg(context, baseBean.msg);
            }
        });
    }

    private void register() {
        progressDilaog.setMessage("注册中...");
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        params.put("password", pwd);
        params.put("referrer", man);
        PostTools.postData(CommonUntilities.MAIN_URL + "register", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, "");
                    return;
                }
                Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                if (login.status) {
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setUserPhone(context, login.data.mobile);
                    AppPrefrence.setUserPwd(context, pwd);
                    AppPrefrence.setToken(context, login.token);
                    AppPrefrence.setReferrer(context, login.data.referrer);
                    AppPrefrence.setAvatar(context, login.data.avatar);
                    AppPrefrence.setAmount(context, login.data.amount);
                    AppPrefrence.setUserNo(context, login.data.id);
                    AppPrefrence.setUserName(context, login.data.nick_name);
                    dismissPop();
                }
                Tools.toastMsgCenter(context, login.msg);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                progressDilaog.dismiss();
            }
        });
    }

    private void dismissPop() {
        if (getCode != null)
            getCode.dismiss();
        if (inputPwdPop != null)
            inputPwdPop.dismiss();
        if (registerPop != null)
            registerPop.dismiss();
        if (loginPop != null)
            loginPop.dismiss();
        if (findPwdPop != null)
            findPwdPop = null;
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
