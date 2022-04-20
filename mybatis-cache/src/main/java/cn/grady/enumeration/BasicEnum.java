package cn.grady.enumeration;

/**
 * ClassName BasicEnum
 * Description
 * Create by gradyly
 * Date 2022/4/18 0:09
 */
public interface BasicEnum <T> extends Valueable<T>{
    public T getValue();

    public String getDescription();
    public String toString();
}
