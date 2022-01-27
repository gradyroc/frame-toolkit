package cn.grady.annotation;

import java.lang.annotation.*;

/**
 * @author grady
 * @version 1.0, on 22:09 2021/12/18.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonColumn {

    String alias() default "";
}
