package cn.grady.config;

import cn.grady.AppContext;
import cn.grady.enumeration.AppPropertyKey;
import cn.grady.enumeration.BasicEnum;
import cn.grady.enumeration.ThreadType;
import cn.grady.tdsql.domain.SysConfigParam;
import cn.grady.tdsql.domain.SysConfigParamCondition;
import cn.grady.tdsql.mapper.SysConfigParamMapper;
import cn.grady.util.AppPropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rociss
 * @version 1.0, on 1:02 2023/4/16.
 */
public class ConfigInDb {

    private static final Logger log = LoggerFactory.getLogger(ConfigInDb.class);
    private static final SysConfigParamMapper mapper = AppContext.getCtx().getBean(SysConfigParamMapper.class);
    static AppConfig appConfig = AppContext.getCtx().getBean(AppConfig.class);

    private static volatile Map<String, String> config = new HashMap<>(0);
    private static final String CONFIG_REFRESH_THREAD_NAME = ThreadType.Thread.getCode() + "-ConfigInDb";
    public static final String configInDbRefreshInterval = AppPropertiesUtil.getString(AppPropertyKey.CONFIG_IN_DB_REFRESH_INTERVAL, "", "60000");

    static {
        updateConfigInDb();
        if (StringUtils.isNotBlank(configInDbRefreshInterval)){
            new Thread(()->{
                while (true){
                    try {
                        Thread.sleep(Long.parseLong(configInDbRefreshInterval));
                        updateConfigInDb();
                    } catch (Exception e) {
                        log.warn("Exception occurs " ,e);
                    }
                }
            },CONFIG_REFRESH_THREAD_NAME).start();
        }
    }

    private static void updateConfigInDb() {
        SysConfigParamCondition condition = new SysConfigParamCondition();
        condition.createCriteria().andSystemIdEqualTo(appConfig.getSystemId());
        List<SysConfigParam> sysConfigParams = mapper.selectByExample(condition);
        if (!CollectionUtils.isEmpty(sysConfigParams)) {
            Map<String, String> configInDb = new HashMap<>(sysConfigParams.size());
            for (SysConfigParam sysConfig : sysConfigParams) {
                configInDb.put(sysConfig.getParam(), sysConfig.getValue());
            }
            if (configInDb.size() != config.size()) {
                log.info("The config size is diff ,the org config:{}，the target config:{}", config, configInDb);
                config = configInDb;
            } else {
                Set<Map.Entry<String, String>> entries = configInDb.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String keyInDb = entry.getKey();
                    String valInDb = entry.getValue();
                    String valInMen = config.get(keyInDb);
                    if (!valInDb.equals(valInMen)) {
                        log.info("the value is diff,  org config:{}，the target config:{}", config, configInDb);
                        config = configInDb;
                    }
                }
            }
        }
    }

    public static String getValue(BasicEnum keyEnum, String defaultVal) {
        String val = getValue(keyEnum);
        if (StringUtils.isEmpty(val)) {
            return defaultVal;
        }else {
            return val;
        }
    }

    public static String getValue(BasicEnum keyEnum) {
        String key = keyEnum.getCode().toString();
        return getValue(key);
    }

    private static String getValue(String key) {
        String val = config.get(key);
        log.info("key:{},value:{}", key, val);
        return val;

    }


}
