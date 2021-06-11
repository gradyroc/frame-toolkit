package cn.grady.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author grady
 * @version 1.0, on 2:15 2021/6/9.
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        //BossGroup and workerGroup
        //1.boss 只处理链接请求
        //2.worker处理具体事务
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {


            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup) //set thread group
                    .channel(NioServerSocketChannel.class) //use NIOServerSocketChannel 作为服务端通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        /**
                         *@Author: grady
                         *@Description:创建一个通道测试对象
                         * getpipline 设置处理器
                         *@return:
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给 workerGroup 的EventLoop对应的管道设置处理器

            System.out.println("... server is ready ....");

            //bind port and 同步，生成future对象
            //start server
            ChannelFuture future = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        }  finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}















