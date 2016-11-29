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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.Tools;

/**
 * author:${白曌勇} on 2016/11/6
 * TODO:
 */
public class RegisterPop extends BasePopupwind  {

    EditText edtPhone;
    EditText edtPwd;
    private View view;
    private InputMethodManager inputMethodManager;

    public RegisterPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.register_pop, null);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtPwd = (EditText) view.findViewById(R.id.edt_pwd);
        view.findViewById(R.id.btn_register).setOnClickListener(this);

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
            case R.id.btn_register:
                String phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Tools.toastMsg(context, "请输入手机号码");
                    return;
                }
                bundle.putString("phone", phone);
                String man = edtPwd.getText().toString();
                if (TextUtils.isEmpty(man)) {
                    Tools.toastMsg(context, "请输入推荐人");
                    return;
                }
                bundle.putString("man", man);
                bundle.putInt("type", 1);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);
        dismiss();

    }

}
