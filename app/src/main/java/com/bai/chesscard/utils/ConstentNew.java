package com.bai.chesscard.utils;

/**
 * Created by Administrator on 2016/12/7.
 * 存放用户状态的公共变量
 */

public class ConstentNew {
    public static boolean[] IS_HAS_GAMER = new boolean[]{false, false, false, false};
    /**
     * 当前用户id
     */
    public static String USER_ID;
    /**
     * 要投注的用户位置
     */
    public static int BET_POS = -1;
    /**
     * 当前用户是否为庄家
     */
    public static boolean IS_BANKER = false;
    /**
     * 当前用户是否为普通玩儿家
     */
    public static boolean IS_GAMER = false;
    /**
     * 当前游戏桌子id
     */
    public static String TABLE_ID;
    /**
     * 当前房间id
     */
    public static String ROOM_ID;
    /**
     * 庄家的点数
     */
    public static int BANKER_POINT;
    /**
     * 庄家是否为对子
     */
    public static int DICE_COUNT=-1;
    public static boolean[] SETTLE_RESULT = new boolean[]{false, false, true, false};
    public static boolean IS_BANKER_MUTIL = false;
    /**
     * 坐庄最低限额
     */
    public static int BANKER_LIMIT_MONEY = -1;

    /**
     * 消息类型 等待阶段
     */
    public static final int TYPE_WAIT_TIME = 0;

    /**
     * 消息类型 洗牌阶段
     */
    public static final int TYPE_RESET_CHESS = 1;

    /**
     * 消息类型 押注阶段
     */
    public static final int TYPE_BET_MONEY = 2;

    /**
     * 消息类型 摇色子阶段
     */
    public static final int TYPE_SHAKE_DICE = 3;

    /**
     * 消息类型 发牌阶段
     */
    public static final int TYPE_DEAL_CHESS = 4;

    /**
     * 消息类型 开牌阶段
     */
    public static final int TYPE_OPEN_CHESS = 5;

    /**
     * 消息类型 结算阶段
     */
    public static final int TYPE_GET_RESULT = 6;

    /**
     * 消息类型 上庄阶段
     */
    public static final int TYPE_UP_BANKER = 7;

    /**
     * 消息类型 续费阶段
     */
    public static final int TYPE_RENEW_MONEY = 8;

    /**
     * 消息类型 玩儿家退出阶段
     */
    public static final int TYPE_EXIT_GAME = 9;

    /**
     * 消息类型 下庄阶段
     */
    public static final int TYPE_DOWN_BANKER = 10;
    /**
     * 消息类型 询问庄家阶段
     */
    public static final int TYPE_NOTIFY_BANKER = 11;
    /**
     * 消息类型 当前游戏状态
     */
    public static final int TYPE_CURRENT_STATUE = 12;
    /**
     * 玩儿家坐下
     */
    public static final int TYPE_SITE_DOWN = 13;

    public static final int SETTING_POP = 0;
    public static final int PERSONAL_POP = 1;
    public static final int DISCONTECT_POP = 2;
}
