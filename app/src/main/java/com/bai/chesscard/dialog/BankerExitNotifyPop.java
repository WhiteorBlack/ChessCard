package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/9.
 */

public class BankerExitNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;
    private String userId = "", tableId = "", houseId = "", num = "";

    public BankerExitNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.banker_exit_notify_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_cancle).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }


    public void setNotify(String notify) {
        txtContent.setText(notify);
    }

    public void setIds(String tableId, String roomId, String userId, String num) {
        this.tableId = tableId;
        this.houseId = roomId;
        this.num = num;
        this.userId = userId;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_confirm:
                //直接退出
                getAudiunce();
                break;
            case R.id.img_cancle:
                //下庄
                downTable();
                break;
        }
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
                BaseBean baseBean=new Gson().fromJson(response,BaseBean.class);
                if (baseBean.id>0){
                    Bundle bundle=new Bundle();
                    bundle.putInt("type",1);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                }else Tools.toastMsgCenter(context,baseBean.msg);
            }
        });
    }

    private void getAudiunce() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", tableId);
        params.put("token", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "LevelTable", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean=new Gson().fromJson(response,BaseBean.class);
                if (baseBean.id>0){
                    Bundle bundle=new Bundle();
                    bundle.putInt("type",2);
                    if (popInterfacer != null)
                        popInterfacer.OnConfirm(flag, bundle);
                    dismiss();
                }else Tools.toastMsgCenter(context,baseBean.msg);
            }
        });
    }
}
