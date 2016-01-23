package com.example.services.menu;

import com.example.constants.image.ImageContainerType;
import com.example.dao.menu.MenuDAO;
import com.example.dao.menu.menu_optional_services.ServiceDAO;
import com.example.dao.menu.menu_tag_categories.TagDAO;
import com.example.dao.place.PlaceDAO;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.services.imageservice.ImageService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDAO menuDAO;

    @Autowired
    PlaceDAO placeDAO;

    @Autowired
    ServiceDAO serviceDAO;
    @Autowired
    ImageService imageService;

    @Autowired
    TagDAO tagDAO;

    @Override
    public PlaceMenuOptionalService registerNewMenuService(DefaultMultipartHttpServletRequest dmhsRequest, PlaceUser user) {
        PlaceMenuOptionalService service = new PlaceMenuOptionalService(dmhsRequest);
        PlaceMenu menu = menuDAO.getMenuById(Long.valueOf(dmhsRequest.getParameter("id")));
        serviceDAO.newPlaceMenuService(menu, service);
        return service;
    }


    @Override
    public PlaceMenu registerNewPlaceMenu(Place place, MenuDTO menuDTO, List<String> tagNames) {
        PlaceMenu menu = new PlaceMenu(menuDTO);
        List<PlaceMenuTags> tags = new ArrayList<>();

        for (String t : tagNames) {
            PlaceMenuTags tag = tagDAO.findTagByName(t);
            if (tag != null) {
                tags.add(tag);
            } else tags.add(tagDAO.newTag(t));
        }

        menuDAO.newMenu(menu, place, tags);
        try {
            imageService.upload(menuDTO.getPhoto(), menu, ImageContainerType.PLACE_MENU);
        } catch (BadHttpRequest badHttpRequest) {
            badHttpRequest.printStackTrace();
        }
        return menu;
    }

    @Override
    public boolean isMenuFromPlace(PlaceMenu menu, Place place) {
        long id = menu.getId();
        for (PlaceMenu m : place.getPlaceMenu()) {
            if (m.getId() == id) return true;
        }
        return false;
    }

    @Override
    public void registerNewPlaceMenuService(PlaceMenu menu, ServiceDTO serviceDTO) {
        PlaceMenuOptionalService service = new PlaceMenuOptionalService(serviceDTO);
        serviceDAO.newPlaceMenuOptionalService(service, menu);
    }
}
