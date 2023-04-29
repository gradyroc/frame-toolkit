package cn.grady.common;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author rociss
 * @version 1.0, on 0:26 2023/4/14.
 */
public abstract class BasicObject implements Serializable {
    @Override
    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this,new MyStyle());
        }catch (Exception e){
            return e.getMessage();
        }
    }

    private static class MyStyle extends ToStringStyle {
        public static final long serialVersionUID=35466780000000000L;

        public MyStyle() {
            super();
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
        }

        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {

            if (fieldName.equals("password")|| fieldName.equals("pwd")|| fieldName.equals("passcode")){
                value="********";
            }
            buffer.append(value);
        }
    }
}
