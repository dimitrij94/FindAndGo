package com.example.services.menu;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.ServiceDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.util.List;

/**
 * Created by Dmitrij on 30.10.2015.
 */
public interface MenuService {

    PlaceMenuOptionalService registerNewMenuService(DefaultMultipartHttpServletRequest dmhsRequest, PlaceUser user);

    PlaceMenu registerNewPlaceMenu(Place place, MenuDTO menuDTO, List<String> tagNames);

    boolean isMenuFromPlace(PlaceMenu menu, Place place);

    void registerNewPlaceMenuService(PlaceMenu menu, ServiceDTO serviceDTO);
}
