package cn.grady.config;

import cn.grady.MainConstant;
import cn.grady.config.condition.HbaseCondition;
import cn.grady.hbase.HBaseDAO;
import cn.grady.hbase.impl.HBaseDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author grady
 * @version 1.0, on 23:05 2022/1/8.
 */
@Conditional(HbaseCondition.class)
@Configuration
public class HBaseDaoConfig extends HBaseConfig {
    public static final Logger logger  = LoggerFactory.getLogger(HBaseDaoConfig.class);

    protected HBaseDaoConfig() {
        super("hbase.properties");
    }

    @Bean(name = MainConstant.hbase_dao_name)
    HBaseDAO getInstance(){
        logger.info("****************************************");
        logger.info("begain create HBase DAO instance: beanName ="+MainConstant.hbase_dao_name);
        HBaseDAO hdao = new HBaseDAOImpl(getMultiConnEx(),MainConstant.hbase_dao_name,this);
        logger.info("create HBaseDAO suceed,beanName= "+MainConstant.hbase_dao_name+",namespace="+nameSpace);

        return hdao;
    }
}
