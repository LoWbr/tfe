package init.crud1.form;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SamePasswordValidator.class)
public @interface SamePassword {

    String message() default "The two passwords do not match.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
