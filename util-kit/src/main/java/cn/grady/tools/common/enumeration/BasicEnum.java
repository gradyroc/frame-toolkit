package cn.grady.tools.common.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:00 2021/5/12.
 * 枚举类通用
 */
public interface BasicEnum<T> {
    T getValue();

    String getDescription();

    @Override
    String toString();
}
