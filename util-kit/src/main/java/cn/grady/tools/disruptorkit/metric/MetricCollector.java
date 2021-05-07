package cn.grady.tools.disruptorkit.metric;

/**
 * @author gradyzhou
 * @version 1.0, on 21:21 2021/5/7.
 */
public interface MetricCollector<M> {
    void collect(M m);
}
