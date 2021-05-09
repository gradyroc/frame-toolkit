package cn.grady.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author grady
 * @version 1.0, on 0:09 2021/5/9.
 */
public class NewIoClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",7001));
        String fileName = "xxx.file";

        //得到文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //在linux下一个transfer() 就可以完成传输
        // 在windows 下，一次transfer() 只能发送8MB数据，需要分段传输文件，注意传输的起始位置
//        transferTo() 底层就用了zero-copy
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        long end =System.currentTimeMillis();
        System.out.println("transfer file size:"+transferCount+" during time :"+(end-startTime));

        fileChannel.close();
    }
}
