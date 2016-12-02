package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/12/2.
 */

public class Bean_TableDetial extends BaseBean {
    public TableDetial data;

    public static class TableDetial {
        public String id;
        public String pointstr;
        public TableUser first_user;
        public TableUser second_user;
        public TableUser third_user;
        public TableUser four_user;
    }

    public static class TableUser {
        public String id;
        public String token;
        public String group_id;
        public String user_name;
        public String salt;
        public String password;
        public String mobile;
        public String avatar;
        public String nick_name;
        public String sex;
        public String email;
        public int amount;
        public int point;
        public int exp;
        public int status;
        public int reffer_tel;
    }
}
