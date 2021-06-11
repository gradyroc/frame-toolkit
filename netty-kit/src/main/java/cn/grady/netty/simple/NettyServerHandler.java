package cn.grady.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * @author grady
 * @version 1.0, on 0:42 2021/6/11.
 * 自定义handler 继承netty 规定好的某个handlerAdapter，才会被调用为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * ChannelHandlerContext ctx 上下文对象，含有管道pipeline 通道channel，地址
     * msg：就是客户端发送的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);

//        msg-> ByteBuf. netty 提供，不是NIO的ByteBuffer

        System.out.println("server thread name "+Thread.currentThread().getName());
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是双向链表，出栈入栈问题


        ByteBuf buf = (ByteBuf) msg;
        System.out.printf("message from client is :{%s}", buf.toString(CharsetUtil.UTF_8));
        System.out.printf("address of client is :{%s}", ctx.channel().remoteAddress());
    }

    /**
     * 数据读写完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        //写缓存，并刷新，对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hi client~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
