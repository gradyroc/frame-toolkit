package cn.grady.netty.tcp.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author grady
 * @version 1.0, on 0:40 2021/6/22.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocal> {

    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocal msg) throws Exception {
        // 接受数据，处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("server recived message ==>>  len:" + len + " content :" + new String(content, Charset.forName("utf-8")));
        System.out.println("server recived messageProtocal count :" + (++this.count));

        //server send data  to client a random id

        String response = UUID.randomUUID().toString();
        int length = response.getBytes("utf-8").length;
        MessageProtocal messageProtocal = new MessageProtocal();
        messageProtocal.setLen(length);
        messageProtocal.setContent(response.getBytes("utf-8"));

        ctx.writeAndFlush(messageProtocal);

    }
}
