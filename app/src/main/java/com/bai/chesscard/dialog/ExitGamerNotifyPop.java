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

public class ExitGamerNotifyPop extends BasePopupwind {
    private View view;
    private TextView txtContent;

    public ExitGamerNotifyPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.gamer_exit_pop, null);
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
        this.showAtLocation(parent, Gravity.LEFT, 0, 0);
    }

    public void setNotify(int notify) {
        txtContent.setText(context.getResources().getString(notify));
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
