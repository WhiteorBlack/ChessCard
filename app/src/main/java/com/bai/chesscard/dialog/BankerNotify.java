package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2017/1/14.
 */

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_SiteTable;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * author:${白曌勇} on 2017/1/14
 * TODO:
 */
public class BankerNotify extends BasePopupwind {
    private View view;
    private TextView txtNotify;

    public BankerNotify(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.banker_notify_pop, null);
        txtNotify = (TextView) view.findViewById(R.id.txt_notify);
        view.findViewById(R.id.btn_go_on).setOnClickListener(this);
        view.findViewById(R.id.btn_down_banker).setOnClickListener(this);
        view.findViewById(R.id.btn_reset).setOnClickListener(this);
        this.setContentView(view);
    }

    @Override
    public void onClick(View v) {
        int type = -1;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_go_on:
                type = 1;
                break;
            case R.id.btn_down_banker:
                type = 3;
                break;
            case R.id.btn_reset:
                type = 2;
                break;
        }
        bankerSelect(type);
    }

    private void bankerSelect(final int type) {
        Tools.debug("banker_notify---" + ConstentNew.TABLE_ID + "--ver--" + ConstentNew.GAMEROUND + "--type--" + type);
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("ver", ConstentNew.GAMEROUND + "");
//        params.put("ver", "30");
        params.put("type", type + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "UserThirdRound", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.id > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", type);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                }
                dismiss();

            }
        });
    }
}
