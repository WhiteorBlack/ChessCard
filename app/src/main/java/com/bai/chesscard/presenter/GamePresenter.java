package com.bai.chesscard.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bai.chesscard.ChessCardApplication;
import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.Bean_BetMoney;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_Message;
import com.bai.chesscard.bean.Bean_Result;
import com.bai.chesscard.bean.Bean_ShakeDice;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenter implements Observer, TIMConnListener {
    private GameOprateView gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private TIMConversation conversation;
    private Bean_TableDetial bean_tableDetial;
    private Constent constent;

    public GamePresenter(GameOprateView gameOprateView, String groupId, TIMConversationType type, Constent constent) {
        this.gameOprateView = gameOprateView;
        conversation = TIMManager.getInstance().getConversation(type, groupId);
        conversation.disableStorage();
        this.constent = constent;
    }

    /**
     * 启动service建立长连接
     *
     * @param context
     */
    public void startService(Context context) {
        TIMManager.getInstance().setConnectionListener(this);
        MessageEvent.getInstance().addObserver(this);
    }

    public void onDestory() {
        MessageEvent.getInstance().clear();
        conversation = null;
    }

    public void getChessData(int count) {
        List<Bean_ChessList.Chess> chess = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Bean_ChessList.Chess chess1 = new Bean_ChessList.Chess();
            if (i < 16 - count)
                chess1.isVisiable = false;
            else chess1.isVisiable = true;
            chess.add(chess1);
        }
        gameOprateView.setChessData(chess);
    }

    public void gamerExit(int pos) {
        gameOprateView.gamerEixt(pos);
        Bean_Message bean_message = new Bean_Message();
        bean_message.type = Constent.GAMER_EXIT;
        bean_message.gamerPos = pos;
        setMessage(bean_message);
    }

    /**
     * 下注
     *
     * @param userId
     * @param point
     */
    public void betMoney(Activity activity, String userId, final int point, String tableId, String houseId, int[] startPoint, int pos) {
        if (constent.getSELECTPOS() < 1 && !constent.ISGAMER()) {
            gameOprateView.toastMsg("请选择要投注的玩儿家");
            return;
        }
        if (constent.getUSERMONEY() < point) {
            gameOprateView.toastMsg("账号余额不足,请及时充值");
            return;
        }

        betMoneyAnim(activity, startPoint, pos);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("mid", constent.getROUNDID());
        params.put("num", "" + (constent.getSELECTPOS() - 1));
        params.put("point", point + "");
        params.put("house_id", houseId);
        params.put("table_id", tableId);
        PostTools.postData(CommonUntilities.MAIN_URL + "yazhu", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("betMoney:" + response);
                if (TextUtils.isEmpty(response)) {
                    gameOprateView.toastMsg(R.string.no_network);
                    return;
                }
                Bean_BetMoney bean_betMoney = new Gson().fromJson(response, Bean_BetMoney.class);
                if (bean_betMoney != null && bean_betMoney.status) {
                    gameOprateView.showPointCard(bean_betMoney.data.num, bean_betMoney.data.totalPoint);
                    gameOprateView.rediusPoint(point);
                    gameOprateView.resetUserMoney(constent.getUSERMONEY());
                    Bean_Message message = new Bean_Message();
                    message.type = Constent.BET_MONEY;
                    message.betNum = bean_betMoney.data.num;
                    message.betPoint = bean_betMoney.data.totalPoint;
                    setMessage(message);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                gameOprateView.moneyClickable(true);
            }
        });
    }

    /**
     * 观众进入房间进行观战或者游戏
     *
     * @param roomId
     * @param userId
     */
    public void getIn(String roomId, String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("table_id", roomId);
        PostTools.postData(CommonUntilities.MAIN_URL + "addgz", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
            }
        });
    }

    /**
     * 玩儿家续费
     */
    public void lackBankerMoney(int point) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("house_id", constent.getROOMID());
        params.put("user_id", constent.getUSERID());
        params.put("point", "" + point);
        PostTools.postData(CommonUntilities.MAIN_URL + "addpoint", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {

                }
            }
        });
    }

    /**
     * 玩儿家续庄
     */
    public void lackBanker() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("house_id", constent.getROOMID());
        params.put("user_id", constent.getUSERID());
        PostTools.postData(CommonUntilities.MAIN_URL + "down", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    constent.setISGAMER(true);
                    constent.setISBANKER(true);
                    constent.setSelectSitePos(1);
                    constent.setSELECTPOS(1);
                    resetChessData();
                }
            }
        });
    }

    /**
     * 玩儿家下庄
     */
    public void downBanker() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("house_id", constent.getROOMID());
        params.put("user_id", constent.getUSERID());
        PostTools.postData(CommonUntilities.MAIN_URL + "down", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    resetGamer();
                    gameOprateView.downBanker();
                }
            }
        });
    }

    /**
     * 玩儿家退出 数据重置
     */
    private void resetGamer() {
        gameOprateView.resetStatue();
        getChessData(0);
        Constent.ROUNDCOUNT = 0;
        Constent.GAMECOUNT = 0;
        constent.setISGAMER(false);
        constent.setISBANKER(false);
        constent.setSelectSitePos(0);
        constent.setSELECTPOS(0);
        Bean_Message message = new Bean_Message();
        message.gamerPos = constent.getSelectSitePos();
        message.type = 0;
        setMessage(message);
    }

    /**
     * 玩儿家下桌
     */
    public void downTable() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("house_id", constent.getROOMID());
        params.put("user_id", constent.getUSERID());
        params.put("num", constent.getSelectSitePos() + "");
        PostTools.postData(CommonUntilities.MAIN_URL + "gameout", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    resetGamer();
                }
            }
        });
    }

    /**
     * 获取桌面信息
     *
     * @param roomId
     * @param tableId
     */
    public void getTableInfo(String roomId, String tableId) {

        gameOprateView.showDialog();
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "tabledetail", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("tabledetail----" + response);
                if (TextUtils.isEmpty(response)) {
                    gameOprateView.toastMsg(R.string.no_network);
                    return;
                }
                bean_tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (bean_tableDetial != null && bean_tableDetial.status) {
                    constent.setIsHasUser(0, bean_tableDetial.data.first_user == null);
                    constent.setIsHasUser(1, bean_tableDetial.data.second_user == null);
                    constent.setIsHasUser(2, bean_tableDetial.data.third_user == null);
                    constent.setIsHasUser(3, bean_tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(bean_tableDetial.data);
                    constent.setMINCOUNT(bean_tableDetial.data.sz_point);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                gameOprateView.hideDialog();
            }
        });
    }

    /**
     * 返回
     */
    public void back() {
        gameOprateView.back();
    }

    /**
     * 如果该作为上有人则展示正在游戏用户的信息
     * 如果没人则视为抢座位
     *
     * @param pos
     * @param userId 用户id,如果抢座位的话就传进去
     */
    public void showUserInfo(int pos, String userId) {

        if (constent.getIsHasUser(pos)) {
            switch (pos) {
                case 0:
                    //庄家
                    gameOprateView.showUserInfo(bean_tableDetial.data.first_user);
                    break;
                case 1:
                    //初家
                    gameOprateView.showUserInfo(bean_tableDetial.data.second_user);
                    break;
                case 2:
                    //天家
                    gameOprateView.showUserInfo(bean_tableDetial.data.third_user);
                    break;
                case 3:
                    //尾家
                    gameOprateView.showUserInfo(bean_tableDetial.data.four_user);
                    break;
            }
        } else {
            if (pos == 0)
                if (constent.getUSERMONEY() < constent.getMINCOUNT()) {
                    gameOprateView.toastMsg("坐庄最低需要" + constent.getMINCOUNT() + "个金币");
                    return;
                } else {
                    gameOprateView.showBankerInfo();
                    return;
                }
            capturePosition(pos + 1, userId);
        }
    }

    /**
     * 选择座位坐下
     *
     * @param pos
     * @param userId
     */
    public void capturePosition(final int pos, String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("num", pos + "");
        params.put("user_id", constent.getUSERID());
        PostTools.postData(CommonUntilities.MAIN_URL + "checktable", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameOprateView.toastMsg(R.string.no_network);
                    constent.setIsHasUser(pos - 1, false);
                    return;
                }
                constent.setIsHasUser(pos - 1, true);
                bean_tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (bean_tableDetial != null && bean_tableDetial.status) {
                    constent.setIsHasUser(0, bean_tableDetial.data.first_user == null);
                    constent.setIsHasUser(1, bean_tableDetial.data.second_user == null);
                    constent.setIsHasUser(2, bean_tableDetial.data.third_user == null);
                    constent.setIsHasUser(3, bean_tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(bean_tableDetial.data);
                    Bean_Message message = new Bean_Message();
                    message.gameStatue = bean_tableDetial.data;
                    message.type = Constent.GAMER_SITE;
                    setMessage(message);
                }
                if (bean_tableDetial.status) {

                    if (pos == 1) {
                        constent.setISBANKER(true);
                        constent.setISGAMER(true);
                        gameOprateView.upBanker();
                    } else {
                        constent.setISBANKER(false);
                        constent.setISGAMER(true);
                    }
                    constent.setSELECTPOS(pos);
                    constent.setSelectSitePos(pos);
                    gameOprateView.moneyClickable(false);
                } else {
                    constent.setISBANKER(false);
                    constent.setISGAMER(false);
                    constent.setSELECTPOS(0);
                    constent.setSelectSitePos(0);
                    gameOprateView.moneyClickable(true);
                }
            }
        });
    }


    /**
     * 展示正在游戏用户的信息
     *
     * @param user
     */
    public void showUserInfo(Bean_TableDetial.TableUser user) {
        gameOprateView.showUserInfo(user);
    }

    /**
     * 展示观众信息
     */
    public void showAudience() {
        gameOprateView.showAudience();
    }

    /**
     * 展示设置
     */
    public void showSetting() {
        gameOprateView.showSetting();
    }

    /**
     * 增加按钮点击
     */
    public void addMoney() {
        gameOprateView.addMoney();
    }

    /**
     * 投注
     *
     * @param pos
     */
    public void followMoney(int pos) {
        gameOprateView.followMoney(pos);
    }

    /**
     * 加载动画展示
     */
    public void showDialog() {
        gameOprateView.showDialog();
    }

    /**
     * 加载动画消失
     */
    public void hideDialog() {
        gameOprateView.hideDialog();
    }

    /**
     * 开牌
     */
    public void openChess(String chessPoint) {
        String[] chessData = new String[8];
        try {
            if (!TextUtils.isEmpty(chessPoint))
                chessPoint = chessPoint.replaceAll("-", ",").trim();
            chessData = chessPoint.split(",");
        } catch (Exception e) {

        }
        int[] chessIntData = getChessPoint(chessData);
        Bundle bundle = new Bundle();
        bundle.putInt("bankerOne", chessIntData[0]);
        bundle.putInt("bankerTwo", chessIntData[1]);
        bundle.putInt("leftOne", chessIntData[2]);
        bundle.putInt("leftTwo", chessIntData[3]);
        bundle.putInt("bottomOne", chessIntData[4]);
        bundle.putInt("bottomTwo", chessIntData[5]);
        bundle.putInt("rightOne", chessIntData[6]);
        bundle.putInt("rightTwo", chessIntData[7]);
        gameOprateView.openChess(bundle);
    }

    /**
     * 结算结果
     */
    private void getResult() {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", constent.getUSERID());
        params.put("mh_id", constent.getROUNDID());
        params.put("house_id", constent.getROOMID());
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "judgepoint", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                Tools.debug("result" + response);
                Bean_Result bean_result = new Gson().fromJson(response, Bean_Result.class);
                if (bean_result.status) {
                    gameOprateView.addPoint(bean_result.data.ucount);

                }
            }
        });
    }

    /**
     * 将String[] 转换为int[] 由于数组从0开始,这里减一
     *
     * @param chess
     * @return
     */
    private int[] getChessPoint(String[] chess) {
        int[] chessPoint = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        try {
            for (int i = 0; i < chess.length; i++) {
                chessPoint[i] = Integer.parseInt(chess[i]);
            }
        } catch (Exception e) {

        }
        return chessPoint;
    }

    /**
     * 开牌展示时间开始计时
     */
    public void openCountTime(int time) {
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameOprateView.openCountTime((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                gameOprateView.resetStatue();
            }
        }.start();
    }

    /**
     * 开始计时
     */
    public void startCountTime(int time, final int type) {
        gameOprateView.startCountTime(time, type);
    }


    /**
     * 显示点数
     */
    public void showPoint(int pos, int one, int two) {
        int point = one + two + 2;
        int mutil = -1;
        boolean isGray = false;
        if (point > 9)
            point = 0;
        else if (one == two)
            mutil = 1;
        showMultiple(pos, mutil);
        if (pos == 0) {
            constent.setBANKERPOINT(point);
            constent.setISBANKERDOUBLE(mutil > 0);
            gameOprateView.showPoint(pos, (mutil > 0) ? 10 : point, false);
        }
        if (mutil > 0) {
            if (constent.ISBANKERDOUBLE()) {
                if (point > constent.getBANKERPOINT())
                    isGray = false;
                else isGray = true;
            } else isGray = false;
        } else {
            if (constent.ISBANKERDOUBLE())
                isGray = true;
            else {
                if (point > constent.getBANKERPOINT())
                    isGray = false;
                else isGray = true;
            }
        }
        gameOprateView.showPoint(pos, (mutil > 0) ? 10 : point, isGray);
    }

    /**
     * 隐藏点数
     */
    public void hidePoint() {
        gameOprateView.hidePoint();
    }

    /**
     * 显示倍数
     */
    public void showMultiple(int pos, int mutil) {
        if (mutil > 0)
            gameOprateView.showMultiple(pos, mutil);
    }

    /**
     * 隐藏倍数
     *
     * @param pos
     */
    public void hideMultiple(int pos) {
        gameOprateView.hideMultiple(pos);
    }

    /**
     * 摇动骰子
     */
    public void shakeDice(Activity context, int one, int two) {
        constent.setISSHAKING(true);
        startDice(context, one, two);
    }

    private Bean_ShakeDice bean_shakeDice;

    private void getDiceData() {
        Tools.debug("getDiceData is runing");
        Map<String, String> params = new HashMap<>();
        params.put("house_id", constent.getROOMID());
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "setscreen", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("shakeDice--" + response);
                if (TextUtils.isEmpty(response))
                    return;
                bean_shakeDice = new Gson().fromJson(response, Bean_ShakeDice.class);
                constent.setROUNDID(bean_shakeDice.data.id);
            }
        });
    }

    public void betMoneyAnim(final Activity activity, int[] startPoint, int pos) {

        ChessCardApplication.getInstance().playGoldSound();
        int height = Tools.dip2px(activity, 30);
        float mutil = 1.34f;
        int width = (int) (height * mutil);
        int centerX = (int) (Tools.getScreenWide(activity) / 2);
        int centerY = (int) (Tools.getScreenHeight(activity) / 2);
        if (constent.getSELECTPOS() == 1)
            centerX -= centerY * 2 / 5;
        if (constent.getSELECTPOS() == 3)
            centerX += centerY / 3;
        final int[] endPoint = new int[]{centerX, centerY};

        final ImageView imgBet = new ImageView(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(height, height);
        imgBet.setLayoutParams(params);
        imgBet.setVisibility(View.GONE);
        if (pos == 0)
            Glide.with(activity).load(R.mipmap.bg_money_left).into(imgBet);
        if (pos == 1)
            Glide.with(activity).load(R.mipmap.bg_money_mid).into(imgBet);
        if (pos == 2)
            Glide.with(activity).load(R.mipmap.bg_money_right).into(imgBet);
        if (viewGroup == null)
            viewGroup = createAnimLayout(activity);
        viewGroup.removeAllViews();
//        startPoint[0] -= width * 2.5;
//        startPoint[1] -= height * 3;
        endPoint[0] -= width / 2;
        endPoint[1] -= height / 2;
        imgBet.setX(startPoint[0]);
        imgBet.setY(startPoint[1]);
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
                gameOprateView.moneyClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBet.clearAnimation();
//                imgBet.setX(endPoint[0]);
//                imgBet.setY(endPoint[1]);
                imgBet.setVisibility(View.GONE);
                imgBet.setBackgroundResource(0);
                viewGroup.removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void startDice(final Activity context, final int one, final int two) {
        final ImageView imgOne = new ImageView(context);
        final ImageView imgTwo = new ImageView(context);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(Tools.dip2px(context, 60), Tools.dip2px(context, 60));
        imgOne.setLayoutParams(params);
        imgTwo.setLayoutParams(params);
        imgOne.setBackgroundResource(R.drawable.anim_dice);
        imgTwo.setBackgroundResource(R.drawable.anim_dice);
        if (viewGroup == null)
            viewGroup = createAnimLayout(context);
        viewGroup.removeAllViews();
        imgOne.setX(Tools.getScreenWide(context) / 2 - Tools.dip2px(context, 70));
        imgOne.setY(Tools.getScreenHeight(context) / 2 - Tools.dip2px(context, 50));
        imgTwo.setX(Tools.getScreenWide(context) / 2 - Tools.dip2px(context, 50));
        imgTwo.setY(Tools.getScreenHeight(context) / 2 - Tools.dip2px(context, 50));
        viewGroup.addView(imgOne);
        viewGroup.addView(imgTwo);
        viewGroup.setBackgroundColor(Color.BLACK);
        viewGroup.getBackground().setAlpha(120);
        ((AnimationDrawable) imgOne.getBackground()).start();
        ((AnimationDrawable) imgTwo.getBackground()).start();
        new CountDownTimer(2000, 2000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                constent.setISSHAKING(false);
                ((AnimationDrawable) imgOne.getBackground()).stop();
                ((AnimationDrawable) imgTwo.getBackground()).stop();
                imgOne.setBackgroundResource(diceRes[one]);
                imgTwo.setBackgroundResource(diceRes[two]);
                endDice(one, two);
            }
        }.start();
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

    /**
     * 骰子停止摇动
     */
    public void endDice(final int one, final int two) {
        constent.setISSHAKING(false);
        final int pos = (one + two) % 4;
        constent.setDEALCHESSPOS(pos);
        String toast = null;
        switch (pos) {
            case 0:
                toast = "庄家开";
                break;
            case 1:
                toast = "初门开";
                break;
            case 2:
                toast = "天门开";
                break;
            case 3:
                toast = "尾门开";
                break;
        }
        gameOprateView.toastMsg(toast);
        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 1) {

                    viewGroup.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void onFinish() {
                viewGroup.removeAllViews();
                if (constent.ISGAMER())
                    dealChessData();
            }
        }.start();

    }

    /**
     * 通知后台进行发牌
     */
    private void dealChessData() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "fapai", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);

            }
        });
    }


    public void showExitPop() {
        gameOprateView.showExitPop();
    }

    private void setMessage(Bean_Message bean_message) {
        TIMMessage message = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(new Gson().toJson(bean_message));
        if (message.addElement(elem) != 0) {
            Tools.debug("添加失败啦啊啊啊啊啊");
            return;
        }
        conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Tools.debug("message--" + s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Tools.debug("发送成功啦");
            }
        });
    }

    public void endCountTime(int type) {
        if (constent.ISGAMER())
            switch (type) {
                case Constent.RESET_CHESS:
                    getDiceData();
                    break;
                case Constent.DEAL_CHESS: //发牌结束
                    betMoneyData(3);
                    break;
                case Constent.BET_MONEY: //投注时间结束
                    gameOprateView.moneyClickable(false);
                    betMoneyData(4);
                    break;
                case Constent.OPEN_CHESS: //开牌时间结束
                    Tools.debug("isdealchess--" + Constent.ISDEALCHESS);
                    resetMsgStatue();
                    gameOprateView.resetStatue();
                    if (constent.isGamerExit() && constent.ISGAMER()) {
                        if (Constent.GAMECOUNT < 4)
                            getDiceData();
                        else {
                            if (constent.ISBANKER()) {
                                checkBankerCount();
                                checkBankerMoney();
                            } else checkGamerMoney();

                        }
                    }
                    break;
                case Constent.BANKER_STATE:
                    if (constent.isGamerExit() && constent.ISGAMER()) {
                        resetChessData();
                    }
                    break;
            }
    }

    /**
     * 流程走完把所有的流程状态标识重置
     */
    private void resetMsgStatue() {
        Constent.IS_BET_MONEY = false;
        Constent.IS_OPEN_CHESS = false;
        Constent.IS_DEAL_CHESS = false;
        Constent.IS_SHAK_EDICE = false;
        Constent.IS_RESET_CHESS = false;
        Constent.IS_BANKER_STATE = false;
    }

    /**
     * 通知后台进行洗牌
     */
    private void resetChessData() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "xipai", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("洗牌--" + response);

            }
        });
    }

    /**
     * 判断庄家是否需要强制下庄
     * 如果局数没有达到限制,并且本局游戏结束,询问是否续庄
     */
    private void checkBankerCount() {
        if (constent.ISBANKER())
            Constent.ISDEALCHESS = true;
        if (Constent.ROUNDCOUNT == 3 && Constent.GAMECOUNT == 4) {
            Constent.ISDEALCHESS = false;
            downBanker();
            gameOprateView.downBankerNotify();
        } else if (Constent.GAMECOUNT == 4) {
            Constent.ISDEALCHESS = false;
            bankerLackMoney(2);
        }
        Tools.debug("isdealchess--" + Constent.ISDEALCHESS);
    }

    /**
     * 判断庄家金币是否足够
     */
    private void checkBankerMoney() {
        Tools.debug("usermoney" + constent.getUSERMONEY() + "---betMoney" + constent.getBETLEDTPOINT());
        if (constent.ISBANKER() && constent.getUSERMONEY() < Constent.pointList[0]) {
            bankerLackMoney(1);
            Constent.ISDEALCHESS = false;
        } else Constent.ISDEALCHESS = true;
        Tools.debug("isdealchess--" + Constent.ISDEALCHESS);
    }

    /**
     * 通知后台庄家金币不够,需要充值
     *
     * @param type 1为金币不够需要续费 2为续庄
     */
    private void bankerLackMoney(final int type) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        PostTools.postData(CommonUntilities.MAIN_URL + "jxzz", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("庄家续费--" + response);
                if (TextUtils.isEmpty(response)) {
                    bankerLackMoney(type);
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.status) {
                    if (type == 1 && constent.ISBANKER())
                        gameOprateView.lackMoney(10);
                    if (type == 2 && constent.ISBANKER())
                        gameOprateView.lackBanker(10);
                }
            }
        });
    }

    /**
     * 判断三个玩儿家金币是否足够,如果不够就踢出该用户
     */
    private void checkGamerMoney() {
        if (constent.ISGAMER() && !constent.ISBANKER())
            if (constent.getUSERMONEY() < Constent.pointList[0]) {
                Constent.ISDEALCHESS = false;
                downTable();
                gamerExit(constent.getSelectSitePos());
                gameOprateView.showGamerLackMoney();
            } else Constent.ISDEALCHESS = true;
        Tools.debug("isdealchess--" + Constent.ISDEALCHESS);
    }

    /**
     * 通知后台发送押注信息
     *
     * @param type 3押注,4开牌
     */
    private void betMoneyData(int type) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", constent.getTABLEID());
        params.put("type", type + "");
        params.put("mid", constent.getROUNDID());
        PostTools.postData(CommonUntilities.MAIN_URL + "message", params, new PostCallBack());
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            Tools.debug("elemType--" + "--" + msg.getConversation().getType() + "--" + msg.getConversation().getPeer());
            //群内用户发送的消息
            if (msg == null || msg.getConversation().getPeer().equals(conversation.getPeer()) && msg.getConversation().getType() == conversation.getType()) {
                for (int i = 0; i < msg.getElementCount(); i++) {
                    TIMElem elem = msg.getElement(i);
                    TIMElemType elemType = elem.getType();

                    if (elemType == TIMElemType.Text) {
                        Bean_Message bean_message = null;
                        TIMTextElem elemText = (TIMTextElem) elem;
                        try {
                            bean_message = new Gson().fromJson(elemText.getText(), Bean_Message.class);
                        } catch (JsonSyntaxException e) {
//
                        }

                        if (bean_message == null)
                            return;
                        switch (bean_message.type) {
                            case 0:

                                break;
                            case Constent.BET_MONEY:
                                //用户投注信息
                                gameOprateView.showPointCard(bean_message.betNum, bean_message.betPoint);
                                break;
                            case Constent.GAMER_EXIT:
                                //玩儿家退出游戏
                                gameOprateView.gamerEixt(bean_message.gamerPos);
                                resetGamer();
                                break;
                            case Constent.GAMER_SITE:
                                gameOprateView.setTableInfo(bean_message.gameStatue);
                                break;
                        }
                    }
                }
            }
            if (msg != null && TextUtils.equals(msg.getConversation().getType().toString(), "System")) {
                for (int i = 0; i < msg.getElementCount(); i++) {
                    TIMElem elem = msg.getElement(i);
                    TIMElemType elemType = elem.getType();
                    if (elemType == TIMElemType.GroupSystem) {

                        TIMGroupSystemElem elemText = (TIMGroupSystemElem) elem;
                        Tools.debug("SystemMessage" + new String(elemText.getUserData()));
                        Bean_Message bean_message = new Gson().fromJson(new String(elemText.getUserData()), Bean_Message.class);

                        if (bean_message == null)
                            return;
                        switch (bean_message.type) {
                            case Constent.RESET_CHESS:
                                //重新洗牌
                                if (!Constent.IS_RESET_CHESS) {
                                    Constent.IS_RESET_CHESS = true;
                                    if (constent.ISBANKER())
                                        Constent.ROUNDCOUNT++;
                                    Constent.GAMECOUNT = 0;
                                    gameOprateView.resetStatue();
                                    getChessData(bean_message.chesscount);
                                    gameOprateView.startCountTime(bean_message.time, Constent.RESET_CHESS);
                                    constent.setROUNDID(bean_message.mid);

                                }

                                break;
                            case Constent.SHAKE_DICE:
                                //开始摇色子
                                if (!Constent.IS_SHAK_EDICE) {
                                    Constent.IS_SHAK_EDICE = true;
                                    Constent.GAMECOUNT++;
                                    gameOprateView.shakeDice(bean_message.diceNum, bean_message.diceNum1);
                                    constent.setISSHAKING(true);
                                    constent.setROUNDID(bean_message.mid);
                                }

                                break;
                            case Constent.DEAL_CHESS:
                                //发牌
                                if (!Constent.IS_DEAL_CHESS) {
                                    Constent.IS_DEAL_CHESS = true;
                                    startCountTime(bean_message.time, Constent.DEAL_CHESS);
                                    gameOprateView.dealChess(constent.getDEALCHESSPOS());
                                    constent.setROUNDID(bean_message.mid);
                                }

                                break;
                            case Constent.BET_MONEY:
                                //押注
                                if (!Constent.IS_BET_MONEY) {
                                    gameOprateView.moneyClickable(true);
                                    startCountTime(bean_message.time, Constent.BET_MONEY);
                                }

                                break;
                            case Constent.OPEN_CHESS:
                                //开牌
                                if (!Constent.IS_OPEN_CHESS) {
                                    Constent.IS_OPEN_CHESS = true;
                                    openChess(bean_message.chessPoint);
                                    startCountTime(bean_message.time, Constent.OPEN_CHESS);
                                    getResult();
                                }

                                break;
                            case Constent.GAMER_EXIT:
                                //玩儿家退出游戏
                                Tools.debug("gamerOut-----");
                                gameOprateView.gamerEixt(bean_message.gamerPos + 1);
                                resetGamer();
                                break;
                            case Constent.FREE_SITE:
                                //座位空闲

                                break;
                            case Constent.GAME_STATUE:
                                //游戏状态,刚进入或者重连
                                gameOprateView.setTableInfo(bean_message.gameStatue);
                                if (bean_message.status > 1) {
                                    constent.setROUNDID(bean_message.mid);
                                }
                                break;
                            case Constent.RENEW_BANKER:
                                //询问庄家是否续庄
                                gameOprateView.lackBanker(bean_message.time);
                                startCountTime(bean_message.time, Constent.RENEW_BANKER);
                                break;
                            case Constent.RENEW_GOLD:
                                //玩儿家续费
                                startCountTime(bean_message.time, Constent.RENEW_GOLD);
                                gameOprateView.lackMoney(bean_message.time);
                                break;
                            case Constent.GAMER_SITE:
                                //有玩儿家坐下
                                gameOprateView.setTableInfo(bean_message.gameStatue);
                                break;
                            case Constent.BANKER_STATE:
                                if (!Constent.IS_BANKER_STATE) {
                                    Constent.IS_BANKER_STATE = true;
                                    gameOprateView.startCountTime(bean_message.time, Constent.BANKER_STATE);
                                }
                                break;
                        }
                    }
                }

            }
        }
    }

    @Override
    public void onConnected() {
        gameOprateView.contect();
        getIn(constent.getTABLEID(), constent.getUSERID());
    }

    @Override
    public void onDisconnected(int i, String s) {
        gameOprateView.disContect();
    }

    @Override
    public void onWifiNeedAuth(String s) {

    }
}
