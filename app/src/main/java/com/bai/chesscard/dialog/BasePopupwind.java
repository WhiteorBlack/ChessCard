package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/9/6.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bai.chesscard.R;
import com.bai.chesscard.interfacer.PopInterfacer;


/**
 * author:${白曌勇} on 2016/9/6
 * TODO:
 */
public class BasePopupwind extends PopupWindow implements View.OnClickListener {

    public PopInterfacer popInterfacer;
    public int flag;
    public Context context;

    public BasePopupwind(Context context) {
        this.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        this.getBackground().setAlpha(120);
        this.context = context;
    }

    public void setPopInterfacer(PopInterfacer l, int flag) {
        this.popInterfacer = l;
        this.flag = flag;
    }

    @Override
    public boolean isOutsideTouchable() {
        return false;
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(false);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (popInterfacer != null)
            popInterfacer.OnDismiss(flag);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void showPop(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
}
