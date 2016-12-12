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

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_BetMoney;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_Message;
import com.bai.chesscard.bean.Bean_ShakeDice;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
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

public class GamePresenter implements Observer {
    private GameOprateView gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private TIMConversation conversation;
    private Bean_TableDetial bean_tableDetial;

    public GamePresenter(GameOprateView gameOprateView, String groupId, TIMConversationType type) {
        this.gameOprateView = gameOprateView;
        conversation = TIMManager.getInstance().getConversation(type, groupId);
        conversation.disableStorage();

    }

    /**
     * 启动service建立长连接
     *
     * @param context
     */
    public void startService(Context context) {
        MessageEvent.getInstance().addObserver(this);
    }

    public void onDestory() {
        MessageEvent.getInstance().clear();

        conversation = null;
    }

    public void getChessData(int count) {
        List<Bean_ChessList.Chess> chess = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            chess.add(new Bean_ChessList.Chess());
        }
        gameOprateView.setChessData(chess);
    }


    /**
     * 下注
     *
     * @param userId
     * @param point
     */
    public void betMoney(Activity activity, String userId, final int point, String tableId, String houseId, int[] startPoint) {
        if (Constent.SELECTPOS < 0 && !Constent.ISGAMER) {
            gameOprateView.toastMsg("请选择要投注的玩儿家");
            return;
        }
        if (Constent.USERMONEY < point) {
            gameOprateView.toastMsg("账号余额不足,请及时充值");
            return;
        }
        betMoneyAnim(activity, startPoint);
        gameOprateView.moneyClickable(false);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("mid", Constent.ROUNDID);
        params.put("num", "" + Constent.SELECTPOS);
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
                    Constent.USERMONEY = Constent.USERMONEY - point;
                    gameOprateView.resetUserMoney(Constent.USERMONEY);
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
                Bean_Message params = new Bean_Message();
                params.type = 0;
                params.data = "测试信息json";
                setMessage(params);

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
        params.put("table_id", tableId);
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
                    Constent.isHasUser[0] = !(bean_tableDetial.data.first_user == null);
                    Constent.isHasUser[1] = !(bean_tableDetial.data.second_user == null);
                    Constent.isHasUser[2] = !(bean_tableDetial.data.third_user == null);
                    Constent.isHasUser[3] = !(bean_tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(bean_tableDetial.data);
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

        if (Constent.isHasUser[pos]) {
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
            capturePosition(pos + 1, userId);
        }
    }

    /**
     * 选择座位坐下
     *
     * @param pos
     * @param userId
     */
    private void capturePosition(final int pos, String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", Constent.TABLEID);
        params.put("num", pos + "");
        params.put("user_id", Constent.USERID);
        PostTools.postData(CommonUntilities.MAIN_URL + "checktable", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameOprateView.toastMsg(R.string.no_network);
                    Constent.isHasUser[pos - 1] = false;
                    return;
                }
                Constent.isHasUser[pos - 1] = true;
                bean_tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (bean_tableDetial != null) {
                    Constent.isHasUser[0] = !(bean_tableDetial.data.first_user == null);
                    Constent.isHasUser[1] = !(bean_tableDetial.data.second_user == null);
                    Constent.isHasUser[2] = !(bean_tableDetial.data.third_user == null);
                    Constent.isHasUser[3] = !(bean_tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(bean_tableDetial.data);
                }
                if (bean_tableDetial.status) {

                    if (pos == 1) {
                        Constent.ISBANKER = true;
                        Constent.ISGAMER = true;
                    } else {
                        Constent.ISBANKER = false;
                        Constent.ISGAMER = true;
                    }
                    Constent.SELECTPOS = pos;
                    gameOprateView.moneyClickable(false);
                } else {
                    Constent.ISGAMER = false;
                    Constent.ISBANKER = false;
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
    public void openChess() {
        Bundle bundle = new Bundle();
        bundle.putInt("bankerOne", getChessPoint(bean_shakeDice.data.first)[0]);
        bundle.putInt("bankerTwo", getChessPoint(bean_shakeDice.data.first)[1]);
        bundle.putInt("leftOne", getChessPoint(bean_shakeDice.data.second)[0]);
        bundle.putInt("leftTwo", getChessPoint(bean_shakeDice.data.second)[1]);
        bundle.putInt("bottomOne", getChessPoint(bean_shakeDice.data.third)[0]);
        bundle.putInt("bottomTwo", getChessPoint(bean_shakeDice.data.third)[1]);
        bundle.putInt("rightOne", getChessPoint(bean_shakeDice.data.four)[0]);
        bundle.putInt("rightTwo", getChessPoint(bean_shakeDice.data.four)[1]);
        gameOprateView.openChess(bundle);
        getResult();
        openCountTime(2 * 1000);
    }

    /**
     * 结算结果
     */
    private void getResult() {
        Map<String, String> params = new HashMap<>();
        params.put("mh_id", Constent.ROUNDID);
        params.put("house_id", Constent.ROOMID);
        params.put("table_id", Constent.TABLEID);
        PostTools.postData(CommonUntilities.MAIN_URL + "judgepoint", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                Tools.debug("result" + response);
            }
        });
    }

    /**
     * 将String[] 转换为int[] 由于数组从0开始,这里减一
     *
     * @param chess
     * @return
     */
    private int[] getChessPoint(String chess) {
        int[] chessPoint = new int[]{-1, -1};
        try {
            chessPoint[0] = Integer.parseInt(chess.split(",")[0]) - 1;
            chessPoint[1] = Integer.parseInt(chess.split(",")[1]) - 1;
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
    public void startCountTime(int time) {
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameOprateView.startCountTime((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                gameOprateView.moneyClickable(false);
                endCountTime();
                openChess();
            }
        }.start();
    }

    /**
     * 结束计时
     */
    public void endCountTime() {
        gameOprateView.endCountTime();
    }

    /**
     * 显示点数
     */
    public void showPoint(int pos, int one, int two) {
        int point = one + two + 2;
        int mutil = -1;
        if (point > 9)
            point = 0;
        else if (one == two)
            mutil = 1;
        showMultiple(pos, mutil);
        gameOprateView.showPoint(pos, point);
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
    public void shakeDice(Activity context) {
        Constent.ISSHAKING = true;
        startDice(context);
        getDiceData();
    }

    private Bean_ShakeDice bean_shakeDice;

    private void getDiceData() {
        Map<String, String> params = new HashMap<>();
        params.put("house_id", Constent.ROOMID);
        params.put("table_id", Constent.TABLEID);
        PostTools.postData(CommonUntilities.MAIN_URL + "setscreen", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Tools.debug("shakeDice--" + response);
                if (TextUtils.isEmpty(response))
                    return;
                bean_shakeDice = new Gson().fromJson(response, Bean_ShakeDice.class);
                Constent.ROUNDID = bean_shakeDice.data.id;
                if (bean_shakeDice != null && bean_shakeDice.status && !Constent.ISSHAKING) {
                    endDice(bean_shakeDice.data.scount, bean_shakeDice.data.scount1);
                }
            }
        });
    }

    public void betMoneyAnim(final Activity activity, int[] startPoint) {
        int height = Tools.dip2px(activity, 50);
        float mutil = 1.34f;
        int width = (int) (height * mutil);

        final int[] endPoint = new int[]{(int) (Tools.getScreenWide(activity) / 2), (int) (Tools.getScreenHeight(activity) / 2)};

        final ImageView imgBet = new ImageView(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        imgBet.setLayoutParams(params);
        imgBet.setBackgroundResource(R.mipmap.bg_gold);
        if (viewGroup == null)
            viewGroup = createAnimLayout(activity);
        viewGroup.removeAllViews();
        startPoint[0] -= width * 2.5;
        startPoint[1] -= height * 3;
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

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBet.clearAnimation();
                imgBet.setX(endPoint[0]);
                imgBet.setY(endPoint[1]);
                imgBet.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void startDice(final Activity context) {
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
                Constent.ISSHAKING = false;
                if (bean_shakeDice != null && bean_shakeDice.status) {
                    ((AnimationDrawable) imgOne.getBackground()).stop();
                    ((AnimationDrawable) imgTwo.getBackground()).stop();
                    imgOne.setBackgroundResource(diceRes[bean_shakeDice.data.scount]);
                    imgTwo.setBackgroundResource(diceRes[bean_shakeDice.data.scount1]);
                    endDice(bean_shakeDice.data.scount, bean_shakeDice.data.scount1);
                }
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
        Constent.ISSHAKING = false;

        final int pos = (one + two) % 4;
        String toast = null;
        switch (pos) {
            case 0:
                toast = "庄家开";
                break;
            case 1:
                toast = "初家开";
                break;
            case 2:
                toast = "天家开";
                break;
            case 3:
                toast = "尾家开";
                break;
        }
        gameOprateView.toastMsg(toast);
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                viewGroup.removeAllViews();
                viewGroup.setBackgroundColor(Color.TRANSPARENT);
                gameOprateView.dealChess(pos);
                gameOprateView.moneyClickable(true);
            }
        }.start();

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
                            if (TextUtils.equals(elemText.getText(), "0"))
                                gameOprateView.shakeDice();
                        }

                        if (bean_message == null)
                            return;
                        switch (bean_message.type) {
                            case 0:
                                //开始摇色子
                                gameOprateView.shakeDice();
                                break;
                            case 2:
                                //用户投注信息
                                gameOprateView.showPointCard(bean_message.betNum, bean_message.betPoint);
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
//                        TIMCustomElem elemText=(TIMCustomElem)elem;
//                        Tools.debug("openChess" + elemText.get + "--" + elemText.getPlatform()+"--"+elemText.getUserData()+"--"+elemText.getSubtype());
                        Tools.debug("data"+new String(elemText.getUserData()));
                    }
                }

            }
        }
    }
}
