package com.bai.chesscard.dialog;/**
 * Created by Administrator on 2016/11/8.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.Tools;

/**
 * author:${白曌勇} on 2016/11/8
 * TODO:
 */
public class NotifyDetialPop extends BasePopupwind {
    private View view;
    private LinearLayout llParent;
    private TextView txtTitle, txtContent;

    public NotifyDetialPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.notify_detial, null);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        llParent = (LinearLayout) view.findViewById(R.id.ll_parent);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llParent.getLayoutParams();
        params.width = (int) (Tools.getScreenWide(context) * 0.8);
        params.height = (int) (Tools.getScreenHeight(context) * 0.8);
        llParent.setLayoutParams(params);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        this.setContentView(view);
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public void setContent(String content) {
        txtContent.setText(content);
    }
}
