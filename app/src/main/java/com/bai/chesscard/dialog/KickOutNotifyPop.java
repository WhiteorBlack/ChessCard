package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_SiteTable;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

/**
 * 系统提示庄家续庄的窗口
 */
public class KickOutNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;

    public KickOutNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.gamer_exit_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);

        view.findViewById(R.id.btn_add).setOnClickListener(this);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }


    @Override
    public void showPop(View parent) {
        super.showPop(parent);
        startCount();
    }

    private void startCount() {
        new CountDownTimer(5 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtContent.setText("账户余额不足\n" + millisUntilFinished / 1000 + "秒后退出房间");
            }

            @Override
            public void onFinish() {
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, null);
                dismiss();
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, null);
                dismiss();
                break;
        }
    }
}
