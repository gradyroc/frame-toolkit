package cn.grady.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author grady
 * @version 1.0, on 0:57 2021/6/11.
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //client 需要一个时间循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {


            //创建客户端启动对象，client 使用的不是ServerBootstrap 而是BootStrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventExecutors) //设置线程组
                    .channel(NioSocketChannel.class) //  设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });

            //start client to connect server
            //分析channelFuture 涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();


        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
