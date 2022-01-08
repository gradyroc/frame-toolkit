package cn.grady.exception.errcode;

/**
 * @author grady
 * @version 1.0, on 1:47 2021/12/30.
 */
public interface ParamaErrCode<T> {
    public T getCode();

    public String getDescription();

    @Override
    public String toString();
}
