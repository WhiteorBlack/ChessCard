package com.bai.chesscard.interfacer;

import android.os.Bundle;

import com.bai.chesscard.bean.Bean_ChessList;
import com.bai.chesscard.bean.Bean_TableDetial;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public interface GameOprateView {
    /**
     * 退出房间时弹窗提示
     */
    void showExitPop();

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
     * 发牌后计时
     */
    void openCountTime(int time);

    /**
     * 开牌后重置所有状态
     */
    void resetStatue();

    /**
     * 结束计时
     */
    void endCountTime();

    /**
     * 显示点数
     */
    void showPoint(int pos, int point,boolean isGray);

    /**
     * 隐藏点数
     */
    void hidePoint();

    /**
     * 显示倍数
     */
    void showMultiple(int pos, int multi);

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

    void endDice(int pos);

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

    /**
     * toast 信息
     *
     * @param msg 信息文字
     */
    void toastMsg(String msg);

    /**
     * toast 信息
     *
     * @param msg 信息stringid
     */
    void toastMsg(int msg);

    /**
     * 投注按钮是否可以点击
     *
     * @param isClickable
     */
    void moneyClickable(boolean isClickable);

    /**
     * 隐藏桌面上面的码牌
     */
    void hidePointCard();

    /**
     * 显示桌面上面的码牌
     *
     * @param pos 对应玩儿家的码牌
     */
    void showPointCard(int pos, int point);

    /**
     * 发牌
     *
     * @param pos
     */
    void dealChess(int pos);

    /**
     * 如果用户投注成功那么将用户的桌面金币减少
     *
     * @param point
     */
    void resetUserMoney(int point);

    /**
     * 用户断开连接
     */
    void disContect();

    /**
     * 用户重连成功
     */
    void contect();

    /**
     * 用户桌面金币不够
     * 每次结算完之后判断用户桌面金币数量是否足够
     */
    void lackMoney();

    /**
     * 玩儿家下桌
     * @param pos
     */
    void gamerEixt(int pos);
}
