package cn.grady.netty.inoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author grady
 * @version 1.0, on 1:52 2021/6/21.
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 编码方法
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode() method is called ");
        System.out.println("msg: " + msg);
        out.writeLong(msg);
    }
}
