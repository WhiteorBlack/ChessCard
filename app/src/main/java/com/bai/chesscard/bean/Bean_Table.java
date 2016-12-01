package com.bai.chesscard.bean;/**
 * Created by Administrator on 2016/11/8.
 */

import java.util.List;

/**
 * author:${白曌勇} on 2016/11/8
 * TODO:
 */
public class Bean_Table extends BaseBean {
    public List<Table> data;

    public static class Table {
        public String id;
        public String house_id;
        public String first_user_id;
        public int first_point_count;
        public String second_user_id;
        public String second_point_id;
        public String third_user_id;
        public int third_point_count;
        public String four_user_id;
        public String stay_user_id;
        public int stay_user_num;
        public int four_point_count;
        public int total_user_count;
        public String add_time;
    }

}
