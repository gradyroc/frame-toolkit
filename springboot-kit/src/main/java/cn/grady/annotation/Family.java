package cn.grady.annotation;

import java.lang.annotation.*;

/**
 * @author grady
 * @version 1.0, on 22:40 2021/12/18.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Family {

    String name();
}
