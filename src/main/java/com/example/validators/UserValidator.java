package com.example.validators;

import com.example.neo_services.user.UserService;
import com.example.pojo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 14.11.2015.
 */
@Component
public class UserValidator implements Validator {


    private UserService userService;
    private Pattern userNamePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
    private Pattern userPasswordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).*$");

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "name.required", "Поле не може бути пустим");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "email.required", "Поле не може бути пустим");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPass", "password.required", "Поле не може бути пустим");

        UserDTO user = (UserDTO) target;

        if (!userNamePattern.matcher(user.getUserName()).matches())
            errors.rejectValue("name", "field.invalid");

        if (!userPasswordPattern.matcher(user.getUserPass()).matches()) {
            errors.rejectValue("pass", "field.invalid");
        }

        if (userService.isUsersWithThisCredentialsExist(user.getUserEmail(), user.getUserName()))
            errors.reject("credentials.not.unique", "Такий логін вже зайнятий");


    }
}
