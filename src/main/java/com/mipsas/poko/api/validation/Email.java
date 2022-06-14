package com.mipsas.poko.api.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Pattern(
        regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
        message = "{validation.email}"
)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface Email {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
