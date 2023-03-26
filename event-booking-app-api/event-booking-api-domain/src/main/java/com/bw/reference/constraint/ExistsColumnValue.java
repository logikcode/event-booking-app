/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD,
        ElementType.TYPE_USE
})
@Constraint(validatedBy = {ExistsColumnValue.Validator.class})
public @interface ExistsColumnValue {

    String message() default "{com.bw.reference.constraints.ExistsColumnValue.message}";

    /**
     * The column name to check for the value
     *
     * @return
     */
    String columnName() default "id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> value();


    interface Validator extends ConstraintValidator<ExistsColumnValue, Object> {
    }
}
