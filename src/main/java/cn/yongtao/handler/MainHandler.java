package cn.yongtao.handler;

import cn.yongtao.ChatServer;
import cn.yongtao.common.LoginUsersSockets;
import cn.yongtao.common.ResultMessage;
import cn.yongtao.controller.ChatController;
import cn.yongtao.controller.LoginController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Component
public class MainHandler extends SimpleChannelInboundHandler<Object>
{

    private static final Logger logger = Logger.getLogger(ChatServer.class);


    private final LoginController loginController;
    private final ChatController chatController;

    public MainHandler(LoginController loginController, ChatController chatController) {
        this.loginController = loginController;
        this.chatController = chatController;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与新的客户端建立连接");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端连接断开");

        // 断开连接
        //loginController.close(ctx);
        LoginUsersSockets.remove((SocketChannel) ctx.channel()); // 删除channel
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        logger.debug("连接异常: 客户端错误的断开了连接");
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) {
        ResultMessage msg = (ResultMessage) o;
        logger.info("收到消息 type=" + msg.getType() + " body=>" + msg.getBody());

        // 将处理分到不同的Controller上
        int type = msg.getType();
        ResultMessage result;

        switch (type >> 8) {
            case 1:
                try {
                    Method method = loginController.methods.get(type);
                    if (method != null)
                        method.invoke(loginController, msg);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    break;
                }
                break;
            case 3:
                try {
                    Method method = chatController.methods.get(type);
                    if (method != null)
                        method.invoke(chatController, msg);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    break;
                }
                break;
        }
    }
}// end

