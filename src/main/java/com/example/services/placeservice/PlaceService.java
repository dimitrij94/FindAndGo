package com.example.services.placeservice;

import com.example.domain.Place;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.users.employee.PlaceEmployee;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface PlaceService {
    Place registerNewPlace(PlaceDTO placeDTO, PlaceOwner owner) throws IOException;

    PlaceMenu registerNewPlaceMenu(Place place, MenuDTO menuDTO, List<String> tags);

    void registerNewPlaceMenuService(PlaceMenu menu, ServiceDTO service);

    void sendNewOrder(PlaceUser user, long placeId, long menuId, List<Long> services, PlaceEmployee employee);

    boolean isMenuFromPlace(PlaceMenu menu, Place place);
}
