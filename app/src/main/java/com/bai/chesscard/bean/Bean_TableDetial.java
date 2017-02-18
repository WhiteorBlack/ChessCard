package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/12/2.
 */

public class Bean_TableDetial extends BaseBean {
    public String pointstr;
    public TableUser firstuser;
    public TableUser seconduser;
    public TableUser thirduser;
    public TableUser fouruser;
    public int secordbet;
    public int thirdbet;
    public int fourbet;
    public int round;
    public int ver;
    public String firstcard;
    public String secondcard;
    public String thirdcard;
    public String fourcard;
    public String lastpai;
    public int game_status;
    public int lasttime;
    public static class TableUser {
        public String id;
        public String token;
        public String mobile;
        public String user_logo;
        public String nick_name;
        public int lookmonery;
        public int amount;
    }
}
