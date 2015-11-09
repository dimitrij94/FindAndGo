package com.example.domain.validation;

import com.example.pojo.dto.UserCreateForm;
import com.example.domain.validation.annotations.UserPasswordsEqual;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Dmitrij on 14.09.2015.
 */
public class FieldEqualValidator implements ConstraintValidator<UserPasswordsEqual, Object> {

    String message = UserPasswordsEqual.MESSAGE;

    @Override
    public void initialize(UserPasswordsEqual constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserCreateForm user = (UserCreateForm)value;
        return user.getUserPass().equals(user.getUserPassConf());
    }
}
