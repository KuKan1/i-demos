package com.kukan.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Admin on 2018/8/2.
 */
public class MessageEncoder extends MessageToByteEncoder<RequestInfo> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestInfo msg, ByteBuf out) throws Exception {
        ByteBufOutputStream writer = new ByteBufOutputStream(out);
        writer.writeByte(msg.getType());
        byte[] info = null;
        if (msg != null && msg.getInfo() != null && msg.getInfo() != ""){
            info = msg.getInfo().getBytes("utf-8");
            writer.write(info);
        }
    }
}
