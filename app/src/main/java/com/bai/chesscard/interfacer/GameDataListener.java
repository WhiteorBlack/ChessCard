package com.bai.chesscard.interfacer;


/**
 * Created by Administrator on 2016/11/25.
 */

public interface GameDataListener {
    /**
     * /**
     * 链接断开
     */
    void onMinaDisconect();

    /**
     * 重连
     *
     * @param time
     */
    void onMinaReconect(int time);


    /**
     * 接收消息
     *
     * @param msg
     */
    void onMessageReceive(Object msg);

    /**
     * 长连接握手失败
     */
    void onContectFail();

    /**
     * 重连失败
     */
    void onReContactFail();

    void onException(Throwable cause);
}
