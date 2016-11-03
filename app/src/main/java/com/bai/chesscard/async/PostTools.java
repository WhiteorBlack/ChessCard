package com.bai.chesscard.async;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.bai.chesscard.interfacer.PostCallBack;

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
        final List<Bean_Params> paramsList = new ArrayList<Bean_Params>();
        Set entries;
        if (params == null)
            params = new HashMap<>();
        params.put("deviceVersion", CommonUntilities.DEVICEID);
        params.put("systemVersion", CommonUntilities.SYSTEMID);
        try {
            params.put("softwareVersion", YexiuApplicaion.getInstance().getPackageManager().getPackageInfo(YexiuApplicaion.getInstance().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (params != null && params.size() > 0)
            entries = params.entrySet();
        else {
            entries = null;
        }
        if (entries != null) {
            Iterator<Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                if (!TextUtils.isEmpty(entry.getValue())) {
                    paramsList.add(new Bean_Params(entry.getKey(), entry.getValue()));
                }
            }
        }

        String appandString = "";
        Collections.sort(paramsList);
        for (int i = 0; i < paramsList.size(); i++) {
            appandString += paramsList.get(i).getMethod() + paramsList.get(i).getValue();
        }
        params.put("sign", TextUtils.isEmpty(Tools.get32MD5Str(signString + appandString)) ? "" : Tools.get32MD5Str(signString + appandString));
        params.put("mac", TextUtils.isEmpty(CommonUntilities.MAC) ? "" : CommonUntilities.MAC);
        params.put("phoneID", TextUtils.isEmpty(CommonUntilities.DEVICE_ID) ? "" : CommonUntilities.DEVICE_ID);
        OkHttpUtils.post().url(url).params(params).build().execute(postCallBack);

    }

    public static void postFile(String url, String uri, Map<String, String> params, PostCallBack postCallBack) {
        File file = new File(uri);
        if (file.exists()) {
            OkHttpUtils.postFile().url(url).file(file).params(params).build().execute(postCallBack);
        }
    }
}
