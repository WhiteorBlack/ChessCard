package com.bai.chesscard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.dialog.HelpPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Home extends BaseActivity {
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.ll_notify_content)
    LinearLayout llNotifyContent;
    @BindView(R.id.txt_pre_limit)
    TextView txtPreLimit;
    @BindView(R.id.fl_pre_room)
    FrameLayout flPreRoom;
    @BindView(R.id.txt_mid_limit)
    TextView txtMidLimit;
    @BindView(R.id.fl_mid_room)
    FrameLayout flMidRoom;
    @BindView(R.id.txt_hig_limit)
    TextView txtHigLimit;
    @BindView(R.id.fl_hig_room)
    FrameLayout flHigRoom;
    @BindView(R.id.txt_online_count)
    TextView txtOnlineCount;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.img_user_photo)
    ImageView imgUserPhoto;
    @BindView(R.id.txt_user_no)
    TextView txtUserNo;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_user_money)
    TextView txtUserMoney;
    @BindView(R.id.txt_help)
    TextView txtHelp;
    @BindView(R.id.txt_setting)
    TextView txtSetting;

    private HelpPop helpPop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_home);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.fl_pre_room, R.id.fl_mid_room, R.id.fl_hig_room, R.id.img_start, R.id.img_user_photo, R.id.txt_help, R.id.txt_setting})
    public void cardClick(View view) {
        switch (view.getId()) {
            case R.id.fl_pre_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "0"), 0);
                break;
            case R.id.fl_mid_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "1"), 1);
                break;
            case R.id.fl_hig_room:
                startActivityForResult(new Intent(context, TableList.class).putExtra("type", "2"), 2);
                break;
            case R.id.img_start:
                break;
            case R.id.img_user_photo:
                break;
            case R.id.txt_help:
                if (helpPop==null)
                    helpPop=new HelpPop(context);
                helpPop.showPop(txtHelp);
                break;
            case R.id.txt_setting:
                break;
        }
    }
}
