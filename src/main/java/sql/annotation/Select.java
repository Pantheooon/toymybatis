package sql.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {

    String value();
}
