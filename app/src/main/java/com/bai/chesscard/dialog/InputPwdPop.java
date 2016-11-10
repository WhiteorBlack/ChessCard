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
public class InputPwdPop extends BasePopupwind implements View.OnLayoutChangeListener {

    EditText edtPhone;
    EditText edtPwd;
    private View view;
    private InputMethodManager inputMethodManager;

    public InputPwdPop(Context context) {
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

        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, new ResultReceiver(inputHandler));
                }
//                startAnimation(b);
            }
        });
        edtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        view.findViewById(R.id.ll_parent).addOnLayoutChangeListener(this);
        this.setFocusable(true);
        this.setContentView(view);
    }

    Handler inputHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Tools.debug("hhhhhhhh");
        }
    };

    private boolean isInputOpen() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive(edtPhone) || inputMethodManager.isActive(edtPwd);
    }

    /**
     * 摇一摇动画
     *
     * @param isBack
     */
    private void startAnimation(boolean isBack) {
        int type = TranslateAnimation.RELATIVE_TO_PARENT;
        float topFromYValue;
        float topToYValue;
        if (isBack) {
            topFromYValue = -0.4f;
            topToYValue = 0;
        } else {
            topFromYValue = 0;
            topToYValue = -0.4f;
        }
        TranslateAnimation topAnimation = new TranslateAnimation(type, 0, type,
                0, type, topFromYValue, type, topToYValue);
        topAnimation.setDuration(200);
        topAnimation.setFillAfter(true);
        if (isBack) {
            topAnimation
                    .setAnimationListener(new TranslateAnimation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // TODO Auto-generated method stub
                            edtPhone.setFocusable(true);
                        }
                    });
        }
    }


    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_register:
                String pwd = edtPhone.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    Tools.toastMsg(context, "请输入密码");
                    return;
                }
                String confirmPwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(confirmPwd)) {
                    Tools.toastMsg(context, "请输入确认密码");
                    return;
                }
                if (!TextUtils.equals(pwd, confirmPwd)) {
                    Tools.toastMsg(context, "两次输入密码不一致");
                    return;
                }
                bundle.putString("pwd", pwd);
                bundle.putInt("type", 1);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);
        dismiss();

    }

    private void login(String phone, String pwd) {
        dismiss();
    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        Tools.debug(i + "--" + i1 + "--" + i2);
    }
}
