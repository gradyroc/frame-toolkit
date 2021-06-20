package cn.grady.netty.inoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 1:43 2021/6/21.
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {


    /**
     *根据接收到的数据，被调用多次，直到确定没有新的元素被添加到list
     * 或者ByteBuf 没有更多的可读字节为止
     * if list out不为空，就会将list的类容传递给下一个ChannelInBoundHadnler处理，该处理器方法也会被调用多次
     * @param ctx
     * @param in 入站ByteBuf
     * @param out list集合，讲解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println(" MyByteToLongDecoder decode() method is called ");
        //long为8个字节,需要判断有8个字节，才能读取一个long
        if (in.readableBytes() >=8){
            out.add(in.readLong());
        }



    }
}
