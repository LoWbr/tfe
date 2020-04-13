package init.crud1.form;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
