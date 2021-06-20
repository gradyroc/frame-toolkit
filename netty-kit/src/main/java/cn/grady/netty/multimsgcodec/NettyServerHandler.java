package cn.grady.netty.multimsgcodec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author grady
 * @version 1.0, on 0:42 2021/6/11.
 * 自定义handler 继承netty 规定好的某个handlerAdapter，才会被调用为一个handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MultiDataInfo.MyMessage> {

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
    protected void channelRead0(ChannelHandlerContext ctx, MultiDataInfo.MyMessage msg) throws Exception {
        MultiDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MultiDataInfo.MyMessage.DataType.StudentType) {
            MultiDataInfo.Student student = msg.getStudent();
            System.out.println("student  id :" + student.getId() + " name :" + student.getName());
        } else if (dataType == MultiDataInfo.MyMessage.DataType.WorkerType) {
            MultiDataInfo.Worker worker = msg.getWorker();
            System.out.println("Worker  age :" + worker.getAge() + " name :" + worker.getName());
        } else {
            System.out.println("传输的类型不正确");
        }
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
