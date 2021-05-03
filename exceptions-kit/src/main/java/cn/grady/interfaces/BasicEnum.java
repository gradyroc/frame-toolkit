package cn.grady.interfaces;

/**
 * @author rociss
 * @version 1.0, on 22:13 2020/4/1.
 * enum 通用接口， name(value,description)
 */
public interface BasicEnum<T> extends Valueable<T> {
    public T getValue();

    public String getDescription();

    @Override
    public String toString();
}
