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
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

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
            view = LayoutInflater.from(context).inflate(R.layout.login_pop_new, null);
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
                dismiss();
                break;
            case R.id.btn_login:
                String phone = edtPhone.getText().toString();

                if (TextUtils.isEmpty(phone)) {
                    Tools.toastMsg(context, "请输入用户名");
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

    private void login(final String phone, final String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
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
                final Bean_Login login = new Gson().fromJson(response, Bean_Login.class);
                bundle.putInt("statue", login.id);
                if (login.id>0) {
                    CommonUntilities.TOKEN=login.result.token;
                    TIMUser user = new TIMUser();
                    user.setAccountType(CommonUntilities.ACCOUNTTYPE);
                    user.setAppIdAt3rd(CommonUntilities.SDKAPPID + "");
                    user.setIdentifier(login.result.id);
                    TIMManager.getInstance().login(CommonUntilities.SDKAPPID, user, login.result.sign, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Tools.debug("IM error--"+s);
                        }

                        @Override
                        public void onSuccess() {
                            TIMFriendshipManager.getInstance().setNickName(TextUtils.isEmpty(login.result.real_name) ? login.result.id : login.result.real_name, null);
                            if (!TextUtils.isEmpty(login.result.user_logo))
                                TIMFriendshipManager.getInstance().setFaceUrl(login.result.user_logo, null);
                        }
                    });
                    AppPrefrence.setIsLogin(context, true);
                    AppPrefrence.setUserPhone(context,phone);
                    AppPrefrence.setUserPwd(context, pwd);
                    AppPrefrence.setToken(context, login.result.token);
                    AppPrefrence.setReferrer(context, login.result.referrer);
                    AppPrefrence.setAvatar(context, login.result.user_logo);
                    AppPrefrence.setAmount(context, login.result.amount);
                    AppPrefrence.setUserNo(context,login.result.id);
                }else Tools.toastMsgCenter(context, login.msg);
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);

            }
        });
    }

}
