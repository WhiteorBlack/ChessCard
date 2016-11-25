package com.bai.chesscard.mina;

import com.bai.chesscard.utils.Tools;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class IoListener implements IoServiceListener {

    public NioSocketConnector connector;

    public IoListener(NioSocketConnector connector) {
        this.connector = connector;
    }

    @Override
    public void serviceActivated(IoService arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void serviceDeactivated(IoService arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void serviceIdle(IoService arg0, IdleStatus arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionClosed(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionCreated(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionDestroyed(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub
        repeatConnect("");
    }

    /*
     * 断线重连操作
     * @param content
     */
    public void repeatConnect(String content) {
        // 执行到这里表示Session会话关闭了，需要进行重连,我们设置每隔3s重连一次,如果尝试重连5次都没成功的话,就认为服务器端出现问题,不再进行重连操作
        int count = 0;// 记录尝试重连的次数
        while (true) {
            try {
                count++;// 重连次数加1
                ConnectFuture future = connector.connect(new InetSocketAddress(
                        ConstantUtil.OUT_MATCH_PATH, ConstantUtil.WEB_MATCH_PORT));
                future.awaitUninterruptibly();// 一直阻塞住等待连接成功
                IoSession session = future.getSession();// 获取Session对象
                if (session.isConnected()) {
                    // 表示重连成功
                    Tools.debug(content + " : 断线重连" + count
                            + "次之后成功.....");
                    count = 0;
                    break;
                }
            } catch (Exception e) {
                if (count == ConstantUtil.REPEAT_TIME) {
                    Tools.debug(content + " : 断线重连"
                            + ConstantUtil.REPEAT_TIME + "次之后仍然未成功,结束重连.....");
                    break;
                } else {
                    Tools.debug(content + " : 本次断线重连失败,3s后进行第" + (count + 1) + "次重连.....");
                    try {
                        if (count < 3)
                            Thread.sleep(5000);
                        else Thread.sleep(10 * 1000);
                        Tools.debug(content + " : 开始第" + (count + 1) + "次重连.....");
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}
