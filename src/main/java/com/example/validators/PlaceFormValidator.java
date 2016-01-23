package com.example.validators;

import com.example.constants.PlaceSpecialities;
import com.example.dao.place.PlaceDAO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 02.11.2015.
 */
@Component
public class PlaceFormValidator implements Validator {

    @Autowired
    PlaceDAO dao;

    private final Validator scheduleValidator;

    @Autowired
    public PlaceFormValidator(AddressValidator addressValidator, ScheduleValidator scheduleValidator) {

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", requiredError);

        PlaceDTO place = (PlaceDTO) target;


        if (!Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{1,40}$").matcher(place.getName()).matches()) {
            errors.rejectValue("name", "field.invalid");
        }

        if (!(Arrays.stream(PlaceSpecialities.class.getEnumConstants())
                .anyMatch((c) -> c.getName().equals(place.getSpecialization()))))
            errors.rejectValue("speciality", "field.invalid");

        int dL = place.getDescription().length();
        if (dL == 0 || dL > 250 || dL < 50)
            errors.rejectValue("description", "field.invalid");

        for (ScheduleDTO schedule : place.getSchedules()) {
            try {
                errors.pushNestedPath("schedules");
                scheduleValidator.validate(schedule.getOpen(), errors);
                scheduleValidator.validate(schedule.getCloses(), errors);
            } finally {
                errors.popNestedPath();
            }
        }

        if (dao.countPlacesWithName(place.getName()) == 0L) errors.rejectValue("name", "field.is.taken");
    }

}
