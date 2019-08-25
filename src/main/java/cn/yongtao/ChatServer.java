package cn.yongtao;

import cn.yongtao.common.LengthFieldMessageDecoder;
import cn.yongtao.common.LengthFieldMessageEncoder;
import cn.yongtao.handler.MainHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class ChatServer
{
    private static final Logger logger = Logger.getLogger(ChatServer.class);
    //private static final String IP = "127.0.0.1";
    private static final String IP = "172.30.192.163";  // 宿舍IP地址
    private static final int PORT = 8000;

    // 线程组数量
    private static final int BIS_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final int WORK_GROUP_SIZE = 1;

    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup(WORK_GROUP_SIZE);

    @Autowired
    MainHandler mainHandler;


    private void run() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>()
        {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 可变长度解码
                pipeline.addLast("framerDecoder", new LengthFieldMessageDecoder());
                pipeline.addLast("frameEncoder", new LengthFieldMessageEncoder());
                //pipeline.addLast("decoder", new StringDecoder());
                //pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast(mainHandler);
            }
        });
        bootstrap.bind(PORT).sync();
        logger.info("Socket服务器已启动完成 " + IP + ":" + PORT);
    }

    protected static void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        logger.info("开始启动,监听地址:" + IP + " 端口:" + PORT);
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        ChatServer chatServer = (ChatServer) context.getBean("chatServer");
        chatServer.run();
    }
}
