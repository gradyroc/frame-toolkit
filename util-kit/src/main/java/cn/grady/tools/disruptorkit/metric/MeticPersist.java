package cn.grady.tools.disruptorkit.metric;

/**
 * @author gradyzhou
 * @version 1.0, on 21:59 2021/5/7.
 */

import java.util.List;

/**
*@Author: gradyzhou
*@Description: 定义持久化接口
*@Params:
*@Date:
*@return:
*/
public interface MeticPersist <M>{

    void persist(M m);

    void persist(List<M> list);

}
