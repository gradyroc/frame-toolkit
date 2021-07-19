package dubborpc.customer;

import dubborpc.netty.NettyClient;
import dubborpc.publicface.HelloService;

/**
 * @author grady
 * @version 1.0, on 2:04 2021/7/19.
 */
public class ClientBootStrap {

    //统一定义协议头
    public static final String providerName = "grady#hello#";


    public static void main(String[] args) throws Exception{
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        //通过代理对象调用服务提供者的方法
        String res = service.hello("hello dubbo~");
        System.out.println("result from hello method:" + res);
    }
}
