package cn.grady.netty.inoutboundhandler;

import cn.grady.netty.multimsgcodec.MultiDataInfo;
import cn.grady.netty.multimsgcodec.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author grady
 * @version 1.0, on 1:39 2021/6/21.
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {


            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup) //set thread group
                    .channel(NioServerSocketChannel.class) //use NIOServerSocketChannel 作为服务端通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持活动链接状态
//                    .handler(null) // handler 对应的bossGroup ，childHandler对应的是workerGroup
                    .childHandler(new MyServerInitializer()); //给 workerGroup 的EventLoop对应的管道设置处理器

            System.out.println("... server is ready ....");

            //bind port and 同步，生成future对象
            //start server
            ChannelFuture future = bootstrap.bind(7000).sync();


            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }






    }

}
