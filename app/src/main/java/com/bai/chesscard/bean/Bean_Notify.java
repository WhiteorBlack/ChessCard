package com.bai.chesscard.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Bean_Notify extends BaseBean {
    public List<Notify> result;
    public static class Notify{
        public String id;
        public String channel_id;
        public String category_id;
        public String title;
        public String link_url;
        public String img_url;
        public String content;
        public String user_name;
        public String add_time;
    }
}
