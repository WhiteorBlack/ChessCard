package com.bai.chesscard.interfacer;

import android.os.Bundle;

import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_TableDetial;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public interface GameOprateViewNew {
    /**
     * 退出游戏
     */
    void exitTable();

    /**
     * 倒计时控制
     *
     * @param time 倒计时时间
     * @param type 流程类别
     */
    void countDownTime(int time, int type);

    /**
     * 摇色子
     */
    void shakeDice();

    /**
     * 展示设置窗口
     */
    void showSettingPop();

    /**
     * 展示个人信息窗口
     */
    void showMinePop();

    void showProgressPop();

    void dissmissProgressPop();

    /**
     * 下庄
     */
    void downBanker();

    /**
     * 上庄
     */
    void upBanker();

    /**
     * 下桌
     */
    void downTable();

    /**
     * 庄家续庄
     */
    void renewMoneyBanker();

    /**
     * 玩儿家续费
     */
    void renewMoneyGamer();

    /**
     * 洗牌
     */
    void resetChess();

    /**
     * 发牌
     */
    void dealChess(int pos);

    /**
     * 开牌
     */
    void openChess(Bundle bundle);

    /**
     * 显示点数
     */
    void showPoint(int pos, int point, boolean isGray);

    /**
     * 显示倍数
     *
     * @param pos
     * @param mutil
     */
    void showMutil(int pos, int mutil);

    /**
     * 结算结果
     *
     * @param pos
     * @param isWinner
     */
    void settleResult(int pos, boolean isWinner);

    /**
     * toast 信息展示
     *
     * @param msg
     */
    void toastMsg(String msg);

    void toastMsg(int resId);

    /**
     * 断线
     */
    void disContect();

    /**
     * 重连
     */
    void reContect();


}
