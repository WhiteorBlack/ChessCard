package com.bai.chesscard.activity;/**
 * Created by Administrator on 2016/11/8.
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.BaseRecyAdapter;
import com.bai.chesscard.adapter.TableAdapter;
import com.bai.chesscard.dialog.HelpPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.SettingPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author:${白曌勇} on 2016/11/8
 * TODO:
 */
public class TableList extends BaseActivity {
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.ll_notify_content)
    LinearLayout llNotifyContent;
    @BindView(R.id.recy_table)
    RecyclerView recyTable;
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

    private List tabList;
    private TableAdapter tabAdapter;
    private HelpPop helpPop;
    private SettingPop settingPop;
    private PersonalPop personalPop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_table_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            tabList.add(new ArrayList<>());
        }
        tabAdapter.notifyDataSetChanged();
    }

    private void initView() {
        tabList = new ArrayList();
        tabAdapter = new TableAdapter(tabList);
        recyTable.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
        recyTable.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new BaseRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    @OnClick({R.id.img_user_photo, R.id.txt_help, R.id.txt_setting})
    public void cardClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_photo:
                if (personalPop==null)
                    personalPop=new PersonalPop(context);
                personalPop.showPop(txtHelp);
                break;
            case R.id.txt_help:
                if (helpPop == null)
                    helpPop = new HelpPop(context);
                helpPop.showPop(recyTable);
                break;
            case R.id.txt_setting:
                if (settingPop==null)
                    settingPop=new SettingPop(context);
                settingPop.showPop(recyTable);
                break;
        }
    }
}
