package cn.grady.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author grady
 * @version 1.0, on 1:25 2021/6/16.
 */
public class MyWebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//增加一个日志处理器
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //先获取pipline
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 因为基于http协议,使用http编解码器
                             *
                             */
                            pipeline.addLast(new HttpServerCodec());
                            /**
                             * 因为基于块方式写,ChunkedWriteHandler
                             *
                             */
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 因为基于http协议
                             *  1、http传输过程中是分段， HttpObjectAggregator 可以将多个段聚合
                             *  2、浏览器发送大量数据时，会产生多次http请求
                             *
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1、对于websocket，数据以 帧（frame）形式传递
                             * 2、 WebSocketFrame  下面有6个子类
                             * 3、 浏览器发送请求时， ws://localhost:7000/xxxx 表示请求的uri
                             * 4、 WebSocketServerProtocolHandler 核心功能是将 http 协议升级为ws协议，保持长链接
                             * 5、是通过一个状态码101
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            pipeline.addLast("myWebSocketServerHandler", new MyTextWebSocketFrameHandler());


                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }




}
