package cn.grady.netty.inoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 23:52 2021/6/21.
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder2 decode() method is called ");

        // 在 ReplayingDecoder  不需要判断数据是否足够读取，内部会进行处理判断
        out.add(in.readLong());
    }
}
