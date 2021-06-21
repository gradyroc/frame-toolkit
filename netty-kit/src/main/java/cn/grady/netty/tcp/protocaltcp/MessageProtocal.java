package cn.grady.netty.tcp.protocaltcp;

/**
 * @author grady
 * @version 1.0, on 1:14 2021/6/22.
 */
// 协议包
public class MessageProtocal {

    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
