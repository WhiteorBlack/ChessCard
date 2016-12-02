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
import com.bai.chesscard.bean.Bean_TableDetial;
import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.mina.MinaClientHandler;
import com.bai.chesscard.service.HeartBeatService;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenter implements GameDataListener {
    private GameOprateView gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};
    private HeartBeatService heartBeatService;
    private IoSession gameSession;

    public GamePresenter(GameOprateView gameOprateView) {
        this.gameOprateView = gameOprateView;
    }

    /**
     * 启动service建立长连接
     *
     * @param context
     */
    public void startService(Context context) {
        context.bindService(new Intent(context, HeartBeatService.class), conn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            heartBeatService = ((HeartBeatService.MyBinder) service).getService();
            heartBeatService.setGameDataListener(GamePresenter.this);
        }
    };

    public void onDestory() {
        heartBeatService.stopSelf();
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
    public void betMoney(String userId, int point) {

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
                if (TextUtils.isEmpty(response))
                    return;
                Bean_TableDetial tableDetial = new Gson().fromJson(response, Bean_TableDetial.class);
                if (tableDetial != null && tableDetial.status) {
                    try {
                    } catch (Exception e) {

                    }
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
     * 展示正在游戏用户的信息
     *
     * @param pos
     */
    public void showUserInfo(int pos) {
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
        gameOprateView.showUserInfo(new Bean_TableDetial.TableUser());
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
        Bundle bundle=new Bundle();
        bundle.putInt("bankerOne",1);
        bundle.putInt("bankerTwo",2);
        bundle.putInt("leftOne",1);
        bundle.putInt("leftTwo",2);
        bundle.putInt("bottomOne",1);
        bundle.putInt("bottomTwo",2);
        bundle.putInt("rightOne",1);
        bundle.putInt("rightTwo",2);
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

    @Override
    public void onMinaCreated(IoSession session) {
        this.gameSession = session;
    }

    @Override
    public void onMinaClose(IoSession session) {

    }

    @Override
    public void onMinaDisconect() {

    }

    @Override
    public void onMinaReconect(int time) {

    }

    @Override
    public void onMinaIdle(IdleStatus status) {

    }

    @Override
    public void onMessageReceive(Object msg) {

    }

    @Override
    public void onContectFail() {
        Tools.debug("hahahhahaah_zheli shi presenter");
    }

    @Override
    public void onReContactFail() {
        Tools.debug("hahahhahaah_zheli shi presenter");
    }

    @Override
    public void onException(Throwable cause) {
        Tools.debug("hahahhahaah_zheli shi presenter");
    }
}
