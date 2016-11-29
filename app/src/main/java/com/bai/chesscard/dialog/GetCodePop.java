package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/11/6.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * author:${白曌勇} on 2016/11/6
 * TODO:
 */
public class GetCodePop extends BasePopupwind implements View.OnLayoutChangeListener {

    EditText edtPhone;
    Button btnGetCode;
    private LinearLayout llContent;
    private View view;
    private InputMethodManager inputMethodManager;

    public GetCodePop(Context context) {
        super(context);
        initView();
        countTime();

    }

    private void initView() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.get_code_pop, null);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
        view.findViewById(R.id.txt_notify).setOnClickListener(this);
        llContent = (LinearLayout) view.findViewById(R.id.ll_login_content);
        btnGetCode = (Button) view.findViewById(R.id.btn_get_code);
        btnGetCode.setOnClickListener(this);
        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, new ResultReceiver(inputHandler));
                }
//                startAnimation(b);
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
        }
    };


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
        llContent.startAnimation(topAnimation);
    }

    String phone;

    public void setPhone(String phone) {
        this.phone = phone;
        sendCode(phone);
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_get_code:
                sendCode(phone);
                countTime();
                break;
            case R.id.btn_login:
                String phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Tools.toastMsg(context, "请输入验证码");
                    return;
                }
                commitCode(phone);
                break;
            case R.id.txt_notify:
                bundle.putInt("type", 1);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);

    }

    private void countTime() {
        btnGetCode.setEnabled(false);
        new CountDownTimer(1000 * 60, 1000) {

            @Override
            public void onTick(long l) {
                btnGetCode.setText(l / 1000 + "秒后获取");
            }

            @Override
            public void onFinish() {
                btnGetCode.setEnabled(true);
                btnGetCode.setText("重新获取");
            }
        }.start();
    }

    private void sendCode(String phone) {
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("type","reg");
        params.put("action","getcode");
        PostTools.postData(CommonUntilities.MAIN_URL+"action=getcode",params,new PostCallBack(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);

            }
        });
    }

    private void commitCode(String phone) {

    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        Tools.debug(i + "--" + i1 + "--" + i2);
    }
}
