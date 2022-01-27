package cn.grady.hbase.dto;

import cn.grady.dto.BasicDTO;
import cn.grady.dto.PrintableObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author grady
 * @version 1.0, on 22:44 2021/12/18.
 */
public class TableMetaDTO extends BasicDTO {

    private static final long serialVersionUID = 3943229830902464528L;

    private String tableName;
    private RowkeyMeta rowkeyMeta;
    private TimestampMeta timestampMeta;
    private Map<String, QuelifierMeta> quelifierMeta;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public RowkeyMeta getRowkeyMeta() {
        return rowkeyMeta;
    }

    public void setRowkeyMeta(RowkeyMeta rowkeyMeta) {
        this.rowkeyMeta = rowkeyMeta;
    }

    public Map<String, QuelifierMeta> getQuelifierMeta() {
        return quelifierMeta;
    }

    public void setQuelifierMeta(Map<String, QuelifierMeta> quelifierMeta) {
        this.quelifierMeta = quelifierMeta;
    }

    public TimestampMeta getTimestampMeta() {
        return timestampMeta;
    }

    public void setTimestampMeta(TimestampMeta timestampMeta) {
        this.timestampMeta = timestampMeta;
    }

    public static class RowkeyMeta extends PrintableObject {
        private static final long serialVersionUID = -7947809055532146575L;
        private Field field;

        public RowkeyMeta() {
        }

        public RowkeyMeta(Field field) {
            this.field = field;
        }

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }


    }

    public static class QuelifierMeta {
        public QuelifierMeta() {
        }

        public Map<String, Field> getQualifierToField() {
            return qualifierToField;
        }

        public void setQualifierToField(String qualifier ,Field field) {
            this.qualifierToField.put(qualifier,field);
        }

        private Map<String, Field> qualifierToField = new HashMap<>();

        public Field getFieldByQualifier(String qualifier) {
            return qualifierToField.get(qualifier);
        }
    }
    public static class TimestampMeta {
        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        private Field field;

        public TimestampMeta(Field field) {
            this.field = field;
        }

    }
}
