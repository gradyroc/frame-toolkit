package cn.grady.netty.inoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author grady
 * @version 1.0, on 1:41 2021/6/21.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //入站的handler 进行解码，
        pipeline.addLast(new MyByteToLongDecoder());

        //加入自定义业务处理逻辑handler
        pipeline.addLast(new MyServerHandler());

    }
}
