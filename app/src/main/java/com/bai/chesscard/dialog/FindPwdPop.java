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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.Tools;

/**
 * author:${白曌勇} on 2016/11/6
 * TODO:
 */
public class FindPwdPop extends BasePopupwind  {

    EditText edtPhone;
    EditText edtPwd;
    private View view;
    private InputMethodManager inputMethodManager;

    public FindPwdPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.input_pwd_pop, null);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtPwd = (EditText) view.findViewById(R.id.edt_pwd);
        view.findViewById(R.id.btn_register).setOnClickListener(this);

        this.setFocusable(true);
        this.setContentView(view);
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_register:
                String pwd = edtPhone.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    Tools.toastMsgCenter(context, "请输入密码");
                    return;
                }
                String confirmPwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(confirmPwd)) {
                    Tools.toastMsgCenter(context, "请输入确认密码");
                    return;
                }
                if (!TextUtils.equals(pwd, confirmPwd)) {
                    Tools.toastMsgCenter(context, "两次输入密码不一致");
                    return;
                }
                bundle.putString("pwd", pwd);
                bundle.putInt("type", 1);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);

    }

}
