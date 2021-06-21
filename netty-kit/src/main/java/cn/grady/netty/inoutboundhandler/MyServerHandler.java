package cn.grady.netty.inoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author grady
 * @version 1.0, on 1:46 2021/6/21.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("from client" + ctx.channel().remoteAddress() + "  msg : " + msg);

        System.out.println("send to client a long 98765L");

        ctx.writeAndFlush(98765L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
