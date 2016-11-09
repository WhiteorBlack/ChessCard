package com.bai.chesscard.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.Tools;

/**
 * Created by Administrator on 2016/11/9.
 */

public class PersonalPop extends BasePopupwind {
    private View view;
    private LinearLayout llParent;
    private TextView txtName;
    private TextView txtNo, txtAccount;
    private FrameLayout flTop;
    private ImageView imgTitle;

    public PersonalPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.personal_pop, null);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        view.findViewById(R.id.img_edit_name).setOnClickListener(this);
        txtName = (TextView) view.findViewById(R.id.txt_user_name);
        txtName.setText(AppPrefrence.getUserName(context));
        txtNo = (TextView) view.findViewById(R.id.txt_user_no);
        txtNo.setText(AppPrefrence.getUserNo(context));
        txtAccount = (TextView) view.findViewById(R.id.txt_user_account);
        txtAccount.setText(AppPrefrence.getUserPhone(context));
        llParent = (LinearLayout) view.findViewById(R.id.ll_parent);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llParent.getLayoutParams();
        params.width = (int) (Tools.getScreenWide(context) * 0.6);
        params.height = (int) (Tools.getScreenHeight(context) * 0.7);
        llParent.setLayoutParams(params);

        flTop = (FrameLayout) view.findViewById(R.id.fl_top);
        LinearLayout.LayoutParams paramsTop = (LinearLayout.LayoutParams) flTop.getLayoutParams();
        paramsTop.height = (int) (Tools.getScreenHeight(context) * 0.15);
        flTop.setLayoutParams(paramsTop);
        imgTitle = (ImageView) view.findViewById(R.id.img_title);
        FrameLayout.LayoutParams titleParams = (FrameLayout.LayoutParams) imgTitle.getLayoutParams();
        titleParams.gravity = Gravity.CENTER;
        titleParams.topMargin = Tools.dip2px(context, 12);
        imgTitle.setLayoutParams(titleParams);
        this.setContentView(view);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_edit_name:

                break;
        }
    }
}
