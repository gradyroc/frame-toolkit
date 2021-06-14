package cn.grady.netty.http;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author grady
 * @version 1.0, on 12:40 2021/6/13.
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger log = LoggerFactory.getLogger(TestHttpServerHandler.class);

    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg HttpObject 客户端和服务器相互通讯的数据被封装成 HttpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("msg type is :" + msg.getClass());
        System.out.println("client address:"+ctx.channel().remoteAddress());


        // check
        if (msg instanceof HttpRequest) {

            System.out.println(((HttpRequest) msg).method().name());

            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());

            //网站图标文件,过滤指定的资源
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了网站图标文件： /favicon.ico,不做响应");
                return;
            }

            //response to exploer
            ByteBuf content = Unpooled.copiedBuffer("hello ,i am server !", CharsetUtil.UTF_8);

            //构造http 响应，即HTTPResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            // return response
            ctx.writeAndFlush(response);

        }
    }
}
