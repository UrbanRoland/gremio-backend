package com.gremio.validator.annotation;

import com.gremio.validator.RequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredValidator.class)
@Documented
public @interface Required {

    String message() default "Field is required!";

    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
    
}