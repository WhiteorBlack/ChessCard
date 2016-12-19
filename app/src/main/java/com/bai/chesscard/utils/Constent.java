package com.bai.chesscard.utils;

/**
 * Created by Administrator on 2016/12/7.
 * 存放用户状态的公共变量
 */

public class Constent {

    private static String USERID = ""; //用户id
    private static  boolean ISBANKER ;  //是否为庄家
    private static boolean ISGAMER = false; //是否为玩儿家
    private static String TABLEID = "-1"; //游戏桌id
    private static String ROOMID = ""; //房间id
    private static String ROUNDID = ""; //游戏局数id
    private static int SELECTPOS = -1; //选择投注的用户位置
    private static int MINCOUNT = 0;  //房间最低限额
    private static int USERMONEY = 0;  //用户在游戏中的金币
    private static int IDENTIFY = 0;  //标识用户身份信息
    private static boolean[] isHasUser = new boolean[]{false, false, false, false}; //标识该位置是否有人坐下
    private static String GROUPID = "";  //房间id对应的是腾讯IM群id
    private static boolean ISSHAKING = false;  //标识是否在摇色子
    private static int BANKERPOINT = -1;  //庄家点数
    private static boolean ISBANKERDOUBLE = false; //庄家是否为对子
    private static int DEALCHESSPOS = -1; //从哪个位置开始发牌
    /**
     * 用户选择座位
     */
    public static int SELECT_SITE_POS = -1;

    public  int getSelectSitePos() {
        return SELECT_SITE_POS;
    }

    public static void setSelectSitePos(int selectSitePos) {
        Constent.SELECT_SITE_POS = selectSitePos;
    }

    public  String getUSERID() {
        return USERID;
    }

    public  void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public static boolean ISBANKER() {
        return ISBANKER;
    }

    public static void setISBANKER(boolean ISBANKER) {
        Constent.ISBANKER = ISBANKER;
    }

    public static boolean ISGAMER() {
        return ISGAMER;
    }

    public static void setISGAMER(boolean ISGAMER) {
        Constent.ISGAMER = ISGAMER;
        Tools.debug("setIsGamer---"+ISGAMER);
    }

    public  String getTABLEID() {
        return TABLEID;
    }

    public  void setTABLEID(String TABLEID) {
        this.TABLEID = TABLEID;
    }

    public  String getROOMID() {
        return ROOMID;
    }

    public  void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public  String getROUNDID() {
        return ROUNDID;
    }

    public  void setROUNDID(String ROUNDID) {
        this.ROUNDID = ROUNDID;
    }

    public  int getSELECTPOS() {
        return SELECTPOS;
    }

    public  void setSELECTPOS(int SELECTPOS) {
        this.SELECTPOS = SELECTPOS;
    }

    public  int getMINCOUNT() {
        return MINCOUNT;
    }

    public  void setMINCOUNT(int MINCOUNT) {
        this.MINCOUNT = MINCOUNT;
    }

    public  int getUSERMONEY() {
        return USERMONEY;
    }

    public  void setUSERMONEY(int USERMONEY) {
        this.USERMONEY = USERMONEY;
    }

    public  int getIDENTIFY() {
        return IDENTIFY;
    }

    public  void setIDENTIFY(int IDENTIFY) {
        this.IDENTIFY = IDENTIFY;
    }

    public  boolean[] getIsHasUser() {
        return isHasUser;
    }

    public  void setIsHasUser(boolean[] isHasUser) {
        this.isHasUser = isHasUser;
    }

    public  void setIsHasUser(int pos, boolean isHas) {
        isHasUser[pos] = isHas;
    }

    public  boolean getIsHasUser(int pos) {
        return isHasUser[pos];
    }

    public  String getGROUPID() {
        return GROUPID;
    }

    public  void setGROUPID(String GROUPID) {
        this.GROUPID = GROUPID;
    }

    public  boolean ISSHAKING() {
        return ISSHAKING;
    }

    public  void setISSHAKING(boolean ISSHAKING) {
        this.ISSHAKING = ISSHAKING;
    }

    public  int getBANKERPOINT() {
        return BANKERPOINT;
    }

    public  void setBANKERPOINT(int BANKERPOINT) {
        this.BANKERPOINT = BANKERPOINT;
    }

    public  boolean ISBANKERDOUBLE() {
        return ISBANKERDOUBLE;
    }

    public  void setISBANKERDOUBLE(boolean ISBANKERDOUBLE) {
        this.ISBANKERDOUBLE = ISBANKERDOUBLE;
    }

    public  int getDEALCHESSPOS() {
        return DEALCHESSPOS;
    }

    public  void setDEALCHESSPOS(int DEALCHESSPOS) {
        this.DEALCHESSPOS = DEALCHESSPOS;
    }

    public  int getBETLEDTPOINT() {
        return BETLEDTPOINT;
    }

    public  void setBETLEDTPOINT(int BETLEDTPOINT) {
        Constent.BETLEDTPOINT = BETLEDTPOINT;
    }

    public  int getBETMIDPOINT() {
        return BETMIDPOINT;
    }

    public  void setBETMIDPOINT(int BETMIDPOINT) {
        Constent.BETMIDPOINT = BETMIDPOINT;
    }

    public  int getBETRIGHTPOINT() {
        return BETRIGHTPOINT;
    }

    public  void setBETRIGHTPOINT(int BETRIGHTPOINT) {
        this.BETRIGHTPOINT = BETRIGHTPOINT;
    }



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
     * 押注
     */
    public static final int BET_MONEY = 3;

    /**
     * 开牌
     */
    public static final int OPEN_CHESS = 4;
    /**
     * 下庄
     */
    public static final int GAMER_EXIT = 5;
    /**
     * 有空闲座位
     */
    public static final int FREE_SITE = 6;
    /**
     * 当前游戏状态
     */
    public static final int GAME_STATUE = 7;
    /**
     * 询问庄家是否续庄
     */
    public static final int RENEW_BANKER = 8;

    /**
     * 续费时间
     */
    public static final int RENEW_GOLD = 9;
    /**
     * 有玩儿家坐下
     */
    public static final int GAMER_SITE = 10;

    /**
     * 押注总金额
     */
    public static int BETLEDTPOINT;
    public static int BETMIDPOINT;
    public static int BETRIGHTPOINT;

}
