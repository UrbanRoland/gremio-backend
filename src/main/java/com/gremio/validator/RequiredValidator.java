package com.gremio.validator;

import com.gremio.validator.annotation.Required;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
public class RequiredValidator implements ConstraintValidator<Required, Object> {

    /**
     * Validate required.
     *
     * @param value The given value.
     * @param context Validator context.
     * @return If validation was successful
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        return value != null && (!(value instanceof String) || StringUtils.hasLength((String)value));
    }
}