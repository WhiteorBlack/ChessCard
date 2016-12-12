package com.bai.chesscard.dialog;

import android.content.Context;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2016/11/9.
 */

public class PersonalPop extends BasePopupwind {
    private View view;
    private LinearLayout llParent;
    private TextView txtName;
    private TextView txtNo, txtAccount,txtMoney;
    private FrameLayout flTop;
    private ImageView imgTitle,imgPhoto;

    public PersonalPop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.personal_pop, null);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        view.findViewById(R.id.img_edit_name).setOnClickListener(this);
        view.findViewById(R.id.img_change_account).setOnClickListener(this);
        view.findViewById(R.id.img_change_photo).setOnClickListener(this);
        txtName = (TextView) view.findViewById(R.id.txt_user_name);
        txtNo = (TextView) view.findViewById(R.id.txt_user_no);
        txtAccount = (TextView) view.findViewById(R.id.txt_user_account);
        imgPhoto=(ImageView)view.findViewById(R.id.img_head);
        txtMoney=(TextView)view.findViewById(R.id.txt_user_money);
        txtAccount.setText("账号: "+AppPrefrence.getUserPhone(context));
        txtName.setText("昵称: "+AppPrefrence.getUserName(context));
        txtNo.setText("编号: "+AppPrefrence.getUserNo(context));
        txtMoney.setText(AppPrefrence.getAmount(context)+"");

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
        imgTitle.setBackgroundResource(R.mipmap.title_personal);
        this.setContentView(view);
    }

    public void setPhoto(String path){
        Glide.with(context).load(path).error(R.mipmap.icon_default_head).into(imgPhoto);
    }

    public void setName(String name){
        txtName.setText(name);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_edit_name:
                bundle.putInt("type", 0);
                break;
            case R.id.img_change_account:
                //更改账号
                bundle.putInt("type", 1);
                break;
            case R.id.img_change_photo:
                //更改头像
                bundle.putInt("type", 2);
                break;
        }
        if (popInterfacer!=null)
            popInterfacer.OnConfirm(flag,bundle);
    }
}
