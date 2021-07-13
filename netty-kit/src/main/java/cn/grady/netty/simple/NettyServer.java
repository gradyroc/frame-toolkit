package cn.grady.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author grady
 * @version 1.0, on 2:15 2021/6/9.
 */
public class NettyServer {

    /**
     * 创建业务线程池
     */
    static final EventExecutorGroup group = new DefaultEventExecutorGroup(2);


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
//                    .handler(null) // handler 对应的bossGroup ，childHandler对应的是workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        /**
                         *@Author: grady
                         *@Description:创建一个通道测试对象
                         * getpipline 设置处理器
                         *@return:
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("client socketchannel hashcode = "+ch.hashCode());
                            //可以使用集合管理socketchannel，推送消息时可以将业务加入到各个channel对应的NIOEventLoop的taskQueue中，或者scheduleTaskQueue中
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new NettyServerHandler());

                            /**
                             * 异步任务
                             * solution 4
                             * 在addLast时，如果前面有指定EventExecutorGroup
                             * 则 handler 默认优先加入到该线程池中
                             */
                            pipeline.addLast(group,new NettyServerHandler());
                        }
                    }); //给 workerGroup 的EventLoop对应的管道设置处理器

            System.out.println("... server is ready ....");

            //bind port and 同步，生成future对象
            //start server
            ChannelFuture future = bootstrap.bind(6668).sync();
            // future - listener 机制
            //给future 注册监听器，监控关心的时间
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("listen port 6668 success");
                    }else {
                        System.out.println("listen port 6668 failer");

                    }
                }
            });

            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        }  finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}















