package cn.yongtao.common;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class LengthFieldMessageEncoder extends MessageToByteEncoder<Message>
{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        if (message == null || message.getBody() == null) {
            throw new Exception("消息内容为空!");
        }

        Object body = message.getBody();
        // 转为json格式 并转为utf-8
        byte[] b = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        byteBuf.writeShort(b.length);
        byteBuf.writeShort(message.getType());
        byteBuf.writeBytes(b);
    }
}
