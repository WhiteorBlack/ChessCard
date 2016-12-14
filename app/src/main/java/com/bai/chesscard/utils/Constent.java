package com.bai.chesscard.utils;

/**
 * Created by Administrator on 2016/12/7.
 * 存放用户状态的公共变量
 */

public class Constent {
    public static String USERID = ""; //用户id
    public static boolean ISBANKER = false;  //是否为庄家
    public static boolean ISGAMER = false; //是否为玩儿家
    public static String TABLEID = ""; //游戏桌id
    public static String ROOMID = ""; //房间id
    public static String ROUNDID = ""; //游戏局数id
    public static int SELECTPOS = -1; //选择投注的用户位置
    public static int MINCOUNT = 0;  //房间最低限额
    public static int USERMONEY = 0;  //用户在游戏中的金币
    public static int IDENTIFY = 0;  //标识用户身份信息
    public static boolean[] isHasUser = new boolean[]{false, false, false, false}; //标识该位置是否有人坐下
    public static String GROUPID = "";  //房间id对应的是腾讯IM群id
    public static boolean ISSHAKING = false;  //标识是否在摇色子
    public static int BANKERPOINT = -1;  //庄家点数
    public static boolean ISBANKERDOUBLE = false; //庄家是否为对子
    /**
     * 用户选择座位
     */
    public static int SELECT_SITE_POS = -1;

    //根据message的类型做相应的操作

    /**
     * 码牌,一共16张牌,每码一次可以进行4轮游戏
     */
    public static final int RESET_CHESS = 0;

    /**
     * 摇色子
     */
    public static final int SHAKE_DICE = 1;

    /**
     * 发牌
     */
    public static final int DEAL_CHESS = 2;

    /**
     * 摇色子
     */
    public static final int BET_MONEY = 3;

    /**
     * 开牌
     */
    public static final int OPEN_CHESS = 4;
    /**
     * 玩儿家退出游戏
     */
    public static final int GAMER_EXIT = 5;
    /**
     * 有空闲座位
     */
    public static final int FREE_SITE = 6;
    /**
     * 当前游戏状态
     */
    public static final int GAME_STATUE=7;
    /**
     * 询问庄家是否续庄
     */
    public static final int RENEW_BANKER=8;

    /**
     * 续费时间
     */
    public static final int RENEW_GOLD=9;

    /**
     * 押注总金额
     */
    public static int BETLEDTPOINT;
    public static int BETMIDPOINT;
    public static int BETRIGHTPOINT;
}
