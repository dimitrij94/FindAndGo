package com.example.validators;

import com.example.pojo.dto.ScheduleTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@Component
public class ScheduleTimeValidtor implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ScheduleTime.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String requiredError = "field.required";
        String invalidError = "field.invalid";

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hour", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minutes", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "part", requiredError);

        ScheduleTime time = (ScheduleTime) target;

        int openHour = time.getHour();
        if (openHour > 12 || openHour < 0) errors.rejectValue("hour", invalidError);

        int openMintue = time.getMinutes();
        if (openMintue > 60 || openMintue < 0) errors.rejectValue("minutes", invalidError);

        String part = time.getPart().toUpperCase();
        if (!(part.equals("PM") || part.equals("AM"))) errors.rejectValue("part", invalidError);
    }
}
