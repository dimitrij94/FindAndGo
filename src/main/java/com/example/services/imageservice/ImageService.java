package com.example.services.imageservice;

import com.example.constants.image.ImageContainerType;
import com.example.interfaces.PhotoCotainable;
import com.example.pojo.dto.PhotoDTO;
import javassist.tools.web.BadHttpRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface ImageService {


    <T extends PhotoCotainable> void upload(PhotoDTO image, T domain, ImageContainerType type) throws BadHttpRequest;

    boolean validate(CommonsMultipartFile image);
}
