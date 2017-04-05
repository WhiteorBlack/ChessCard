package com.bai.chesscard.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.GameChessAdapter;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_Message;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.dialog.AudiencelPop;
import com.bai.chesscard.dialog.BankerExitNotifyPop;
import com.bai.chesscard.dialog.BankerNotify;
import com.bai.chesscard.dialog.ChangeBankerPop;
import com.bai.chesscard.dialog.ChargeBankerNotifyPop;
import com.bai.chesscard.dialog.ChargeMoneyNotifyPop;
import com.bai.chesscard.dialog.DiscontectNotifyPop;
import com.bai.chesscard.dialog.DiscontectPop;
import com.bai.chesscard.dialog.ExitGamerNotifyPop;
import com.bai.chesscard.dialog.GamerExitNotifyPop;
import com.bai.chesscard.dialog.KickOutNotifyPop;
import com.bai.chesscard.dialog.LackBankerNotifyPop;
import com.bai.chesscard.dialog.LackMoneyNotifyPop;
import com.bai.chesscard.dialog.PersonalPop;
import com.bai.chesscard.dialog.PersonalPopInfo;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.dialog.UpBankerNotifyPop;
import com.bai.chesscard.dialog.UpTableNotifyPop;
import com.bai.chesscard.interfacer.GameOprateViewNew;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.presenter.GamePresenterNew;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.bai.chesscard.widget.StrokeTextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.txt_time_statue)
    StrokeTextView txtTimeStatue;
    @BindView(R.id.img_shake_dice)
    ImageView imgShakeDice;

    private GamePresenterNew gamePresenterNew;
    private List<Bean_ChessList.Chess> chessList;
    private GameChessAdapter gameChessAdapter;

    private SettingPop settingPop;
    private PersonalPop personalPop;
    private DiscontectNotifyPop discontectNotifyPop;
    private PersonalPopInfo personalPopInfo;
    private UpBankerNotifyPop upBankerNotifyPop;
    private UpTableNotifyPop upTableNotifyPop;
    private GamerExitNotifyPop gamerExitNotifyPop;
    private BankerExitNotifyPop bankerExitNotifyPop;
    private ExitGamerNotifyPop exitGamerNotifyPop;
    private AudiencelPop audiencePop;
    private LackBankerNotifyPop lackBankerNotifyPop;
    private LackMoneyNotifyPop lackMoneyNotifyPop;
    private BankerNotify bankerNotify;
    private ChargeBankerNotifyPop chargeBankerNotify;
    private ChargeMoneyNotifyPop chargeMoneyNotify;
    private KickOutNotifyPop kickOutNotifyPop;
    private ChangeBankerPop changeBankerPop;
    private DiscontectPop discontectPop;

    /**
     * dialog
     */

    AlertDialog gamerBackNotify;
    AlertDialog bankerBackNotify;
    AlertDialog bankerSelectNotify;
    AlertDialog kickGamerOut;

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

        ButterKnife.bind(this);
        initView();
        initData();
        gamePresenterNew.getInGame();
    }

    private void initData() {
        txtMoneyLeft.setText(ConstentNew.LEFTPOINT > 10000 ? (ConstentNew.LEFTPOINT / 10000 + "万") : ConstentNew.LEFTPOINT + "");
        txtMoneyMid.setText(ConstentNew.MIDPOINT > 10000 ? (ConstentNew.MIDPOINT / 10000 + "万") : ConstentNew.MIDPOINT + "");
        txtMoneyRight.setText(ConstentNew.RIGHTPOINT > 10000 ? (ConstentNew.RIGHTPOINT / 10000 + "万") : ConstentNew.RIGHTPOINT + "");
        glideImg(AppPrefrence.getAvatar(context), imgHead);
        initUserData();
        txtMoney.setText(AppPrefrence.getAmount(context) + "");
        ConstentNew.TABLE_ID = getIntent().getStringExtra("tableId");
        ConstentNew.ROOM_ID = getIntent().getStringExtra("roomId");
        ConstentNew.USER_ID = AppPrefrence.getUserNo(context);
        ConstentNew.IS_HAS_GAMER[0] = false;
        ConstentNew.IS_HAS_GAMER[1] = false;
        ConstentNew.IS_HAS_GAMER[2] = false;
        ConstentNew.IS_HAS_GAMER[3] = false;
        invisCountTime();
        invisBetMoney();
        invisChess();
        invisPoint();
        invisMutil();
        initBetCount();
        invisTabelMoney(-1);
        endBetMoeny();
    }

    private void initUserData() {
        ConstentNew.IS_BANKER = false;
        ConstentNew.IS_GAMER = false;
        ConstentNew.IS_HAS_GAMER[0] = false;
        ConstentNew.IS_HAS_GAMER[1] = false;
        ConstentNew.IS_HAS_GAMER[1] = false;
        ConstentNew.IS_HAS_GAMER[1] = false;
        ConstentNew.BANKERCHARGECOUNT = 1;
        ConstentNew.USER_ID = AppPrefrence.getUserNo(context);
        ConstentNew.USERPOS = 0;
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

    /**
     * 庄家投注 注码
     */
    private void invisBetPoint() {
        txtMoneyLeft.setVisibility(View.INVISIBLE);
        txtMoneyMid.setVisibility(View.INVISIBLE);
        txtMoneyRight.setVisibility(View.INVISIBLE);
    }

    private void visBetPoint() {
        txtMoneyLeft.setVisibility(View.VISIBLE);
        txtMoneyMid.setVisibility(View.VISIBLE);
        txtMoneyRight.setVisibility(View.VISIBLE);
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
        imgChessTopCount.setVisibility(View.GONE);
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
        recyChess.setLayoutManager(new GridLayoutManager(context, 9, LinearLayoutManager.VERTICAL, false));
        gameChessAdapter = new GameChessAdapter(chessList);
        recyChess.setAdapter(gameChessAdapter);

        imgAdd.setVisibility(View.GONE);
        imgShakeDice.setVisibility(View.GONE);
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
        if (isFinishing())
            return;
        Glide.with(this).load(path).error(R.mipmap.icon_default_head).into(imageView);
    }

    private void glideImg(String path, ImageView imageView) {
        if (isFinishing())
            return;
        Glide.with(this).load(path).error(R.mipmap.icon_default_head).into(imageView);
    }

    private void logoutGroup() {
        TIMGroupManager.getInstance().quitGroup(ConstentNew.GROUP_ID, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Tools.debug("quite group fail --" + s);
            }

            @Override
            public void onSuccess() {
                Tools.debug("quite group success");
            }
        });
    }

    @Override
    public void exitTable() {
        if (exitGamerNotifyPop == null)
            exitGamerNotifyPop = new ExitGamerNotifyPop(context);
        exitGamerNotifyPop.showPop(txtBankerMoney);
        exitGamerNotifyPop.setNotify("是否退出游戏?");
        exitGamerNotifyPop.setPopInterfacer(this, ConstentNew.EXITGAMER);
    }

    private CountDownTimer countDownTimer;

    @Override
    public void countDownTime(int time, final int type) {
        Tools.debug("countDownTime--" + type);
        if (type != ConstentNew.TYPE_BET_MONEY) {
            endBetMoeny();
        }
        if (!ConstentNew.IS_BANKER || type != ConstentNew.SHAKE_DICE) {
            imgShakeDice.setVisibility(View.GONE);
        }
        visCountTime();
        switch (type) {
            case ConstentNew.TYPE_WAIT_TIME:  //等待
                txtTimeStatue.setText("准备中...");
                resetTable();
                break;
            case ConstentNew.TYPE_BET_MONEY:  //押注
                txtTimeStatue.setText("押注时间");
                break;
            case ConstentNew.TYPE_OPEN_CHESS:  //开牌
                txtTimeStatue.setText("开牌时间");
                break;
            case ConstentNew.TYPE_DEAL_CHESS:
                txtTimeStatue.setText("发牌中...");
                break;
            case ConstentNew.TYPE_GET_RESULT:
                txtTimeStatue.setText("结算中...");
                break;
            case ConstentNew.TYPE_SHAKE_DICE:
                txtTimeStatue.setText("掷骰子...");
                break;
            case ConstentNew.TYPE_RESET_CHESS:
                txtTimeStatue.setText("洗牌...");
                break;
            case ConstentNew.TYPE_NOTIFY_BANKER:
                txtTimeStatue.setText("等待中...");
                break;
            case ConstentNew.SHAKE_DICE:
                if (!ConstentNew.IS_BET_MONEY) {
                    gamePresenterNew.endCountTime(ConstentNew.TYPE_BET_MONEY);
                }
                txtTimeStatue.setText("等待中...");
                if (ConstentNew.IS_BANKER) {
                    imgShakeDice.setVisibility(View.VISIBLE);
                    imgAdd.setVisibility(View.GONE);
                }
                break;
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
//            gamePresenterNew.endCountTime(type);
        }

        countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing())
                    cancel();
                txtTime.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                if (type == ConstentNew.TYPE_NOTIFY_BANKER && bankerSelectNotify != null && bankerSelectNotify.isShowing()) {
                    bankerSelectNotify.dismiss();
                    bankerSelectNotify = null;
                }
                gamePresenterNew.endCountTime(type);
                invisCountTime();
//                countDownTimer=null;
            }
        };
        countDownTimer.start();
    }


    @Override
    public void shakeDice(int one, int two) {
        int[] startPoint = new int[2];
        relHeadTop.getLocationInWindow(startPoint);
        gamePresenterNew.startDice(this, one, two, startPoint);
    }

    @Override
    public void endDice() {
        gamePresenterNew.endDice(this);
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
            personalPop = new PersonalPop(GamingActivityNew.this);
        personalPop.showPop(txtBankerMoney);
        personalPop.setPhotoInvis();
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

        /*if (bankerBackNotify == null)
            bankerBackNotify = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        bankerBackNotify.setOwnerActivity(this);
        View view = LayoutInflater.from(context).inflate(R.layout.down_banker_dialog, null);
        view.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTable();
                bankerBackNotify.dismiss();
            }
        });
        view.findViewById(R.id.img_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTableAsync();
                bankerBackNotify.dismiss();
            }
        });
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankerBackNotify.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.txt_content)).setText("庄家中途不能退出游戏");
        bankerBackNotify.setView(view);
        if (this != null && !isFinishing())
            bankerBackNotify.show();*/

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (bankerExitNotifyPop == null)
//                    bankerExitNotifyPop = new BankerExitNotifyPop(GamingActivityNew.this);
//                bankerExitNotifyPop.showPop(txtBankerMoney);
//                bankerExitNotifyPop.setNotify("选择下庄将成为观众,退出将直接退出房间");
//                bankerExitNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.BANKEREXITPOP);
//            }
//        });

    }

    @Override
    public void upBanker() {
        if (AppPrefrence.getAmount(context) < ConstentNew.BANKER_LIMIT_MONEY) {
            Tools.toastMsgCenter(context, "账户余额不足");
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (upBankerNotifyPop == null)
                    upBankerNotifyPop = new UpBankerNotifyPop(GamingActivityNew.this);
                upBankerNotifyPop.showPop(txtBankerMoney);
                upBankerNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.UPBANKER_POP);
            }
        });

    }

    @Override
    public void downTable() {
        if (gamerBackNotify == null)
            gamerBackNotify = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        gamerBackNotify.setOwnerActivity(this);
        View view = LayoutInflater.from(context).inflate(R.layout.down_table_dialog, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTable();
                gamerBackNotify.dismiss();
            }
        });
        view.findViewById(R.id.img_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTableAsync();
                gamerBackNotify.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.txt_content)).setText("选择下桌将成为观众,退出将直接退出房间");
        gamerBackNotify.setView(view);
        gamerBackNotify.show();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (gamerExitNotifyPop == null)
//                    gamerExitNotifyPop = new GamerExitNotifyPop(GamingActivityNew.this);
//                gamerExitNotifyPop.showPop(txtHeadLeft);
//                gamerExitNotifyPop.setNotify("选择下桌将成为观众,退出将直接退出房间");
//                gamerExitNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.GAMEREXITPOP);
//            }
//        });

    }

    @Override
    public void upTable(final int pos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (upTableNotifyPop == null)
                    upTableNotifyPop = new UpTableNotifyPop(GamingActivityNew.this);
                upTableNotifyPop.setTitle("上桌");
                upTableNotifyPop.setPos(pos);
                upTableNotifyPop.showPop(txtHeadTop);
                upTableNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.UPTABLE_POP);
            }
        });

    }

    @Override
    public void renewMoneyBanker(final int time) {
        if (lackBankerNotifyPop == null)
            lackBankerNotifyPop = new LackBankerNotifyPop(this);
        lackBankerNotifyPop.setCountTime(time);
        lackBankerNotifyPop.setTitle("庄家续庄");
        lackBankerNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.LACKBANKERPOP);
        if (this != null && !isFinishing())
            lackBankerNotifyPop.showPop(txtMoney);
        else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lackBankerNotifyPop.showPop(txtMoney);
                        } catch (Exception e) {

                        }
                    }
                });
            }
        }, 300);

    }

    @Override
    public void renewMoneyGamer(final int time) {
        if (lackMoneyNotifyPop == null)
            lackMoneyNotifyPop = new LackMoneyNotifyPop(GamingActivityNew.this);
        lackMoneyNotifyPop.setCountTime(time);
        lackMoneyNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.LACKGAMERPOP);
        if (this != null && !isFinishing())
            lackMoneyNotifyPop.showPop(txtMoney);
        else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lackMoneyNotifyPop.showPop(txtMoney);
                        } catch (WindowManager.BadTokenException e) {
                            renewMoneyGamer(time);
                        }

                    }
                });
            }
        }, 300);
    }

    @Override
    public void resetChess() {
        if (bankerNotify != null && bankerNotify.isShowing())
            bankerNotify.dismiss();
        chessList.clear();
        for (int i = 0; i < ConstentNew.CHESSLIST.length / 2; i++) {
            Bean_ChessList.Chess chess = new Bean_ChessList.Chess();
            chess.isVisiable = true;
            chessList.add(chess);
        }
        gameChessAdapter.notifyDataSetChanged();
        recyChess.setVisibility(View.VISIBLE);
    }

    @Override
    public void dealChess(int userPos) {
        if (chessList == null || chessList.size() == 0)
            return;
        for (int i = 0; i < 4; i++) {
            final int currentI = i;
            if (userPos > 4)
                userPos = 1;
            switch (userPos) {
                case 0:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessRightOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessRightTwo);
                            imgChessRightOne.setVisibility(View.VISIBLE);
                            imgChessRightTwo.setVisibility(View.VISIBLE);
                            chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                            gameChessAdapter.notifyDataSetChanged();
                        }
                    }, 500 * i);

                    break;
                case 1:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgTopLeft);
                            glideImg(R.mipmap.bg_chess_back, imgTopRight);
                            imgTopLeft.setVisibility(View.VISIBLE);
                            imgTopRight.setVisibility(View.VISIBLE);
                            chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                            gameChessAdapter.notifyDataSetChanged();
                        }
                    }, 500 * i);

                    break;
                case 2:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessLeftOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessLeftTwo);
                            imgChessLeftOne.setVisibility(View.VISIBLE);
                            imgChessLeftTwo.setVisibility(View.VISIBLE);
                            try {
                                chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                                gameChessAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                            }
                            chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                            gameChessAdapter.notifyDataSetChanged();
                        }
                    }, 500 * i);

                    break;
                case 3:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessMidOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessMidTwo);
                            imgChessMidOne.setVisibility(View.VISIBLE);
                            imgChessMidTwo.setVisibility(View.VISIBLE);
                            chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                            gameChessAdapter.notifyDataSetChanged();
                        }
                    }, 500 * i);

                    break;
                case 4:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            glideImg(R.mipmap.bg_chess_back, imgChessRightOne);
                            glideImg(R.mipmap.bg_chess_back, imgChessRightTwo);
                            imgChessRightOne.setVisibility(View.VISIBLE);
                            imgChessRightTwo.setVisibility(View.VISIBLE);
                            chessList.get(((ConstentNew.CURRENTROUND - 1) * 4) + currentI).isVisiable = false;
                            gameChessAdapter.notifyDataSetChanged();
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
        glideImg(chessRes[bundle.getInt("bankerOne") - 1], imgTopLeft);
        glideImg(chessRes[bundle.getInt("bankerTwo") - 1], imgTopRight);
        imgTopLeft.setVisibility(View.VISIBLE);
        imgTopRight.setVisibility(View.VISIBLE);
        gamePresenterNew.showPoint(0, bundle.getInt("bankerOne"), bundle.getInt("bankerTwo"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //初家的牌
                glideImg(chessRes[bundle.getInt("leftOne") - 1], imgChessLeftOne);
                glideImg(chessRes[bundle.getInt("leftTwo") - 1], imgChessLeftTwo);
                imgChessLeftOne.setVisibility(View.VISIBLE);
                imgChessLeftTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(1, bundle.getInt("leftOne"), bundle.getInt("leftTwo"));
                if (bundle.getInt("leftOne") == bundle.getInt("leftTwo"))
                    imgChessLeftMultiple.setVisibility(View.VISIBLE);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //天家
                glideImg(chessRes[bundle.getInt("bottomOne") - 1], imgChessMidOne);
                glideImg(chessRes[bundle.getInt("bottomTwo") - 1], imgChessMidTwo);
                imgChessMidOne.setVisibility(View.VISIBLE);
                imgChessMidTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(2, bundle.getInt("bottomOne"), bundle.getInt("bottomTwo"));
                if (bundle.getInt("bottomOne") == bundle.getInt("bottomTwo"))
                    imgChessMidMultiple.setVisibility(View.VISIBLE);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //尾家
                glideImg(chessRes[bundle.getInt("rightOne") - 1], imgChessRightOne);
                glideImg(chessRes[bundle.getInt("rightTwo") - 1], imgChessRightTwo);
                imgChessRightOne.setVisibility(View.VISIBLE);
                imgChessLeftTwo.setVisibility(View.VISIBLE);
                gamePresenterNew.showPoint(3, bundle.getInt("rightOne"), bundle.getInt("rightTwo"));
                if (bundle.getInt("rightOne") == bundle.getInt("rightTwo"))
                    imgChessRightMultiple.setVisibility(View.VISIBLE);
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

            case 2:
                flHeadLeft.getLocationInWindow(gamerPoint);
                break;
            case 3:
                flHeadBottom.getLocationInWindow(gamerPoint);
                break;
            case 4:
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
        Tools.toastMsgCenter(context, msg);
    }

    @Override
    public void toastMsg(int resId) {
        Tools.toastMsgCenter(context, resId);
    }

    @Override
    public void disContect() {
        if (discontectNotifyPop == null)
            discontectNotifyPop = new DiscontectNotifyPop(GamingActivityNew.this);
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
        if (bean_tableDetial.firstuser != null) {
            if (!TextUtils.isEmpty(bean_tableDetial.firstuser.id)) {
                txtBankerMoney.setText(bean_tableDetial.firstuser.lookmonery + "");
                txtBankerMoney.setVisibility(View.VISIBLE);
                glideImg(bean_tableDetial.firstuser.user_logo, imgHeadTop);
                ConstentNew.IS_HAS_GAMER[0] = true;
            } else {
                ConstentNew.IS_BANKER = false;
                invisTabelMoney(0);
                glideImg(R.mipmap.site_empty, imgHeadTop);
                ConstentNew.IS_HAS_GAMER[0] = false;
            }

            if (TextUtils.equals(bean_tableDetial.firstuser.id, ConstentNew.USER_ID)) {
                ConstentNew.IS_BANKER = true;
                ConstentNew.USERPOS = 1;
                ConstentNew.GAMER_TABLE_MONEY = bean_tableDetial.firstuser.lookmonery;
            } else {
                ConstentNew.IS_BANKER = false;
            }
        } else {
            ConstentNew.IS_BANKER = false;
            invisTabelMoney(0);
            glideImg(R.mipmap.site_empty, imgHeadTop);
            ConstentNew.IS_HAS_GAMER[0] = false;
        }

        if (bean_tableDetial.seconduser != null) {
            if (!TextUtils.isEmpty(bean_tableDetial.seconduser.id)) {
                visBetPoint();
                txtLeftMoney.setText(bean_tableDetial.seconduser.lookmonery + "");
                txtLeftMoney.setVisibility(View.VISIBLE);
                glideImg(bean_tableDetial.seconduser.user_logo, imgHeadLeft);
                ConstentNew.IS_HAS_GAMER[1] = true;
            } else {
                invisTabelMoney(1);
                glideImg(R.mipmap.site_empty, imgHeadLeft);
                ConstentNew.IS_HAS_GAMER[1] = false;
            }

            if (TextUtils.equals(bean_tableDetial.seconduser.id, ConstentNew.USER_ID)) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
                ConstentNew.USERPOS = 2;
                ConstentNew.GAMER_TABLE_MONEY = bean_tableDetial.seconduser.lookmonery;
            }
        } else {
            invisTabelMoney(1);
            glideImg(R.mipmap.site_empty, imgHeadLeft);
            ConstentNew.IS_HAS_GAMER[1] = false;
        }

        if (bean_tableDetial.thirduser != null) {
            if (!TextUtils.isEmpty(bean_tableDetial.thirduser.id)) {
                txtMidMoney.setText(bean_tableDetial.thirduser.lookmonery + "");
                txtMidMoney.setVisibility(View.VISIBLE);
                glideImg(bean_tableDetial.thirduser.user_logo, imgHeadBottom);
                ConstentNew.IS_HAS_GAMER[2] = true;
            } else {
                invisTabelMoney(2);
                glideImg(R.mipmap.site_empty, imgHeadBottom);
                ConstentNew.IS_HAS_GAMER[2] = false;
            }

            if (TextUtils.equals(bean_tableDetial.thirduser.id, ConstentNew.USER_ID)) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
                ConstentNew.USERPOS = 3;
                ConstentNew.GAMER_TABLE_MONEY = bean_tableDetial.thirduser.lookmonery;
            }
        } else {
            invisTabelMoney(2);
            glideImg(R.mipmap.site_empty, imgHeadBottom);
            ConstentNew.IS_HAS_GAMER[2] = false;
        }

        if (bean_tableDetial.fouruser != null) {
            if (!TextUtils.isEmpty(bean_tableDetial.fouruser.id)) {
                txtRightMoney.setText(bean_tableDetial.fouruser.lookmonery + "");
                txtRightMoney.setVisibility(View.VISIBLE);
                glideImg(bean_tableDetial.fouruser.user_logo, imgHeadRight);
                ConstentNew.IS_HAS_GAMER[3] = true;

            } else {
                invisTabelMoney(3);
                glideImg(R.mipmap.site_empty, imgHeadRight);
                ConstentNew.IS_HAS_GAMER[3] = false;
            }

            if (TextUtils.equals(bean_tableDetial.fouruser.id, ConstentNew.USER_ID)) {
                ConstentNew.IS_GAMER = true;
                ConstentNew.IS_BANKER = false;
                ConstentNew.USERPOS = 4;
                ConstentNew.GAMER_TABLE_MONEY = bean_tableDetial.fouruser.lookmonery;
            }
        } else {
            invisTabelMoney(3);
            glideImg(R.mipmap.site_empty, imgHeadRight);
            ConstentNew.IS_HAS_GAMER[3] = false;
        }

        if (ConstentNew.IS_BANKER) {
            invisBetPoint();
        } else {
            visBetPoint();
        }

        if (ConstentNew.IS_GAMER && !ConstentNew.IS_BANKER) {
            imgAdd.setVisibility(View.VISIBLE);
        } else {
            imgAdd.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void showUserInfo(final Bean_TableDetial.TableUser userInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (personalPopInfo == null)
                    personalPopInfo = new PersonalPopInfo(context);
                personalPopInfo.setInfo(userInfo);
                personalPopInfo.setPopInterfacer(GamingActivityNew.this, ConstentNew.USERINFO_POP);
                personalPopInfo.showPop(txtBankerMoney);
            }
        });

    }

    @Override
    public void initTable(Bean_TableDetial bean_tableDetial) {
        if (bean_tableDetial.game_status > 0 || bean_tableDetial.round > 1 || !TextUtils.isEmpty(bean_tableDetial.lastpai)) { //判断游戏是否正在进行中
            ConstentNew.GAMEROUND = bean_tableDetial.round;
            ConstentNew.CURRENTROUND = bean_tableDetial.round;
            if (bean_tableDetial.game_status == 1) {
                startBetMoney();
                countDownTime(bean_tableDetial.lasttime, ConstentNew.TYPE_BET_MONEY);
            }
            if (bean_tableDetial.game_status == 16) {
                dealChessStatue(bean_tableDetial);
                countDownTime(bean_tableDetial.lasttime, ConstentNew.TYPE_DEAL_CHESS);

            }
            if (bean_tableDetial.game_status == 17) {
                dealChessStatue(bean_tableDetial);
                countDownTime(bean_tableDetial.lasttime, ConstentNew.TYPE_OPEN_CHESS);
                if (!TextUtils.isEmpty(bean_tableDetial.firstcard)) {
                    glideImg(chessRes[getChessPoint(bean_tableDetial.firstcard)[0] - 1], imgTopLeft);
                    glideImg(chessRes[getChessPoint(bean_tableDetial.firstcard)[1] - 1], imgTopRight);
                }

                if (!TextUtils.isEmpty(bean_tableDetial.secondcard)) {
                    glideImg(chessRes[getChessPoint(bean_tableDetial.secondcard)[0] - 1], imgChessLeftOne);
                    glideImg(chessRes[getChessPoint(bean_tableDetial.secondcard)[1] - 1], imgChessLeftTwo);
                    gamePresenterNew.showPoint(1, getChessPoint(bean_tableDetial.secondcard)[0], getChessPoint(bean_tableDetial.secondcard)[1]);
                    if (getChessPoint(bean_tableDetial.secondcard)[0] == getChessPoint(bean_tableDetial.secondcard)[1])
                        imgChessLeftMultiple.setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(bean_tableDetial.thirdcard)) {
                    glideImg(chessRes[getChessPoint(bean_tableDetial.thirdcard)[0] - 1], imgChessMidOne);
                    glideImg(chessRes[getChessPoint(bean_tableDetial.thirdcard)[1] - 1], imgChessMidTwo);
                    gamePresenterNew.showPoint(2, getChessPoint(bean_tableDetial.thirdcard)[0], getChessPoint(bean_tableDetial.thirdcard)[1]);
                    if (getChessPoint(bean_tableDetial.thirdcard)[1] == getChessPoint(bean_tableDetial.thirdcard)[0])
                        imgChessMidMultiple.setVisibility(View.VISIBLE);

                }

                if (!TextUtils.isEmpty(bean_tableDetial.fourcard)) {
                    glideImg(chessRes[getChessPoint(bean_tableDetial.fourcard)[0] - 1], imgChessRightOne);
                    glideImg(chessRes[getChessPoint(bean_tableDetial.fourcard)[1] - 1], imgChessRightTwo);
                    imgChessRightOne.setVisibility(View.VISIBLE);
                    imgChessLeftTwo.setVisibility(View.VISIBLE);
                    gamePresenterNew.showPoint(3, getChessPoint(bean_tableDetial.fourcard)[1], getChessPoint(bean_tableDetial.fourcard)[0]);
                    if (getChessPoint(bean_tableDetial.fourcard)[1] == getChessPoint(bean_tableDetial.fourcard)[0])
                        imgChessRightMultiple.setVisibility(View.VISIBLE);
                }

            }

            if (!TextUtils.isEmpty(bean_tableDetial.lastpai) && !TextUtils.isEmpty(bean_tableDetial.lastpaicount)) {
                ConstentNew.LAST_CHESS_POINT = Integer.parseInt(bean_tableDetial.lastpai);
                int lastCount = Integer.parseInt(bean_tableDetial.lastpaicount);
                chessList.clear();
                for (int i = 0; i < 18; i++) {
                    Bean_ChessList.Chess chess = new Bean_ChessList.Chess();
                    if (i < (18 - (lastCount / 2))) {
                        chess.isVisiable = false;
                    } else {
                        chess.isVisiable = true;
                    }
                    chessList.add(chess);
                }
                recyChess.setVisibility(View.VISIBLE);
                gameChessAdapter.notifyDataSetChanged();
            }

        }

    }

    private void dealChessStatue(Bean_TableDetial bean_tableDetial) {
        if (TextUtils.isEmpty(bean_tableDetial.firstcard)) {
            glideImg(R.mipmap.bg_chess_back, imgTopLeft);
            glideImg(R.mipmap.bg_chess_back, imgTopRight);
            imgTopLeft.setVisibility(View.VISIBLE);
            imgTopRight.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(bean_tableDetial.secondcard)) {
            glideImg(R.mipmap.bg_chess_back, imgChessLeftOne);
            glideImg(R.mipmap.bg_chess_back, imgChessLeftTwo);
            imgChessLeftOne.setVisibility(View.VISIBLE);
            imgChessLeftTwo.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(bean_tableDetial.thirdcard)) {
            glideImg(R.mipmap.bg_chess_back, imgChessMidOne);
            glideImg(R.mipmap.bg_chess_back, imgChessMidTwo);
            imgChessMidOne.setVisibility(View.VISIBLE);
            imgChessMidTwo.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(bean_tableDetial.fourcard)) {
            glideImg(R.mipmap.bg_chess_back, imgChessRightOne);
            glideImg(R.mipmap.bg_chess_back, imgChessRightTwo);
            imgChessRightOne.setVisibility(View.VISIBLE);
            imgChessRightTwo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bankerCharge() {
        if (chargeBankerNotify == null)
            chargeBankerNotify = new ChargeBankerNotifyPop(context);
        chargeBankerNotify.setTitle("庄家续庄");

        chargeBankerNotify.setPopInterfacer(GamingActivityNew.this, ConstentNew.BANKERCHARGEMONEY);
        if (this != null && !isFinishing())
            chargeBankerNotify.showPop(txtBankerMoney);
        else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chargeBankerNotify.showPop(txtBankerMoney);
                    }
                });
            }
        }, 300);
    }

    @Override
    public void gamerCharge() {
        if (chargeMoneyNotify == null)
            chargeMoneyNotify = new ChargeMoneyNotifyPop(context);
        chargeMoneyNotify.setTitle("玩儿续费");

        chargeMoneyNotify.setPopInterfacer(GamingActivityNew.this, ConstentNew.GAMERCHARGEMONEY);
        if (this != null && !isFinishing())
            chargeMoneyNotify.showPop(txtTime);
        else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chargeMoneyNotify.showPop(txtTime);
                    }
                });
            }
        }, 300);
    }

    @Override
    public void BankerNotify() {

        if (bankerSelectNotify == null)
            bankerSelectNotify = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        bankerSelectNotify.setOwnerActivity(this);
        View view = LayoutInflater.from(context).inflate(R.layout.banker_notify_pop, null);
        view.findViewById(R.id.btn_go_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankerSelect(1);
                bankerSelectNotify.dismiss();
            }
        });
        view.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankerSelect(2);
                bankerSelectNotify.dismiss();
            }
        });
        view.findViewById(R.id.btn_down_banker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankerSelect(3);
                bankerSelectNotify.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.txt_notify)).setText("庄家选择");
        bankerSelectNotify.setView(view);
        if (this != null && !isFinishing())
            bankerSelectNotify.show();


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (bankerNotify == null)
//                    bankerNotify = new BankerNotify(GamingActivityNew.this);
//                bankerNotify.showPop(imgAdd);
//                bankerNotify.setPopInterfacer(GamingActivityNew.this, ConstentNew.BANKERNOTIFYPOP);
//            }
//        });

    }

    @Override
    public void startBetMoney() {
        if (!ConstentNew.IS_GAMER) {
            ConstentNew.USERPOS = 0;
            flUserLeft.setClickable(true);
            flUserMid.setClickable(true);
            flUserRight.setClickable(true);
        }
        txtMoneyLeft.setClickable(true);
        txtMoneyMid.setClickable(true);
        txtMoneyRight.setClickable(true);
    }

    @Override
    public void endBetMoeny() {
        if (!ConstentNew.IS_GAMER)
            ConstentNew.USERPOS = 0;
        flUserLeft.setClickable(false);
        flUserMid.setClickable(false);
        flUserRight.setClickable(false);
        flUserLeft.setBackgroundResource(R.mipmap.table_first);
        flUserMid.setBackgroundResource(R.mipmap.table_second);
        flUserRight.setBackgroundResource(R.mipmap.table_last);
        txtMoneyMid.setClickable(false);
        txtMoneyRight.setClickable(false);
        txtMoneyLeft.setClickable(false);
    }

    @Override
    public void updateMoney(int pos, final int money) {
        switch (pos) {
            case 0:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) + money);
//                        txtMoney.setText(AppPrefrence.getAmount(context) + "");
                    }
                });

                break;
            case 1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtBankerMoney.setText(money + "");
                    }
                });
                break;
            case 2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtLeftMoney.setText(money + "");
                    }
                });

                break;
            case 3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtMidMoney.setText(money + "");
                    }
                });

                break;
            case 4:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtRightMoney.setText(money + "");
                    }
                });

                break;
        }
        setUserMoney();
    }

    @Override
    public void gamerExit(int pos) {
        clearUserInfo(pos);
    }


    @Override
    public void resetTable() {
        invisChess();
        invisMutil();
        invisPoint();
        invisTablePoint();
        initBetCount();
    }

    @Override
    public void betMoney(int pos, int money) {
        switch (pos) {
            case 2:
                txtTotalLeft.setText(money + "");
                imgBgLeft.setVisibility(View.VISIBLE);
                break;
            case 3:
                txtTotalMid.setText(money + "");
                imgBgMid.setVisibility(View.VISIBLE);
                break;

            case 4:
                txtTotalRight.setText(money + "");
                imgBgRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void betMoneyNormal(int pos, int money) {
        switch (pos) {
            case 2:
                String moenyString = txtTotalLeft.getText().toString();
                if (TextUtils.isEmpty(moenyString)) {
                    moenyString = "0";
                }
                money += Integer.parseInt(moenyString);
                txtTotalLeft.setText(money + "");
                imgBgLeft.setVisibility(View.VISIBLE);
                break;
            case 3:
                String moenyStringM = txtTotalMid.getText().toString();
                if (TextUtils.isEmpty(moenyStringM)) {
                    moenyStringM = "0";
                }
                money += Integer.parseInt(moenyStringM);
                txtTotalMid.setText(money + "");
                imgBgMid.setVisibility(View.VISIBLE);
                break;

            case 4:
                String moenyStringR = txtTotalRight.getText().toString();
                if (TextUtils.isEmpty(moenyStringR)) {
                    moenyStringR = "0";
                }
                money += Integer.parseInt(moenyStringR);
                txtTotalRight.setText(money + "");
                imgBgRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 隐藏押注的注码
     */
    private void invisTablePoint() {
        imgBgMid.setVisibility(View.GONE);
        imgBgLeft.setVisibility(View.GONE);
        imgBgRight.setVisibility(View.GONE);
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
            case ConstentNew.BANKEREXITPOP:
                bankerExitNotifyPop = null;
                break;
            case ConstentNew.EXITGAMER:
                exitGamerNotifyPop = null;
                break;
            case ConstentNew.GAMEREXITPOP:
                gamerExitNotifyPop = null;
                break;
            case ConstentNew.AUDIENCEPOP:
                audiencePop = null;
                break;
            case ConstentNew.LACKBANKERPOP:
                lackBankerNotifyPop = null;
                break;
            case ConstentNew.LACKGAMERPOP:
                lackMoneyNotifyPop = null;
                break;
            case ConstentNew.BANKERCHARGEMONEY:
                chargeBankerNotify = null;
                break;
            case ConstentNew.GAMERCHARGEMONEY:
                chargeMoneyNotify = null;
                break;
            case ConstentNew.KICKOUTPOP:
                kickOutNotifyPop = null;
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
                ConstentNew.USERPOS = 1;
                bean_message.gamerPos = 1;
                bean_message.type = ConstentNew.TYPE_SITE_DOWN;
                Bean_TableDetial.TableUser bankerUser = new Bean_TableDetial.TableUser();
                bankerUser.user_logo = AppPrefrence.getAvatar(context);
                bankerUser.id = AppPrefrence.getUserNo(context);
                bankerUser.nick_name = AppPrefrence.getUserName(context);
                bankerUser.lookmonery = bundle.getInt("money");
                bean_message.tableUser = bankerUser;
                gamePresenterNew.sendMessage(bean_message);
                gamePresenterNew.upTable(bean_message);
                setUserInfo(bean_message);
                refreshUserMoney(AppPrefrence.getAmount(this));
                invisBetPoint();
                imgAdd.setVisibility(View.GONE);
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
                gamePresenterNew.upTable(messageTable);
                gamePresenterNew.sendMessage(messageTable);
                setUserMoney();
                setUserInfo(messageTable);
                break;
            case ConstentNew.GAMEREXITPOP:
                if (bundle == null)
                    return;
                if (bundle.getInt("type") == 1) {
                    resetUserStatue();
                }
                if (bundle.getInt("type") == 2) {
                    resetUserStatue();
                    logoutGroup();
                    finish();
                }
                break;
            case ConstentNew.BANKEREXITPOP:
                if (bundle == null)
                    return;
                if (bundle.getInt("type") == 1) {
                    resetUserStatue();
                }
                if (bundle.getInt("type") == 2) {
                    resetUserStatue();
                    logoutGroup();
                    finish();
                }
                break;
            case ConstentNew.EXITGAMER:
//                resetUserStatue();
                logoutGroup();
                finish();
                break;
            case ConstentNew.LACKBANKERPOP:
                if (bundle == null)
                    return;
                if (lackBankerNotifyPop != null)
                    lackBankerNotifyPop.dismiss();
                if (bundle.getInt("type") == 1) {
                    Bean_Message bankerMessage = new Bean_Message();
                    bankerMessage.gamerPos = 1;
                    bankerMessage.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                    bankerMessage.type = ConstentNew.TYPE_NOTIFY_BANKER;
                    gamePresenterNew.sendMessage(bankerMessage);
                    updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                    txtMoney.setText(AppPrefrence.getAmount(context) + "");
                }
                if (bundle.getInt("type") == 2) {
//                    resetUserStatue();
                }
                break;
            case ConstentNew.LACKGAMERPOP:
                if (bundle == null)
                    return;
                if (bundle.getInt("type") == 1) {
                    Bean_Message bankerMessage = new Bean_Message();
                    bankerMessage.gamerPos = ConstentNew.USERPOS;
                    bankerMessage.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                    bankerMessage.type = ConstentNew.TYPE_RENEW_MONEY;
                    gamePresenterNew.sendMessage(bankerMessage);
                    updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                    txtMoney.setText(AppPrefrence.getAmount(context) + "");
                }
                if (bundle.getInt("type") == 2) {
                    resetUserStatue();
                }
                if (bundle.getInt("type") == 5) {
                    dismissWithCheckPop(lackMoneyNotifyPop);
                }
                break;
            case ConstentNew.BANKERNOTIFYPOP:
                if (bundle == null)
                    return;
                Bean_Message message = new Bean_Message();
                message.type = ConstentNew.TYPE_NOTIFY_BANKER;
                message.gamerPos = ConstentNew.USERPOS;
                message.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                gamePresenterNew.sendMessage(message);
                updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                break;
            case ConstentNew.BANKERCHARGEMONEY:
                Bean_Message bankerMessage = new Bean_Message();
                bankerMessage.type = ConstentNew.TYPE_NOTIFY_BANKER;
                bankerMessage.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                bankerMessage.gamerPos = 1;
                gamePresenterNew.sendMessage(bankerMessage);
                updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                break;
            case ConstentNew.GAMERCHARGEMONEY:
                Bean_Message gamerMessage = new Bean_Message();
                gamerMessage.type = ConstentNew.TYPE_NOTIFY_BANKER;
                gamerMessage.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                gamerMessage.gamerPos = ConstentNew.USERPOS;
                gamePresenterNew.sendMessage(gamerMessage);
                gamePresenterNew.updateTableMoney();
                updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                break;
            case ConstentNew.KICKOUTPOP:
                if (kickOutNotifyPop != null)
                    kickOutNotifyPop.dismiss();
                finish();
                break;
            case ConstentNew.DISCONNECTPOP:
                dismissWithCheckPop(discontectPop);
                break;
        }
    }

    private void setUserMoney() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtMoney.setText(AppPrefrence.getAmount(context) + "");
                    }
                });
            }
        }, 200);


    }

    private void resetUserStatue() {
        if (!ConstentNew.IS_BANKER) {
            AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) + ConstentNew.GAMER_TABLE_MONEY);
            txtMoney.setText(AppPrefrence.getAmount(context) + "");
        }
        ConstentNew.IS_BANKER = false;
        ConstentNew.IS_GAMER = false;
        try {
            ConstentNew.IS_HAS_GAMER[ConstentNew.USERPOS - 1] = false;
        } catch (Exception e) {
        }

        Bean_Message message = new Bean_Message();
        message.gamerPos = ConstentNew.USERPOS;
        message.type = ConstentNew.TYPE_EXIT_GAME;
        message.userId = AppPrefrence.getUserNo(context);
        gamePresenterNew.sendMessage(message);
        clearUserInfo(ConstentNew.USERPOS);
        ConstentNew.USERPOS = -1;
        visBetPoint();
    }


    private void resetDisUserStatue() {

        ConstentNew.IS_BANKER = false;
        ConstentNew.IS_GAMER = false;
        try {
            ConstentNew.IS_HAS_GAMER[ConstentNew.USERPOS - 1] = false;
        } catch (Exception e) {
        }

        Bean_Message message = new Bean_Message();
        message.gamerPos = ConstentNew.USERPOS;
        message.type = ConstentNew.TYPE_EXIT_GAME;
        message.userId = ConstentNew.USER_ID;
        gamePresenterNew.sendMessage(message);
        clearUserInfo(ConstentNew.USERPOS);
        ConstentNew.USERPOS = -1;
        visBetPoint();
    }


    private void clearUserInfo(int pos) {
        try {
            ConstentNew.IS_HAS_GAMER[pos - 1] = false;
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        imgAdd.setVisibility(View.GONE);
        switch (pos) {
            case 1:
                glideImg(R.mipmap.site_empty, imgHeadTop);
                txtBankerMoney.setVisibility(View.GONE);
                break;
            case 2:
                glideImg(R.mipmap.site_empty, imgHeadLeft);
                txtLeftMoney.setVisibility(View.GONE);
                break;
            case 3:
                glideImg(R.mipmap.site_empty, imgHeadBottom);
                txtMidMoney.setVisibility(View.GONE);
                break;
            case 4:
                glideImg(R.mipmap.site_empty, imgHeadRight);
                txtRightMoney.setVisibility(View.GONE);
                break;
        }
        if (!ConstentNew.IS_HAS_GAMER[0] && !ConstentNew.IS_HAS_GAMER[1] && !ConstentNew.IS_HAS_GAMER[2] && !ConstentNew.IS_HAS_GAMER[3]) {
            chessList.clear();
            gameChessAdapter.notifyDataSetChanged();
            recyChess.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserInfo(Bean_Message bean_message) {

        switch (bean_message.gamerPos) {
            case 1:
                if (TextUtils.equals(bean_message.tableUser.id, ConstentNew.USER_ID)) {
                    imgAdd.setVisibility(View.GONE);
                    ConstentNew.IS_BANKER = true;
                } else {
                    ConstentNew.IS_BANKER = false;
                }
                glideImg(bean_message.tableUser.user_logo, imgHeadTop);
                txtBankerMoney.setVisibility(View.VISIBLE);
                txtBankerMoney.setText(bean_message.tableUser.lookmonery + "");
                break;
            case 2:
                if (TextUtils.equals(bean_message.tableUser.id, ConstentNew.USER_ID)) {
                    imgAdd.setVisibility(View.VISIBLE);
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                }
                glideImg(bean_message.tableUser.user_logo, imgHeadLeft);
                txtLeftMoney.setVisibility(View.VISIBLE);
                txtLeftMoney.setText(bean_message.tableUser.lookmonery + "");
                break;
            case 3:
                if (TextUtils.equals(bean_message.tableUser.id, ConstentNew.USER_ID)) {
                    imgAdd.setVisibility(View.VISIBLE);
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                }
                glideImg(bean_message.tableUser.user_logo, imgHeadBottom);
                txtMidMoney.setVisibility(View.VISIBLE);
                txtMidMoney.setText(bean_message.tableUser.lookmonery + "");
                break;
            case 4:
                if (TextUtils.equals(bean_message.tableUser.id, ConstentNew.USER_ID)) {
                    imgAdd.setVisibility(View.VISIBLE);
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                }
                glideImg(bean_message.tableUser.user_logo, imgHeadRight);
                txtRightMoney.setVisibility(View.VISIBLE);
                txtRightMoney.setText(bean_message.tableUser.lookmonery + "");
                break;
        }
    }

    @Override
    public void kickOut() {
        if (kickGamerOut == null)
            kickGamerOut = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        kickGamerOut.setOwnerActivity(this);
        View view = LayoutInflater.from(context).inflate(R.layout.down_table_dialog, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTable();
                kickGamerOut.dismiss();
            }
        });
        view.findViewById(R.id.img_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTableAsync();
                kickGamerOut.dismiss();
            }
        });
        final TextView txtContent = (TextView) view.findViewById(R.id.txt_content);
        kickGamerOut.setView(view);
        if (this != null && !isFinishing())
            kickGamerOut.show();

        new CountDownTimer(5 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtContent.setText("账户余额不足\n" + millisUntilFinished / 1000 + "秒后退出房间");
            }

            @Override
            public void onFinish() {
                gamePresenterNew.resetUserInfo(ConstentNew.USERPOS);
                resetUserStatue();
                kickGamerOut.dismiss();
            }
        }.start();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (kickOutNotifyPop == null)
//                    kickOutNotifyPop = new KickOutNotifyPop(GamingActivityNew.this);
//                kickOutNotifyPop.showPop(imgBack);
//                kickOutNotifyPop.setPopInterfacer(GamingActivityNew.this, ConstentNew.KICKOUTPOP);
//            }
//        });

    }

    @Override
    public void setUserMoney(int pos, Bean_TableDetial.TableUser user) {
        if (pos == ConstentNew.USERPOS && ConstentNew.IS_GAMER)
            ConstentNew.GAMER_TABLE_MONEY = user.lookmonery;
        switch (pos) {
            case 1:
                if (TextUtils.equals(user.id, AppPrefrence.getUserNo(context))) {
                    ConstentNew.IS_BANKER = true;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = 1;
                    invisBetPoint();
                } else {
                    ConstentNew.IS_BANKER = false;
                }
                glideImg(user.user_logo, imgHeadTop);
                txtBankerMoney.setVisibility(View.VISIBLE);
                txtBankerMoney.setText(user.lookmonery + "");
                break;
            case 2:
                if (TextUtils.equals(user.id, AppPrefrence.getUserNo(context))) {
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = 2;
                    visBetPoint();
                }
                glideImg(user.user_logo, imgHeadLeft);
                txtLeftMoney.setVisibility(View.VISIBLE);
                txtLeftMoney.setText(user.lookmonery + "");
                break;
            case 3:
                if (TextUtils.equals(user.id, AppPrefrence.getUserNo(context))) {
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = 3;
                    visBetPoint();
                }
                glideImg(user.user_logo, imgHeadBottom);
                txtMidMoney.setVisibility(View.VISIBLE);
                txtMidMoney.setText(user.lookmonery + "");
                break;
            case 4:
                if (TextUtils.equals(user.id, AppPrefrence.getUserNo(context))) {
                    ConstentNew.IS_BANKER = false;
                    ConstentNew.IS_GAMER = true;
                    ConstentNew.USERPOS = 4;
                    visBetPoint();
                }
                glideImg(user.user_logo, imgHeadRight);
                txtRightMoney.setVisibility(View.VISIBLE);
                txtRightMoney.setText(user.lookmonery + "");
                break;
        }
    }

    @Override
    public void refreshUserMoney(final long amount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppPrefrence.setAmount(context, amount);
                txtMoney.setText(amount + "");
            }
        });

    }

    @Override
    public void changeBankerNotify() {

        if (changeBankerPop == null)
            changeBankerPop = new ChangeBankerPop(context);
        changeBankerPop.setContent("你已成为庄家，上庄金额 " + ConstentNew.BANKER_LIMIT_MONEY + " 金币");
        try {
            changeBankerPop.showPop(imgHead);
        } catch (Exception e) {
        }
    }

    @Override
    public void bankerExit() {
        if (bankerBackNotify == null)
            bankerBackNotify = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        View view = LayoutInflater.from(context).inflate(R.layout.banker_exit_notify_pop, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTable();
                dismissWithCheck(bankerBackNotify);
            }
        });
        view.findViewById(R.id.img_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTableAsync();
                dismissWithCheck(bankerBackNotify);
            }
        });
        ((TextView) view.findViewById(R.id.txt_content)).setText("是否退出游戲？");
        bankerBackNotify.setView(view);
        bankerBackNotify.show();
    }

    @Override
    public void clearRenewPop() {
        if (lackBankerNotifyPop != null) {
            dismissWithCheckPop(lackBankerNotifyPop);
        }
        if (lackMoneyNotifyPop != null) {
            dismissWithCheckPop(lackMoneyNotifyPop);
        }
    }

    @Override
    public void clearChargePop() {
        if (chargeBankerNotify != null && chargeBankerNotify.isShowing()) {
            chargeBankerNotify.dismiss();
        }
        if (chargeMoneyNotify != null && chargeMoneyNotify.isShowing()) {
            chargeMoneyNotify.dismiss();
        }
    }

    @Override
    public void betMoneyAble() {
        txtMoneyLeft.setClickable(true);
        txtMoneyMid.setClickable(true);
        txtMoneyRight.setClickable(true);
    }

    @Override
    public void betMoneyDisable() {
        txtMoneyLeft.setClickable(false);
        txtMoneyMid.setClickable(false);
        txtMoneyRight.setClickable(false);
    }

    @Override
    public void getInSuccess() {
        TIMGroupManager.getInstance().applyJoinGroup(ConstentNew.GROUP_ID, "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                if (i != 10013 && i != 10021) {
                    toastMsg("获取房间数据失败,请重试");
                } else if (i == 10013) {
                    gamePresenterNew.startService();
                    gamePresenterNew.initTable();
                }

            }

            @Override
            public void onSuccess() {
                gamePresenterNew.startService();
                gamePresenterNew.initTable();
            }
        });
    }

    @Override
    public void disconnect() {
        if (discontectPop == null) {
            discontectPop = new DiscontectPop(context);
            discontectPop.setPopInterfacer(this, ConstentNew.DISCONNECTPOP);
        }
        discontectPop.setNotify("你已经掉线,请检查网络后重试!");
        if (this != null && !isFinishing()) {
            discontectPop.showPop(txtMoney);
        } else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        discontectPop.showPop(txtMoney);
                    }
                });
            }
        }, 300);

        gamePresenterNew.resetUserInfo(ConstentNew.USERPOS);
        resetDisUserStatue();
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
            case ConstentNew.LACKBANKERPOP:
                dismissWithCheckPop(lackBankerNotifyPop);
                break;
        }
    }


    @OnClick({R.id.img_back, R.id.rel_head_left, R.id.rel_head_bottom, R.id.rel_head_right, R.id.rel_head_top, R.id.img_gameing_user,
            R.id.img_head, R.id.img_add, R.id.txt_money_left, R.id.txt_money_mid, R.id.txt_money_right, R.id.img_setting,
            R.id.fl_user_left, R.id.fl_user_mid, R.id.fl_user_right, R.id.img_shake_dice})
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
                if (audiencePop == null)
                    audiencePop = new AudiencelPop(GamingActivityNew.this);
                audiencePop.showPop(txtBankerMoney);
                audiencePop.setPresenter(gamePresenterNew);
                audiencePop.setPopInterfacer(this, ConstentNew.AUDIENCEPOP);
                break;
            case R.id.img_head:
                gamePresenterNew.showPersonalPop();
                break;
            case R.id.img_add:
                if (ConstentNew.IS_GAMER && !ConstentNew.IS_BANKER)
                    if (AppPrefrence.getAmount(context) < ConstentNew.LEFTPOINT) {
                        toastMsg("账户余额不足");
                        return;
                    }
                gamePresenterNew.addMoney();
                break;
            case R.id.txt_money_left:
                if (ConstentNew.USERPOS < 1 && !ConstentNew.IS_GAMER) {
                    Tools.toastMsgCenter(context, "选择要投注的用户");
                    return;
                }
                if (ConstentNew.IS_GAMER && ConstentNew.GAMER_TABLE_MONEY < ConstentNew.LEFTPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                if (!ConstentNew.IS_GAMER && AppPrefrence.getAmount(context) < ConstentNew.LEFTPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                gamePresenterNew.betMoney(ConstentNew.LEFTPOINT);
                int[] leftPoint = new int[2];
                txtMoneyLeft.getLocationInWindow(leftPoint);
                betMoneyAnim(leftPoint, 0);
                break;
            case R.id.txt_money_mid:
                if (ConstentNew.USERPOS < 1 && !ConstentNew.IS_GAMER) {
                    Tools.toastMsgCenter(context, "选择要投注的用户");
                    return;
                }
                if (ConstentNew.IS_GAMER && ConstentNew.GAMER_TABLE_MONEY < ConstentNew.MIDPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                if (!ConstentNew.IS_GAMER && AppPrefrence.getAmount(context) < ConstentNew.MIDPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                gamePresenterNew.betMoney(ConstentNew.MIDPOINT);
                int[] midPoint = new int[2];
                txtMoneyMid.getLocationInWindow(midPoint);
                betMoneyAnim(midPoint, 1);
                break;
            case R.id.txt_money_right:
                if (ConstentNew.USERPOS < 1 && !ConstentNew.IS_GAMER) {
                    Tools.toastMsgCenter(context, "选择要投注的用户");
                    return;
                }
                if (ConstentNew.IS_GAMER && ConstentNew.GAMER_TABLE_MONEY < ConstentNew.RIGHTPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                if (!ConstentNew.IS_GAMER && AppPrefrence.getAmount(context) < ConstentNew.RIGHTPOINT) {
                    Tools.toastMsgCenter(context, "金幣不足");
                    return;
                }
                gamePresenterNew.betMoney(ConstentNew.RIGHTPOINT);
                int[] rightPoint = new int[2];
                txtMoneyRight.getLocationInWindow(rightPoint);
                betMoneyAnim(rightPoint, 2);
                break;
            case R.id.img_setting:
                gamePresenterNew.showSettingPop();
                break;
            case R.id.fl_user_left:
                if (ConstentNew.IS_GAMER)
                    return;
                flUserLeft.setBackgroundResource(R.mipmap.table_first_selected);
                flUserMid.setBackgroundResource(R.mipmap.table_second);
                flUserRight.setBackgroundResource(R.mipmap.table_last);
                ConstentNew.USERPOS = 2;
                break;
            case R.id.fl_user_mid:
                if (ConstentNew.IS_GAMER)
                    return;
                flUserMid.setBackgroundResource(R.mipmap.table_second_selected);
                flUserLeft.setBackgroundResource(R.mipmap.table_first);
                flUserRight.setBackgroundResource(R.mipmap.table_last);
                ConstentNew.USERPOS = 3;
                break;
            case R.id.fl_user_right:
                if (ConstentNew.IS_GAMER)
                    return;
                flUserRight.setBackgroundResource(R.mipmap.table_thrid_selected);
                flUserMid.setBackgroundResource(R.mipmap.table_second);
                flUserLeft.setBackgroundResource(R.mipmap.table_first);
                ConstentNew.USERPOS = 4;
                break;
            case R.id.img_shake_dice:
                gamePresenterNew.shakeDice();
                break;
        }
    }


    private int[] getChessPoint(String point) {
        int[] pointInt = new int[]{0, 0};
        if (!TextUtils.isEmpty(point)) {
            String[] pointString = point.split(",");
            pointInt[0] = Integer.parseInt(pointString[0]);
            pointInt[1] = Integer.parseInt(pointString[1]);
        }
        return pointInt;
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
        switch (ConstentNew.USERPOS) {
            case 2:
                flUserLeft.getLocationInWindow(endPoint);
                break;
            case 3:
                flUserMid.getLocationInWindow(endPoint);
                break;
            case 4:
                flUserRight.getLocationInWindow(endPoint);
                break;
        }

        imgBet.setLeft(startPoint[0]);
        imgBet.setTop(startPoint[1]);
        viewGroup.addView(imgBet);
        viewGroup.setBackgroundColor(Color.TRANSPARENT);

        TranslateAnimation translateAnimation = new TranslateAnimation(startPoint[0], endPoint[0], startPoint[1], endPoint[1]);
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

    @Override
    public void onBackPressed() {
        gamePresenterNew.back();
        return;
    }


    private void downTableAsync() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "UserSiteUp", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.id == 1) {
                    gamePresenterNew.resetUserInfo(ConstentNew.USERPOS);
                    resetUserStatue();
                } else Tools.toastMsgCenter(context, baseBean.msg);

            }
        });
    }

    private void levelTable() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "LevelTable", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.id == 1) {
                    resetUserStatue();
                    logoutGroup();
                    finish();
                } else Tools.toastMsgCenter(context, baseBean.msg);
            }
        });
    }

    private void bankerSelect(final int type) {
        Tools.debug("banker_notify---" + ConstentNew.TABLE_ID + "--ver--" + ConstentNew.GAMEROUND + "--type--" + type);
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("token", CommonUntilities.TOKEN);
        params.put("ver", ConstentNew.GAMEROUND + "");
        params.put("type", type + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "UserThirdRound", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response))
                    return;
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.id > 0) {
                    Bean_Message message = new Bean_Message();
                    message.type = ConstentNew.TYPE_NOTIFY_BANKER;
                    message.gamerPos = ConstentNew.USERPOS;
                    message.betPoint = ConstentNew.GAMER_TABLE_MONEY;
//                    gamePresenterNew.sendMessage(message);
                } else Tools.toastMsgCenter(context, baseBean.msg);

            }
        });
    }

    public void dismissWithCheck(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

                // if the Context used here was an activity AND it hasn't been finished or destroyed
                // then dismiss it
                if (context instanceof Activity) {

                    // Api >=17
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                            dismissWithTryCatch(dialog);
                        }
                    } else {

                        // Api < 17. Unfortunately cannot check for isDestroyed()
                        if (!((Activity) context).isFinishing()) {
                            dismissWithTryCatch(dialog);
                        }
                    }
                } else
                    // if the Context used wasn't an Activity, then dismiss it too
                    dismissWithTryCatch(dialog);
            }
            dialog = null;
        }
    }

    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }

    public void dismissWithCheckPop(PopupWindow dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {

                //get the Context object that was used to great the dialog

                // if the Context used here was an activity AND it hasn't been finished or destroyed
                // then dismiss it

                // Api >=17
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!isFinishing() && !isDestroyed()) {
                        dismissWithTryCatchPop(dialog);
                    }
                } else {

                    // Api < 17. Unfortunately cannot check for isDestroyed()
                    if (!((Activity) context).isFinishing()) {
                        dismissWithTryCatchPop(dialog);
                    }
                }
            } else
                // if the Context used wasn't an Activity, then dismiss it too
                dismissWithTryCatchPop(dialog);
            dialog = null;
        }
    }

    public void dismissWithTryCatchPop(PopupWindow dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }


}
