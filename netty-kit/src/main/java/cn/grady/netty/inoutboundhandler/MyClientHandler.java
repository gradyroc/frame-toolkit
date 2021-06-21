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
        System.out.println("client's IP :" + ctx.channel().remoteAddress());
        System.out.println("from server's msg : " + msg);
    }


    //重写active 方法，发送数据

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("client send message method  is called !");
        ctx.writeAndFlush(123456L);//发送的是long

        /**
         * 1、abcdabcdabcdabcd 16个字节
         * 2、该处理器的前一个handler 是 MyLongToByteEncoder
         * 3、 MyLongToByteEncoder 的父类 是 MessageToByteEncoder.write()
         *
         * if (acceptOutboundMessage(msg)) { //判断当前msg是不是应该处理的类型，如果是，则编码，不是，则直接发送数据
         *                 @SuppressWarnings("unchecked")
         *                 I cast = (I) msg;
         *                 buf = allocateBuffer(ctx, cast, preferDirect);
         *                 try {
         *                     encode(ctx, cast, buf);
         *                 } finally {
         *                     ReferenceCountUtil.release(cast);
         *                 }
         *             } else {
         *             //直接写数据
         *                 ctx.write(msg, promise);
         *             }
         *
         *  4、因此我们在编写encoder 时，要注意传入的类型和处理的数据类型要一致
         */
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));

    }
}
