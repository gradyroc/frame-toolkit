package cn.grady.tools.disruptorkit.event;

/**
 * @author gradyzhou
 * @version 1.0, on 21:12 2021/5/7.
 */
public class MetricsEvent {

    private String bizSeq;
    private String sysSeq;
    private String message;
    private String reqCostTime;


    public String getBizSeq() {
        return bizSeq;
    }

    public void setBizSeq(String bizSeq) {
        this.bizSeq = bizSeq;
    }

    public String getSysSeq() {
        return sysSeq;
    }

    public void setSysSeq(String sysSeq) {
        this.sysSeq = sysSeq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReqCostTime() {
        return reqCostTime;
    }

    public void setReqCostTime(String reqCostTime) {
        this.reqCostTime = reqCostTime;
    }


}
