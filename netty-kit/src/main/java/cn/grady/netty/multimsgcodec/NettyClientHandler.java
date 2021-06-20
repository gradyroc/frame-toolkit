package cn.grady.netty.multimsgcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author grady
 * @version 1.0, on 1:45 2021/6/11.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * channel ready 时就调用该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("client " + ctx);
        //随机的发送Student 或者Worker 对象
        int random = new Random().nextInt(3);
        MultiDataInfo.MyMessage myMessage;
        if (0 == random) {
//            myMessage = MultiDataInfo.MyMessage.newBuilder().setDataType(MultiDataInfo.MyMessage.DataType.StudentType)
            myMessage = MultiDataInfo.MyMessage.newBuilder().setDataType(MultiDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MultiDataInfo.Student.newBuilder().setId(5).setName("grady roc student").build()).build();

        } else {
            // 发送worker
            myMessage = MultiDataInfo.MyMessage.newBuilder().setDataType(MultiDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MultiDataInfo.Worker.newBuilder().setAge(18).setName("grady worker").build()).build();
        }

        ctx.writeAndFlush(myMessage);

    }


    /**
     * 通道有读取事件时，会触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server's back msg :" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("server address :" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
