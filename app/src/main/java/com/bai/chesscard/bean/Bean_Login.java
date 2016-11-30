package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/11/30.
 */

public class Bean_Login extends BaseBean {
    public Login data;

    public static class Login {
        public String id;
        public String user_name;
        public String birthday;
        public String groupid;
        public String nick_name;
        public String avatar;
        public String mobile;
        public String telphone;
        public String msn;
        public int amount;
        public String referrer;
        public String point;
        public int back_music;
        public int game_music;
        public int status;
        public String sex;
    }
}
