package cn.grady.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author grady
 * @version 1.0, on 13:22 2021/12/19.
 */
public abstract class PrintableObject implements Serializable {

    @Override
    public String toString() {
        try {

            return ToStringBuilder.reflectionToString(this, new CustmizeStyle());
        } catch (Throwable e) {
            return e.getMessage();
        }
    }


    private class CustmizeStyle extends ToStringStyle {

        public CustmizeStyle() {
            super();
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
        }


        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {

            if (fieldName.equals("password")||fieldName.equals("pwd")||fieldName.equals("passcode")){
                value="*********";
            }
            buffer.append(value);
        }
    }
}
