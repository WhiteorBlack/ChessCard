package com.bai.chesscard.presenter;

import android.os.CountDownTimer;

import com.bai.chesscard.interfacer.GameOprateView;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GamePresenter {
    private GameOprateView gameOprateView;

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
}
