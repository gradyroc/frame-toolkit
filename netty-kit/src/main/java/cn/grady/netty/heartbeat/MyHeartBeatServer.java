package cn.grady.netty.heartbeat;

import cn.grady.netty.groupchat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author grady
 * @version 1.0, on 1:25 2021/6/16.
 */
public class MyHeartBeatServer {

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
                            //加入netty提供的idleStateHandler
                            /**
                             * 1、IdleStateHandler 是处理空闲状态的处理器
                             * 2、  long readerIdleTime : 表示多长时间没有读，就会发送一个心跳检测包 检查是否连接
                             *      long writerIdleTime : 表示多长时间没有写，就会发送一个心跳检测包 检查是否连接
                             *      long allIdleTime : 表示多长时间没有读写，就会发送一个心跳检测包 检查是否连接
                             * Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             * read, write, or both operation for a while.
                             *
                             * 3、当 IdleStateEvent 时间触发后，就会传递给管道的下一个handler 去处理
                             *     通过调用（触发）下一个handler的 userEventTriggered ，在该方法中去处理
                             *     IdleStateEvent(读空闲，写空闲，读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7,TimeUnit.SECONDS));

                            // 加入一个队空闲检测进一步处理的handler （自定义）
                            pipeline.addLast("myHeartBeatHandler", new MyHeartBeatHandler());


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
