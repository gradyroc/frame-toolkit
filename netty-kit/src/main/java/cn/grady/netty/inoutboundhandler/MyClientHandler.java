package cn.grady.netty.inoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author grady
 * @version 1.0, on 1:55 2021/6/21.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

    }



    //重写active 方法，发送数据

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("client send message method  is called !");
        ctx.writeAndFlush(123456L);//发送的是long


    }
}
