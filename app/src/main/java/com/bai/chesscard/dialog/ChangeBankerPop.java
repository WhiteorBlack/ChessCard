package com.bai.chesscard.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bai.chesscard.R;

/**
 * Created by 41508 on 2017/1/20.
 */

public class ChangeBankerPop extends BasePopupwind {
    private View view;
    private TextView txtContent;

    public ChangeBankerPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.change_banker_pop, null);
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    public void setContent(String notify) {
        txtContent.setText(notify);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}
