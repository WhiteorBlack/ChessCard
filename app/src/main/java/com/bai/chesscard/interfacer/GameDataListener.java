package com.bai.chesscard.interfacer;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface GameDataListener {
    /**
     * 长连接握手成功
     * @param session
     */
    void onMinaCreated(IoSession session);

    /**
     * 长连接关闭
     */
    void onMinaClose(IoSession session);

    /**
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
     * 空闲中
     */
    void onMinaIdle(IdleStatus status);

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
