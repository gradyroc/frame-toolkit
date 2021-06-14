package cn.grady.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author grady
 * @version 1.0, on 1:12 2021/6/15.
 */
public class NettyBuf02 {

    public static void main(String[] args) {

        /**
         *
         */
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world !", CharsetUtil.UTF_8);
        //相关method
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();

            //content - > string
            System.out.println(new String (content,Charset.forName("utf-8")));
            System.out.println(byteBuf.arrayOffset()); //0
            System.out.println(byteBuf.readerIndex()); //0
            System.out.println(byteBuf.writerIndex()); //13
            System.out.println(byteBuf.capacity()); //39

            System.out.println(byteBuf.readableBytes()) ; //可读取的字节数

            System.out.println(byteBuf.getCharSequence(0,5,Charset.forName("utf-8")));
        }


    }
}
