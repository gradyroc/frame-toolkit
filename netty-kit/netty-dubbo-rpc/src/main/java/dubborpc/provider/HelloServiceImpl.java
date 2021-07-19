package dubborpc.provider;

import dubborpc.publicface.HelloService;

/**
 * @author grady
 * @version 1.0, on 1:45 2021/7/16.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {

        System.out.println("recive client's message");
        /**
         *
         */
        if (msg !=null){

            return "hello"+msg+" i'am server";
        }else {
            return "not any message from the client ";
        }
    }
}
