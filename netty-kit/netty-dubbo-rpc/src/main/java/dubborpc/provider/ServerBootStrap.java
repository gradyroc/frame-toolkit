package dubborpc.provider;

import dubborpc.netty.NettyServer;

/**
 * @author grady
 * @version 1.0, on 1:47 2021/7/16.
 * 启动服务提供者，nettyServer
 */
public class ServerBootStrap {


    public static void main(String[] args) {

        NettyServer.startServer("127.0.0.1",7000);
    }
}
