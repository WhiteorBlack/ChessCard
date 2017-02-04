package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_SiteTable;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;
import com.tencent.TIMGroupManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class UpBankerNotifyPop extends BasePopupwind {
    private View view;
    private TextView edtMoney;
    private TextView txtTitle;
    private int money;

    public UpBankerNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.up_banker_notify, null);
        view.findViewById(R.id.btn_up_banker).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        edtMoney = (TextView) view.findViewById(R.id.edt_money);
        money = ConstentNew.BANKER_LIMIT_MONEY * ConstentNew.BANKERCHARGECOUNT;
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        edtMoney.setText(money + "");
        view.findViewById(R.id.btn_add).setVisibility(View.INVISIBLE);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_up_banker:
                String moneyString = edtMoney.getText().toString();
                if (TextUtils.isEmpty(moneyString)) {
                    Tools.toastMsgCenter(context, "请输入金额");
                    return;
                }
                if (Integer.parseInt(moneyString) < ConstentNew.BANKER_LIMIT_MONEY * ConstentNew.BANKERCHARGECOUNT) {
                    Tools.toastMsgCenter(context, "坐庄金额必须大于" + ConstentNew.BANKER_LIMIT_MONEY * ConstentNew.BANKERCHARGECOUNT);
                    return;
                }
                money = Integer.parseInt(moneyString);
                if (money>AppPrefrence.getAmount(context)){
                    Tools.toastMsgCenter(context,"账户余额不足");
                    return;
                }
                upBanker();
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, null);
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_add:
                if (AppPrefrence.getAmount(context) < ConstentNew.BANKER_LIMIT_MONEY) {
                    Tools.toastMsgCenter(context, "账户余额不足");
                    return;
                }
                money += ConstentNew.BANKER_LIMIT_MONEY;
                edtMoney.setText(money + "");
                break;
        }
    }

    private void upBanker() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("seat", "1");
        params.put("lookmonery", money + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "UserSiteDown", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                Bean_SiteTable siteTable = new Gson().fromJson(response, Bean_SiteTable.class);
                Bundle bundle = new Bundle();
                if (siteTable.id > 0) {
                    bundle.putBoolean("result", true);
                    bundle.putInt("money", money);
                    AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - money);
                    ConstentNew.GAMER_TABLE_MONEY=money;
                    ConstentNew.IS_HAS_GAMER[0] = true;
                    ConstentNew.IS_BANKER = true;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = 1;
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                } else {
                    bundle.putBoolean("result", false);
                    Tools.toastMsgCenter(context, siteTable.msg);
                }
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, bundle);
            }
        });
    }

}
