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
    public static int BANKERPOINT=-1;  //庄家点数
    public static boolean ISBANKERDOUBLE=false; //庄家是否为对子

    //根据message的类型做相应的操作
    /**
     * 摇动色子
     */
    public static int SHAKE_DICE = 0;
    /**
     * 用户选择座位
     */
    public static int SELECT_SITE_POS = 1;
    /**
     * 投注
     */
    public static int BET_MONEY = 2;

    /**
     * 玩儿家掉线
     */
    public static int GAMER_OFF_LINE = 3;
    /**
     * 玩儿家重连
     */
    public static int GAMER_RECONTECT = 4;
    /**
     * 玩儿家退出游戏
     */
    public static int GAMER_EXIT = 5;
    /**
     * 玩儿家从账户中加钱
     */
    public static int ADD_MONEY = 6;

    /**
     * 押注总金额
     */
    public static int BETLEDTPOINT;
    public static int BETMIDPOINT;
    public static int BETRIGHTPOINT;
}
