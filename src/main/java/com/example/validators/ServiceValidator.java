package com.example.validators;

import com.example.pojo.dto.ServiceDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 04.11.2015.
 */
@Component
public class ServiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ServiceDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceDescription", "field.required", "Заповніть поле");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "servicePrice", "field.required", "Заповніть поле");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceHours", "field.required", "Заповніть поле");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceMinutes", "field.required", "Заповніть поле");
        ServiceDTO service = (ServiceDTO) target;

        if (service.getServicePrice() > 100000 || service.getServicePrice() < 0)
            errors.rejectValue("servicePrice", "field.invalid", "Невірна ціна");

        if (service.getServiceHours() > 24 || service.getServiceHours() < 0)
            errors.rejectValue("serviceHours", "field.invalid", "Невірна тривалість");

        if (service.getServiceMinutes() > 60 || service.getServiceMinutes() < 0)
            errors.rejectValue("serviceMinutes", "field.invalid", "Невірна тривалість");

    }
}
