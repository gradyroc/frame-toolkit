package dubborpc.netty;

import dubborpc.customer.ClientBootStrap;
import dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author grady
 * @version 1.0, on 2:03 2021/7/16.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //get from client ,and call service
        System.out.println(msg);
        //客户端在调用server的api时，需按照协议
        //etc. 每次发消息时都必须以某个字符串开头，etc："grady#"
        if (msg.toString().startsWith(ClientBootStrap.providerName)){
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
