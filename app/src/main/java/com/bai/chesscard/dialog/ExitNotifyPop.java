package com.bai.chesscard.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.tencent.TIMGroupManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ExitNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;
    private String userId = "", tableId = "", houseId = "", num = "";

    public ExitNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.exit_notify_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        view.findViewById(R.id.img_cancle).setOnClickListener(this);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.audi_anim);
    }

    @Override
    public void showPop(View parent) {
        this.showAtLocation(parent, Gravity.LEFT, 0, 0);
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
                getAudiunce();
                if (popInterfacer != null)
                    popInterfacer.OnConfirm(flag, null);
                break;
            case R.id.btn_cancel:
                if (popInterfacer != null)
                    popInterfacer.OnCancle(flag);
                break;
        }
        dismiss();
    }

    private void getAudiunce() {
        TIMGroupManager.getInstance().quitGroup(tableId,null);
        Map<String, String> params = new HashMap<>();
        params.put("table_id", tableId);
        params.put("house_id", houseId);
        params.put("user_id", userId);
        params.put("num", num);
        PostTools.postData(CommonUntilities.MAIN_URL + "gameout", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
            }
        });
    }
}
