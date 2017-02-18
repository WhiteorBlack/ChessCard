package com.bai.chesscard.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class Bean_Audience extends BaseBean {
    public List<Audience> data;
    public static class Audience{
        String id;
        String table_id;
        String user_id;
        public Bean_TableDetial.TableUser userinfo;
    }
    public List<AudienceNew> result;
    public static class AudienceNew{
        public int id;
        public String user_logo;
    }
}
