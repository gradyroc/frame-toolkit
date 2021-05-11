package cn.grady.tools.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:00 2021/5/12.
 * 枚举类通用
 */
public interface BasicEnum<T> {
    public T getValue();

    public String getDescription();

    @Override
    public String toString();
}
