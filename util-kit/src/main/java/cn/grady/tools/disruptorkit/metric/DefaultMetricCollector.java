package cn.grady.tools.disruptorkit.metric;

/**
 * @author gradyzhou
 * @version 1.0, on 21:22 2021/5/7.
 */
public class DefaultMetricCollector<M> implements MetricCollector<M> {

    private Disruptor<M> disruptor;


    @Override
    public void collect(M m) {

    }
}
