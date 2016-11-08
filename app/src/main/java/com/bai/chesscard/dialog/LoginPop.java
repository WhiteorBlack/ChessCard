package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/11/6.
 */

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.Tools;

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
    private View view;

    public LoginPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.login_pop, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.closeInput(edtPhone, context);
            }
        });
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtPwd = (EditText) view.findViewById(R.id.edt_pwd);
        view.findViewById(R.id.btn_login).setOnClickListener(this);
        view.findViewById(R.id.txt_forget_pwd).setOnClickListener(this);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
        edtPhone.setFocusable(true);
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
                bundle.putString("pwd", pwd);
                bundle.putInt("type", 2);
                login(phone, pwd);
                break;
        }

    }

    private void login(String phone, String pwd) {
        dismiss();
    }

}
