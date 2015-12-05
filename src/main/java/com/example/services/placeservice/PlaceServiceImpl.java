package com.example.services.placeservice;

import com.example.domain.Place;
import com.example.domain.PlaceSpeciality;
import com.example.domain.UserOrders;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.addresses.PlaceAddress;
import com.example.dao.IDBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.users.employee.EmployeeBreaks;
import com.example.domain.users.employee.EmployeePauses;
import com.example.domain.users.employee.PlaceEmployee;
import com.example.pojo.dto.EmployeeTimePeriod;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.services.imageservice.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrij on 25.10.2015.
 */

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    ImageService imageService;

    @Autowired
    IDBBean dao;

    @Autowired
    LocaleResolver localeResolver;

    @Override
    public Place registerNewPlace(PlaceDTO placeDTO, PlaceOwner owner) throws IOException {
        Place place = new Place(placeDTO);
        PlaceAddress placeAddress = new PlaceAddress(placeDTO.getAddress());
        PlaceSpeciality speciality = dao.getPlaceSpeciality(placeDTO.getSpecialization());
        dao.addNewPlace(place, placeAddress, owner, speciality);
        imageService.uploadPlaceMainPhoto(placeDTO.getPhoto(), place);
        return place;
    }

    @Override
    public PlaceMenu registerNewPlaceMenu(Place place, MenuDTO menuDTO) {
        PlaceMenu menu = new PlaceMenu(menuDTO);
        dao.newMenu(menu, place);
        imageService.uploadMenuPhoto(menuDTO.getPhoto(), menu);
        return menu;
    }

    @Override
    public void registerNewPlaceMenuService(PlaceMenu menu, ServiceDTO serviceDTO) {
        PlaceMenuOptionalService service = new PlaceMenuOptionalService(serviceDTO);
        dao.newPlaceMenuOptionalService(service, menu);
    }

    @Override
    public void sendNewOrder(PlaceUser user, long placeId,
                             long menuId, List<Long> services,
                             PlaceEmployee employee) {

        Place place = dao.getPlaceById(placeId);
        PlaceMenu menu = dao.getMenuById(menuId);
        UserOrders order = dao.newOrder(user, place, menu, services, employee);
    }


    @Override
    public boolean isMenuFromPlace(PlaceMenu menu, Place place) {
        long id = menu.getId();
        for (PlaceMenu m : place.getPlaceMenu()) {
            if (m.getId() == id) return true;
        }
        return false;
    }


    private int calculateServiceDuration() {
        return 0;
    }

}
