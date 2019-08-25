package cn.chat.test;

import cn.yongtao.common.ResultMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class SocketClientHandlerTest extends SimpleChannelInboundHandler<Object>
{
    private static final Logger logger = Logger.getLogger(SocketClientTest.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) {
        logger.info("异常发生", arg1);
    }

    @Override
    public void channelRead(ChannelHandlerContext arg0, Object msg) throws Exception {
        super.channelRead(arg0, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, Object o) {
        ResultMessage msg = (ResultMessage) o;
        logger.info("收到消息 type=" + msg.getType() + " body=>" + msg.getBody());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接建立");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接断开");
        super.channelInactive(ctx);
    }
}
