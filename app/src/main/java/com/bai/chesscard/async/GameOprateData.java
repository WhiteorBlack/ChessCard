package com.bai.chesscard.async;

import android.text.TextUtils;

import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/27.
 */

public class GameOprateData {
    private GameDataListener gameDataListener;

    public GameOprateData getInstance(GameDataListener gameDataListener) {
        this.gameDataListener = gameDataListener;
        return new GameOprateData();
    }

    /**
     * 用户进入游戏
     */
    public void getInGame() {
        Map<String, String> params = new HashMap<>();

        PostTools.postData(CommonUntilities.MAIN_URL, params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameDataListener.getInGameFail();
                    return;
                }
            }
        });
    }

    public void betMoney() {
        Map<String, String> params = new HashMap<>();

        PostTools.postData(CommonUntilities.MAIN_URL, params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameDataListener.betMoneyFial();
                    return;
                }
            }
        });
    }
}
