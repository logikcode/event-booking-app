package com.bw.reference.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE_USE})
@Constraint(validatedBy = {com.bw.reference.constraint.UniqueColumnValue.Validator.class})
public @interface UniqueColumnValue {
    String message() default "{com.bw.reference.constraints.column.name.does.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String column();

    String value() default "";

    interface Validator extends ConstraintValidator<UniqueColumnValue, String> {
    }
}
