package cn.grady.nio.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author grady
 * @version 1.0, on 1:54 2021/5/7.
 */
public class GroupChatClient {
    private static final Logger log = LoggerFactory.getLogger(GroupChatClient.class);
    //定义属性
    private final String HOST = "127.0.0.1";//server ip
    private final int PORT = 6667;// server port
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws IOException {
        //init
        selector = Selector.open();

        //链接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));

        socketChannel.configureBlocking(false);

        //register
        socketChannel.register(selector, SelectionKey.OP_READ);
        //get userName
        userName = socketChannel.getLocalAddress().toString().substring(1);

        System.out.println(userName + " is ok ");


    }

    //send
    public void sendMsg(String msg) {
        String contends = userName + " said: " + msg;
        System.out.println(contends);

        try {
            socketChannel.write(ByteBuffer.wrap(contends.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read msg
    public void readMsg() {
        try {
            int select = selector.select();
            if (select > 0) {// 有可用的通道
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        //得到可读通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        //trans to string
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                keyIterator.remove();
            } else {
                System.out.println("no useable channel !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // start client
        GroupChatClient client = new GroupChatClient();

        new Thread() {
            @Override
            public void run(){
                while (true){
                    client.readMsg();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给server
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            client.sendMsg(msg);
        }


    }

}
