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
 * 系统提示三个玩儿家充值的窗口
 */
public class LackMoneyNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtTitle;
    private TextView txtContent;
    private EditText edtMoney;
    private int countTime = 10;
    private long money = 0;
    private boolean isCharge = false;

    public LackMoneyNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lack_money_pop, null);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_exit).setOnClickListener(this);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        edtMoney = (EditText) view.findViewById(R.id.edt_money);
        edtMoney.setText(ConstentNew.LEFTPOINT + "");
        view.findViewById(R.id.btn_add).setOnClickListener(this);
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
        new CountDownTimer(countTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isCharge)
                    cancel();
                txtContent.setText("桌面金币不足,请续金币\n" + millisUntilFinished / 1000 + "秒后未续金币将下桌");
            }

            @Override
            public void onFinish() {
                if (popInterfacer != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 5);
                    popInterfacer.OnConfirm(flag, bundle);
                }
//                dismiss();
//                if (!isCharge)
//                    downTable();
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_confirm:
                String moneyString = edtMoney.getText().toString();
                if (TextUtils.isEmpty(moneyString)) {
                    Tools.toastMsgCenter(context, "请输入金额");
                    return;
                }
                money = Long.parseLong(moneyString);
                if (money > AppPrefrence.getAmount(context)) {
                    Tools.toastMsgCenter(context, "账户余额不足");
                    return;
                }
                if (isCharge)
                    return;
                upBanker(Integer.parseInt(moneyString));
                isCharge = true;
                break;
            case R.id.img_exit:
                isCharge = true;
                downTable();
                break;
            case R.id.img_add:
                if (AppPrefrence.getAmount(context) < ConstentNew.LEFTPOINT) {
                    Tools.toastMsgCenter(context, "账户余额不足");
                    return;
                }
                AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - ConstentNew.LEFTPOINT);
                money += ConstentNew.LEFTPOINT;
                edtMoney.setText(money + "");
                break;
        }

    }

    private void upBanker(final int money) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("seat", ConstentNew.USERPOS + "");
        params.put("point", money + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "UserRenewal", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                Bean_SiteTable siteTable = new Gson().fromJson(response, Bean_SiteTable.class);
                if (siteTable.id > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    ConstentNew.GAMER_TABLE_MONEY += money;
                    AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - money);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                } else {
                    isCharge = false;
                    Tools.toastMsgCenter(context, siteTable.msg);
                }
            }
        });
    }

    private void downTable() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "UserSiteUp", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.id > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                } else Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }
}
