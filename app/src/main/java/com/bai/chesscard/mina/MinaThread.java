package com.bai.chesscard.mina;

import com.bai.chesscard.interfacer.GameDataListener;
import com.bai.chesscard.utils.Tools;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaThread extends Thread {
    private GameDataListener gameDataListener;

    public void setGameDataListener(GameDataListener gameDataListener) {
        this.gameDataListener = gameDataListener;
    }

    @Override
    public void run() {
        super.run();
        // TODO Auto-generated method stub]
        Tools.debug("heard start.....");
        NioSocketConnector connector = null;
        try {
            connector = new NioSocketConnector();
            MinaClientHandler handler = new MinaClientHandler(gameDataListener);//创建handler对象，用于业务逻辑处理
            connector.setHandler(handler);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));//添加Filter对象
        } catch (Exception e2) {
            e2.printStackTrace();
           Tools.debug("eeeeerrrrrror"+e2.toString());
        }
//        connector.setConnectTimeout(ConstantUtil.TIMEOUT);//设置连接超时时间
        connector.setConnectTimeoutMillis(ConstantUtil.TIMEOUT);
        int count = 0;//记录连接次数
        while (true) {
            try {
                count++;
                //执行到这里表示客户端刚刚启动需要连接服务器,第一次连接服务器的话是没有尝试次数限制的，但是随后的断线重连就有次数限制了
                ConnectFuture future = connector.connect(new InetSocketAddress(ConstantUtil.OUT_MATCH_PATH, ConstantUtil.WEB_MATCH_PORT));
                future.awaitUninterruptibly();//一直阻塞,直到连接建立
                IoSession session = future.getSession();//获取Session对象
                if (session.isConnected()) {
                    //表示连接成功
                    Tools.debug(" : 客户端连接服务器成功.....");
                    break;
                }
            } catch (RuntimeIoException e) {
                Tools.debug(" : 第" + count + "次客户端连接服务器失败，因为" + ConstantUtil.TIMEOUT + "s没有连接成功");
                try {
                    if (count < 3)
                        Thread.sleep(5000);//如果本次连接服务器失败，则间隔2s后进行重连操作
                    else
                        Thread.sleep(10 * 1000);
                    Tools.debug(" : 开始第" + (count + 1) + "次连接服务器");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }catch (Exception e){
            }
            /**
             * 超出次数不在重连
             */
            if (count > ConstantUtil.REPEAT_TIME) {
                if (gameDataListener != null)
                    gameDataListener.onReContactFail();
                break;
            }
        }
        //为MINA客户端添加监听器，当Session会话关闭的时候，进行自动重连
        connector.addListener(new IoListener(connector));
    }

}