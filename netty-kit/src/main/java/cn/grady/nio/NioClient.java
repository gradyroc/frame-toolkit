package cn.grady.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author rociss
 * @version 1.0, on 2:39 2021/4/25.
 */
public class NioClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

        //set as non blocking
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);

        //connect to server
        if (!socketChannel.connect(inetSocketAddress)){

            while (!socketChannel.finishConnect()){
                System.out.println("need time to connect,do something else");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // connect success,send mssage
            String msg="hello netty";
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            //data buffer write to the channel
            socketChannel.write(buffer);

            System.in.read();
        }


    }
}
