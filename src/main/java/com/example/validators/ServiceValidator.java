package com.example.validators;

import com.example.domain.menu.PlaceMenuOptionalService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 04.11.2015.
 */
public class ServiceValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PlaceMenuOptionalService.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"price","field.required");

        PlaceMenuOptionalService service = (PlaceMenuOptionalService)target;
        Pattern pattern=Pattern.compile("[a-zA-Z0-9,.!?-]{4,20}");
        if(!pattern.matcher(service.getDescription()).matches()) errors.rejectValue("description","field.invalid");
        
    }
}
