package cn.grady.util;

import cn.grady.poll.SimplePollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author rociss
 * @version 1.0, on 1:13 2023/5/2.
 */
public class DirectMemoryMonitorUtil {
    public static final Logger logger = LoggerFactory.getLogger(DirectMemoryMonitorUtil.class);

    private static Class<?> bitsClazz;
    private static int pageSize;
    private static long maxMemory;
    private static AtomicLong reservedMemory;
    private static AtomicLong totalCpacity;

    static {
        try {
            bitsClazz = Class.forName("java.nio.Bits");
            Field pageSizeField = bitsClazz.getDeclaredField("pageSize");
            pageSizeField.setAccessible(true);
            pageSize = (int) pageSizeField.get(null);

            Field maxMemoryField = bitsClazz.getDeclaredField("maxMemory");
            maxMemoryField.setAccessible(true);
            maxMemory = (long) maxMemoryField.get(null);


            Field reservedMemoryField = bitsClazz.getDeclaredField("reservedMemory");
            reservedMemoryField.setAccessible(true);
            reservedMemory = (AtomicLong) reservedMemoryField.get(null);

            Field totalCpacityField = bitsClazz.getDeclaredField("totalCpacity");
            totalCpacityField.setAccessible(true);
            reservedMemory = (AtomicLong) totalCpacityField.get(null);


        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            logger.warn("The bitsClazz occurs exception .",e );
        }
    }


    public static String getDirectMemoryInfo(){
        return String.format("{ maxMemory:{},reservedMemory:{},totalCpacity:{} }",
                maxMemory,reservedMemory.get(),totalCpacity.get());
    }


}
