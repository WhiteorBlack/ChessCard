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
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * author:${白曌勇} on 2016/11/6
 * TODO:
 */
public class GetCodePop extends BasePopupwind {

    EditText edtPhone;
    Button btnGetCode;
    private LinearLayout llContent;
    private View view;
    private InputMethodManager inputMethodManager;
    private int type = 0; //0为注册,1为找回密码

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
        this.setFocusable(true);
        this.setContentView(view);
    }

    public void setType(int type) {
        this.type = type;
    }

    String phone;

    public void setPhone(String phone) {
        this.phone = phone;
        sendCode(phone);
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("putType", type);
        switch (view.getId()) {
            case R.id.btn_get_code:
                sendCode(phone);
                countTime();
                break;
            case R.id.btn_register:
                String phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Tools.toastMsgCenter(context, "请输入验证码");
                    return;
                }
                checkCode(this.phone, phone);
                break;
            case R.id.txt_notify:
                bundle.putInt("type", 1);
                break;
        }
        if (popInterfacer != null)
            popInterfacer.OnConfirm(flag, bundle);

    }

    private void checkCode(final String phone, final String phone1) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("Type", (type == 0) ? "reg" : "findpwd");
        params.put("code", phone1);
        params.put("action", "getcode");
        PostTools.postData(CommonUntilities.MAIN_URL + "verifycode", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    bundle.putString("code", phone1);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                }else  Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
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
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", (type == 0) ? "reg" : "findpwd");
        params.put("action", "getcode");
        PostTools.postData(CommonUntilities.MAIN_URL + "getcode", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);

            }
        });
    }


}
