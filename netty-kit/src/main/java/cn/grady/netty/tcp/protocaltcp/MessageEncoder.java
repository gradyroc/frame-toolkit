package cn.grady.netty.tcp.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author grady
 * @version 1.0, on 1:21 2021/6/22.
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocal> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocal msg, ByteBuf out) throws Exception {
        System.out.println("messageEncoder is called");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
