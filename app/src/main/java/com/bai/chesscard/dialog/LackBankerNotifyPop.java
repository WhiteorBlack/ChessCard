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
import com.bai.chesscard.utils.Constent;
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
public class LackBankerNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtTitle;
    private TextView txtContent;
    private TextView edtMoney;
    private int countTime = 10;
    private int money = 0;
    private boolean isCharge = false;

    public LackBankerNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lack_banker_pop, null);
            view.findViewById(R.id.img_confirm).setOnClickListener(this);
            view.findViewById(R.id.img_exit).setOnClickListener(this);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtContent = (TextView) view.findViewById(R.id.txt_content);

            edtMoney = (TextView) view.findViewById(R.id.edt_money);

            view.findViewById(R.id.btn_add).setOnClickListener(this);
            view.findViewById(R.id.btn_add).setVisibility(View.INVISIBLE);
        }

        this.setContentView(view);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }

    public void setTitle(String content) {
        if (!TextUtils.isEmpty(content))
            txtTitle.setText(content);
    }

    public void setTitle(int content) {
        txtTitle.setText(content);
    }

    @Override
    public void showPop(View parent) {
        super.showPop(parent);
        money = (int) (ConstentNew.BANKER_LIMIT_MONEY * Math.pow(2, (ConstentNew.BANKERCHARGECOUNT - 1)));
        edtMoney.setText(money + "");
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
                txtContent.setText("是否续庄\n" + millisUntilFinished / 1000 + "秒后未续系统将您下庄");
            }

            @Override
            public void onFinish() {
                if (popInterfacer != null) {
                    popInterfacer.OnCancle(flag);
                }

            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_confirm:
                if (money > AppPrefrence.getAmount(context)) {
                    Tools.toastMsgCenter(context, "账户余额不足");
                    return;
                }
                if (isCharge) {
                    return;
                }
                isCharge = true;
                try {
                    upBanker(money);
                } catch (Exception e) {
                    e.printStackTrace();
                    Tools.toastMsgCenter(context, "请求数据出错:" + e.toString());
                }

                break;
            case R.id.img_exit:
                isCharge = true;
                downTable();
                break;
        }

    }

    private void upBanker(final int money) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("seat", "1");
        params.put("point", money + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "UserRenewal", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean siteTable = null;
                try {
                    siteTable = new Gson().fromJson(response, BaseBean.class);
                } catch (Exception e) {
                    Tools.toastMsgCenter(context, "解析输错:" + e.toString());
                }

                if (siteTable != null && siteTable.id > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    ConstentNew.GAMER_TABLE_MONEY += money;
                    AppPrefrence.setAmount(context, (AppPrefrence.getAmount(context) - money) < 0 ? 0 : (AppPrefrence.getAmount(context) - money));
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
//                    dismiss();
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
        params.put("type", "3");
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
//                    dismiss();
                } else Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }
}
