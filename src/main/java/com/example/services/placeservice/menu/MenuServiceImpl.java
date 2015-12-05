package com.example.services.placeservice.menu;

import com.example.domain.users.PlaceUser;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.dao.IDBBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    IDBBean dao;

    @Override
    public PlaceMenuOptionalService registerNewMenuService(DefaultMultipartHttpServletRequest dmhsRequest, PlaceUser user) {
        PlaceMenuOptionalService service = new PlaceMenuOptionalService(dmhsRequest);
        PlaceMenu menu = dao.getMenuById(Long.valueOf(dmhsRequest.getParameter("id")));
        dao.newPlaceMenuService(menu,service);
        return service;
    }
}
