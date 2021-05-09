package cn.grady.tools.disruptorkit.metric.biz;

import cn.grady.tools.disruptorkit.metric.DefaultMetricCollector;
import cn.grady.tools.disruptorkit.metric.MeticPersist;
import com.lmax.disruptor.EventFactory;

/**
 * @author grady
 * @version 1.0, on 2:09 2021/5/9.
 */
public class BizMetricCollector extends DefaultMetricCollector<BizMetric> {

    public BizMetricCollector(EventFactory<BizMetric> eventFactory, MeticPersist<BizMetric> persistUtil) {
        super(eventFactory, persistUtil);
    }

    public BizMetricCollector(int queueMaxSize, EventFactory<BizMetric> eventFactory, MeticPersist<BizMetric> persistUtil) {
        super(queueMaxSize, eventFactory, persistUtil);
    }
}
