package cn.grady.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author grady
 * @version 1.0, on 0:42 2021/6/11.
 * 自定义handler 继承netty 规定好的某个handlerAdapter，才会被调用为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

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

//        System.out.println("server thread name "+Thread.currentThread().getName());
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline(); //本质是双向链表，出栈入栈问题
//
//
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.printf("message from client is :{%s}", buf.toString(CharsetUtil.UTF_8));
//        System.out.printf("address of client is :{%s}", ctx.channel().remoteAddress());

        /**
         * 如果业务耗时严重-》异步 -》 提交该 channel 到对应的 NIOEventLoop 的 taskQueue 中
         *solution1 自定义普通任务
         *solution2 :用户自定义定时任务
         */
        //solution1 自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client !", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("ocour exceptions ", e.fillInStackTrace());
                }
            }
        });

        System.out.println("go on server work !");

        //solution2 :用户自定义定时任务
        // 用户自定义定时任务，该任务提交到schduleTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client !", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("ocour exceptions ", e.fillInStackTrace());
                }
            }
        }, 5, TimeUnit.SECONDS);


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
