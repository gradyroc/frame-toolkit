package cn.grady.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author grady
 * @version 1.0, on 1:37 2021/6/16.
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        System.out.println("server recived the msg :" + msg.text());
        //response client

        ctx.channel().writeAndFlush(new TextWebSocketFrame("server's time :" + LocalDateTime.now() + msg.text()));

    }

    /**
     * web client 连接后触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id 表示唯一值，LongText是唯一的，ShortText 不是唯一
        System.out.println("handlerAdded is  called " + ctx.channel().id().asLongText());
        System.out.println("handlerAdded is  called " + ctx.channel().id().asShortText());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerRemoved is called " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("exception occured "+cause.getMessage());
        ctx.channel().close();
    }
}
