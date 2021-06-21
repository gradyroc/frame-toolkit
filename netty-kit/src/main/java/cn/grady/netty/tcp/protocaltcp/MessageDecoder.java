package cn.grady.netty.tcp.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 1:24 2021/6/22.
 */
public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder decoder is called !");
        //将接收到的二进制字节码 转换为MessageProtocal数据包（对象）
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        // 封装，放入out，传递给下一个handler
        MessageProtocal messageProtocal = new MessageProtocal();
        messageProtocal.setLen(length);
        messageProtocal.setContent(content);
        out.add(messageProtocal);

    }
}
