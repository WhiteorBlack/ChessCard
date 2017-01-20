package com.bai.chesscard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.activity.Home;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_Login;
import com.bai.chesscard.dialog.FindPwdPop;
import com.bai.chesscard.dialog.InputPwdPop;
import com.bai.chesscard.dialog.LoginPop;
import com.bai.chesscard.dialog.RegisterPop;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements PopInterfacer, Observer {


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
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.fl_parent)
    FrameLayout flParent;
    private LoginPop loginPop;
    private RegisterPop registerPop;
    private InputPwdPop inputPwdPop;
    private ProgressDialog progressDilaog;
    private FindPwdPop findPwdPop;
    private FrameLayout flTEst;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        if (AppPrefrence.getIsLogin(context)) {
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
        params.put("username", AppPrefrence.getUserPhone(context));
        params.put("password", AppPrefrence.getUserPwd(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (Tools.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                final Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                if (login.id ==1) {
                    CommonUntilities.TOKEN = login.result.token;
                    TIMUser user = new TIMUser();
                    user.setAccountType(CommonUntilities.ACCOUNTTYPE);
                    user.setAppIdAt3rd(CommonUntilities.SDKAPPID + "");
                    user.setIdentifier(login.result.id);
                    TIMManager.getInstance().login(CommonUntilities.SDKAPPID, user, login.result.sign, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Tools.debug("IM error--" + s);
                        }

                        @Override
                        public void onSuccess() {
                            TIMFriendshipManager.getInstance().setNickName(TextUtils.isEmpty(AppPrefrence.getUserPhone(context)) ? login.result.id : AppPrefrence.getUserPhone(context), null);
                            if (!TextUtils.isEmpty(login.result.user_logo))
                                TIMFriendshipManager.getInstance().setFaceUrl(login.result.user_logo, null);
                        }
                    });
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setToken(context, login.result.token);
                    AppPrefrence.setAvatar(context, login.result.user_logo);
                    AppPrefrence.setAmount(context, login.result.amount);
                    AppPrefrence.setUserNo(context, login.result.id);
                    AppPrefrence.setUserName(context, login.result.real_name);
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
                inputPwdPop = null;
                break;

        }
    }

    String phone = "", man = "", pwd = "";

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 0:
                if (bundle == null) return;
                if (bundle.getInt("type", -1) == 1) {
                    //注册
                    if (registerPop == null)
                        registerPop = new RegisterPop(context);
                    registerPop.showPop(txtLoading);
                    registerPop.setPopInterfacer(this, 1);
                }
                if (bundle.getInt("type", -1) == 2) {
                    //登录成功
                    if (bundle.getInt("statue") > 0) {
                        dismissPop();
                        startActivity(new Intent(context, Home.class));
                        finish();
                    }
                }
                break;
            case 1:
                if (bundle == null)
                    return;
//                if (inputPwdPop == null)
//                    inputPwdPop = new InputPwdPop(context);
//                inputPwdPop.showPop(txtLoading);
//                inputPwdPop.setPopInterfacer(this, 2);
                phone = bundle.getString("phone");
                man = bundle.getString("man");
                pwd = bundle.getString("pwd");
                register();
                break;
            case 2:
                //填写密码
                if (bundle == null)
                    return;
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
                if (baseBean.id > 0) {
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
        params.put("username", phone);
        params.put("password", pwd);
        params.put("recname", man);
        PostTools.postData(CommonUntilities.MAIN_URL + "UserRegister", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("register==" + response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, "检查网络后重试");
                    return;
                }
                Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                if (login.id > 0) {
                    login(phone, pwd);
                    AppPrefrence.setUserName(context, phone);
                    AppPrefrence.setUserPhone(context, phone);
                    AppPrefrence.setReferrer(context, man);
//                    AppPrefrence.setIsLogin(context, true);
//                    AppPrefrence.setUserPhone(context, login.data.mobile);
//                    AppPrefrence.setUserPwd(context, pwd);
//                    AppPrefrence.setToken(context, login.token);
//                    AppPrefrence.setReferrer(context, login.data.referrer);
//                    AppPrefrence.setAvatar(context, login.data.avatar);
//                    setAmount(context, login.data.amount);
//                    AppPrefrence.setUserNo(context, login.data.id);
//                    AppPrefrence.setUserName(context, login.data.nick_name);
//                    dismissPop();
                }
                Tools.toastMsgCenter(context, login.msg);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                progressDilaog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                Tools.debug("error--" + e.toString());
            }
        });
    }

    private void login(final String phone, final String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        params.put("deviceid", Tools.getDeviceId(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (Tools.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                final Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                if (login.id > 0) {
                    CommonUntilities.TOKEN = login.result.token;
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setUserPhone(context, phone);
                    AppPrefrence.setUserPwd(context, pwd);
                    AppPrefrence.setToken(context, login.result.token);
                    AppPrefrence.setAvatar(context, login.result.user_logo);
                    AppPrefrence.setAmount(context, login.result.amount);
                    AppPrefrence.setUserNo(context, login.result.id);
                    AppPrefrence.setUserName(context, login.result.real_name);
                    TIMUser user = new TIMUser();
                    user.setAccountType(CommonUntilities.ACCOUNTTYPE);
                    user.setAppIdAt3rd(CommonUntilities.SDKAPPID + "");
                    user.setIdentifier(login.result.id);

                    TIMManager.getInstance().login(CommonUntilities.SDKAPPID, user, login.result.sign, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Tools.debug("IM error--" + s);
                        }

                        @Override
                        public void onSuccess() {
                            TIMFriendshipManager.getInstance().setNickName(TextUtils.isEmpty(login.result.real_name) ? login.result.id : login.result.real_name, null);
                            if (!TextUtils.isEmpty(login.result.user_logo))
                                TIMFriendshipManager.getInstance().setFaceUrl(login.result.user_logo, null);
                            startActivity(new Intent(context, Home.class));
                            finish();
                        }
                    });

                    dismissPop();
                } else Tools.toastMsgCenter(context, login.msg);

            }
        });
    }

    private void dismissPop() {
        if (registerPop != null)
            registerPop.dismiss();
        if (loginPop != null)
            loginPop.dismiss();
        if (findPwdPop != null)
            findPwdPop = null;
        if (inputPwdPop != null)
            inputPwdPop.dismiss();
    }

    @Override
    public void OnCancle(int flag) {
        switch (flag) {
            case 0:
                //登录失败
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            for (int i = 0; i < msg.getElementCount(); i++) {
                TIMTextElem elem = (TIMTextElem) msg.getElement(i);
                Tools.debug("home receive---" + elem.getText().toString());
            }

        }
    }
}
