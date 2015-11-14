package com.example.services.imageservice;

import com.example.domain.Place;
import com.example.domain.menu.PlaceMenu;
import com.example.pojo.dto.PhotoDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.io.IOException;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface ImageService {

    void uploadPlaceMainPhoto(PhotoDTO image, Place place);

    void uploadMenuPhoto(PhotoDTO image, PlaceMenu menu);

}
