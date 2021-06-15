package cn.grady.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author grady
 * @version 1.0, on 1:38 2021/6/15.
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(GroupChatServerHandler.class);

    /**
     * 定义channel组，netty已经提供，不需要用list管理，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局时间执行器，单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * handlerAdded 表示连接建立时，一旦连接，第一个被执行
     * 将当前 channel  加入到 channelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线客户端
        // writeAndFlush 会将group中所有的channel遍历，并发送消息，所以不需要自己遍历
        channelGroup.writeAndFlush("[ client ] :" + channel.remoteAddress() + " 加入聊天 \n");


        channelGroup.add(channel);
    }


    /**
     * 表示channel出于活动状态，上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " get online "+sdf.format(new Date()));


    }

    /**
     * 提示下线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " get offline ");

    }

    /**
     * 断开连接,将xx客户离开信息推送给当前在线客户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[ client ]" + channel.remoteAddress() + "get out \n");
        System.out.println("channelGroup size :" + channelGroup.size());// 自动remove
    }

    /**
     * 读取数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();

        //根据不同channel 回复不同消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                //不是当前channel，直接转发
                ch.writeAndFlush("[client]:" + channel.remoteAddress() + " send a msg :" + msg + "\n");
            } else {
                ch.writeAndFlush("[self] send a msg :" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}
