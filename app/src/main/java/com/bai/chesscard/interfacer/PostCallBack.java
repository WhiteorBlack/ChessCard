package com.bai.chesscard.interfacer;


import android.telecom.Call;

import com.bai.chesscard.utils.Tools;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Request;
import okhttp3.Response;

public class PostCallBack extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        // TODO Auto-generated method stub
        String result = response.body().string();
        Tools.debug(result);
        return result;
    }

    @Override
    public void onError(okhttp3.Call call, Exception e) {

    }


    @Override
    public void onResponse(String response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBefore(Request request) {
        // TODO Auto-generated method stub
        super.onBefore(request);
    }

    @Override
    public void onAfter() {
        // TODO Auto-generated method stub
        super.onAfter();
    }

}
