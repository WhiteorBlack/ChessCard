package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.dialog.BasePopupwind;

/**
 * Created by Administrator on 2016/11/9.
 */

public class DiscontectNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;
    private boolean isContect = false;

    public DiscontectNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.exit_notify_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    @Override
    public void showPop(View parent) {
        startCount();
    }

    public void setIsContect(boolean isContect){
        this.isContect=isContect;
    }

    private void startCount() {
        new CountDownTimer(10 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtContent.setText("您已断开连接\n" + millisUntilFinished / 1000 + "秒后将退出游戏");
            }

            @Override
            public void onFinish() {
                if (isContect)
                    return;
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
                break;
        }
        dismiss();
    }

}
