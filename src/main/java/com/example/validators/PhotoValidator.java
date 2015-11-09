package com.example.validators;

import com.example.domain.photos.PlacePhoto;
import com.example.pojo.dto.PhotoDTO;
import com.example.services.imageservice.ImageService;
import com.example.services.imageservice.ImageServiceImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class PhotoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PlacePhoto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"image","field.required");
        PhotoDTO photo = (PhotoDTO)target;
        if(photo.getImage().isEmpty()){
            errors.rejectValue("photo","field.required");
        }

        if(!photo.getImage().getContentType().split("/")[0].equals("image")){
            errors.rejectValue("photo","field.invalid");
        }
        if(Math.round((photo.getW()/photo.getH())*10)!= ImageServiceImpl.ImageSize.PLACE_PROFILE_IMAGE_SIZE.getIndex()){
            errors.reject("coordinates.invalid");
        }
        if(photo.getX()>photo.getW()){
            errors.rejectValue("x","field.invalid");
        }
        if(photo.getY()>photo.getW()){
            errors.rejectValue("y","field.invalid");
        }
    }
}
