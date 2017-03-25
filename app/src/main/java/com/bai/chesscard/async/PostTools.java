package com.bai.chesscard.async;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.AppPrefrence;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.Tools;
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

import okhttp3.OkHttpClient;

public class PostTools {

    public static void postData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        if (params == null)
            params = new HashMap<>();
        params.put("timestamp",System.currentTimeMillis()+"");
        OkHttpUtils.post().url(url).params(params).addHeader("sign", Tools.get32MD5Str(CommonUntilities.TOKEN)).build().execute(postCallBack);
    }

    public static void postFile(String url, String uri, Map<String, String> params, PostCallBack postCallBack) {
        File file = new File(uri);
        if (file.exists()) {
            OkHttpUtils.postFile().url(url).file(file).params(params).build().execute(postCallBack);
        }
    }

    public static void getData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        if (params == null)
            params = new HashMap<>();
        params.put("timestamp",System.currentTimeMillis()+"");
        OkHttpUtils.get().url(url).params(params).addHeader("sign", Tools.get32MD5Str(CommonUntilities.TOKEN)).build().execute(postCallBack);
    }
}
