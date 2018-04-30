package sql.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    boolean geratekey() default true;

    String keyColumn() default "id";

    String value();
}
