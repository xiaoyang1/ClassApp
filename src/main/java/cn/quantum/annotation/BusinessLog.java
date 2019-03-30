package cn.quantum.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface BusinessLog {
    String value() default "";
}
