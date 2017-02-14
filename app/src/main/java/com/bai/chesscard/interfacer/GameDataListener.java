package com.bai.chesscard.interfacer;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface GameDataListener {
    /**
     * 进入游戏获取数据失败
     */
    void getInGameFail();

    /**
     * 进入游戏获取数据成功
     *
     * @param result
     */
    void getInGameSuccess(String result);


    /**
     * 押注失败
     */
    void betMoneyFial();

    /**
     * 押注成功
     */
    void betMoneySuccess(String result,int money);

    void gameOutFail();
    void gameOutSuccess();

    void getResultFail();
    void getResultSuccess(String result);
}
