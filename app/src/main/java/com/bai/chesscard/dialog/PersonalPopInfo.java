package com.bai.chesscard.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.R;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2016/11/9.
 */

public class PersonalPopInfo extends BasePopupwind {
    private View view;
    private LinearLayout llParent;
    private TextView txtName,txtMoney;
    private TextView txtNo, txtAccount;
    private FrameLayout flTop;
    private ImageView imgTitle, imgPhoto;

    public PersonalPopInfo(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.personal_pop, null);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        view.findViewById(R.id.img_edit_name).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.img_change_account).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.img_change_photo).setVisibility(View.INVISIBLE);
        txtName = (TextView) view.findViewById(R.id.txt_user_name);
        txtNo = (TextView) view.findViewById(R.id.txt_user_no);
        txtAccount = (TextView) view.findViewById(R.id.txt_user_account);
        imgPhoto = (ImageView) view.findViewById(R.id.img_head);
        txtMoney=(TextView)view.findViewById(R.id.txt_user_money);

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
        imgTitle.setBackgroundResource(R.mipmap.title_user_info);
        this.setContentView(view);
    }

    public void setPhoto(String path) {
        Glide.with(context).load(path).error(R.mipmap.icon_default_head).into(imgPhoto);
    }

    public void setName(String name) {
        txtName.setText(name);
    }

    public void setInfo(Bean_TableDetial.TableUser user) {
        try{
            txtAccount.setText("账号: " + (TextUtils.isEmpty(user.mobile)?"":user.mobile));
        }catch (NullPointerException e){
            txtAccount.setText("");
        }
        try{
            txtName.setText("昵称:" + user.nick_name);
        }catch (NullPointerException e){
            txtName.setText("昵称:"+user.real_name);
        }
        txtNo.setText("编号: " + user.id);
        txtMoney.setText(user.amount+"");
        Glide.with(context).load( user.user_logo).error(R.mipmap.icon_default_head).into(imgPhoto);
    }

}
