package cn.grady.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author grady
 * @version 1.0, on 12:41 2021/6/13.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入netty 提供的httpServerCodec =》{coder--decoder}
        //HttpServerCodec 是netty 提供的处理http的编码解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //增加自定义handler

        pipeline.addLast("MyTestHttoServerHandler",new TestHttpServerHandler());

        System.out.println("server handler is ok !");

    }
}
