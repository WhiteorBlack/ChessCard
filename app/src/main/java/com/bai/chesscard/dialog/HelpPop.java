package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/11/8.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.Tools;

/**
 * author:${白曌勇} on 2016/11/8
 * TODO:
 */
public class HelpPop extends BasePopupwind {
    private View view;
    private LinearLayout llParent;

    public HelpPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.help_pop, null);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        llParent=(LinearLayout)view.findViewById(R.id.ll_parent);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) llParent.getLayoutParams();
        params.width= (int) (Tools.getScreenWide(context)*0.8);
        params.height=(int)(Tools.getScreenHeight(context)*0.8);
        llParent.setLayoutParams(params);
        this.setContentView(view);
    }
}
