package com.bai.chesscard.async;

import android.os.Bundle;
import android.text.TextUtils;

import com.bai.chesscard.bean.BaseBean;
import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.interfacer.PostCallBack;
import com.bai.chesscard.utils.CommonUntilities;
import com.bai.chesscard.utils.ConstentNew;
import com.bai.chesscard.utils.Tools;
import com.google.gson.Gson;

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
        params.put("TOKEN", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "TableDetial", params, new PostCallBack() {
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

    /**
     * 退出游戏
     */
    public void gameOut() {
        Map<String, String> params = new HashMap<>();
        params.put("table_id", ConstentNew.TABLE_ID);
        params.put("TOKEN", CommonUntilities.TOKEN);
        PostTools.postData(CommonUntilities.MAIN_URL + "LevelTable", params, new PostCallBack() {
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

    /**
     * 下注
     *
     * @param money
     */
    public void betMoney(int money) {
        if (ConstentNew.IS_GAMER) {
            Map<String, String> params = new HashMap<>();
            params.put("table_id", ConstentNew.TABLE_ID);
            params.put("token", CommonUntilities.TOKEN);
            params.put("seat", ConstentNew.USERPOS + "");
            params.put("bet", money + "");
            params.put("ver", ConstentNew.GAMEROUND + "");
            PostTools.postData(CommonUntilities.MAIN_URL + "UserBetting", params, new PostCallBack() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    if (TextUtils.isEmpty(response)) {
                        gameDataListener.betMoneyFial();
                        return;
                    }
                    gameDataListener.betMoneySuccess(response);
                }
            });
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("table_id", ConstentNew.TABLE_ID);
            params.put("token", CommonUntilities.TOKEN);
            params.put("seat", ConstentNew.USERPOS + "");
            params.put("bet", money + "");
            params.put("ver", ConstentNew.GAMEROUND + "");
            PostTools.postData(CommonUntilities.MAIN_URL + "LookUserBetting", params, new PostCallBack() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    if (TextUtils.isEmpty(response)) {
                        gameDataListener.betMoneyFial();
                        return;
                    }
                    gameDataListener.betMoneySuccess(response);
                }
            });
        }
    }

    public void getResult() {
        if (ConstentNew.IS_GAMER) {
            Map<String, String> params = new HashMap<>();
            params.put("table_id", ConstentNew.TABLE_ID);
            params.put("token", CommonUntilities.TOKEN);
            params.put("ver", ConstentNew.GAMEROUND + "");
            PostTools.postData(CommonUntilities.MAIN_URL + "GetUserSellete", params, new PostCallBack() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    if (TextUtils.isEmpty(response)) {
                        gameDataListener.betMoneyFial();
                        return;
                    }
                    gameDataListener.betMoneySuccess(response);
                }
            });
        }else {
            Map<String, String> params = new HashMap<>();
            params.put("table_id", ConstentNew.TABLE_ID);
            params.put("token", CommonUntilities.TOKEN);
            params.put("ver", ConstentNew.GAMEROUND + "");
            PostTools.postData(CommonUntilities.MAIN_URL + "GetLookUserSellete", params, new PostCallBack() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    if (TextUtils.isEmpty(response)) {
                        gameDataListener.betMoneyFial();
                        return;
                    }
                    gameDataListener.betMoneySuccess(response);
                }
            });
        }
    }


}
