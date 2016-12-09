package com.bai.chesscard.bean;

/**
 * Created by Administrator on 2016/12/9.
 */

public class Bean_ShakeDice extends BaseBean{
    public DiceData data;
    public static class DiceData{
        public String id;
        public String house_id;
        public String table_id;
        public int scount;
        public int scount1;
        public String first;
        public String second;
        public String third;
        public String four;
        public int pcount;
    }
}
