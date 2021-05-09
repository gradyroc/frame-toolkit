package cn.grady.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author rociss
 * @version 1.0, on 12:08 2021/5/3.
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //constructor
    public GroupChatServer() {

        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //bind port
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //set as non bloking
            listenChannel.configureBlocking(false);
            //care of accept event
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //listen
    public void listen() {

        try {

            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    //have event need to do
                    //loop to deal
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            //accept event ,用一个socketchannel accept
                            SocketChannel socketChannel = listenChannel.accept();

                            //设置非阻塞
                            socketChannel.configureBlocking(false);

                            //regist the socketchannel to the selector
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress() + " get online !");

                        }
                        if (key.isReadable()) {
                            //read event.deal read
                            readData(key);

                        }

                        //remove the key of dealed
                        iterator.remove();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * @Author: zhoup
     * @Description:
     * @Params:
     * @Date:
     * @return:
     */
    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            //get the relation channel by the selectionKey
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 根据count值做处理
            if (count > 0) {
                //处理buffer 的数据
                String msg = new String(buffer.array());

                System.out.println("from client's message: " + msg);
                // 向其他client 转发消息,排除自己
                sendMsgToOtherClient(msg, channel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress()+"offline");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendMsgToOtherClient(String msg, SocketChannel self) throws IOException {
        System.out.println("transmit the msg to the others");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                ByteBuffer targetBuffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) targetChannel).write(targetBuffer);
            }
        }
    }


    public static void main(String[] args) {

        // start server
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
