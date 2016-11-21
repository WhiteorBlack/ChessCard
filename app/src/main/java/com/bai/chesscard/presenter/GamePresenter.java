package com.bai.chesscard.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.chesscard.R;
import com.bai.chesscard.interfacer.GameOprateView;
import com.bai.chesscard.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenter {
    private GameOprateView gameOprateView;
    private ViewGroup viewGroup;
    private int[] diceRes = new int[]{R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};

    public GamePresenter(GameOprateView gameOprateView) {
        this.gameOprateView = gameOprateView;
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
        gameOprateView.showUserInfo(pos);
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
        gameOprateView.openChess();
    }

    /**
     * 开始计时
     */
    public void startCountTime(int time) {
        new CountDownTimer(10 * 1000, 1000) {

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
}
