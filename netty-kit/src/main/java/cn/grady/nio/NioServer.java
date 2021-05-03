package cn.grady.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author rociss
 * @version 1.0, on 0:24 2021/4/24.
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        //create serversocketchannel -> serversocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //get selector
        Selector selector = Selector.open();

        //bind a port
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //set as non-blocking
        serverSocketChannel.configureBlocking(false);

        //serversocketchannel regist to the selector and event of accept
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        //loop wait for client link
        while (true){

            //wait 1s,no links then return
            if (selector.select(1000)==0){
                System.out.println("server wait for 1000ms ,no any links!");
                continue;
            }

            //if return > 0,then get the set of selectionKeys
            //if >0 表示已经获取到关注的事件了，selector.selectedKeys() 为关注事件的集合
            // 通过selectionKeys 获取channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey keys = iterator.next();
                if (keys.isAcceptable()) {
                    // create a socketChannel 给client
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);
                    System.out.println("connect arraived server,create a socketChannel"+socketChannel);

                    //将 socketChannel 注册到selector上,关注事件为OP_READ  关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }

                if (keys.isReadable()){//OP_READ

                    //通过key 反向获取到channel
                    SocketChannel channel = (SocketChannel) keys.channel();

                    //get channel's links buffer
                    ByteBuffer buffer = (ByteBuffer) keys.attachment();

                    channel.read(buffer);

                    System.out.println("from client: "+ new String(buffer.array()));

                }

                //手动从selector中移除 keys，防止重复操作
                iterator.remove();
            }


        }



    }
}
