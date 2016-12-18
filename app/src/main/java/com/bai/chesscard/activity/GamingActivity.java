package com.bai.chesscard.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bai.chesscard.BaseActivity;
import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.R;
import com.bai.chesscard.adapter.GameChessAdapter;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.dialog.AddMoney;
import com.bai.chesscard.dialog.AudiencelPop;
import com.bai.chesscard.dialog.BankerExitNotifyPop;
import com.bai.chesscard.dialog.DiscontectNotifyPop;
import com.bai.chesscard.dialog.ExitBankerPop;
import com.bai.chesscard.dialog.ExitNotifyPop;
import com.bai.chesscard.dialog.GamerExitNotifyPop;
import com.bai.chesscard.dialog.LackBankerNotifyPop;
import com.bai.chesscard.dialog.LackMoneyNotifyPop;
import com.bai.chesscard.dialog.PersonalPopInfo;
import com.bai.chesscard.dialog.SettingPop;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.interfacer.PopInterfacer;
import com.bai.chesscard.presenter.GamePresenter;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.Tools;
import com.bai.chesscard.widget.StrokeTextView;
import com.bumptech.glide.Glide;
import com.tencent.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/11.
 */

public class GamingActivity extends BaseActivity implements GameOprateView, PopInterfacer {
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
    @BindView(R.id.fl_time)
    FrameLayout flTime;
    @BindView(R.id.img_chess_top_count)
    ImageView imgChessTopCount;
    @BindView(R.id.img_chess_top_multiple)
    ImageView imgChessTopMultiple;
    @BindView(R.id.fl_user_left)
    FrameLayout flUserLeft;
    @BindView(R.id.fl_user_mid)
    FrameLayout flUserMid;
    @BindView(R.id.fl_user_right)
    FrameLayout flUserRight;

    private int[] timeRes = new int[]{R.mipmap.num_zero, R.mipmap.num_one, R.mipmap.num_two, R.mipmap.num_three, R.mipmap.num_four, R.mipmap.num_five, R.mipmap.num_six,
            R.mipmap.num_seven, R.mipmap.num_eight, R.mipmap.num_nine, R.mipmap.num_ten};
    private int[] chessRes = new int[]{R.mipmap.chess_one, R.mipmap.chess_two, R.mipmap.chess_three, R.mipmap.chess_four, R.mipmap.chess_five, R.mipmap.chess_six, R.mipmap.chess_seven,
            R.mipmap.chess_eight, R.mipmap.chess_nine};
    private int[] pointRes = new int[]{R.mipmap.point_zero, R.mipmap.point_one, R.mipmap.point_two, R.mipmap.point_three, R.mipmap.point_four, R.mipmap.point_five,
            R.mipmap.point_six, R.mipmap.point_seven, R.mipmap.point_eight, R.mipmap.point_nine, R.mipmap.point_double};

    private int[] pointGrayRes = new int[]{R.mipmap.point_zero_gray, R.mipmap.point_one_gray, R.mipmap.point_two_gray, R.mipmap.point_three_gray, R.mipmap.point_four_gray, R.mipmap.point_five_gray,
            R.mipmap.point_six_gray, R.mipmap.point_seven_gray, R.mipmap.point_eight_gray, R.mipmap.point_nine_gray, R.mipmap.point_double_gray};

    private int[] mutilRes = new int[]{R.mipmap.one_multiple, R.mipmap.double_multiple, R.mipmap.trable_multiple};
    private GamePresenter gamePresenter;
    private ProgressDialog progressDialog;
    private SettingPop settingPop;
    private PersonalPopInfo personalPopInfo;
    private AudiencelPop audiencePop;
    private ExitNotifyPop exitNotifyPop;
    private AddMoney addMoney;
    private DiscontectNotifyPop discontectNotifyPop;
    private LackMoneyNotifyPop lackMoneyNotifyPop;
    private GamerExitNotifyPop gamerExitNotifyPop;
    private BankerExitNotifyPop bankerExitNotifyPop;
    private LackBankerNotifyPop lackBankerNotifyPop;
    private ExitBankerPop exitBankerPop;

    private int[] pointList = new int[]{100, 500, 1000};
    private List chessList;
    private GameChessAdapter gameChessAdapter;
    private int wide;

    /**
     * 标识用户的身份,
     * 0 为观众
     * 1 为庄家
     * 2 为初
     * 3 为天
     * 4 为尾
     */
    private int identify = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);
        ButterKnife.bind(this);
        clearData();
        initView();
        init();
        initData();
        resetData();
    }

    private void clearData() {
        Constent.USERMONEY = 0;
        Constent.ISSHAKING = false;
        Constent.BETLEDTPOINT = 0;
        Constent.BETMIDPOINT = 0;
        Constent.BETRIGHTPOINT = 0;
        Constent.ISBANKER = false;
        Constent.ISGAMER = false;
        for (int i = 0; i < 4; i++) {
            Constent.isHasUser[i] = false;
        }
    }

    /**
     * 重置桌面上面的数据
     */
    private void resetData() {
        txtMoney.setText(Constent.USERMONEY + "");
        txtTotalLeft.setText("");
        Constent.BETLEDTPOINT = 0;
        txtTotalMid.setText("");
        Constent.BETMIDPOINT = 0;
        txtTotalRight.setText("");
        Constent.BETRIGHTPOINT = 0;
    }

    private void initView() {
        roomId = getIntent().getStringExtra("roomId");
        tableId = getIntent().getStringExtra("tableId");
        Constent.USERMONEY = getIntent().getIntExtra("point", 0);
        AppPrefrence.setAmount(context, AppPrefrence.getAmount(context) - Constent.USERMONEY);
        Constent.MINCOUNT = getIntent().getIntExtra("point", 0);

        Constent.USERID = AppPrefrence.getUserNo(context);
        Constent.ROOMID = roomId;
        Constent.TABLEID = tableId;
        wide = (int) ((int) Tools.getScreenWide(context) * 0.05);
        invisTime();
        invisChess();
        invisPoint();
        inVisMul();
        inVisPointCard();
        clearSelectBg();
    }

    private void inVisPointCard() {
        imgBgLeft.setVisibility(View.INVISIBLE);
        imgBgMid.setVisibility(View.INVISIBLE);
        imgBgRight.setVisibility(View.INVISIBLE);
    }

    private void clearSelectBg() {
        flUserLeft.setBackgroundResource(0);
        flUserMid.setBackgroundResource(0);
        flUserRight.setBackgroundResource(0);
    }

    /**
     * 为了防止多次投注出现错误,所以做出限制,投注过程中投注按钮不可用,
     * 投注完成之后置为可用
     *
     * @param clickable
     */
    private void moneyViewClickable(boolean clickable) {
        txtMoneyLeft.setClickable(clickable);
        txtMoneyMid.setClickable(clickable);
        txtMoneyRight.setClickable(clickable);
        flUserLeft.setClickable(clickable);
        flUserMid.setClickable(clickable);
        flUserRight.setClickable(clickable);
    }

    /**
     * 桌面倍数隐藏
     */
    private void inVisMul() {
        imgChessMidMultiple.setVisibility(View.INVISIBLE);
        imgChessLeftMultiple.setVisibility(View.INVISIBLE);
        imgChessRightMultiple.setVisibility(View.INVISIBLE);
        imgChessTopMultiple.setVisibility(View.INVISIBLE);
    }

    /**
     * 桌面点数隐藏
     */
    private void invisPoint() {    //桌面点数控件隐藏
        imgChessTopCount.setVisibility(View.INVISIBLE);
        imgChessLeftCount.setVisibility(View.INVISIBLE);
        imgChessMidCount.setVisibility(View.INVISIBLE);
        imgChessRightCount.setVisibility(View.INVISIBLE);
    }

    /**
     * 桌面牌隐藏
     */
    private void invisChess() {
        //桌面牌控件隐藏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wide, (int) (wide * 1.51));
        imgTopLeft.setVisibility(View.GONE);
        imgTopLeft.setLayoutParams(params);
        imgTopRight.setVisibility(View.GONE);
        imgTopRight.setLayoutParams(params);

        imgChessLeftOne.setVisibility(View.GONE);
        imgChessLeftOne.setLayoutParams(params);
        imgChessLeftTwo.setVisibility(View.GONE);
        imgChessLeftTwo.setLayoutParams(params);

        imgChessMidOne.setVisibility(View.GONE);
        imgChessMidOne.setLayoutParams(params);
        imgChessMidTwo.setVisibility(View.GONE);
        imgChessMidTwo.setLayoutParams(params);

        imgChessRightOne.setVisibility(View.GONE);
        imgChessRightOne.setLayoutParams(params);
        imgChessRightTwo.setVisibility(View.GONE);
        imgChessRightTwo.setLayoutParams(params);
    }

    /**
     * 倒计时控件隐藏
     */
    private void invisTime() {
        //倒计时控件隐藏
        flTime.setVisibility(View.INVISIBLE);
        imgTime.setVisibility(View.INVISIBLE);
        imgTimeStatus.setVisibility(View.INVISIBLE);
    }

    /**
     * 桌面倍数显示
     */
    private void visMul() {
        imgChessMidMultiple.setVisibility(View.VISIBLE);
        imgChessLeftMultiple.setVisibility(View.VISIBLE);
        imgChessRightMultiple.setVisibility(View.VISIBLE);
        imgChessTopMultiple.setVisibility(View.VISIBLE);
    }

    /**
     * 桌面点数显示
     */
    private void visPoint() {    //桌面点数控件隐藏
        imgChessTopCount.setVisibility(View.VISIBLE);
        imgChessLeftCount.setVisibility(View.VISIBLE);
        imgChessMidCount.setVisibility(View.VISIBLE);
        imgChessRightCount.setVisibility(View.VISIBLE);
    }

    /**
     * 桌面牌显示
     */
    private void visChess() {
        //桌面牌控件隐藏
        imgTopLeft.setVisibility(View.VISIBLE);
        imgTopRight.setVisibility(View.VISIBLE);

        imgChessLeftOne.setVisibility(View.VISIBLE);
        imgChessLeftTwo.setVisibility(View.VISIBLE);

        imgChessMidOne.setVisibility(View.VISIBLE);
        imgChessMidTwo.setVisibility(View.VISIBLE);

        imgChessRightOne.setVisibility(View.VISIBLE);
        imgChessRightTwo.setVisibility(View.VISIBLE);
    }

    /**
     * 倒计时控件显示
     */
    private void visTime() {
        //倒计时控件隐藏
        flTime.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        imgTimeStatus.setVisibility(View.VISIBLE);
    }

    private String tableId = "", roomId = "";

    private void initData() {
        gamePresenter.getTableInfo(roomId, tableId);
        Glide.with(context).load(AppPrefrence.getAvatar(context)).error(R.mipmap.icon_default_head).into(imgHead);
        gamePresenter.getIn(tableId, AppPrefrence.getUserNo(context));
    }

    private void init() {
        chessList = new ArrayList();
        gamePresenter = new GamePresenter(this, tableId, TIMConversationType.Group);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llTable.getLayoutParams();
        params.width = (int) (Tools.getScreenWide(context) * 0.6);
        params.height = (int) (Tools.getScreenHeight(context) * 0.55);
        llTable.setLayoutParams(params);
        gamePresenter.startService(this);
        txtUserName.setText("昵称: " + AppPrefrence.getUserName(context));
        recyChess.setLayoutManager(new GridLayoutManager(context, 8, LinearLayoutManager.VERTICAL, false));
        gameChessAdapter = new GameChessAdapter(chessList);
        recyChess.setAdapter(gameChessAdapter);
    }

    @OnClick({R.id.img_back, R.id.rel_head_left, R.id.rel_head_bottom, R.id.rel_head_right, R.id.rel_head_top, R.id.img_gameing_user,
            R.id.img_head, R.id.img_add, R.id.txt_money_left, R.id.txt_money_mid, R.id.txt_money_right, R.id.img_setting,
            R.id.fl_user_left, R.id.fl_user_mid, R.id.fl_user_right})
    public void cardClick(View view) {
        super.cardClick(view);
        switch (view.getId()) {
            case R.id.img_back: //返回
                gamePresenter.showExitPop();
                break;
            case R.id.rel_head_left:
                //初家信息
                gamePresenter.showUserInfo(1, AppPrefrence.getUserNo(context));
                break;
            case R.id.rel_head_bottom:
                //天家信息
                gamePresenter.showUserInfo(2, AppPrefrence.getUserNo(context));
                break;
            case R.id.rel_head_right:
                //尾家信息
                gamePresenter.showUserInfo(3, AppPrefrence.getUserNo(context));
                break;
            case R.id.rel_head_top:
                //庄家信息
                gamePresenter.showUserInfo(0, AppPrefrence.getUserNo(context));
                break;
            case R.id.img_gameing_user:
                //观众信息
                gamePresenter.showAudience();
                break;
            case R.id.img_head:

                break;
            case R.id.img_add:
                //追加金币
                gamePresenter.addMoney();
                break;
            case R.id.txt_money_left:
                //投注
                if (pointList == null || pointList.length == 0)
                    return;
                int[] start_location = new int[]{(int) (Tools.getScreenWide(this) / 2 - Tools.dip2px(this, 20)), (int) (Tools.getScreenHeight(this) - Tools.dip2px(this, 50))};
                gamePresenter.betMoney(this, AppPrefrence.getUserNo(context), pointList[0], tableId, roomId, start_location, 0);
                break;
            case R.id.txt_money_mid:
                //投注
                int[] start_location1 = new int[]{(int) (Tools.getScreenWide(this) * 3 / 5), (int) (Tools.getScreenHeight(this) - Tools.dip2px(this, 50))};
                gamePresenter.betMoney(this, AppPrefrence.getUserNo(context), pointList[1], tableId, roomId, start_location1, 1);
                break;
            case R.id.txt_money_right:
                //投注
                int[] start_location2 = new int[]{(int) (Tools.getScreenWide(this) * 4 / 5 - Tools.dip2px(this, 40)), (int) (Tools.getScreenHeight(this) - Tools.dip2px(this, 50))};
                gamePresenter.betMoney(this, AppPrefrence.getUserNo(context), pointList[2], tableId, roomId, start_location2, 2);
                break;
            case R.id.img_setting:
                //设置选项
                gamePresenter.showSetting();
                break;
            case R.id.fl_user_left:
                //选择初家进行投注
                if (Constent.ISGAMER)
                    return;
                Constent.SELECTPOS = 1;
                setChoice(1);
                break;
            case R.id.fl_user_mid:
                //选择天家进行投注
                if (Constent.ISGAMER)
                    return;
                Constent.SELECTPOS = 2;
                setChoice(2);
                break;
            case R.id.fl_user_right:
                //选择尾家进行投注
                if (Constent.ISGAMER)
                    return;
                Constent.SELECTPOS = 3;
                setChoice(3);
                break;
        }
    }

    private void setChoice(int pos) {
        clearSelectBg();
        switch (pos) {
            case 1:
                flUserLeft.setBackgroundResource(R.drawable.bg_select_user);
                break;
            case 2:
                flUserMid.setBackgroundResource(R.drawable.bg_select_user);
                break;
            case 3:
                flUserRight.setBackgroundResource(R.drawable.bg_select_user);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gamePresenter.onDestory();
        AppPrefrence.setAmount(this, AppPrefrence.getAmount(context) + Constent.USERMONEY);
        Constent.USERMONEY = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showExitPop() {
        extiPop();
    }

    private void extiPop() {
        if (!Constent.ISBANKER && !Constent.ISGAMER) {
            if (exitNotifyPop == null)
                exitNotifyPop = new ExitNotifyPop(context);
            exitNotifyPop.setIds(tableId, roomId, AppPrefrence.getUserNo(context), identify + "");
            exitNotifyPop.setNotify("是否退出游戏?");
            exitNotifyPop.setPopInterfacer(this, 5);
            exitNotifyPop.showPop(txtHeadBottom);
        }
        if (Constent.ISGAMER && !Constent.ISBANKER) {
            if (gamerExitNotifyPop == null)
                gamerExitNotifyPop = new GamerExitNotifyPop(context);
            gamerExitNotifyPop.setNotify("下桌之后可以进行观战押注\n退出将会直接退出该局游戏");
            gamerExitNotifyPop.setIds(tableId, roomId, AppPrefrence.getUserNo(context), identify + "");
            gamerExitNotifyPop.setPopInterfacer(this, 9);
            gamerExitNotifyPop.showPop(txtHeadBottom);
        }
        if (Constent.ISBANKER) {
            if (bankerExitNotifyPop == null)
                bankerExitNotifyPop = new BankerExitNotifyPop(context);
            bankerExitNotifyPop.setNotify("下庄之后可以进行观战押注\n" +
                    "退出将会直接退出该局游戏");
            bankerExitNotifyPop.setIds(tableId, roomId, AppPrefrence.getUserNo(context), identify + "");
            bankerExitNotifyPop.setPopInterfacer(this, 10);
            bankerExitNotifyPop.showPop(txtHeadBottom);
        }
    }

    @Override
    public void setChessData(List<Bean_ChessList.Chess> data) {
        chessList.clear();
        chessList.addAll(data);
        gameChessAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUserInfo(Bean_TableDetial.TableUser user) {
        if (personalPopInfo == null)
            personalPopInfo = new PersonalPopInfo(context);
        personalPopInfo.setInfo(user);
        personalPopInfo.showPop(txtHeadBottom);
        personalPopInfo.setPopInterfacer(this, 2);
    }

    @Override
    public void showAudience() {
        if (audiencePop != null && audiencePop.isShowing()) {
            audiencePop.dismiss();
            return;
        }
        if (audiencePop == null)
            audiencePop = new AudiencelPop(context);
        audiencePop.setId(tableId);
        audiencePop.showPop(txtHeadBottom);
        audiencePop.setPresenter(gamePresenter);
        audiencePop.setPopInterfacer(this, 3);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void showSetting() {
        if (settingPop == null)
            settingPop = new SettingPop(context);
        settingPop.showPop(imgAdd);
        settingPop.setPopInterfacer(this, 1);
    }

    @Override
    public void addMoney() {
        if (addMoney == null)
            addMoney = new AddMoney(context);
        addMoney.setPopInterfacer(this, 6);
        addMoney.showPop(txtHeadBottom);
    }

    @Override
    public void followMoney(int pos) {

    }

    @Override
    public void showDialog() {
        if (progressDialog == null)
            progressDialog = Tools.getDialog(this, "");
        progressDialog.setMessage("游戏准备中...");
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void openChess(final Bundle bundle) {
        clearSelectBg();
        //庄家
        glideImg(chessRes[bundle.getInt("bankerOne")], imgTopLeft);
        glideImg(chessRes[bundle.getInt("bankerTwo")], imgTopRight);
        imgTopLeft.setVisibility(View.VISIBLE);
        imgTopRight.setVisibility(View.VISIBLE);
        gamePresenter.showPoint(0, bundle.getInt("bankerOne"), bundle.getInt("bankerTwo"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //初家的牌
                glideImg(chessRes[bundle.getInt("leftOne")], imgChessLeftOne);
                glideImg(chessRes[bundle.getInt("leftTwo")], imgChessLeftTwo);
                imgChessLeftOne.setVisibility(View.VISIBLE);
                imgChessLeftTwo.setVisibility(View.VISIBLE);
                gamePresenter.showPoint(1, bundle.getInt("leftOne"), bundle.getInt("leftTwo"));
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
                gamePresenter.showPoint(2, bundle.getInt("bottomOne"), bundle.getInt("bottomTwo"));
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
                gamePresenter.showPoint(3, bundle.getInt("rightOne"), bundle.getInt("rightTwo"));
            }
        }, 1500);

    }

    private void glideImg(String path, ImageView imageView) {
        Glide.with(this).load(path).skipMemoryCache(true).into(imageView);
    }

    private void glideImg(int path, ImageView imageView) {
//        Glide.with(this).load(path).skipMemoryCache(true).into(imageView);
        imageView.setBackgroundResource(path);
    }

    @Override
    public void startCountTime(int time, int type) {
        switch (type) {
            case Constent.BET_MONEY:
                //投注
                imgTimeStatus.setBackgroundResource(R.mipmap.text_set_point);
                break;
            case Constent.OPEN_CHESS:
                //开牌
                imgTimeStatus.setBackgroundResource(R.mipmap.text_open_chess);
                break;
            case Constent.DEAL_CHESS:
                imgTimeStatus.setBackgroundResource(R.mipmap.text_deal_chess);
                break;
            default:
                imgTimeStatus.setBackgroundResource(R.mipmap.text_wait);
                break;
        }
        imgTimeStatus.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        flTime.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing())
                    cancel();
                imgTime.setBackgroundResource(timeRes[(int) (millisUntilFinished / 1000)]);
            }

            @Override
            public void onFinish() {
            }
        };
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    public void openCountTime(int time) {
        imgTimeStatus.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        imgTime.setBackgroundResource(timeRes[time]);
        flTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetStatue() {
        invisChess();
        invisPoint();
        invisTime();
        inVisMul();
        inVisPointCard();
        resetData();
    }

    @Override
    public void endCountTime() {
        imgTime.setVisibility(View.INVISIBLE);
        imgTimeStatus.setVisibility(View.INVISIBLE);
        flTime.setVisibility(View.INVISIBLE);
//        gamePresenter.openChess();
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
    public void hidePoint() {

    }

    @Override
    public void showMultiple(int pos, int mutil) {
        switch (pos) {
            case 0:
                glideImg(mutilRes[mutil], imgChessTopMultiple);
                imgChessTopMultiple.setVisibility(View.VISIBLE);
                break;
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
    public void hideMultiple(int pos) {

    }

    @Override
    public void shakeDice(int one, int two) {
        gamePresenter.shakeDice(this, one, two);
        ChessCardApplication.getInstance().playDiceSound();
    }

    @Override
    public void endDice(int one, int two) {
        ChessCardApplication.getInstance().stopDiecSound();
    }

    @Override
    public void endDice(int pos) {
        ChessCardApplication.getInstance().stopDiecSound();
    }

    @Override
    public void setTableInfo(Bean_TableDetial.TableDetial tableInfo) {
        if (!TextUtils.isEmpty(tableInfo.pointstr)) {
            try {
                String[] pointTmp = tableInfo.pointstr.split(",");
                for (int i = 0; i < pointTmp.length; i++) {
                    pointList[i] = Integer.parseInt(pointTmp[i]);
                }
            } catch (Exception e) {

            }
            txtMoneyLeft.setText(pointList[0] + "");
            txtMoneyMid.setText(pointList[1] + "");
            txtMoneyRight.setText(pointList[2] + "");
        }

        setBankerInfo(tableInfo.first_user);
        setLeftInfo(tableInfo.second_user);
        setBottomInfo(tableInfo.third_user);
        setRightInfo(tableInfo.four_user);
    }

    /**
     * 尾家信息
     *
     * @param four_user
     */
    private void setRightInfo(Bean_TableDetial.TableUser four_user) {
        if (four_user == null || TextUtils.isEmpty(four_user.id)) {
            imgHeadRight.setBackgroundResource(0);
            Constent.isHasUser[3] = false;
            return;
        }
        Constent.isHasUser[3] = true;
        if (!Constent.ISGAMER || !Constent.ISBANKER)
            if (TextUtils.equals(Constent.USERID, four_user.id))
                Constent.ISGAMER = true;
            else Constent.ISGAMER = false;
        Glide.with(this).load(CommonUntilities.PIC_URL + four_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadRight);
    }

    /**
     * 天 家信息
     *
     * @param third_user
     */
    private void setBottomInfo(Bean_TableDetial.TableUser third_user) {
        if (third_user == null || TextUtils.isEmpty(third_user.id)) {
            imgHeadBottom.setBackgroundResource(0);
            Constent.isHasUser[2] = false;
            return;
        }
        Constent.isHasUser[2] = true;
        if (!Constent.ISGAMER || !Constent.ISBANKER)
            if (TextUtils.equals(Constent.USERID, third_user.id))
                Constent.ISGAMER = true;
            else Constent.ISGAMER = false;
        Glide.with(this).load(CommonUntilities.PIC_URL + third_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadBottom);
    }

    /**
     * 初 家信息
     *
     * @param second_user
     */
    private void setLeftInfo(Bean_TableDetial.TableUser second_user) {
        if (second_user == null || TextUtils.isEmpty(second_user.id)) {
            imgHeadLeft.setBackgroundResource(0);
            Constent.isHasUser[1] = false;
            return;
        }
        Constent.isHasUser[1] = true;
        if (!Constent.ISGAMER || !Constent.ISBANKER)
            if (TextUtils.equals(Constent.USERID, second_user.id))
                Constent.ISGAMER = true;
            else Constent.ISGAMER = false;
        Glide.with(this).load(CommonUntilities.PIC_URL + second_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadLeft);
    }

    /**
     * 庄家信息
     *
     * @param first_user
     */
    private void setBankerInfo(Bean_TableDetial.TableUser first_user) {
        if (first_user == null || TextUtils.isEmpty(first_user.id)) {
            imgHeadTop.setBackgroundResource(0);
            Constent.isHasUser[0] = false;
            Constent.ISBANKER = false;
            return;
        }
        Constent.isHasUser[0] = true;
        if (!Constent.ISGAMER || !Constent.ISBANKER)
            if (TextUtils.equals(Constent.USERID, first_user.id)) {
                Constent.ISGAMER = true;
                Constent.ISBANKER = true;
            } else {
                Constent.ISGAMER = false;
                Constent.ISBANKER = false;
            }

        Glide.with(this).load(CommonUntilities.PIC_URL + first_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadTop);
    }

    @Override
    public void rediusPoint(int point) {

    }

    @Override
    public void addPoint(int point) {

    }

    @Override
    public void toastMsg(String msg) {
        Tools.toastMsgCenter(context, msg);
    }

    @Override
    public void toastMsg(int msg) {
        Tools.toastMsgCenter(context, msg);
    }

    @Override
    public void moneyClickable(boolean isClickable) {
        moneyViewClickable(isClickable);
    }

    @Override
    public void hidePointCard() {
        hidePointCard();
    }

    @Override
    public void showPointCard(int pos, int count) {
        switch (pos) {
            case 1:
                Constent.BETLEDTPOINT = count;
                txtTotalLeft.setText(Constent.BETLEDTPOINT + "");
                imgBgLeft.setVisibility(View.VISIBLE);
                break;
            case 2:
                Constent.BETMIDPOINT = count;
                txtTotalMid.setText(Constent.BETMIDPOINT + "");
                imgBgMid.setVisibility(View.VISIBLE);
                break;
            case 3:
                Constent.BETRIGHTPOINT = count;
                txtTotalRight.setText(Constent.BETRIGHTPOINT + "");
                imgBgRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    private int userPos = -1;

    @Override
    public void dealChess(int pos) {
        userPos = pos;
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
                            gamePresenter.getChessData(gameChessAdapter.getInvisableCount() - 1);
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
                            gamePresenter.getChessData(gameChessAdapter.getInvisableCount() - 1);
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
                            gamePresenter.getChessData(gameChessAdapter.getInvisableCount() - 1);
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
                            gamePresenter.getChessData(gameChessAdapter.getInvisableCount() - 1);
                        }
                    }, 500 * i);

                    break;
            }

            userPos++;
        }
    }

    @Override
    public void resetUserMoney(int point) {
        txtMoney.setText(point + "");
    }

    @Override
    public void disContect() {
        if (discontectNotifyPop == null)
            discontectNotifyPop = new DiscontectNotifyPop(context);
        discontectNotifyPop.showPop(txtHeadBottom);
        discontectNotifyPop.setPopInterfacer(this, 7);
    }

    @Override
    public void contect() {
        if (discontectNotifyPop != null && discontectNotifyPop.isShowing()) {
            discontectNotifyPop.setIsContect(true);
            discontectNotifyPop.dismiss();
        }
    }

    @Override
    public void lackMoney(int time) {
        if (lackMoneyNotifyPop == null)
            lackMoneyNotifyPop = new LackMoneyNotifyPop(context);
        lackMoneyNotifyPop.setPopInterfacer(this, 8);
        lackMoneyNotifyPop.setCountTime(time);
        lackMoneyNotifyPop.showPop(txtHeadBottom);
    }

    @Override
    public void gamerEixt(int pos) {
        exitGame(pos);
    }

    @Override
    public void lackBanker(int time) {
        if (lackBankerNotifyPop == null)
            lackBankerNotifyPop = new LackBankerNotifyPop(context);
        lackBankerNotifyPop.setCountTime(time);
        lackBankerNotifyPop.setContent(R.string.lack_banker_notify);
        lackBankerNotifyPop.setPopInterfacer(this, 11);
    }

    @Override
    public void downBanker() {

    }

    @Override
    public void tempCountTime(int time, int type) {
        switch (type) {
            case Constent.BET_MONEY:
                //投注
                imgTimeStatus.setBackgroundResource(R.mipmap.text_set_point);
                break;
            case Constent.OPEN_CHESS:
                //开牌
                imgTimeStatus.setBackgroundResource(R.mipmap.text_open_chess);
                break;
            case Constent.DEAL_CHESS:
                imgTimeStatus.setBackgroundResource(R.mipmap.text_deal_chess);
                break;
            default:
                imgTimeStatus.setBackgroundResource(R.mipmap.text_wait);
                break;
        }
        imgTimeStatus.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        flTime.setVisibility(View.VISIBLE);
        imgTime.setBackgroundResource(timeRes[time]);
    }

    private void exitGame(int pos) {
        Constent.isHasUser[pos] = false;
        Constent.ISGAMER = false;
        switch (pos) {
            case 0:
                Constent.ISBANKER = false;
                imgHeadTop.setBackgroundResource(0);
                break;
            case 1:
                imgHeadLeft.setBackgroundResource(0);
                break;
            case 2:
                imgHeadBottom.setBackgroundResource(0);
                break;
            case 3:
                imgHeadRight.setBackgroundResource(0);
                break;
        }
    }

    @Override
    public void OnDismiss(int flag) {
        switch (flag) {
            case 1:
                settingPop = null;
                break;
            case 2:
                personalPopInfo = null;
                break;
            case 3:
                audiencePop = null;
                break;
            case 5:
                exitNotifyPop = null;
                break;
            case 6:
                addMoney = null;
                break;
            case 7:
                discontectNotifyPop = null;
                break;
            case 8:
                lackMoneyNotifyPop = null;
                break;
            case 9:
                gamerExitNotifyPop = null;
                break;
            case 10:
                bankerExitNotifyPop = null;
                break;
            case 11:
                lackBankerNotifyPop = null;
                break;
            case 12:
                exitBankerPop = null;
                break;
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 1:
                if (bundle == null)
                    return;
                if (bundle.getBoolean("type")) {
                    ChessCardApplication.getInstance().playBack();
                } else ChessCardApplication.getInstance().stopBack();
                break;
            case 5:
                gamePresenter.back();
                break;
            case 6:
                if (bundle == null)
                    return;
                Constent.USERMONEY += bundle.getInt("money");
                txtMoney.setText(Constent.USERMONEY + "");
                break;
            case 7:
                finish();
                break;
            case 8:
                //用户续费
                if (bundle == null)
                    return;
                Constent.USERMONEY += bundle.getInt("money");
                gamePresenter.lackBankerMoney(bundle.getInt("money"));
                txtMoney.setText(Constent.USERMONEY + "");
                break;
            case 9:
                gamePresenter.gamerExit(Constent.SELECT_SITE_POS);
                break;
            case 10:
                gamePresenter.gamerExit(Constent.SELECT_SITE_POS);
                finish();
                break;
            case 11:
                gamePresenter.lackBanker();
                break;
        }
    }

    @Override
    public void OnCancle(int flag) {
        switch (flag) {
            case 5:
                exitNotifyPop.dismiss();
                break;
            case 8:
                //为续费
                if (Constent.ISBANKER)
                    gamePresenter.downBanker();
                else gamePresenter.downTable();

                break;
            case 9:
                gamePresenter.gamerExit(Constent.SELECT_SITE_POS);
                finish();
                break;
            case 10:
                gamePresenter.gamerExit(Constent.SELECT_SITE_POS);
                break;
            case 11:
                gamePresenter.downBanker();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showExitPop();
        return;
    }

}
