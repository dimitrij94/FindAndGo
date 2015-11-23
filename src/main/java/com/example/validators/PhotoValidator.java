package com.example.validators;

import com.example.domain.photos.PlacePhoto;
import com.example.pojo.dto.PhotoDTO;
import com.example.services.imageservice.ImageService;
import com.example.services.imageservice.ImageServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
        PhotoDTO photo = (PhotoDTO)target;

        if(photo.getImage().isEmpty()){
            errors.rejectValue("photo","field.required","Виберіть фото");
        }

        if(!photo.getImage().getContentType().split("/")[0].equals("image")){
            errors.rejectValue("photo","field.invalid","Невірний формат зображення");
        }

        if(photo.getW()==0||photo.getH()==0){
            errors.rejectValue("photo","field.invalid","Невірні координати");
        }
    }
}
