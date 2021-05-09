package cn.grady.tools.disruptorkit.metric.biz;

/**
 * @author grady
 * @version 1.0, on 2:08 2021/5/9.
 */
public class BizMetric {
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
