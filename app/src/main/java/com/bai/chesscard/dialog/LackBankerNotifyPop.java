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
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.Tools;

/**
 * Created by Administrator on 2016/11/9.
 */

public class LackBankerNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;
    private int countTime = 10;

    public LackBankerNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lack_banker_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_add).setOnClickListener(this);
        view.findViewById(R.id.img_exit).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    public void setContent(String content) {
        if (!TextUtils.isEmpty(content))
            txtContent.setText(content);
    }

    public void setContent(int content) {
        txtContent.setText(content);
    }


    @Override
    public void showPop(View parent) {
        startCount();
    }

    public void setCountTime(int time) {
        this.countTime = time;
    }

    private void startCount() {
        new CountDownTimer(countTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtContent.setText("桌面金币不足,请续金币\n" + millisUntilFinished / 1000 + "秒后未续金币将下桌");
            }

            @Override
            public void onFinish() {
                if (popInterfacer != null)
                    popInterfacer.OnCancle(flag);
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
            case R.id.img_exit:
                if (popInterfacer != null)
                    popInterfacer.OnCancle(flag);
                dismiss();
                break;
        }

    }

}
