package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_SiteTable;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class UpTableNotifyPop extends BasePopupwind {
    private View view;
    private EditText edtMoney;
    private int money;
    private TextView txtTitle;

    public UpTableNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.up_banker_pop, null);
        view.findViewById(R.id.btn_up_banker).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        edtMoney = (EditText) view.findViewById(R.id.edt_money);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        money = ConstentNew.LEFTPOINT;
        edtMoney.setText(money + "");
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
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
                if (Integer.parseInt(moneyString) < ConstentNew.LEFTPOINT) {
                    Tools.toastMsgCenter(context, "上桌金额必须大于" + ConstentNew.LEFTPOINT);
                    return;
                }
                money = Integer.parseInt(moneyString);
                upBanker();
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, null);
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_add:
                money += ConstentNew.BANKER_LIMIT_MONEY;
                edtMoney.setText(money + "");
                break;
        }
    }

    private int pos;

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    private void upBanker() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("seat", pos + "");
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
                    bundle.putInt("mone", money);
                    ConstentNew.IS_HAS_GAMER[pos-1] = true;
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = pos;
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
