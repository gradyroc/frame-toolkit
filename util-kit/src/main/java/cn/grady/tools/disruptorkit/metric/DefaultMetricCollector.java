package cn.grady.tools.disruptorkit.metric;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author gradyzhou
 * @version 1.0, on 21:22 2021/5/7.
 */
public class DefaultMetricCollector<M> implements MetricCollector<M> {

    private static final Logger log = LoggerFactory.getLogger(DefaultMetricCollector.class);
    private static final int DEFAULT_QUEUE_MAX_SIZE = 1024;
    private static final int BATCH_METRIC_SIZE = 32;
    private static final long PERSIST_INTERVAL_MS = 200;

    private Disruptor<M> disruptor;
    private MeticPersist<M> persistUtil;

    public DefaultMetricCollector(EventFactory<M> eventFactory, MeticPersist<M> persistUtil) {
        this(DEFAULT_QUEUE_MAX_SIZE, eventFactory, persistUtil);
    }

    public DefaultMetricCollector(int queueMaxSize, EventFactory<M> eventFactory, MeticPersist<M> meticPersistUtil) {
        final Class clazz = this.getClass();
        ThreadFactory threadFactory = runnable -> new Thread(runnable, "Disruptor-consumer-" + clazz.getSimpleName());
        /*
        以上等于
         ThreadFactory factory1 = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
               return new Thread(r,"Disruptor-consumer-"+clazz.getSimpleName();
            }
        };
         */

        disruptor = new Disruptor<M>(eventFactory, queueMaxSize, threadFactory, ProducerType.MULTI, new SleepingWaitStrategy());
        disruptor.handleEventsWith(new EventHandler<M>() {
            List<M> list = new ArrayList<>(BATCH_METRIC_SIZE);

            @Override
            public void onEvent(M event, long sequence, boolean endOfBatch) throws Exception {
                list.add(event);
                if (list.size() >= BATCH_METRIC_SIZE) {
                    persistMetrics(list);
                } else if (endOfBatch) {
                    persistMetrics(list);
                }
            }

            private void persistMetrics(List<M> list) {
                //调用真正的落库工具类接口，具体实现类决定是落库还是落磁盘即可
                // 注意日志记录以及异常处理，落库失败怎么办
                if (list.size() > BATCH_METRIC_SIZE) {
                    log.error("the list size is : {} .", list.size());
                }
                try {
                    persistUtil.persist(list);
                } catch (Throwable e) {
                    log.warn("persistUtil occurs exception.{}", e.getMessage());
                } finally {
                    //每次提交之后，清理list
                    list.clear();
                }

                try {
                    Thread.sleep(PERSIST_INTERVAL_MS);
                } catch (InterruptedException e) {
                    log.warn("the interruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<M>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, M event) {
                log.warn(" the handleEventException msg:{},sequence:{}", event, sequence, ex);
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                log.warn("the handleOnStartException.", ex);
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                log.warn("the handleOnShutdownException", ex);
            }
        });

        //工具类接口的实现赋值
        persistUtil = meticPersistUtil;

        disruptor.start();
    }

    public void collect(M m) {
        boolean success = disruptor.getRingBuffer().tryPublishEvent(new EventTranslator<M>() {
            @Override
            public void translateTo(M event, long sequence) {
                BeanCopier.create(m.getClass(), event.getClass(), false)
                        .copy(m, event, null);
            }
        });
        if (!success) {
            log.warn("the {} start to discard mssage:{}", this.getClass().getSimpleName(), m);
        }
    }
}
