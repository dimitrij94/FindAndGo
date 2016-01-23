package com.example.validators;

import com.example.constants.WeekDays;
import com.example.pojo.dto.ScheduleDTO;
import com.example.pojo.dto.ScheduleTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@Component
public class ScheduleValidator implements Validator {

    private ScheduleTimeValidtor timeValidtor;

    @Autowired
    public ScheduleValidator(ScheduleTimeValidtor timeValidtor) {
        if (timeValidtor.supports(ScheduleTime.class)) this.timeValidtor = timeValidtor;
        else throw new IllegalArgumentException("The supplied [Validator] must " +
                "support the validation of [ScheduleTime] instances.");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ScheduleDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String requiredError = "field.required";
        String invalidError = "field.invalid";

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "open", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "closes", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "day", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isWorking", requiredError);

        ScheduleDTO schedule = (ScheduleDTO) target;

        String dayName = null;
        for (WeekDays day : WeekDays.values()) {
            if (schedule.getDay().toLowerCase().equals(day.getTwoLetters())) {
              dayName = day.getThreeLetters();
            }
        }

        if(dayName==null) errors.rejectValue("day",invalidError);

        try {
            errors.pushNestedPath("open");
            ValidationUtils.invokeValidator(timeValidtor, schedule.getOpen(), errors);
        } finally {
            errors.popNestedPath();
        }

        try {
            errors.pushNestedPath("closes");
            ValidationUtils.invokeValidator(timeValidtor, schedule.getCloses(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
