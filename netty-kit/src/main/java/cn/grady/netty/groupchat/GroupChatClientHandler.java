package cn.grady.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author grady
 * @version 1.0, on 0:57 2021/6/16.
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String > {




    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
