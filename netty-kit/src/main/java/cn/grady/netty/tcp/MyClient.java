package cn.grady.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author grady
 * @version 1.0, on 1:48 2021/6/21.
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        //client 需要一个时间循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {


            //创建客户端启动对象，client 使用的不是ServerBootstrap 而是BootStrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventExecutors) //设置线程组
                    .channel(NioSocketChannel.class) //  设置客户端通道的实现类
                    .handler(new MyClientInitializer());

            //start client to connect server
            //分析channelFuture 涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();

            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();


        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
