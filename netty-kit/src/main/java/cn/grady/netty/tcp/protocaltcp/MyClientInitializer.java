package cn.grady.netty.tcp.protocaltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author grady
 * @version 1.0, on 1:50 2021/6/21.
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {


    /**
     * 客户端pipline 调用顺序与addLast顺序相反，先 1、业务处理、2、发送、3、编码器handler
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //加入出站的handler
        pipeline.addLast(new MessageEncoder());//自定义编码器
        pipeline.addLast(new MyClientHandler());
    }
}
