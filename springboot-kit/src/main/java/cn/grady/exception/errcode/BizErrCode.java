package cn.grady.exception.errcode;

/**
 * @author grady
 * @version 1.0, on 22:28 2022/1/7.
 */
public interface BizErrCode<T> {
    public T getValue();

    public String getDescription();

    @Override
    public String toString();
}
