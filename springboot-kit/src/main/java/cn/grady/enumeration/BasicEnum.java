package cn.grady.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:29 2021/12/30.
 */
public interface BasicEnum<T> {
    public T getCode();

    public String getDescription();

    @Override
    public String toString();
}
