package cn.grady.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

/**
 * @author grady
 * @version 1.0, on 0:32 2021/6/15.
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {

        //创建一个ByteBuf对象，包含一个数组arr，是一个byte[10]
        // netty的buffer中，不需要flip 进行翻转
        //  底层维护了 readerindex 和writerIndex
        //3、通过  readerindex 和 writerIndex 和 capacity 将buffer 分成3个区域
        // 0-readerIndex  已经读取的区域
        // readerIndex - writerIndex ： 可读区域
        // writerIndex - capacity， 可写区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

//        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));//readerIndex 不会变化
//        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte()); //readerIndex 会变化
        }
//        buffer.forEachByte(0, buffer.capacity(), new ByteProcessor() {
//            @Override
//            public boolean process(byte value) throws Exception {
//                System.out.println(value);
//                return true;
//            }
//        });
//        System.out.println();
    }
}
