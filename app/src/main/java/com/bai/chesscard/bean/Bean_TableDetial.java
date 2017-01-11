package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/12/2.
 */

public class Bean_TableDetial extends BaseBean {
//    public TableDetial data;
//
//    public static class TableDetial {
        public String pointstr;
        public TableUser first_user;
        public TableUser second_user;
        public TableUser third_user;
        public TableUser four_user;
        public int game_status;
        public int secordbet;
        public int thirdbet;
        public int fourbet;
        public int round;
        public int ver;
//    }

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
