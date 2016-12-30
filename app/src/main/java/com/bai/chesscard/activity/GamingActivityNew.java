package com.bai.chesscard.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.GameChessAdapter;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.interfacer.GameOprateViewNew;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.presenter.GamePresenterNew;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.bai.chesscard.widget.StrokeTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/11.
 */

public class GamingActivityNew extends BaseActivity implements GameOprateViewNew, PopInterfacer {

    @BindView(R.id.fl_head_top)
    FrameLayout flHeadTop;
    @BindView(R.id.ll_table)
    RelativeLayout llTable;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_time)
    TextView txtTime;
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
    @BindView(R.id.img_chess_top_count)
    ImageView imgChessTopCount;
    @BindView(R.id.recy_chess)
    RecyclerView recyChess;
    @BindView(R.id.fl_banker_content)
    FrameLayout flBankerContent;
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
    @BindView(R.id.txt_total_left)
    StrokeTextView txtTotalLeft;
    @BindView(R.id.fl_user_left)
    FrameLayout flUserLeft;
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
    @BindView(R.id.txt_total_mid)
    StrokeTextView txtTotalMid;
    @BindView(R.id.fl_user_mid)
    FrameLayout flUserMid;
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
    @BindView(R.id.txt_total_right)
    StrokeTextView txtTotalRight;
    @BindView(R.id.fl_user_right)
    FrameLayout flUserRight;
    @BindView(R.id.img_head_left)
    ImageView imgHeadLeft;
    @BindView(R.id.txt_left_money)
    TextView txtLeftMoney;
    @BindView(R.id.fl_head_left)
    FrameLayout flHeadLeft;
    @BindView(R.id.txt_head_left)
    TextView txtHeadLeft;
    @BindView(R.id.rel_head_left)
    RelativeLayout relHeadLeft;
    @BindView(R.id.img_head_bottom)
    ImageView imgHeadBottom;
    @BindView(R.id.txt_mid_money)
    TextView txtMidMoney;
    @BindView(R.id.fl_head_bottom)
    FrameLayout flHeadBottom;
    @BindView(R.id.txt_head_bottom)
    TextView txtHeadBottom;
    @BindView(R.id.rel_head_bottom)
    RelativeLayout relHeadBottom;
    @BindView(R.id.img_head_right)
    ImageView imgHeadRight;
    @BindView(R.id.txt_right_money)
    TextView txtRightMoney;
    @BindView(R.id.fl_head_right)
    FrameLayout flHeadRight;
    @BindView(R.id.txt_head_right)
    TextView txtHeadRight;
    @BindView(R.id.rel_head_right)
    RelativeLayout relHeadRight;
    @BindView(R.id.img_head_top)
    ImageView imgHeadTop;
    @BindView(R.id.txt_banker_money)
    TextView txtBankerMoney;
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

    private GamePresenterNew gamePresenterNew;
    private List chessList;
    private GameChessAdapter gameChessAdapter;

    private SettingPop settingPop;
    private PersonalPop personalPop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming_new);
        gamePresenterNew = new GamePresenterNew(this);
        gamePresenterNew.startService();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        invisCountTime();
        invisBetMoney();
        invisChess();
        invisPoint();
        invisMutil();
        initBetCount();
    }

    private void initBetCount() {
        txtTotalLeft.setText("");
        txtTotalMid.setText("");
        txtTotalRight.setText("");
    }

    /**
     * 隐藏倍数
     */
    private void invisMutil() {
        imgChessLeftMultiple.setVisibility(View.GONE);
        imgChessMidMultiple.setVisibility(View.GONE);
        imgChessRightMultiple.setVisibility(View.GONE);
    }

    /**
     * 隐藏牌的点数
     */
    private void invisPoint() {
        imgChessLeftCount.setVisibility(View.GONE);
        imgChessMidCount.setVisibility(View.GONE);
        imgChessRightCount.setVisibility(View.GONE);
    }

    /**
     * 隐藏玩儿家牌
     */
    private void invisChess() {
        imgTopLeft.setVisibility(View.GONE);
        imgTopRight.setVisibility(View.GONE);

        imgChessLeftOne.setVisibility(View.GONE);
        imgChessLeftTwo.setVisibility(View.GONE);

        imgChessMidOne.setVisibility(View.GONE);
        imgChessMidTwo.setVisibility(View.GONE);

        imgChessRightOne.setVisibility(View.GONE);
        imgChessRightTwo.setVisibility(View.GONE);
    }

    /**
     * 隐藏投注码牌
     */
    private void invisBetMoney() {
        imgBgLeft.setVisibility(View.GONE);
        imgBgMid.setVisibility(View.GONE);
        imgBgRight.setVisibility(View.GONE);
    }

    private void initView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llTable.getLayoutParams();
        params.width = (int) (Tools.getScreenWide(context) * 0.59);
        params.height = (int) (Tools.getScreenHeight(context) * 0.57);
        llTable.setLayoutParams(params);

        txtUserName.setText("昵称: " + AppPrefrence.getUserName(context));
        recyChess.setLayoutManager(new GridLayoutManager(context, 8, LinearLayoutManager.VERTICAL, false));
        gameChessAdapter = new GameChessAdapter(chessList);
        recyChess.setAdapter(gameChessAdapter);
    }

    /**
     * 倒计时空间状态
     */
    private void visCountTime() {
        llTimeContent.setVisibility(View.VISIBLE);
    }

    private void invisCountTime() {
        llTimeContent.setVisibility(View.INVISIBLE);
    }

    @Override
    public void exitTable() {

    }

    @Override
    public void countDownTime(final int time, final int type) {
        visCountTime();

        switch (type) {
            case ConstentNew.TYPE_WAIT_TIME:  //等待
                imgTimeStatus.setBackgroundResource(R.mipmap.text_wait);
                break;
            case ConstentNew.TYPE_BET_MONEY:  //押注
                imgTimeStatus.setBackgroundResource(R.mipmap.text_set_point);
                break;
            case ConstentNew.TYPE_OPEN_CHESS:  //开牌
                imgTimeStatus.setBackgroundResource(R.mipmap.text_open_chess);
                break;
            case ConstentNew.TYPE_DEAL_CHESS:
                imgTimeStatus.setBackgroundResource(R.mipmap.text_deal_chess);
                break;
        }

        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing())
                    cancel();
                txtTime.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                gamePresenterNew.endCountTime(type);
                invisCountTime();
            }
        };
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    public void shakeDice() {

    }

    @Override
    public void showSettingPop() {
        if (settingPop == null)
            settingPop = new SettingPop(context);
        settingPop.showPop(imgAdd);
        settingPop.setPopInterfacer(this, ConstentNew.SETTING_POP);
    }

    @Override
    public void showMinePop() {
        if (personalPop == null)
            personalPop = new PersonalPop(context);
        personalPop.showPop(txtBankerMoney);
        personalPop.setPopInterfacer(this, ConstentNew.PERSONAL_POP);
    }

    @Override
    public void showProgressPop() {
        Tools.getDialog(context, "获取数据中...").show();
    }

    @Override
    public void dissmissProgressPop() {
        Tools.getDialog(context, "").dismiss();
    }

    @Override
    public void downBanker() {

    }

    @Override
    public void upBanker() {

    }

    @Override
    public void downTable() {

    }

    @Override
    public void renewMoneyBanker() {

    }

    @Override
    public void renewMoneyGamer() {

    }

    @Override
    public void resetChess() {

    }

    @Override
    public void dealChess() {

    }

    @Override
    public void openChess() {

    }

    @Override
    public void toastMsg(String msg) {
        Tools.toastMsgCenter(this, msg);
    }

    @Override
    public void toastMsg(int resId) {
        Tools.toastMsgCenter(this, resId);
    }

    @Override
    public void disContect() {

    }

    @Override
    public void reContect() {

    }

    @Override
    public void OnDismiss(int flag) {

    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case ConstentNew.SETTING_POP:

                break;
        }
    }

    @Override
    public void OnCancle(int flag) {
        switch (flag) {
            case ConstentNew.SETTING_POP:
                settingPop = null;
                break;
            case ConstentNew.PERSONAL_POP:
                personalPop = null;
                break;
        }
    }


    @OnClick({R.id.img_back, R.id.rel_head_bottom, R.id.rel_head_right, R.id.rel_head_top, R.id.img_gameing_user, R.id.img_head, R.id.img_add, R.id.txt_money_left, R.id.txt_money_mid, R.id.txt_money_right, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.rel_head_bottom:
                break;
            case R.id.rel_head_right:
                break;
            case R.id.rel_head_top:
                break;
            case R.id.img_gameing_user:
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
}
