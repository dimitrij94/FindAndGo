package com.example.services.placeservice;

import com.example.domain.Place;
import com.example.domain.PlaceSpeciality;
import com.example.domain.PlaceUser;
import com.example.domain.addresses.PlaceAddress;
import com.example.dao.IDBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.services.imageservice.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Override
    public Place registerNewPlace(PlaceDTO placeDTO, PlaceUser owner) throws IOException {
        Place place = new Place(placeDTO);
        PlaceAddress placeAddress = new PlaceAddress(placeDTO.getAddress());
        PlaceSpeciality speciality = dao.getPlaceSpeciality(placeDTO.getSpecialization());
        dao.addNewPlace(place, placeAddress, owner,speciality);
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
    public void sendNewOrder(PlaceUser user, long placeId, long menuId, List<Long> services) {

        dao.newOrder(user, dao.getPlaceById(placeId), dao.getMenuById(menuId), services);
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
    public int newPlaceRating(int rating, long pId, PlaceUser user) {
        Place place = dao.getPlaceById(pId);
        if(!(dao.countUserPlaceRatings(pId,user.getId())>0)){
            dao.newPlaceRating(place,user,rating);
        }
        else {
            dao.deleteUserPlaceRating(user.getId(),pId);
            dao.newPlaceRating(place,user,rating);
        }
        int finalRating = dao.getPlaceFinalRating(place);
        dao.updatePlaceRating(place,finalRating);
        return finalRating;
    }


    private int calculateServiceDuration() {
        return 0;
    }

}
