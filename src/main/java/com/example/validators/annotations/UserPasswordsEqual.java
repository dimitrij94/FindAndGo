package com.example.domain.validation.annotations;

import com.example.domain.validation.FieldEqualValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dmitrij on 14.09.2015.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldEqualValidator.class)
public @interface UserPasswordsEqual {
    String MESSAGE = "fields.notMatches";
    String message() default MESSAGE;
    Class<?>[]groups()default {};
    Class<? extends Payload>[]payload() default {};
}
