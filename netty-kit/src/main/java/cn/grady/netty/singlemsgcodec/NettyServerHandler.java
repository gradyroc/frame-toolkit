package cn.grady.netty.singlemsgcodec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        //读取从客户端发送的StudentJOJO.Student
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("from client's data id = " + student.getId() + " name :" + student.getName());

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
