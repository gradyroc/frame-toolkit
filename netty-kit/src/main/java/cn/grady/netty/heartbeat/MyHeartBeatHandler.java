package cn.grady.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author grady
 * @version 1.0, on 1:37 2021/6/16.
 */
public class MyHeartBeatHandler extends ChannelInboundHandlerAdapter {


    /**
     *
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if (evt instanceof IdleStateEvent){
            //evt 向下转型
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;

            switch (event.state()){
                case READER_IDLE:
                    eventType =" no read ";
                    break;
                case WRITER_IDLE:
                    eventType="no write";
                    break;
                case ALL_IDLE:
                    eventType="no read and write";
                    break;

                    default:
                        break;

            }
            System.out.println(ctx.channel().remoteAddress()+" heartbeat event is : "+eventType);
            System.out.println("server can do someting ! ");

        }

    }
}
