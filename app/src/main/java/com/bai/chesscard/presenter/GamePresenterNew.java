package com.bai.chesscard.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bai.chesscard.R;
import com.bai.chesscard.async.GameOprateData;
import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.bean.BeanCharge;
import com.bai.chesscard.bean.Bean_Message;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.GameOprateViewNew;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
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
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenterNew implements Observer, TIMConnListener, GameDataListener {
    private GameOprateViewNew gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private TIMConversation conversation;

    public GamePresenterNew(GameOprateViewNew gameOprateView) {
        this.gameOprateView = gameOprateView;
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, ConstentNew.GROUP_ID);
        conversation.disableStorage();
    }

    public void startService() {
        TIMManager.getInstance().setConnectionListener(this);
        MessageEvent.getInstance().addObserver(this);
    }

    public void onDestory() {
        MessageEvent.getInstance().clear();
        conversation = null;
    }

    public void resetUserInfo(int pos) {
        switch (pos) {
            case 1:
                ConstentNew.BANKERCHARGECOUNT = 1;
                bean_tableDetial.firstuser = null;
                break;
            case 2:
                bean_tableDetial.seconduser = null;
                break;
            case 3:
                bean_tableDetial.thirduser = null;
                break;
            case 4:
                bean_tableDetial.fouruser = null;
                break;
        }
    }

    /**
     * 进入游戏获取桌面用户数据
     */
    public void getInGame() {
        GameOprateData.getInstance(this).getInGame();
    }

    /**
     * 用户投注
     */
    public void betMoney(int money) {
        gameOprateView.betMoneyDisable();
        GameOprateData.getInstance(this).betMoney(money);
    }

    /**
     * 展示设置弹窗
     */
    public void showSettingPop() {
        gameOprateView.showSettingPop();
    }

    /**
     * 展示个人中心弹窗
     */
    public void showPersonalPop() {
        gameOprateView.showMinePop();
    }

    /**
     * 选择座位坐下
     *
     * @param pos
     */
    public void selectSite(int pos) {
        Tools.debug("selectSite--" + pos);
        switch (pos) {
            case 1:
                if (bean_tableDetial == null || bean_tableDetial.firstuser == null || !ConstentNew.IS_HAS_GAMER[pos - 1])
                    gameOprateView.upBanker();
                else gameOprateView.showUserInfo(bean_tableDetial.firstuser);
                break;
            case 2:
                if (bean_tableDetial == null || bean_tableDetial.seconduser == null || !ConstentNew.IS_HAS_GAMER[pos - 1])
                    gameOprateView.upTable(pos);
                else gameOprateView.showUserInfo(bean_tableDetial.seconduser);
                break;
            case 3:
                if (bean_tableDetial == null || bean_tableDetial.thirduser == null || !ConstentNew.IS_HAS_GAMER[pos - 1])
                    gameOprateView.upTable(pos);
                else gameOprateView.showUserInfo(bean_tableDetial.thirduser);
                break;
            case 4:
                if (bean_tableDetial == null || bean_tableDetial.fouruser == null || !ConstentNew.IS_HAS_GAMER[pos - 1])
                    gameOprateView.upTable(pos);
                else gameOprateView.showUserInfo(bean_tableDetial.fouruser);
                break;
        }
    }

    /**
     * 返回操作
     */
    public void back() {
//        if (ConstentNew.IS_BANKER && ConstentNew.IS_HAS_GAMER[0] && ConstentNew.IS_HAS_GAMER[1]
//                && ConstentNew.IS_HAS_GAMER[2] && ConstentNew.IS_HAS_GAMER[3])
//            gameOprateView.downBanker();
        if (ConstentNew.IS_BANKER)
            gameOprateView.bankerExit();
        if (!ConstentNew.IS_BANKER && ConstentNew.IS_GAMER)
            gameOprateView.downTable();
        if (!ConstentNew.IS_BANKER && !ConstentNew.IS_GAMER)
            gameOprateView.exitTable();
    }

    public void addMoney() {
        if (ConstentNew.IS_BANKER)
            gameOprateView.bankerCharge();
        if (!ConstentNew.IS_BANKER && ConstentNew.IS_GAMER)
            gameOprateView.gamerCharge();
    }

    /**
     * 发送消息
     *
     * @param bean_message
     */
    public void sendMessage(Bean_Message bean_message) {
        if (bean_message == null)
            return;
        String msg = new Gson().toJson(bean_message, Bean_Message.class);
        TIMMessage message = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(msg);
        message.addElement(elem);
        conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Tools.debug("send fail " + s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Tools.debug("send success");
            }
        });
    }

    public void upTable(Bean_Message message) {
        switch (message.gamerPos) {
            case 1:
                bean_tableDetial.firstuser = message.tableUser;
                break;
            case 2:
                bean_tableDetial.seconduser = message.tableUser;
                break;
            case 3:
                bean_tableDetial.thirduser = message.tableUser;
                break;
            case 4:
                bean_tableDetial.fouruser = message.tableUser;
                break;
        }
    }


    /**
     * 展示用户信息
     *
     * @param userInfo
     */
    public void showUserInfo(Bean_TableDetial.TableUser userInfo) {
        gameOprateView.showUserInfo(userInfo);
    }

    /**
     * 展示点数
     *
     * @param pos 玩家为之
     * @param one 点数1
     * @param two 点数2
     */
    public void showPoint(int pos, int one, int two) {
        int point = one + two;
        int mutil = -1;
        boolean isGray = false;
        if (point > 9)
            point -= 10;
        if (one == two) {
            mutil = 1;
            if (pos != 0)
                gameOprateView.showMutil(pos, mutil);
        }
        if (pos == 0) {
            ConstentNew.BANKER_POINT = point;
            ConstentNew.IS_BANKER_MUTIL = (mutil > 0);
        } else {
            if (mutil > 0) {
                if (ConstentNew.IS_BANKER_MUTIL) {
                    if (point > ConstentNew.BANKER_POINT)
                        isGray = false;
                    else isGray = true;
                } else isGray = false;
            } else {
                if (ConstentNew.IS_BANKER_MUTIL)
                    isGray = true;
                else {
                    if (point > ConstentNew.BANKER_POINT)
                        isGray = false;
                    else isGray = true;
                }
            }
        }

        ConstentNew.SETTLE_RESULT[pos] = !isGray;
        gameOprateView.showPoint(pos, (mutil > 0) ? 10 : point, isGray);
    }

    /**
     * 结算结果f3az4
     */
    private int pos;

    public void settleResult() {
        if (!ConstentNew.IS_GAMER)
            GameOprateData.getInstance(this).getResult();
        pos = ConstentNew.DICE_COUNT;

        for (int i = 0; i < 3; i++) {
            if (pos == 0)
                pos = 4;
            if (pos == 1)
                pos = 2;
            if (!ConstentNew.SETTLE_RESULT[pos - 1]) {
                gameOprateView.settleResult(pos, ConstentNew.SETTLE_RESULT[pos - 1]);
            }
            pos++;
            if (pos > 4)
                pos = 2;
        }

        for (int i = 0; i < 3; i++) {
            if (pos == 0)
                pos = 4;
            if (pos == 1)
                pos = 2;
            if (ConstentNew.SETTLE_RESULT[pos - 1]) {
                gameOprateView.settleResult(pos, ConstentNew.SETTLE_RESULT[pos - 1]);
            }
            pos++;
            if (pos > 4)
                pos = 2;
        }

    }

    public void startDice(final Activity context, final int one, final int two, int[] startPoint) {
        final int[] endPoint = new int[]{(int) (Tools.getScreenWide(context) / 2 - Tools.dip2px(context, 50)), (int) (Tools.getScreenHeight(context) / 2 - Tools.dip2px(context, 50))};
        final ImageView imgOne = new ImageView(context);
        final ImageView imgTwo = new ImageView(context);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(Tools.dip2px(context, 30), Tools.dip2px(context, 30));
        imgOne.setLayoutParams(params);
        imgTwo.setLayoutParams(params);
        imgOne.setBackgroundResource(R.drawable.anim_dice);
        imgTwo.setBackgroundResource(R.drawable.anim_dice);
        if (viewGroup == null)
            viewGroup = createAnimLayout(context);
        viewGroup.removeAllViews();
        imgOne.setLeft(startPoint[0] - Tools.dip2px(context, 10));
        imgOne.setTop(startPoint[1]);
        imgTwo.setLeft(startPoint[0] + Tools.dip2px(context, 10));
        imgTwo.setTop(startPoint[1]);
        viewGroup.addView(imgOne);
        viewGroup.addView(imgTwo);
        ((AnimationDrawable) imgOne.getBackground()).start();
        ((AnimationDrawable) imgTwo.getBackground()).start();
        TranslateAnimation oneY = new TranslateAnimation(0, 0, startPoint[1], endPoint[1]);
        oneY.setDuration(800);
        TranslateAnimation oneX = new TranslateAnimation(startPoint[0], endPoint[0] - Tools.dip2px(context, 15), 0, 0);
        oneX.setDuration(800);
        oneX.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimationSet animationSet = new AnimationSet(context, null);
        animationSet.addAnimation(oneY);
        animationSet.addAnimation(oneX);
        TranslateAnimation twoY = new TranslateAnimation(0, 0, startPoint[1], endPoint[1]);
        twoY.setDuration(800);
        TranslateAnimation twoX = new TranslateAnimation(startPoint[0], endPoint[0], 0, 0);
        twoX.setDuration(800);
        twoX.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimationSet animationSetTwo = new AnimationSet(context, null);
        animationSetTwo.addAnimation(twoX);
        animationSetTwo.addAnimation(twoY);
        imgOne.setAnimation(animationSet);
        animationSet.start();
        imgTwo.setAnimation(animationSetTwo);
        animationSetTwo.start();
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgOne.setBackgroundResource(diceRes[one - 1]);
                imgTwo.setBackgroundResource(diceRes[two - 1]);

                imgOne.setY(endPoint[1]);
                imgOne.setX(endPoint[0] - Tools.dip2px(context, 5));

                imgTwo.setY(endPoint[1]);
                imgTwo.setX(endPoint[0] + Tools.dip2px(context, 35));
                imgOne.clearAnimation();
                imgTwo.clearAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgOne.setVisibility(View.GONE);
                        imgTwo.setVisibility(View.GONE);
                    }
                }, 1000);
                switch (ConstentNew.DICE_COUNT) {
                    case 0:
                        gameOprateView.toastMsg("尾门开牌");
                        break;
                    case 1:
                        gameOprateView.toastMsg("庄家开牌");
                        break;
                    case 2:
                        gameOprateView.toastMsg("初门开牌");
                        break;
                    case 3:
                        gameOprateView.toastMsg("天门开牌");
                        break;
                    case 4:
                        gameOprateView.toastMsg("尾门开牌");
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void endDice(Context context) {
        createAnimLayout((Activity) context).removeAllViews();
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

    public void endCountTime(int type) {
        Tools.debug("endCountTIme--" + type);
        switch (type) {
            case ConstentNew.TYPE_SHAKE_DICE:
                gameOprateView.endDice();
                break;
            case ConstentNew.TYPE_BET_MONEY:
                gameOprateView.endBetMoeny();
                if (!ConstentNew.IS_BET_MONEY && ConstentNew.IS_GAMER && !ConstentNew.IS_BANKER) {
                    Bean_Message message = new Bean_Message();
                    message.type = ConstentNew.TYPE_BET_MONEY;
                    message.gamerPos = ConstentNew.USERPOS;
                    message.betNum = ConstentNew.LEFTPOINT;
                    message.isBet = false;
                    ConstentNew.GAMER_TABLE_MONEY -= ConstentNew.LEFTPOINT;
                    gameOprateView.updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                    message.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                    sendMessage(message);
                    gameOprateView.betMoneyNormal(ConstentNew.USERPOS, ConstentNew.LEFTPOINT);
                }

                break;
            case ConstentNew.TYPE_GET_RESULT:
                gameOprateView.resetTable();
                ConstentNew.IS_GET_RESULT = false;
                break;
            case ConstentNew.TYPE_RENEW_MONEY:
                gameOprateView.clearRenewPop();
                break;

        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;

            if (msg != null && TextUtils.equals(msg.getConversation().getType().toString(), "C2C")) {
                for (int i = 0; i < msg.getElementCount(); i++) {
                    TIMTextElem elem = (TIMTextElem) msg.getElement(i);
                    String msgString = elem.getText().toString();
                    Tools.debug("account" + msgString);
                    if (!TextUtils.isEmpty(msgString)) {
                        final BeanCharge beanCharge = new Gson().fromJson(msgString, BeanCharge.class);
                        if (beanCharge != null && beanCharge.type == 15) {
                            gameOprateView.refreshUserMoney(beanCharge.amount);
                        }
                    }

                }
            }
            //群内用户发送的消息
            if (msg != null || msg.getConversation().getPeer().equals(conversation.getPeer()) && msg.getConversation().getType() == conversation.getType()
                    && TextUtils.equals(msg.getConversation().getPeer().toString(), ConstentNew.GROUP_ID)) {
                for (int i = 0; i < msg.getElementCount(); i++) {
                    TIMElem elem = msg.getElement(i);
                    TIMElemType elemType = elem.getType();

                    if (elemType == TIMElemType.Text) {
                        Bean_Message bean_message = null;

                        TIMTextElem elemText = (TIMTextElem) elem;
                        Tools.debug("UserMessage" + elemText.getText());
                        try {
                            bean_message = new Gson().fromJson(elemText.getText(), Bean_Message.class);
                        } catch (JsonSyntaxException e) {

                        }

                        if (bean_message == null)
                            return;

                        switch (bean_message.type) {
                            case ConstentNew.TYPE_SITE_DOWN:
                                ConstentNew.IS_HAS_GAMER[bean_message.gamerPos - 1] = true;
                                switch (bean_message.gamerPos) {
                                    case 1:
                                        bean_tableDetial.firstuser = bean_message.tableUser;
                                        break;
                                    case 2:
                                        bean_tableDetial.seconduser = bean_message.tableUser;
                                        break;
                                    case 3:
                                        bean_tableDetial.thirduser = bean_message.tableUser;
                                        break;
                                    case 4:
                                        bean_tableDetial.fouruser = bean_message.tableUser;
                                        break;
                                }
                                if (!TextUtils.equals(ConstentNew.USER_ID, bean_message.tableUser.id))
                                    gameOprateView.setUserInfo(bean_message);
                                break;
                            case ConstentNew.TYPE_EXIT_GAME:
                                switch (bean_message.gamerPos) {
                                    case 1:
                                        bean_tableDetial.firstuser = null;
                                        gameOprateView.toastMsg("庄家已下庄");
                                        break;
                                    case 2:
                                        bean_tableDetial.seconduser = null;
                                        break;
                                    case 3:
                                        bean_tableDetial.thirduser = null;
                                        break;
                                    case 4:
                                        bean_tableDetial.fouruser = null;
                                        break;
                                }
                                gameOprateView.setTableInfo(bean_tableDetial);
                                break;
                            case ConstentNew.TYPE_GET_RESULT:
                                ConstentNew.IS_BET_MONEY = false;
                                gameOprateView.updateMoney(bean_message.gamerPos, bean_message.betPoint);
                                break;
                            case ConstentNew.TYPE_NOTIFY_BANKER:
                                gameOprateView.updateMoney(bean_message.gamerPos, bean_message.betPoint);
                                break;
                            case ConstentNew.TYPE_BET_MONEY:
                                //更新玩兒傢的桌面金幣書和賬號數目
                                gameOprateView.updateMoney(bean_message.gamerPos, bean_message.betPoint);
                                //更新桌面投注的狀態
                                if (!bean_message.isBet) {
                                    gameOprateView.betMoneyNormal(bean_message.gamerPos, bean_message.betNum);
                                } else {
                                    gameOprateView.betMoney(bean_message.gamerPos, bean_message.betNum);
                                }
                                break;
                            case ConstentNew.TYPE_RENEW_MONEY:
                                gameOprateView.updateMoney(bean_message.gamerPos, bean_message.betPoint);
                                break;
                            case ConstentNew.TYPE_LOOK_BET:
                                gameOprateView.betMoney(bean_message.gamerPos, bean_message.betNum);
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
                        Bean_Message bean_message = null;
                        try {
                            bean_message = new Gson().fromJson(new String(elemText.getUserData()), Bean_Message.class);
                        } catch (Exception e) {
                        }

                        if (bean_message == null)
                            return;
                        if (!TextUtils.equals(bean_message.groupname, ConstentNew.GROUP_ID))
                            return;
                        switch (bean_message.type) {
                            case ConstentNew.TYPE_RESET_CHESS: //洗牌
                                ConstentNew.GAMEROUND = bean_message.ver;
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_RESET_CHESS);
                                String[] chessString = bean_message.chessList.split(",");
                                if (chessString != null && chessString.length > 0)
                                    for (int j = 0; j < chessString.length; j++) {
                                        ConstentNew.CHESSLIST[i] = Integer.parseInt(chessString[i]);
                                    }
                                ConstentNew.LAST_CHESS_POINT = Integer.parseInt(chessString[chessString.length - 1]);
                                gameOprateView.resetChess();
                                break;
                            case ConstentNew.TYPE_BET_MONEY: //押注时间
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_BET_MONEY);
                                ConstentNew.GAMEROUND = bean_message.ver;
                                if (!ConstentNew.IS_BANKER)
                                    gameOprateView.startBetMoney();
                                break;
                            case ConstentNew.TYPE_CURRENT_STATUE: //当前游戏状态

                                break;
                            case ConstentNew.TYPE_DEAL_CHESS: //发牌
                                ConstentNew.IS_BET_MONEY = false;
                                ConstentNew.CURRENTROUND = bean_message.round;
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_DEAL_CHESS);
                                gameOprateView.dealChess(ConstentNew.DICE_COUNT);
                                ConstentNew.GAMEROUND = bean_message.ver;
                                break;
                            case ConstentNew.TYPE_DOWN_BANKER: //下庄
                                ConstentNew.BANKERCHARGECOUNT = 1;
                                gameOprateView.gamerExit(bean_message.gamerPos);
                                break;
                            case ConstentNew.TYPE_EXIT_GAME: //退出游戏
                                if (TextUtils.equals(ConstentNew.USER_ID, bean_message.userId)) {
                                    gameOprateView.kickOut();
                                }
                                switch (bean_message.gamerPos) {
                                    case 1:
                                        bean_tableDetial.firstuser = null;
                                        break;
                                    case 2:
                                        bean_tableDetial.seconduser = null;
                                        break;
                                    case 3:
                                        bean_tableDetial.thirduser = null;
                                        break;
                                    case 4:
                                        bean_tableDetial.fouruser = null;
                                        break;
                                }
                                gameOprateView.gamerExit(bean_message.gamerPos);
                                break;
                            case ConstentNew.TYPE_GET_RESULT: //结算
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_GET_RESULT);
                                bean_tableDetial.firstuser = bean_message.firstuser;
                                bean_tableDetial.seconduser = bean_message.seconduser;
                                bean_tableDetial.thirduser = bean_message.thirduser;
                                bean_tableDetial.fouruser = bean_message.fouruser;
                                bean_tableDetial.ver = bean_message.ver;
                                gameOprateView.setUserMoney(1, bean_message.firstuser);
                                gameOprateView.setUserMoney(2, bean_message.seconduser);
                                gameOprateView.setUserMoney(3, bean_message.thirduser);
                                gameOprateView.setUserMoney(4, bean_message.fouruser);
                                if (!ConstentNew.IS_GAMER) {
                                    settleResult();
                                }
                                ConstentNew.IS_BET_MONEY = false;
                                break;
                            case ConstentNew.TYPE_NOTIFY_BANKER: //通知庄家进行选择
                                endCountTime(ConstentNew.TYPE_GET_RESULT);
                                if (ConstentNew.IS_BANKER)
                                    gameOprateView.BankerNotify();
                                ConstentNew.GAMEROUND = bean_message.ver;
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_NOTIFY_BANKER);

                                break;
                            case ConstentNew.TYPE_OPEN_CHESS: //开牌
                                ConstentNew.IS_GET_RESULT = false;
                                ConstentNew.GAMEROUND = bean_message.ver;
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_OPEN_CHESS);
                                Bundle bundle = new Bundle();
                                bundle.putInt("bankerOne", getChessPoint(bean_message.p1)[0]);
                                bundle.putInt("bankerTwo", getChessPoint(bean_message.p1)[1]);

                                bundle.putInt("leftOne", getChessPoint(bean_message.p2)[0]);
                                bundle.putInt("leftTwo", getChessPoint(bean_message.p2)[1]);

                                bundle.putInt("bottomOne", getChessPoint(bean_message.p3)[0]);
                                bundle.putInt("bottomTwo", getChessPoint(bean_message.p3)[1]);

                                bundle.putInt("rightOne", getChessPoint(bean_message.p4)[0]);
                                bundle.putInt("rightTwo", getChessPoint(bean_message.p4)[1]);
                                gameOprateView.openChess(bundle);
                                break;
                            case ConstentNew.TYPE_RENEW_MONEY: //续费
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_RENEW_MONEY);
                                if (ConstentNew.IS_BANKER && TextUtils.equals(ConstentNew.USER_ID, bean_message.userId)) {
                                    ConstentNew.BANKERCHARGECOUNT = bean_message.seatnumber;
                                    gameOprateView.renewMoneyBanker(bean_message.time);
                                }
                                if (!ConstentNew.IS_BANKER && ConstentNew.IS_GAMER && TextUtils.equals(ConstentNew.USER_ID, bean_message.userId))
                                    gameOprateView.renewMoneyGamer(bean_message.time);
                                break;
                            case ConstentNew.TYPE_SHAKE_DICE: //摇色子
                                ConstentNew.DICE_COUNT = bean_message.chessPointOne + bean_message.chessPointTwo;
                                if (ConstentNew.DICE_COUNT > 3)
                                    ConstentNew.DICE_COUNT = ConstentNew.DICE_COUNT % 4;
                                gameOprateView.shakeDice(bean_message.chessPointOne, bean_message.chessPointTwo);
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_SHAKE_DICE);
                                break;
                            case ConstentNew.TYPE_UP_BANKER: //上庄

                                break;
                            case ConstentNew.TYPE_WAIT_TIME: //等待时间
                                gameOprateView.countDownTime(bean_message.time, ConstentNew.TYPE_WAIT_TIME);
                                break;
                            case ConstentNew.TYPE_SITE_DOWN: //玩儿家坐下

                                break;
                            case ConstentNew.EXCHANGE_POS:
                                if (TextUtils.equals(ConstentNew.USER_ID, bean_message.firstuser.id)) {
                                    //如果换庄的玩儿家是自己，那么弹窗提示用户
                                    gameOprateView.changeBankerNotify();
                                }
                                gameOprateView.clearChargePop();
                                bean_tableDetial.firstuser = bean_message.firstuser;
                                bean_tableDetial.seconduser = bean_message.seconduser;
                                bean_tableDetial.thirduser = bean_message.thirduser;
                                bean_tableDetial.fouruser = bean_message.fouruser;
                                gameOprateView.setTableInfo(bean_tableDetial);
                                gameOprateView.toastMsg("已换庄,请注意位置变化");
                                break;
                        }
                    }
                }

            }
        }
    }

    @Override
    public void onConnected() {
        gameOprateView.reContect();
    }

    @Override
    public void onDisconnected(int i, String s) {
        gameOprateView.disContect();
    }

    @Override
    public void onWifiNeedAuth(String s) {

    }

    /**
     * 异步获取数据回调
     */

    @Override
    public void getInGameFail() {
        GameOprateData.getInstance(this).getInGame();
    }

    Bean_TableDetial bean_tableDetial;

    @Override
    public void getInGameSuccess(String result) {
        bean_tableDetial = new Gson().fromJson(result, Bean_TableDetial.class);
        ConstentNew.GAMEROUND = bean_tableDetial.ver;
        gameOprateView.setTableInfo(bean_tableDetial);
        gameOprateView.initTable(bean_tableDetial);
    }

    @Override
    public void betMoneyFial() {
        gameOprateView.betMoneyAble();
    }

    @Override
    public void betMoneySuccess(String result, int money) {
        gameOprateView.betMoneyAble();
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean.id > 0) {

            Bean_Message message = new Bean_Message();

            if (ConstentNew.IS_GAMER) {
                message.type = ConstentNew.TYPE_BET_MONEY;
                message.gamerPos = ConstentNew.USERPOS;
                message.isBet = true;
                message.betNum = baseBean.totalpoint;
                ConstentNew.GAMER_TABLE_MONEY -= baseBean.amount;
                gameOprateView.updateMoney(ConstentNew.USERPOS, ConstentNew.GAMER_TABLE_MONEY);
                message.betPoint = ConstentNew.GAMER_TABLE_MONEY;
                ConstentNew.IS_BET_MONEY = true;

                gameOprateView.betMoney(ConstentNew.USERPOS, baseBean.totalpoint);
            } else {
                message.type = ConstentNew.TYPE_LOOK_BET;
                message.gamerPos = ConstentNew.USERPOS;
                message.betNum = baseBean.amount;
                gameOprateView.updateMoney(0, -money);
                gameOprateView.betMoney(ConstentNew.USERPOS, baseBean.amount);
            }
            sendMessage(message);
        }
    }

    @Override
    public void gameOutFail() {

    }

    @Override
    public void gameOutSuccess() {
    }

    @Override
    public void getResultFail() {
        settleResult();
    }

    @Override
    public void getResultSuccess(String result) {
        if (ConstentNew.IS_GET_RESULT) {

        } else {
            ConstentNew.IS_GET_RESULT = true;
            BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
            if (baseBean.id > 0) {
                gameOprateView.updateMoney(0, baseBean.amount);
            }
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

}
