package com.example.validators;

import com.example.dao.place.PlaceDAO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 02.11.2015.
 */
@Component
public class PlaceFormValidator implements Validator {

    @Autowired
    PlaceDAO dao;

    @Autowired
    @Qualifier("usernamePattern")
    Pattern usernamePattern;

    private final ScheduleValidator scheduleValidator;

    @Autowired
    public PlaceFormValidator(ScheduleValidator scheduleValidator) {

        if (scheduleValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }

        if (!(scheduleValidator.supports(ScheduleDTO.class))) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [Schedule] instances.");
        }
        this.scheduleValidator = scheduleValidator;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return PlaceDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String requiredError = "field.required";

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", requiredError);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "speciality", requiredError);

        if (errors.hasErrors()) return;

        PlaceDTO place = (PlaceDTO) target;
        Matcher matcher = usernamePattern.matcher(place.getName());
        if (!matcher.matches()) {
            errors.rejectValue("name", "field.invalid");
        }


        int dL = place.getDescription().length();
        if (dL > 500 || dL < 50) errors.rejectValue("description", "field.invalid");

        if (place.getSchedules().isEmpty()) errors.rejectValue("schedules", "field.invalid");

        int i = 0;

        for (ScheduleDTO schedule : place.getSchedules()) {
            try {
                String fieldName = String.format("schedules" + "[%d]", i);
                errors.pushNestedPath(fieldName);
                scheduleValidator.validate(schedule, errors);
            } finally {
                errors.popNestedPath();
                i++;
            }
        }

        if (!(dao.countPlacesWithName(place.getName()) == 0L)) errors.rejectValue("name", "field.is.taken");
    }

}
