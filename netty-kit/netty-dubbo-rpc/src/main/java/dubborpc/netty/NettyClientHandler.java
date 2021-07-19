package dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author grady
 * @version 1.0, on 1:36 2021/7/19.
 */
public class NettyClientHandler  extends ChannelInboundHandlerAdapter implements Callable {


    private ChannelHandlerContext context;
    private String result;
    private String parma;

//    step1
    /**
     * 与服务器的链接创建之后就会被调用，是第一个被调用的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //其他方法中需要用到 context
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();

        //唤醒等待的线程
        notify();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }

//    step3
    /**
     * 被代理对象调用，发送数据给服务器，then -->> wait for 唤醒 notify() -->> 返回结果
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {

        //发送给服务器的消息
        context.writeAndFlush(parma);

        //waiting,等待channelRead 方法获取到服务器的结果之后，唤醒
        wait();

        return result;
    }

//    setp2
    void setParma(String parma){
        this.parma = parma;
    }
}
