package com.example.services.placeservice;

import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface PlaceService {
    Place registerNewPlace(PlaceDTO placeDTO, PlaceUser owner) throws IOException;

    PlaceMenu registerNewPlaceMenu(Place place, MenuDTO menuDTO);

    void registerNewPlaceMenuService(PlaceMenu menu, ServiceDTO service);

    void sendNewOrder(PlaceUser user, long placeId, long menuId, List<Long> services);

    boolean isMenuFromPlace(PlaceMenu menu, Place place);

    void newLike(PlaceUser user, long id);
}
