package com.bai.chesscard.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.GameChessAdapter;
import com.bai.chesscard.bean.Bean_Message;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.dialog.DiscontectNotifyPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.PersonalPopInfo;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.dialog.UpBankerNotifyPop;
import com.bai.chesscard.dialog.UpTableNotifyPop;
import com.bai.chesscard.interfacer.GameOprateViewNew;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.presenter.GamePresenterNew;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.bai.chesscard.widget.StrokeTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
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
    private DiscontectNotifyPop discontectNotifyPop;
    private PersonalPopInfo personalPopInfo;
    private UpBankerNotifyPop upBankerNotifyPop;
    private UpTableNotifyPop upTableNotifyPop;

    private int[] chessRes = new int[]{R.mipmap.chess_one, R.mipmap.chess_two, R.mipmap.chess_three, R.mipmap.chess_four, R.mipmap.chess_five, R.mipmap.chess_six, R.mipmap.chess_seven,
            R.mipmap.chess_eight, R.mipmap.chess_nine};
    private int[] mutilRes = new int[]{R.mipmap.one_multiple, R.mipmap.double_multiple, R.mipmap.trable_multiple};
    private int[] pointRes = new int[]{R.mipmap.point_zero, R.mipmap.point_one, R.mipmap.point_two, R.mipmap.point_three, R.mipmap.point_four, R.mipmap.point_five,
            R.mipmap.point_six, R.mipmap.point_seven, R.mipmap.point_eight, R.mipmap.point_nine, R.mipmap.point_double};
    private int[] pointGrayRes = new int[]{R.mipmap.point_zero_gray, R.mipmap.point_one_gray, R.mipmap.point_two_gray, R.mipmap.point_three_gray, R.mipmap.point_four_gray, R.mipmap.point_five_gray,
            R.mipmap.point_six_gray, R.mipmap.point_seven_gray, R.mipmap.point_eight_gray, R.mipmap.point_nine_gray, R.mipmap.point_double_gray};

    private int timeCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming_new);
        gamePresenterNew = new GamePresenterNew(this);
        gamePresenterNew.startService();
        ButterKnife.bind(this);
        initView();
        initData();

        gamePresenterNew.getInGame();
    }

    private void initData() {
        ConstentNew.TABLE_ID = getIntent().getStringExtra("tableId");
        ConstentNew.ROOM_ID = getIntent().getStringExtra("roomId");
        ConstentNew.USER_ID = AppPrefrence.getUserNo(context);
        invisCountTime();
        invisBetMoney();
        invisChess();
        invisPoint();
        invisMutil();
        initBetCount();
        invisTabelMoney(-1);
    }

    private void invisTabelMoney(int pos) {
        switch (pos) {
            case -1:
                txtBankerMoney.setVisibility(View.INVISIBLE);
                txtLeftMoney.setVisibility(View.INVISIBLE);
                txtMidMoney.setVisibility(View.INVISIBLE);
                txtRightMoney.setVisibility(View.INVISIBLE);
                break;
            case 0:
                txtBankerMoney.setVisibility(View.INVISIBLE);
                break;
            case 1:
                txtLeftMoney.setVisibility(View.INVISIBLE);
                break;
            case 2:
                txtMidMoney.setVisibility(View.INVISIBLE);
                break;
            case 3:
                txtRightMoney.setVisibility(View.INVISIBLE);
                break;
        }
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
        chessList = new ArrayList();
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

    private void glideImg(int path, ImageView imageView) {
        imageView.setBackgroundResource(path);
    }

    private void glideImg(String path, ImageView imageView) {
        Glide.with(this).load(path).error(R.mipmap.icon_default_head).into(imageView);
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
        if (AppPrefrence.getAmount(context) < ConstentNew.BANKER_LIMIT_MONEY) {
            Tools.toastMsgCenter(context, "账户余额不足");
            return;
        }
        if (upBankerNotifyPop == null)
            upBankerNotifyPop = new UpBankerNotifyPop(context);
        upBankerNotifyPop.showPop(txtBankerMoney);
        upBankerNotifyPop.setPopInterfacer(this, ConstentNew.UPBANKER_POP);
    }

    @Override
    public void downTable() {

    }

    @Override
    public void upTable(int pos) {
        if (upTableNotifyPop == null)
            upTableNotifyPop = new UpTableNotifyPop(context);
        upTableNotifyPop.setTitle("上桌");
        upTableNotifyPop.setPos(pos);
        upTableNotifyPop.showPop(txtBankerMoney);
        upTableNotifyPop.setPopInterfacer(this, ConstentNew.UPTABLE_POP);
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
    public void dealChess(int userPos) {
        for (int i = 0; i < 4; i++) {
            if (userPos > 3)
                userPos = 0;
            switch (userPos) {
                case 0:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgTopLeft);
                            glideImg(R.mipmap.bg_chess_back, imgTopRight);
                            imgTopLeft.setVisibility(View.VISIBLE);
                            imgTopRight.setVisibility(View.VISIBLE);
                        }
                    }, 500 * i);

                    break;
                case 1:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessLeftOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessLeftTwo);
                            imgChessLeftOne.setVisibility(View.VISIBLE);
                            imgChessLeftTwo.setVisibility(View.VISIBLE);
                        }
                    }, 500 * i);

                    break;
                case 2:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessMidOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessMidTwo);
                            imgChessMidOne.setVisibility(View.VISIBLE);
                            imgChessMidTwo.setVisibility(View.VISIBLE);
                        }
                    }, 500 * i);

                    break;
                case 3:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessRightOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessRightTwo);
                            imgChessRightOne.setVisibility(View.VISIBLE);
                            imgChessRightTwo.setVisibility(View.VISIBLE);
                        }
                    }, 500 * i);

                    break;
            }
            userPos++;
        }
    }

    @Override
    public void openChess(final Bundle bundle) {
        //庄家
        glideImg(chessRes[bundle.getInt("bankerOne")], imgTopLeft);
        glideImg(chessRes[bundle.getInt("bankerTwo")], imgTopRight);
        imgTopLeft.setVisibility(View.VISIBLE);
        imgTopRight.setVisibility(View.VISIBLE);
        gamePresenterNew.showPoint(0, bundle.getInt("bankerOne"), bundle.getInt("bankerTwo"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //初家的牌
                glideImg(chessRes[bundle.getInt("leftOne")], imgChessLeftOne);
                glideImg(chessRes[bundle.getInt("leftTwo")], imgChessLeftTwo);
                imgChessLeftOne.setVisibility(View.VISIBLE);
                imgChessLeftTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(1, bundle.getInt("leftOne"), bundle.getInt("leftTwo"));
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //天家
                glideImg(chessRes[bundle.getInt("bottomOne")], imgChessMidOne);
                glideImg(chessRes[bundle.getInt("bottomTwo")], imgChessMidTwo);
                imgChessMidOne.setVisibility(View.VISIBLE);
                imgChessMidTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(2, bundle.getInt("bottomOne"), bundle.getInt("bottomTwo"));
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //尾家
                glideImg(chessRes[bundle.getInt("rightOne")], imgChessRightOne);
                glideImg(chessRes[bundle.getInt("rightTwo")], imgChessRightTwo);
                imgChessRightOne.setVisibility(View.VISIBLE);
                imgChessLeftTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(3, bundle.getInt("rightOne"), bundle.getInt("rightTwo"));
            }
        }, 1500);
    }

    @Override
    public void showPoint(int pos, int point, boolean isGray) {
        switch (pos) {
            case 0:
                glideImg(pointRes[point], imgChessTopCount);
                imgChessTopCount.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (isGray)
                    glideImg(pointGrayRes[point], imgChessLeftCount);
                else
                    glideImg(pointRes[point], imgChessLeftCount);
                imgChessLeftCount.setVisibility(View.VISIBLE);
                break;
            case 2:
                if (isGray)
                    glideImg(pointGrayRes[point], imgChessMidCount);
                else
                    glideImg(pointRes[point], imgChessMidCount);
                imgChessMidCount.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (isGray)
                    glideImg(pointGrayRes[point], imgChessRightCount);
                else
                    glideImg(pointRes[point], imgChessRightCount);
                imgChessRightCount.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void showMutil(int pos, int mutil) {
        switch (pos) {
            case 1:
                glideImg(mutilRes[mutil], imgChessLeftMultiple);
                imgChessLeftMultiple.setVisibility(View.VISIBLE);
                break;
            case 2:
                glideImg(mutilRes[mutil], imgChessMidMultiple);
                imgChessMidMultiple.setVisibility(View.VISIBLE);
                break;
            case 3:
                glideImg(mutilRes[mutil], imgChessRightMultiple);
                imgChessRightMultiple.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void settleResult(int pos, final boolean isWinner) {
        timeCount++;
        final int[] bankerPoint = new int[2];
        final int[] gamerPoint = new int[2];
        flHeadTop.getLocationInWindow(bankerPoint);
        switch (pos) {
            case 0:

                break;
            case 1:
                flHeadLeft.getLocationInWindow(gamerPoint);
                break;
            case 2:
                flHeadBottom.getLocationInWindow(gamerPoint);
                break;
            case 3:
                flHeadRight.getLocationInWindow(gamerPoint);
                break;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isWinner)
                    settleResult(bankerPoint, gamerPoint);
                else settleResult(gamerPoint, bankerPoint);
            }
        }, 500 * timeCount);

    }

    private void settleResult(final int[] startPoint, final int[] endPoint) {
        for (int i = 0; i < 3; i++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    settleMoneyAnim(startPoint, endPoint);
                }
            }, 100 * i);
        }
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
        if (discontectNotifyPop == null)
            discontectNotifyPop = new DiscontectNotifyPop(context);
        discontectNotifyPop.showPop(txtHeadBottom);
        discontectNotifyPop.setPopInterfacer(this, ConstentNew.DISCONTECT_POP);
    }

    @Override
    public void reContect() {
        gamePresenterNew.getInGame();
        if (discontectNotifyPop != null && discontectNotifyPop.isShowing()) {
            discontectNotifyPop.setIsContect(true);
            discontectNotifyPop.dismiss();
        }
    }

    @Override
    public void setTableInfo(Bean_TableDetial bean_tableDetial) {
        if (bean_tableDetial.firstuser != null&&!TextUtils.isEmpty(bean_tableDetial.firstuser.id)) {
            txtBankerMoney.setText(bean_tableDetial.firstuser.lookmonery + "");
            txtBankerMoney.setVisibility(View.VISIBLE);
            glideImg(bean_tableDetial.firstuser.user_logo, imgHeadTop);
            ConstentNew.IS_HAS_GAMER[0] = true;
            if (TextUtils.equals(bean_tableDetial.firstuser.id, AppPrefrence.getUserNo(context)))
                ConstentNew.IS_BANKER = true;
            else {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
            }
        } else {
            invisTabelMoney(0);
            glideImg(R.mipmap.site_empty, imgHeadTop);
            ConstentNew.IS_HAS_GAMER[0] = false;
            ConstentNew.IS_BANKER = false;
            ConstentNew.IS_GAMER = false;
        }

        if (bean_tableDetial.seconduser != null&&!TextUtils.isEmpty(bean_tableDetial.seconduser.id)) {
            txtLeftMoney.setText(bean_tableDetial.seconduser.lookmonery + "");
            txtLeftMoney.setVisibility(View.VISIBLE);
            glideImg(bean_tableDetial.seconduser.user_logo, imgHeadLeft);
            ConstentNew.IS_HAS_GAMER[1] = true;
            if (TextUtils.equals(bean_tableDetial.seconduser.id, AppPrefrence.getUserNo(context))) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
            } else {
                ConstentNew.IS_GAMER = false;
                ConstentNew.IS_BANKER = false;
            }
        } else {
            invisTabelMoney(1);
            glideImg(R.mipmap.site_empty, imgHeadLeft);
            ConstentNew.IS_HAS_GAMER[1] = false;
            ConstentNew.IS_BANKER = false;
            ConstentNew.IS_GAMER = false;
        }

        if (bean_tableDetial.thirduser != null&&!TextUtils.isEmpty(bean_tableDetial.thirduser.id)) {
            txtMidMoney.setText(bean_tableDetial.thirduser.lookmonery + "");
            txtMidMoney.setVisibility(View.VISIBLE);
            glideImg(bean_tableDetial.thirduser.user_logo, imgHeadBottom);
            ConstentNew.IS_HAS_GAMER[2] = true;
            if (TextUtils.equals(bean_tableDetial.seconduser.id, AppPrefrence.getUserNo(context))) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
            } else {
                ConstentNew.IS_GAMER = false;
                ConstentNew.IS_BANKER = false;
            }
        } else {
            invisTabelMoney(2);
            glideImg(R.mipmap.site_empty, imgHeadBottom);
            ConstentNew.IS_HAS_GAMER[2] = false;
            ConstentNew.IS_BANKER = false;
            ConstentNew.IS_GAMER = false;
        }

        if (bean_tableDetial.fouruser != null&&!TextUtils.isEmpty(bean_tableDetial.fouruser.id)) {
            txtRightMoney.setText(bean_tableDetial.fouruser.lookmonery + "");
            txtRightMoney.setVisibility(View.VISIBLE);
            glideImg(bean_tableDetial.fouruser.user_logo, imgHeadRight);
            ConstentNew.IS_HAS_GAMER[3] = true;
            if (TextUtils.equals(bean_tableDetial.fouruser.id, AppPrefrence.getUserNo(context))) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
            } else {
                ConstentNew.IS_GAMER = false;
                ConstentNew.IS_BANKER = false;
            }
        } else {
            invisTabelMoney(3);
            glideImg(R.mipmap.site_empty, imgHeadRight);
            ConstentNew.IS_HAS_GAMER[3] = false;
            ConstentNew.IS_BANKER = false;
            ConstentNew.IS_GAMER = false;
        }
    }

    @Override
    public void showUserInfo(Bean_TableDetial.TableUser userInfo) {
        if (personalPopInfo == null)
            personalPopInfo = new PersonalPopInfo(context);
        personalPopInfo.setInfo(userInfo);
        personalPopInfo.setPopInterfacer(this, ConstentNew.USERINFO_POP);
        personalPopInfo.showPop(txtBankerMoney);
    }

    @Override
    public void OnDismiss(int flag) {
        switch (flag) {
            case ConstentNew.DISCONTECT_POP:
                discontectNotifyPop = null;
                break;
            case ConstentNew.PERSONAL_POP:
                personalPop = null;
                break;
            case ConstentNew.SETTING_POP:
                settingPop = null;
                break;
            case ConstentNew.USERINFO_POP:
                personalPopInfo = null;
                break;
            case ConstentNew.UPBANKER_POP:
                upBankerNotifyPop = null;
                break;
            case ConstentNew.UPTABLE_POP:
                upTableNotifyPop = null;
                break;
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case ConstentNew.SETTING_POP:
                if (bundle == null)
                    return;
                if (bundle.getBoolean("type")) {
                    ChessCardApplication.getInstance().playBack();
                } else ChessCardApplication.getInstance().stopBack();
                break;
            case ConstentNew.UPBANKER_POP:
                if (bundle == null || !bundle.getBoolean("result"))
                    return;
                Bean_Message bean_message = new Bean_Message();
                bean_message.gamerPos = 0;
                bean_message.type = ConstentNew.TYPE_SITE_DOWN;
                Bean_TableDetial.TableUser bankerUser=new Bean_TableDetial.TableUser();
                bankerUser.user_logo = AppPrefrence.getAvatar(context);
                bankerUser.id = AppPrefrence.getUserNo(context);
                bankerUser.nick_name = AppPrefrence.getUserName(context);
                bankerUser.lookmonery = bundle.getInt("money");
                bean_message.tableUser=bankerUser;
                gamePresenterNew.sendMessage(bean_message);
                break;
            case ConstentNew.UPTABLE_POP:
                if (bundle == null || !bundle.getBoolean("result"))
                    return;
                Bean_Message messageTable = new Bean_Message();
                messageTable.gamerPos = ConstentNew.USERPOS;
                messageTable.type = ConstentNew.TYPE_SITE_DOWN;
                Bean_TableDetial.TableUser tableUser = new Bean_TableDetial.TableUser();
                tableUser.user_logo = AppPrefrence.getAvatar(context);
                tableUser.id = AppPrefrence.getUserNo(context);
                tableUser.nick_name = AppPrefrence.getUserName(context);
                tableUser.lookmonery = bundle.getInt("money");
                messageTable.tableUser = tableUser;
                gamePresenterNew.sendMessage(messageTable);
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


    @OnClick({R.id.img_back, R.id.rel_head_left, R.id.rel_head_bottom, R.id.rel_head_right, R.id.rel_head_top, R.id.img_gameing_user,
            R.id.img_head, R.id.img_add, R.id.txt_money_left, R.id.txt_money_mid, R.id.txt_money_right, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                gamePresenterNew.back();
                break;
            case R.id.rel_head_bottom:
                gamePresenterNew.selectSite(3);
                break;
            case R.id.rel_head_right:
                gamePresenterNew.selectSite(4);
                break;
            case R.id.rel_head_top:
                gamePresenterNew.selectSite(1);
                break;
            case R.id.rel_head_left:
                gamePresenterNew.selectSite(2);
                break;
            case R.id.img_gameing_user:
                break;
            case R.id.img_head:
                gamePresenterNew.showPersonalPop();
                break;
            case R.id.img_add:
                break;
            case R.id.txt_money_left:
                gamePresenterNew.betMoney();
                int[] leftPoint = new int[2];
                txtMoneyLeft.getLocationInWindow(leftPoint);
                betMoneyAnim(leftPoint, 0);
                break;
            case R.id.txt_money_mid:
                gamePresenterNew.betMoney();
                int[] midPoint = new int[2];
                txtMoneyLeft.getLocationInWindow(midPoint);
                betMoneyAnim(midPoint, 1);
                break;
            case R.id.txt_money_right:
                gamePresenterNew.betMoney();
                int[] rightPoint = new int[2];
                txtMoneyLeft.getLocationInWindow(rightPoint);
                betMoneyAnim(rightPoint, 2);
                break;
            case R.id.img_setting:
                gamePresenterNew.showSettingPop();
                break;
        }
    }

    public void settleMoneyAnim(int[] startPoint, int[] endPoint) {
        ChessCardApplication.getInstance().playGoldSound();
        int height = Tools.dip2px(context, 40);
        final ViewGroup viewGroup = createAnimLayout(this);
        viewGroup.removeAllViews();
        viewGroup.setBackgroundColor(Color.TRANSPARENT);
        TranslateAnimation translateAnimation = new TranslateAnimation(startPoint[0], endPoint[0], startPoint[1], endPoint[1]);
        translateAnimation.setDuration(300);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setInterpolator(new LinearInterpolator());
        final ImageView imgBet = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(height, height);
        imgBet.setLayoutParams(params);
        glideImg(R.mipmap.bg_money_right, imgBet);
        imgBet.setLeft(startPoint[0]);
        imgBet.setTop(startPoint[1]);
        viewGroup.addView(imgBet);
        translateAnimation.setFillAfter(true);
        imgBet.setAnimation(translateAnimation);
        translateAnimation.start();

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBet.clearAnimation();
                imgBet.setVisibility(View.GONE);
                viewGroup.removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void betMoneyAnim(int[] startPoint, int pos) {
        ChessCardApplication.getInstance().playGoldSound();
        int height = Tools.dip2px(context, 40);
        float mutil = 1.34f;
        int width = (int) (height * mutil);
        int centerX = (int) (Tools.getScreenWide(context) / 2);
        int centerY = (int) (Tools.getScreenHeight(context) / 2);

        final int[] endPoint = new int[]{centerX, centerY};

        final ImageView imgBet = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(height, height);
        imgBet.setLayoutParams(params);
        imgBet.setVisibility(View.GONE);
        if (pos == 0)
            glideImg(R.mipmap.bg_money_left, imgBet);
        if (pos == 1)
            glideImg(R.mipmap.bg_money_mid, imgBet);
        if (pos == 2)
            glideImg(R.mipmap.bg_money_right, imgBet);
        final ViewGroup viewGroup = createAnimLayout(this);
        viewGroup.removeAllViews();
        endPoint[0] -= width / 2;
        endPoint[1] -= height / 2;
        imgBet.setLeft(startPoint[0]);
        imgBet.setTop(startPoint[1]);
        viewGroup.addView(imgBet);
        viewGroup.setBackgroundColor(Color.TRANSPARENT);

        TranslateAnimation translateAnimation = new TranslateAnimation(startPoint[0], endPoint[0] - startPoint[0], startPoint[1], endPoint[1] - startPoint[1]);
        translateAnimation.setDuration(500);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);
        imgBet.setAnimation(translateAnimation);
        translateAnimation.start();
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imgBet.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBet.clearAnimation();
                imgBet.setVisibility(View.GONE);
                imgBet.setBackgroundResource(0);
                viewGroup.removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 创建动画层
     */
    private ViewGroup createAnimLayout(Activity context) {
        ViewGroup rootView = (ViewGroup) context.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

}
