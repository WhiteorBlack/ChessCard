package com.bai.chesscard.activity;/**
 * Created by Administrator on 2016/11/8.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.BaseRecyAdapter;
import com.bai.chesscard.adapter.TableAdapter;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_Table;
import com.bai.chesscard.dialog.HelpPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Bean_Table.Table> tabList;
    private TableAdapter tabAdapter;
    private HelpPop helpPop;
    private SettingPop settingPop;
    private PersonalPop personalPop;
    private ProgressDialog progressDialog;
    private String id;
    private int minPoint = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_table_list);
        ButterKnife.bind(this);
        initView();
        id = getIntent().getStringExtra("id");
        progressDialog = Tools.getDialog(context, "");
        progressDialog.setMessage("正在获取游戏数据...");
        progressDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTableData(id);
        Tools.debug("onstart--" + id);
    }


    private void getTableData(String id) {

        Map<String, String> params = new HashMap<>();
        params.put("houseid", id);
        params.put("user_id", AppPrefrence.getUserNo(context));
        PostTools.postData(CommonUntilities.MAIN_URL + "TableList", params, new PostCallBack() {
            @Override
            public void onAfter() {
                super.onAfter();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    Tools.toastMsgCenter(context, R.string.no_network);
                    return;
                }
                Bean_Table beanTable = new Gson().fromJson(response, Bean_Table.class);
                if (beanTable != null && beanTable.id > 0 && beanTable.result != null && beanTable.result.size() > 0) {
                    tabList.clear();
                    tabList.addAll(beanTable.result);
                    tabAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        ConstentNew.TABLE_ID="";
        ConstentNew.GROUP_ID="";
        minPoint = getIntent().getIntExtra("point", 0);
        tabList = new ArrayList();
        tabAdapter = new TableAdapter(tabList);
        recyTable.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
        recyTable.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new BaseRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, final int position) {
                ChessCardApplication.getInstance().playBtnSound();
                ConstentNew.GROUP_ID = tabList.get(position).groupname;
                ConstentNew.TABLE_ID = tabList.get(position).id;
                TIMGroupManager.getInstance().applyJoinGroup(tabList.get(position).groupname, "", new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Tools.debug("onError--" + s + "code--" + i);

                        if (i == 10013||i==10021)
                            startActivity(new Intent(context, GamingActivityNew.class).putExtra("roomId", tabList.get(position).house_id).
                                    putExtra("tableId", tabList.get(position).id).putExtra("point", minPoint));
                        else Tools.toastMsgCenter(context, "code:"+i+" "+s);
                    }

                    @Override
                    public void onSuccess() {
                        Tools.debug("successful ---" + ConstentNew.GROUP_ID);
                        startActivity(new Intent(context, GamingActivityNew.class).putExtra("roomId", tabList.get(position).house_id).
                                putExtra("tableId", tabList.get(position).id).putExtra("point", minPoint));
                    }
                });

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
                if (personalPop == null)
                    personalPop = new PersonalPop(context);
                personalPop.showPop(txtHelp);
                break;
            case R.id.txt_help:
                if (helpPop == null)
                    helpPop = new HelpPop(context);
                helpPop.showPop(recyTable);
                break;
            case R.id.txt_setting:
                if (settingPop == null)
                    settingPop = new SettingPop(context);
                settingPop.showPop(recyTable);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChessCardApplication.getInstance().playBack();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChessCardApplication.getInstance().stopBack();
    }
}
