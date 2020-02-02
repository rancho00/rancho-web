package com.rancho.web.admin.validation;

import com.rancho.web.admin.validation.impl.NotAdminValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotAdminValidator.class)
public @interface NotAdmin {
    String message() default "{notAdmin.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
