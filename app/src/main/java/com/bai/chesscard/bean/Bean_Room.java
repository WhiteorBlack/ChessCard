package com.bai.chesscard.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Bean_Room extends BaseBean {
    public List<Room> data;
    public static class Room{
        public String id;
        public String house_name;
        public int min_piont;
        public int people_count;
        public String add_time;
        public String remark;
        public String img_url;
        public int max_point;
        public String point_str;
        public int ext1;
    }
}
