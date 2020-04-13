package init.crud1.form;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class SamePasswordValidator implements ConstraintValidator<SamePassword, SportsManForm> {

    @Override
    public void initialize(SamePassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(SportsManForm value, ConstraintValidatorContext context) {
        return (value.getPassword().equals(value.getConfirmPassword()));
    }
}
