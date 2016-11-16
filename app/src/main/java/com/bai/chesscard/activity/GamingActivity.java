package com.bai.chesscard.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.presenter.GamePresenter;
import com.bai.chesscard.widget.StrokeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/11.
 */

public class GamingActivity extends BaseActivity implements GameOprateView {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_time)
    ImageView imgTime;
    @BindView(R.id.img_time_status)
    ImageView imgTimeStatus;
    @BindView(R.id.ll_time_content)
    LinearLayout llTimeContent;
    @BindView(R.id.img_top_left)
    ImageView imgTopLeft;
    @BindView(R.id.img_top_right)
    ImageView imgTopRight;
    @BindView(R.id.ll_banker_chess)
    LinearLayout llBankerChess;
    @BindView(R.id.recy_chess)
    RecyclerView recyChess;
    @BindView(R.id.img_bg_left)
    ImageView imgBgLeft;
    @BindView(R.id.img_chess_left_one)
    ImageView imgChessLeftOne;
    @BindView(R.id.img_chess_left_two)
    ImageView imgChessLeftTwo;
    @BindView(R.id.img_chess_left_count)
    ImageView imgChessLeftCount;
    @BindView(R.id.img_chess_left_multiple)
    ImageView imgChessLeftMultiple;
    @BindView(R.id.img_bg_mid)
    ImageView imgBgMid;
    @BindView(R.id.img_chess_mid_one)
    ImageView imgChessMidOne;
    @BindView(R.id.img_chess_mid_two)
    ImageView imgChessMidTwo;
    @BindView(R.id.img_chess_mid_count)
    ImageView imgChessMidCount;
    @BindView(R.id.img_chess_mid_multiple)
    ImageView imgChessMidMultiple;
    @BindView(R.id.img_bg_right)
    ImageView imgBgRight;
    @BindView(R.id.img_chess_right_one)
    ImageView imgChessRightOne;
    @BindView(R.id.img_chess_right_two)
    ImageView imgChessRightTwo;
    @BindView(R.id.img_chess_right_count)
    ImageView imgChessRightCount;
    @BindView(R.id.img_chess_right_multiple)
    ImageView imgChessRightMultiple;
    @BindView(R.id.txt_total_left)
    StrokeTextView txtTotalLeft;
    @BindView(R.id.txt_total_mid)
    StrokeTextView txtTotalMid;
    @BindView(R.id.txt_total_right)
    StrokeTextView txtTotalRight;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    @BindView(R.id.img_head_left)
    ImageView imgHeadLeft;
    @BindView(R.id.fl_head_left)
    FrameLayout flHeadLeft;
    @BindView(R.id.txt_head_left)
    TextView txtHeadLeft;
    @BindView(R.id.rel_head_left)
    RelativeLayout relHeadLeft;
    @BindView(R.id.img_head_bottom)
    ImageView imgHeadBottom;
    @BindView(R.id.fl_head_bottom)
    FrameLayout flHeadBottom;
    @BindView(R.id.txt_head_bottom)
    TextView txtHeadBottom;
    @BindView(R.id.rel_head_bottom)
    RelativeLayout relHeadBottom;
    @BindView(R.id.img_head_right)
    ImageView imgHeadRight;
    @BindView(R.id.fl_head_right)
    FrameLayout flHeadRight;
    @BindView(R.id.txt_head_right)
    TextView txtHeadRight;
    @BindView(R.id.rel_head_right)
    RelativeLayout relHeadRight;
    @BindView(R.id.img_head_top)
    ImageView imgHeadTop;
    @BindView(R.id.fl_head_top)
    FrameLayout flHeadTop;
    @BindView(R.id.txt_head_top)
    TextView txtHeadTop;
    @BindView(R.id.rel_head_top)
    RelativeLayout relHeadTop;
    @BindView(R.id.img_gameing_user)
    ImageView imgGameingUser;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.txt_money_left)
    TextView txtMoneyLeft;
    @BindView(R.id.txt_money_mid)
    TextView txtMoneyMid;
    @BindView(R.id.txt_money_right)
    TextView txtMoneyRight;
    @BindView(R.id.img_setting)
    ImageView imgSetting;

    private int[] timeRes = new int[]{R.mipmap.num_zero, R.mipmap.num_one, R.mipmap.num_two, R.mipmap.num_three, R.mipmap.num_four, R.mipmap.num_five, R.mipmap.num_six,
            R.mipmap.num_seven, R.mipmap.num_eight, R.mipmap.num_nine, R.mipmap.num_ten};
    private int[] chessRes = new int[]{R.mipmap.chess_one, R.mipmap.chess_two, R.mipmap.chess_three, R.mipmap.chess_four, R.mipmap.chess_five, R.mipmap.chess_six, R.mipmap.chess_seven,
            R.mipmap.chess_eight, R.mipmap.chess_nine};

    private GamePresenter gamePresenter;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);
        ButterKnife.bind(this);
        gamePresenter = new GamePresenter(this);
    }

    @OnClick({R.id.img_back, R.id.rel_head_left, R.id.rel_head_bottom, R.id.rel_head_right, R.id.rel_head_top, R.id.img_gameing_user, R.id.img_head, R.id.img_add, R.id.txt_money_left, R.id.txt_money_mid, R.id.txt_money_right, R.id.img_setting})
    public void cardClick(View view) {
        super.cardClick(view);
        switch (view.getId()) {
            case R.id.img_back:
                gamePresenter.back();
                break;
            case R.id.rel_head_left:
                break;
            case R.id.rel_head_bottom:
                break;
            case R.id.rel_head_right:
                break;
            case R.id.rel_head_top:
                break;
            case R.id.img_gameing_user:
                gamePresenter.showAudience();
                break;
            case R.id.img_head:

                break;
            case R.id.img_add:
                break;
            case R.id.txt_money_left:
                break;
            case R.id.txt_money_mid:
                break;
            case R.id.txt_money_right:
                break;
            case R.id.img_setting:
                break;
        }
    }

    @Override
    public void showUserInfo(int pos) {

    }

    @Override
    public void showAudience() {

    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void showSetting() {

    }

    @Override
    public void addMoney() {

    }

    @Override
    public void followMoney(int pos) {

    }

    @Override
    public void showDialog() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void openChess() {

    }

    @Override
    public void startCountTime(int time) {
        imgTimeStatus.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        imgTime.setBackgroundResource(timeRes[time]);
    }

    @Override
    public void endCountTime() {
        imgTime.setVisibility(View.INVISIBLE);
        imgTimeStatus.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPoint() {

    }

    @Override
    public void hidePoint() {

    }

    @Override
    public void showMultiple(int pos) {

    }

    @Override
    public void hideMultiple(int pos) {

    }
}
