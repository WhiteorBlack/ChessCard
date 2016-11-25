package com.bai.chesscard.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import android.util.Log;

import com.bai.chesscard.interfacer.GameDataListener;

public class MinaClientHandler extends IoHandlerAdapter {

    private GameDataListener gameDataListener;

    public MinaClientHandler(GameDataListener gameDataListener) {
        this.gameDataListener = gameDataListener;
    }


    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Log.i("TEST", "客户端发生异常");
        super.exceptionCaught(session, cause);
        if (gameDataListener != null)
            gameDataListener.onException(cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String msg = message.toString();
        Log.i("TEST", "i客户端接收到的信息为:" + msg);
        super.messageReceived(session, message);
        if (gameDataListener != null)
            gameDataListener.onMessageReceive(message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
        super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        if (gameDataListener!=null)
            gameDataListener.onMinaCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        if (gameDataListener != null)
            gameDataListener.onMinaIdle(status);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        if (gameDataListener != null)
            gameDataListener.onMinaClose(session);
    }

}
