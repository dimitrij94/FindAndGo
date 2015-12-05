package com.example.services.placeservice.menu;

import com.example.domain.users.PlaceUser;
import com.example.domain.menu.PlaceMenuOptionalService;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * Created by Dmitrij on 30.10.2015.
 */
public interface MenuService {

    PlaceMenuOptionalService registerNewMenuService(DefaultMultipartHttpServletRequest dmhsRequest, PlaceUser user);
}
