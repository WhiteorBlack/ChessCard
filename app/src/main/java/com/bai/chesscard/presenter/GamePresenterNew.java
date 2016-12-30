package com.bai.chesscard.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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
import com.bai.chesscard.interfacer.GameOprateViewNew;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.ConstentNew;
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

public class GamePresenterNew implements Observer, TIMConnListener {
    private GameOprateViewNew gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private TIMConversation conversation;

    public GamePresenterNew(GameOprateViewNew gameOprateView) {
        this.gameOprateView = gameOprateView;
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, ConstentNew.TABLE_ID);
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


    public void betMoneyAnim(final Activity activity, int[] startPoint, int pos) {
        ChessCardApplication.getInstance().playGoldSound();
        int height = Tools.dip2px(activity, 40);
        float mutil = 1.34f;
        int width = (int) (height * mutil);
        int centerX = (int) (Tools.getScreenWide(activity) / 2);
        int centerY = (int) (Tools.getScreenHeight(activity) / 2);

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
        final ViewGroup viewGroup = createAnimLayout(activity);
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


    public void startDice(final Activity context, final int one, final int two, int[] startPoint) {
        final int[] endPoint = new int[]{(int) (Tools.getScreenWide(context) / 2), (int) (Tools.getScreenHeight(context) / 2)};
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
                imgOne.setBackgroundResource(diceRes[one]);
                imgTwo.setBackgroundResource(diceRes[two]);

                imgOne.setY(endPoint[1]);
                imgOne.setX(endPoint[0] - Tools.dip2px(context, 15));

                imgTwo.setY(endPoint[1]);
                imgTwo.setX(endPoint[0]);
                imgOne.clearAnimation();
                imgTwo.clearAnimation();
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

    public void endCountTime(int type) {
        switch (type) {

        }
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

                        }

                        if (bean_message == null)
                            return;
                        switch (bean_message.type) {

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
                            case ConstentNew.TYPE_RESET_CHESS: //洗牌

                                break;
                            case ConstentNew.TYPE_BET_MONEY: //押注时间

                                break;
                            case ConstentNew.TYPE_CURRENT_STATUE: //当前游戏状态

                                break;
                            case ConstentNew.TYPE_DEAL_CHESS: //发牌

                                break;
                            case ConstentNew.TYPE_DOWN_BANKER: //下庄

                                break;
                            case ConstentNew.TYPE_EXIT_GAME: //退出游戏

                                break;
                            case ConstentNew.TYPE_GET_RESULT: //结算

                                break;
                            case ConstentNew.TYPE_NOTIFY_BANKER: //通知庄家进行选择

                                break;
                            case ConstentNew.TYPE_OPEN_CHESS: //开牌

                                break;
                            case ConstentNew.TYPE_RENEW_MONEY: //续费

                                break;
                            case ConstentNew.TYPE_SHAKE_DICE: //摇色子

                                break;
                            case ConstentNew.TYPE_UP_BANKER: //上庄

                                break;
                            case ConstentNew.TYPE_WAIT_TIME: //等待时间

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
}
