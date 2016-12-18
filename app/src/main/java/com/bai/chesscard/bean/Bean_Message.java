package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class Bean_Message {
    public String id;
    public int type;
    public String data;
    public int betNum; //下注的玩家位置 1.初 2.天 3.尾
    public int betPoint; //下注的数量
    public int gamerPos; //玩儿家 位置
    public int chesscount; //牌的数量
    public String diceNum;  //筛子的数目
    public int time; //时间数
    public Bean_TableDetial.TableDetial gameStatue;

    //一下为前端逻辑控制
}
