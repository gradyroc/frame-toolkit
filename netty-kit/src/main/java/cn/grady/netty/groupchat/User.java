package cn.grady.netty.groupchat;

/**
 * @author grady
 * @version 1.0, on 1:17 2021/6/16.
 */
public class User {
    private int id;
    private String pwd;

    public User(int id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
