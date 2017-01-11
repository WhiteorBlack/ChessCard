package com.bai.chesscard.async;

import android.text.TextUtils;

import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/27.
 */

public class GameOprateData {
    private static GameDataListener gameDataListener;

    public static GameOprateData getInstance(GameDataListener DataListener) {
        gameDataListener = DataListener;
        return new GameOprateData();
    }

    /**
     * 用户进入游戏
     */
    public void getInGame() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("TOKEN",CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL+"TableDetial", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameDataListener.getInGameFail();
                    return;
                }
                gameDataListener.getInGameSuccess(response);
            }
        });
    }

    public void gameOut(){
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("TOKEN",CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL+"LevelTable", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    gameDataListener.gameOutFail();
                    return;
                }
                gameDataListener.gameOutSuccess();
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
