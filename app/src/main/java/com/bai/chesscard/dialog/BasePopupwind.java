package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/9/6.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bai.chesscard.ChessCardApplication;
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
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.context = context;
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void setPopInterfacer(PopInterfacer l, int flag) {
        this.popInterfacer = l;
        this.flag = flag;
    }


    @Override
    public boolean isOutsideTouchable() {
        return super.isOutsideTouchable();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (popInterfacer != null)
            popInterfacer.OnDismiss(flag);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }


    @Override
    public void onClick(View v) {
        ChessCardApplication.getInstance().playBtnSound();
        switch (v.getId()) {
            case R.id.img_close:
                dismiss();
                break;
        }
    }

    public void showPop(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
}
