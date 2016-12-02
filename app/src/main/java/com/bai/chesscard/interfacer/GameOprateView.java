package com.bai.chesscard.interfacer;

import android.os.Bundle;

import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_TableDetial;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public interface GameOprateView {
    void setChessData(List<Bean_ChessList.Chess> data);
    /**
     * 展示玩儿家信息
     *
     * @param user
     */
    void showUserInfo(Bean_TableDetial.TableUser user);

    /**
     * 展示观众信息
     */
    void showAudience();

    /**
     * 返回
     */
    void back();

    /**
     * 展示设置
     */
    void showSetting();

    /**
     * 增加按钮点击
     */
    void addMoney();

    /**
     * 投注
     *
     * @param pos
     */
    void followMoney(int pos);

    /**
     * 加载动画展示
     */
    void showDialog();

    /**
     * 加载动画消失
     */
    void hideDialog();

    /**
     * 开牌
     */
    void openChess(Bundle bundle);

    /**
     * 开始计时
     */
    void startCountTime(int time);

    /**
     * 结束计时
     */
    void endCountTime();

    /**
     * 显示点数
     */
    void showPoint();

    /**
     * 隐藏点数
     */
    void hidePoint();

    /**
     * 显示倍数
     */
    void showMultiple(int pos);

    /**
     * 隐藏倍数
     *
     * @param pos
     */
    void hideMultiple(int pos);

    /**
     * 骰子摇动
     */
    void shakeDice();

    /**
     * 骰子停止
     */
    void endDice(int one, int two);

    /**
     * 桌面详情
     *
     * @param tableInfo
     */
    void setTableInfo(Bean_TableDetial.TableDetial tableInfo);

    /**
     * 用户下注之后本地更改用户金币数量
     *
     * @param point
     */
    void rediusPoint(int point);

    /**
     * 获胜之后本地增加用户金币
     *
     * @param point
     */
    void addPoint(int point);
}
