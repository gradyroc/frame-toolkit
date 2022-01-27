package cn.grady.util;

import cn.grady.annotation.*;
import cn.grady.hbase.dto.TableMetaDTO;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author grady
 * @version 1.0, on 0:49 2022/1/11.
 */
public class HBaseUtil {

    public static final Map<Class<?>, TableMetaDTO> TABLE_META_CACHE = new ConcurrentHashMap<>(32);

    public static String getTableName(Class<?> clazz) {

        TableMetaDTO tableMeta = getTableMeta(clazz);
        String tableName = tableMeta.getTableName();
        Preconditions.checkArgument(StringUtils.isNotBlank(tableName),"the Class[%s] must have the @Table of annotation.",clazz.getName());

        return tableName;

    }

    public static TableMetaDTO getTableMeta(Class<?> clazz) {

        TableMetaDTO tableMetaInCache = TABLE_META_CACHE.get(clazz);
        if (null != tableMetaInCache) {
            return tableMetaInCache;
        }
        TableMetaDTO tableMetaDTO = new TableMetaDTO();
        //从子类找到父类，找Table 注解得到表名
        Table table = getAnnotation2SuperClass(clazz, Table.class);
        if (null != table) {
            String name = table.name();
            if (StringUtils.isNotEmpty(name)) {
                tableMetaDTO.setTableName(name);
            }
        }

        Family family = getAnnotation2SuperClass(clazz, Family.class);
        String familyName = null;
        if (null != family) {
            familyName = family.name();
        }

        Map<String, TableMetaDTO.QuelifierMeta> family2QualifyList = new HashMap<>(1);
        //父类的field 也取出来，方便实体复用
        Field[] fields = getFields2SuperClass(clazz);
        for (Field field : fields) {

            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            //忽略ColumnIgnore 注解的field
            if (null == columnIgnore){
                field.setAccessible(true);
                Rowkey rowkey = field.getAnnotation(Rowkey.class);
                if (null != rowkey){
                    tableMetaDTO.setRowkeyMeta(new TableMetaDTO.RowkeyMeta(field));
                }else {
                    TimeStamp timeStamp = field.getAnnotation(TimeStamp.class);
                    if (null!=timeStamp){
                        tableMetaDTO.setTimestampMeta(new TableMetaDTO.TimestampMeta(field));
                    }else {
                        Family familyOnField = field.getAnnotation(Family.class);
                        if (null!=familyOnField) {
                            familyName = familyOnField.name();
                        }
                        if (null !=familyName){
                            TableMetaDTO.QuelifierMeta quelifierMeta = family2QualifyList.computeIfAbsent(familyName, s -> new TableMetaDTO.QuelifierMeta());

                            Column column = field.getAnnotation(Column.class);
                            if (null != column && !column.alias().isEmpty()){
                                quelifierMeta.setQualifierToField(column.alias(),field);
                            }
                            JsonColumn jsonColumn = field.getAnnotation(JsonColumn.class);
                            if (null != jsonColumn && !jsonColumn.alias().isEmpty()){
                                quelifierMeta.setQualifierToField(jsonColumn.alias(),field);
                            }

                        }
                    }
                }

            }
        }
        if (!family2QualifyList.isEmpty()){
            tableMetaDTO.setQuelifierMeta(family2QualifyList);
        }
        TABLE_META_CACHE.put(clazz,tableMetaDTO);

        return tableMetaDTO;


    }

    public static Field[] getFields2SuperClass(Class clazz) {

        Field[] allFields = null;
        while (!clazz.isAssignableFrom(Object.class)) {
            Field[] declaredFields = clazz.getDeclaredFields();
            if (null != declaredFields && declaredFields.length > 0) {
                if (null == allFields){
                    allFields = declaredFields;
                }else {
                    Field[] newAllFields = new Field[allFields.length+declaredFields.length];
                    System.arraycopy(allFields,0,newAllFields,0,allFields.length);
                    System.arraycopy(declaredFields,0,newAllFields,allFields.length,declaredFields.length);
                    allFields = newAllFields;
                }
            }
            //将父类的field 字段也都全部取出来，方便实例复用
            clazz = clazz.getSuperclass();
        }
        return allFields;
    }

    public static <T> T getAnnotation2SuperClass(Class clazz, Class<T> annoClazz) {
        while (!clazz.isAssignableFrom(Object.class)) {
            Annotation annotation = clazz.getAnnotation(annoClazz);
            if (null != annotation) {
                return (T) annotation;
            }
            clazz = clazz.getSuperclass();

        }
        return null;
    }

}
