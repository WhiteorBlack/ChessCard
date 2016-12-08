package com.bai.chesscard.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.chesscard.R;
import com.bai.chesscard.async.PostTools;
import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_Login;
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.mina.MinaClientHandler;
import com.bai.chesscard.service.HeartBeatService;
import com.bai.chesscard.service.MessageEvent;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Constent;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import static android.R.attr.data;
import static android.R.attr.readPermission;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenter implements TIMMessageListener {
    private GameOprateView gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private IoSession gameSession;
    private TIMConversation conversation;

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
//        MessageEvent.getInstance().addObserver(this);
        TIMManager.getInstance().addMessageListener(this);
    }

    public void onDestory() {
        MessageEvent.getInstance().clear();
        conversation = null;
    }

    public void getChessData() {
        List<Bean_ChessList.Chess> chess = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
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
    public void betMoney(String userId, int point, String tableId, String houseId) {
        if (Constent.SELECTPOS < 0 && !Constent.ISGAMER) {
            gameOprateView.toastMsg("请选择要投注的玩儿家");
            return;
        }
        gameOprateView.moneyClickable(false);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("mid", "");
        params.put("num", "" + Constent.SELECTPOS);
        params.put("point", point + "");
        params.put("house_id", houseId);
        params.put("table_id", tableId);
        PostTools.postData(CommonUntilities.MAIN_URL + "yazhu", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameOprateView.toastMsg(R.string.no_network);
                    return;
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("data", "测试信息");
                params.put("type", "0");
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
                Bean_TableDetial tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (tableDetial != null && tableDetial.status) {
                    Constent.isHasUser[0] = !(tableDetial.data.first_user == null);
                    Constent.isHasUser[1] = !(tableDetial.data.second_user == null);
                    Constent.isHasUser[2] = !(tableDetial.data.third_user == null);
                    Constent.isHasUser[3] = !(tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(tableDetial.data);
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
        switch (pos) {
            case 0:
                //庄家

                break;
            case 1:
                //初家

                break;
            case 2:
                //天家

                break;
            case 3:
                //尾家

                break;
        }
        if (Constent.isHasUser[pos])
            gameOprateView.showUserInfo(new Bean_TableDetial.TableUser());
        else {
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
                Bean_TableDetial tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (tableDetial != null) {
                    Constent.isHasUser[0] = !(tableDetial.data.first_user == null);
                    Constent.isHasUser[1] = !(tableDetial.data.second_user == null);
                    Constent.isHasUser[2] = !(tableDetial.data.third_user == null);
                    Constent.isHasUser[3] = !(tableDetial.data.four_user == null);
                    gameOprateView.setTableInfo(tableDetial.data);
                }
                if (tableDetial.status) {

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
        bundle.putInt("bankerOne", 1);
        bundle.putInt("bankerTwo", 2);
        bundle.putInt("leftOne", 1);
        bundle.putInt("leftTwo", 2);
        bundle.putInt("bottomOne", 1);
        bundle.putInt("bottomTwo", 2);
        bundle.putInt("rightOne", 1);
        bundle.putInt("rightTwo", 2);
        gameOprateView.openChess(bundle);
    }

    /**
     * 开始计时
     */
    public void startCountTime(int time) {
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameOprateView.startCountTime((int) (millisUntilFinished / 1000));
                Map<String, String> params = new HashMap<String, String>();
                params.put("data", "测试信息" + millisUntilFinished);
                params.put("type", "0");
                setMessage(params);
            }

            @Override
            public void onFinish() {
                endCountTime();
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
    public void showPoint() {
        gameOprateView.showPoint();
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
    public void showMultiple(int pos) {
        gameOprateView.showMultiple(pos);
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
        gameOprateView.shakeDice();
        startDice(context);
    }

    private int onePoint, twoPoint;

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
        new CountDownTimer(1500, 1500) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ((AnimationDrawable) imgOne.getBackground()).stop();
                ((AnimationDrawable) imgTwo.getBackground()).stop();
                onePoint = new Random().nextInt(6);
                twoPoint = new Random().nextInt(6);
                Tools.debug("one" + onePoint + "two" + twoPoint);
//                imgOne.clearAnimation();
//                imgTwo.clearAnimation();
                imgOne.setBackgroundResource(diceRes[onePoint]);
                imgTwo.setBackgroundResource(diceRes[twoPoint]);
                endDice(onePoint, twoPoint);
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
    public void endDice(int one, int two) {
        gameOprateView.endDice(one, two);
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                viewGroup.removeAllViews();
                viewGroup.setBackgroundColor(Color.TRANSPARENT);
                showToast(viewGroup.getContext(), "庄家开");
            }
        }.start();

    }

    private void showToast(Context context, String text) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView textView = new TextView(context);
        textView.setLayoutParams(new FrameLayout.LayoutParams(Tools.dip2px(context, 200), Tools.dip2px(context, 200)));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setTextColor(Color.WHITE);
        textView.setText(text);
        textView.setBackgroundResource(R.drawable.toast_bg);
        textView.getBackground().setAlpha(180);
        toast.setView(textView);
        toast.show();
    }

    public void showExitPop() {
        gameOprateView.showExitPop();
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        if (list != null && list.size() > 0) {
            for (TIMMessage item : list) {
                for (int i = 0; i < item.getElementCount(); i++) {
                    TIMElem elem = item.getElement(i);
                    TIMElemType elemType = elem.getType();
                    Tools.debug("elemType--" + elemType.name());
                    if (elemType == TIMElemType.Text) {
                        TIMTextElem elemText = (TIMTextElem) elem;
                        Tools.debug("收到的消息:" + elemText.getText());
                    }

                }
            }
        }
        return false;
    }


    private void setMessage(Map<String, String> params) {
        int index = 0;
        String content = "";
        TIMMessage message = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();

        Set entries = null;
        if (params != null && params.size() > 0) {
            entries = params.entrySet();
        }
        if (entries != null) {
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (index == 0)
                    content += entry.getKey() + ":" + entry.getValue();
                else content += "," + entry.getKey() + ":" + entry.getValue();
                index++;
            }
        }
        elem.setText("这里是测试消息啊a");
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


}
