package cn.grady.netty.tcp.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author grady
 * @version 1.0, on 0:35 2021/6/22.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocal> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //用client 发送10条数据,"天气冷，吃火锅"
        for (int i = 0; i < 5; i++) {
            String msg = " 天气冷，吃火锅 ";
            byte[] content = msg.getBytes(Charset.forName("utf-8"));

            int length = msg.getBytes(Charset.forName("utf-8")).length;
            MessageProtocal messageProtocal = new MessageProtocal();
            messageProtocal.setLen(length);
            messageProtocal.setContent(content);
            ctx.writeAndFlush(messageProtocal);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocal msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("client recived message ==>> length:" + len+ " content :"+ new String (content,Charset.forName("utf-8")));
        System.out.println("client recived data count :" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
