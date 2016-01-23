package com.example.services.placeservice;

import com.example.domain.employee.PlaceEmployee;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ScheduleDTO;
import com.example.services.authorization.CustomUserDetails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface PlaceService {
    void sendNewOrder(PlaceUser user, long placeId,
                      long menuId, List<Long> services,
                      PlaceEmployee employee);

    Place registerNewPlace(PlaceDTO placeDTO, PlaceOwner owner) throws IOException;

    void addPhoto(CommonsMultipartFile photo, long id);

    void updatePlaceSchedual(List<ScheduleDTO> newSchedual);

    Place newPlace(PlaceDTO place, CustomUserDetails details);
}
