package cn.grady.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ExecutorService;

/**
 * @author grady
 * @version 1.0, on 0:54 2021/12/27.
 */
public class HBaseKerberosUtil {

    private static final Logger log = LoggerFactory.getLogger(HBaseKerberosUtil.class);

    private static final String JAVA_SECURITY_KRB5_CONF_PARAM = "java.security.krb5.conf";
    private static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
    private static final String HBASE_REGIONSERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";
    private static final String SECURITY_AUTHENTICATION_NAME = "kerberos";
    private static final String HBASE_SECURITY_AUTHENTICATION = "hbase.security.authentication";

    public static final String KEYTAB_USER_NAME = "kerberos.keytab.user.name";
    public static final String KEYTAB_FILE_NAME = "kerberos.keytab.password.file";

    private static volatile boolean isInitialUgi;
    private static UserGroupInformation ugi;

    public static interface Config {

        String getVal(String param);
    }

    public static String getFileWithDir(String fileDir, String fileName) {
        String configFile = fileDir + fileName;
        log.info("the fileName  is {} and the fullFileName is {}.", fileName, fileDir);
        return configFile;
    }

    private static void validate(Configuration config, String paramPrefix, Config conf) {
        Preconditions.checkArgument(null != config, "The configuration must not be null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(paramPrefix), "The configPrefix must not be null");
        Preconditions.checkArgument(null != conf, "The conf must not be null");
    }

    public static Connection configKerberosIfNeedWithFileDir(Configuration config, ExecutorService pool, String paramPrefix, Config conf, String fileDir) {
        validate(config, paramPrefix, conf);
        Preconditions.checkArgument(StringUtils.isNotEmpty(fileDir), "The fileDir must not be null");

        String krb5Config = conf.getVal(getFullParam(paramPrefix, JAVA_SECURITY_KRB5_CONF_PARAM));
        if (StringUtils.isNotEmpty(krb5Config)) {
            krb5Config = getFileWithDir(fileDir, krb5Config);
        }
        String masterPrincipal = conf.getVal(getFullParam(paramPrefix, HBASE_MASTER_KERBEROS_PRINCIPAL));
        String regionServerPrincipal = conf.getVal(getFullParam(paramPrefix, HBASE_REGIONSERVER_KERBEROS_PRINCIPAL));
        String keytabUserName = conf.getVal(getFullParam(paramPrefix, KEYTAB_USER_NAME));
        String keytabPasswordFile = conf.getVal(getFullParam(paramPrefix, KEYTAB_FILE_NAME));
        keytabPasswordFile = getFileWithDir(fileDir, keytabPasswordFile);

        return getKerberosConnIfNeed(config, pool, krb5Config, masterPrincipal, regionServerPrincipal, keytabUserName, keytabPasswordFile);

    }

    private static Connection getKerberosConnIfNeed(Configuration config,
                                                    ExecutorService pool,
                                                    String krb5Config,
                                                    String masterPrincipal,
                                                    String regionServerPrincipal,
                                                    String keytabUserName,
                                                    String keytabPasswordFile) {

        return configKerberosIfNeed(config, krb5Config, masterPrincipal, regionServerPrincipal, keytabUserName, keytabPasswordFile, new Func<Connection>() {
            @Override
            public Connection newObj(Configuration conf) throws IOException {
                return ConnectionFactory.createConnection(conf, pool);
            }

            @Override
            public Connection newObj() throws IOException {
                return ConnectionFactory.createConnection(config, pool);
            }
        });
    }

    public interface Func<T> {
        T newObj(Configuration conf) throws IOException;

        T newObj() throws IOException;
    }

    public static <T> T configKerberosIfNeed(Configuration config,
                                             String krb5Config,
                                             String masterPrincipal,
                                             String regionServerPrincipal,
                                             String keytabUserName,
                                             String keytabPasswordFile, Func<T> func) {

        if (StringUtils.isBlank(krb5Config)) {
            try {
                return func.newObj(config);
            } catch (IOException e) {
                log.error("Creating HBase Connection occurs exception.", e);
//                Throwables.propagate(e);
                Throwables.throwIfUnchecked(e);
                return null;
            }
        } else {
            log.info("The HBase {} ," +
                            "the Kerberos krb5Config is {}" +
                            "regionServerPrincipal is {}," +
                            "keytabUserName is {},keytabPasswordFile is {}",
                    getHBaseClusterInfo(config), krb5Config, masterPrincipal,
                    regionServerPrincipal, keytabUserName, keytabPasswordFile);
            System.setProperty(JAVA_SECURITY_KRB5_CONF_PARAM, krb5Config);
            config.set(CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION_NAME);
            config.setBoolean(CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHORIZATION, true);
            config.set(HBASE_MASTER_KERBEROS_PRINCIPAL, masterPrincipal);
            config.set(HBASE_REGIONSERVER_KERBEROS_PRINCIPAL, regionServerPrincipal);
            config.set(HBASE_SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION_NAME);

            T conn = null;
            try {
                UserGroupInformation ugi = getUgi(config, keytabUserName, keytabPasswordFile);
                conn = ugi.doAs(new PrivilegedExceptionAction<T>() {
                    @Override
                    public T run() throws IOException {
                        return func.newObj(config);
                    }
                });
            } catch (Exception e) {
                log.error("The kerberos occurs exception. krb5Config: " + krb5Config +
                        ", masterPrincipal:" + masterPrincipal +
                        ",regionServerPrincipal:" + regionServerPrincipal +
                        ",keytabUserName:" + keytabUserName +
                        ",keytabPasswordFile:" + keytabPasswordFile, e);
                Throwables.throwIfUnchecked(e);
            }
            return conn;
        }


    }

    private static UserGroupInformation getUgi(Configuration config, String keytabUserName, String keytabPasswordFile) throws IOException {
        if (isInitialUgi) {
            return ugi;
        }
        synchronized (HBaseKerberosUtil.class) {
            if (isInitialUgi) {
                return ugi;
            }
            UserGroupInformation.setConfiguration(config);
            ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(keytabUserName, keytabPasswordFile);
            isInitialUgi = true;
            return ugi;
        }
    }

    private static String getHBaseClusterInfo(Configuration config) {
        String quorum = config.get(HConstants.ZOOKEEPER_QUORUM);
        String port = config.get(HConstants.ZOOKEEPER_CLIENT_PORT);
        String zNodeParent = config.get(HConstants.ZOOKEEPER_ZNODE_PARENT);
        return quorum + ":" + port + zNodeParent;
    }


    private static String getFullParam(String paramPrefix, String param) {

        return paramPrefix + param;
    }


}
