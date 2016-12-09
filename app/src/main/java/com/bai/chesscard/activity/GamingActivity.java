package com.bai.chesscard.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.dialog.AudiencelPop;
import com.bai.chesscard.dialog.ExitNotifyPop;
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
            R.mipmap.point_six, R.mipmap.point_seven, R.mipmap.point_eight, R.mipmap.point_nine};

    private int[] mutilRes = new int[]{R.mipmap.one_multiple, R.mipmap.double_multiple, R.mipmap.trable_multiple};
    private GamePresenter gamePresenter;
    private ProgressDialog progressDialog;
    private SettingPop settingPop;
    private PersonalPopInfo personalPopInfo;
    private AudiencelPop audiencePop;
    private ExitNotifyPop exitNotifyPop;

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
        initView();
        init();
        initData();
    }

    private void initView() {
        roomId = getIntent().getStringExtra("roomId");
        tableId = getIntent().getStringExtra("tableId");
        Constent.USERMONEY = getIntent().getIntExtra("point", 0);
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
        gamePresenter.getChessData();
        gamePresenter.getIn(tableId, AppPrefrence.getUserNo(context));
        txtMoney.setText(Constent.MINCOUNT + "");
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
        recyChess.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
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

                break;
            case R.id.txt_money_left:
                //投注
                if (pointList == null || pointList.length == 0)
                    return;
                gamePresenter.betMoney(AppPrefrence.getUserNo(context), pointList[0], tableId, roomId);
                break;
            case R.id.txt_money_mid:
                //投注
                gamePresenter.betMoney(AppPrefrence.getUserNo(context), pointList[1], tableId, roomId);
                break;
            case R.id.txt_money_right:
                //投注
                gamePresenter.betMoney(AppPrefrence.getUserNo(context), pointList[2], tableId, roomId);
                break;
            case R.id.img_setting:
                //设置选项

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
        if (exitNotifyPop == null)
            exitNotifyPop = new ExitNotifyPop(context);
        exitNotifyPop.setIds(tableId, roomId, AppPrefrence.getUserNo(context), identify + "");
        exitNotifyPop.setPopInterfacer(this, 5);
        exitNotifyPop.showPop(txtHeadBottom);
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
    public void openChess(Bundle bundle) {

        //庄家
        glideImg(chessRes[bundle.getInt("bankerOne")], imgTopLeft);
        glideImg(chessRes[bundle.getInt("bankerTwo")], imgTopRight);
        imgTopLeft.setVisibility(View.VISIBLE);
        imgTopRight.setVisibility(View.VISIBLE);
        gamePresenter.showPoint(0, bundle.getInt("bankerOne"), bundle.getInt("bankerTwo"));
        //初家的牌
        glideImg(chessRes[bundle.getInt("leftOne")], imgChessLeftOne);
        glideImg(chessRes[bundle.getInt("leftTwo")], imgChessLeftTwo);
        imgChessLeftOne.setVisibility(View.VISIBLE);
        imgChessLeftTwo.setVisibility(View.VISIBLE);
        gamePresenter.showPoint(1, bundle.getInt("leftOne"), bundle.getInt("leftTwo"));
        //天家
        glideImg(chessRes[bundle.getInt("bottomOne")], imgChessMidOne);
        glideImg(chessRes[bundle.getInt("bottomTwo")], imgChessMidTwo);
        imgChessMidOne.setVisibility(View.VISIBLE);
        imgChessMidTwo.setVisibility(View.VISIBLE);
        gamePresenter.showPoint(2, bundle.getInt("bottomOne"), bundle.getInt("bottomTwo"));
        //尾家
        glideImg(chessRes[bundle.getInt("rightOne")], imgChessRightOne);
        glideImg(chessRes[bundle.getInt("rightTwo")], imgChessRightTwo);
        imgChessRightOne.setVisibility(View.VISIBLE);
        imgChessLeftTwo.setVisibility(View.VISIBLE);
        gamePresenter.showPoint(3, bundle.getInt("rightOne"), bundle.getInt("rightTwo"));
    }

    private void glideImg(String path, ImageView imageView) {
        Glide.with(this).load(path).into(imageView);
    }

    private void glideImg(int path, ImageView imageView) {
        Glide.with(this).load(path).into(imageView);
    }

    @Override
    public void startCountTime(int time) {
        imgTimeStatus.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.VISIBLE);
        imgTime.setBackgroundResource(timeRes[time]);
        flTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void endCountTime() {
        imgTime.setVisibility(View.INVISIBLE);
        imgTimeStatus.setVisibility(View.INVISIBLE);
        flTime.setVisibility(View.INVISIBLE);
        gamePresenter.openChess();
    }

    @Override
    public void showPoint(int pos, int point) {
        switch (pos) {
            case 0:
                glideImg(pointRes[point], imgChessTopCount);
                imgChessTopCount.setVisibility(View.VISIBLE);
                break;
            case 1:
                glideImg(pointRes[point], imgChessLeftCount);
                imgChessLeftCount.setVisibility(View.VISIBLE);
                break;
            case 2:
                glideImg(pointRes[point], imgChessMidCount);
                imgChessMidCount.setVisibility(View.VISIBLE);
                break;
            case 3:
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
    public void shakeDice() {
        gamePresenter.shakeDice(this);
    }

    @Override
    public void endDice(int one, int two) {

    }

    @Override
    public void endDice(int pos) {

    }

    @Override
    public void setTableInfo(Bean_TableDetial.TableDetial tableInfo) {
        try {
            String[] pointTmp = tableInfo.pointstr.split(",");
            for (int i = 0; i < pointTmp.length; i++) {
                pointList[i] = Integer.parseInt(pointTmp[i]);
            }
        } catch (Exception e) {

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
        if (four_user == null)
            return;
        Glide.with(this).load(CommonUntilities.PIC_URL + four_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadRight);
    }

    /**
     * 天 家信息
     *
     * @param third_user
     */
    private void setBottomInfo(Bean_TableDetial.TableUser third_user) {
        if (third_user == null)
            return;
        Glide.with(this).load(CommonUntilities.PIC_URL + third_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadBottom);
    }

    /**
     * 初 家信息
     *
     * @param second_user
     */
    private void setLeftInfo(Bean_TableDetial.TableUser second_user) {
        if (second_user == null)
            return;
        Glide.with(this).load(CommonUntilities.PIC_URL + second_user.avatar).error(R.mipmap.icon_default_head).into(imgHeadLeft);
    }

    /**
     * 庄家信息
     *
     * @param first_user
     */
    private void setBankerInfo(Bean_TableDetial.TableUser first_user) {
        if (first_user == null)
            return;
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
                Constent.BETLEDTPOINT += count;
                txtMoneyLeft.setText(Constent.BETLEDTPOINT + "");
                imgBgLeft.setVisibility(View.VISIBLE);
                break;
            case 2:
                Constent.BETMIDPOINT += count;
                txtMoneyMid.setText(Constent.BETMIDPOINT + "");
                imgBgMid.setVisibility(View.VISIBLE);
                break;
            case 3:
                Constent.BETRIGHTPOINT += count;
                txtMoneyRight.setText(Constent.BETRIGHTPOINT + "");
                imgBgRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void dealChess(int pos) {
        for (int i = 0; i < 4; i++) {
            if (pos > 3)
                pos = 0;
            switch (pos) {
                case 0:
                    glideImg(R.mipmap.bg_chess_back, imgTopLeft);
                    glideImg(R.mipmap.bg_chess_back, imgTopRight);
                    imgTopLeft.setVisibility(View.VISIBLE);
                    imgTopRight.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    glideImg(R.mipmap.bg_chess_back, imgChessLeftOne);
                    glideImg(R.mipmap.bg_chess_back, imgChessLeftTwo);
                    imgChessLeftOne.setVisibility(View.VISIBLE);
                    imgChessLeftTwo.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    glideImg(R.mipmap.bg_chess_back, imgChessMidOne);
                    glideImg(R.mipmap.bg_chess_back, imgChessMidTwo);
                    imgChessMidOne.setVisibility(View.VISIBLE);
                    imgChessMidTwo.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    glideImg(R.mipmap.bg_chess_back, imgChessRightOne);
                    glideImg(R.mipmap.bg_chess_back, imgChessRightTwo);
                    imgChessRightOne.setVisibility(View.VISIBLE);
                    imgChessRightTwo.setVisibility(View.VISIBLE);
                    break;
            }

            pos++;
        }
        gamePresenter.startCountTime(10 * 1000);
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
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case 5:
                gamePresenter.back();
                break;
        }
    }

    @Override
    public void OnCancle(int flag) {
        switch (flag) {
            case 5:
                exitNotifyPop.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showExitPop();
        return;
    }

}
