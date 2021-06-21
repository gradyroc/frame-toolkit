package cn.grady.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author grady
 * @version 1.0, on 0:35 2021/6/22.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //用client 发送10条数据
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.copiedBuffer(" hello ,server " + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);


        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("client recived message :" + message);
        System.out.println("client recived data count :" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
