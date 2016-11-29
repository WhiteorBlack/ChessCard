package com.bai.chesscard.async;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PostTools {

    public static void postData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        final String signString = CommonUntilities.MD5KEY;
        if (params == null)
            params = new HashMap<>();
        params.put("timestamp",System.currentTimeMillis()+"");
        OkHttpUtils.post().url(url).params(params).build().execute(postCallBack);

    }

    public static void postFile(String url, String uri, Map<String, String> params, PostCallBack postCallBack) {
        File file = new File(uri);
        if (file.exists()) {
            OkHttpUtils.postFile().url(url).file(file).params(params).build().execute(postCallBack);
        }
    }
}
