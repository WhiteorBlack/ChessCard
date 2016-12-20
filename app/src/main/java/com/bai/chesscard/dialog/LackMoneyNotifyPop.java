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

public class LackMoneyNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;
    private EditText edtMoney;
    private int countMoney = 0;
    private int countTime = 10;

    public LackMoneyNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lack_money_pop, null);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_add).setOnClickListener(this);
        view.findViewById(R.id.img_exit).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        edtMoney = (EditText) view.findViewById(R.id.edt_money);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    @Override
    public void showPop(View parent) {
        super.showPop(parent);
        startCount();
    }

    public void setCountTime(int time) {
        this.countTime = time;
    }

    private void startCount() {
        new CountDownTimer(countTime*1000, 1000) {

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
            case R.id.img_confirm:
                String money1 = edtMoney.getText().toString();
                if (TextUtils.isEmpty(money1)) {
                    dismiss();
                    return;
                } else countMoney = Integer.parseInt(money1.trim());
                if (countMoney > AppPrefrence.getAmount(context)) {
                    Tools.toastMsgCenter(context, "账户余额不足,请充值");
                    return;
                }
                AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - countMoney);
                Bundle bundle = new Bundle();
                bundle.putInt("money", countMoney);
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);
                dismiss();
                break;
            case R.id.img_add:
                String money = edtMoney.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    countMoney += 100;
                } else {
                    countMoney = Integer.parseInt(money.trim());
                    countMoney += 100;
                }
                edtMoney.setText(countMoney + "");
                break;
            case R.id.img_exit:
                if (popInterfacer != null)
                    popInterfacer.OnCancle(flag);
                dismiss();
                break;
        }

    }

}
