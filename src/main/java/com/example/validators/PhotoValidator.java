package com.example.validators;

import com.example.pojo.dto.PhotoDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Dmitrij on 03.11.2015.
 */
@Component
public class PhotoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PhotoDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"image","field.required");

        MultipartFile photo = ((PhotoDTO)target).getImage();

        if(photo.isEmpty()){
            errors.rejectValue("image","field.required","Виберіть фото");
        }

        if(!photo.getContentType().split("/")[0].equals("image")){
            errors.rejectValue("image","field.invalid","Невірний формат зображення");
        }

    }
}
