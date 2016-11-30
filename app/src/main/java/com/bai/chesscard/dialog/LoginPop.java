package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/11/6.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_Login;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:${白曌勇} on 2016/11/6
 * TODO:
 */
public class LoginPop extends BasePopupwind {

    EditText edtPhone;
    EditText edtPwd;
    TextView txtForgetPwd;
    ImageView btnRegister;
    ImageView btnLogin;
    private LinearLayout llContent;
    private View parent;
    private View view;
    private InputMethodManager inputMethodManager;

    public LoginPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.login_pop, null);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtPwd = (EditText) view.findViewById(R.id.edt_pwd);
        view.findViewById(R.id.btn_login).setOnClickListener(this);
        view.findViewById(R.id.txt_forget_pwd).setOnClickListener(this);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
        llContent = (LinearLayout) view.findViewById(R.id.ll_login_content);

        edtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        this.setFocusable(true);
        this.setContentView(view);
    }


    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.txt_forget_pwd:
                bundle.putInt("type", 0);
                break;
            case R.id.btn_register:
                bundle.putInt("type", 1);
                break;
            case R.id.btn_login:
                String phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Tools.toastMsg(context, "请输入手机号码");
                    return;
                }
                if (!Tools.isMobileNum(phone)) {
                    Tools.toastMsg(context, "请输入正确的手机号码");
                    return;
                }
                bundle.putString("phone", phone);
                String pwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    Tools.toastMsg(context, "请输入密码");
                    return;
                }
                login(phone, pwd);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);

    }

    private void login(String phone, final String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", pwd);
        PostTools.postData(CommonUntilities.MAIN_URL + "login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                if (Tools.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                bundle.putBoolean("statue", login.status);
                if (login.status) {
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setUserPhone(context, login.data.mobile);
                    AppPrefrence.setUserPwd(context, pwd);
                    AppPrefrence.setToken(context, login.token);
                    AppPrefrence.setReferrer(context, login.data.referrer);
                    AppPrefrence.setAvatar(context, login.data.avatar);
                    AppPrefrence.setAmount(context, login.data.amount);
                    AppPrefrence.setUserNo(context,login.data.id);
                }
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);
                Tools.toastMsgCenter(context, login.msg);
            }
        });
    }

}
