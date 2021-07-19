package dubborpc.netty;

import dubborpc.netty.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author grady
 * @version 1.0, on 1:49 2021/7/16.
 */
public class NettyServer {

    public static void startServer(String hostName, int port){

        startServer0(hostName,port);
    }
    /**
     * init the nettyServer and start
     */
    private static void startServer0(String hostName, int port) {
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
                            //可以使用集合管理socketchannel，推送消息时可以将业务加入到各个channel对应的NIOEventLoop的taskQueue中，或者scheduleTaskQueue中
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyServerHandler());//do something
                        }
                    }); //给 workerGroup 的EventLoop对应的管道设置处理器

            System.out.println("... server is ready ....");

            //bind port and 同步，生成future对象
            //start server
            ChannelFuture future = bootstrap.bind(hostName, port).sync();

            System.out.println("server start to provide service ");
            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
