package cn.grady.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author grady
 * @version 1.0, on 0:40 2021/6/22.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        //将buffer 转成字符串

        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("server recived data ：" + message);
        System.out.println("server recived data count :" + (++this.count));

        //server send data  to client a random id
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString()+" ", Charset.forName("utf-8"));

        ctx.writeAndFlush(byteBuf);

    }
}
