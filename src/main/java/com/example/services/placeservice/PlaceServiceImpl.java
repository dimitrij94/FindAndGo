package com.example.services.placeservice;

import com.example.constants.PlaceSpeciality;
import com.example.constants.image.ImageContainerType;
import com.example.dao.menu.MenuDAO;
import com.example.dao.menu.menu_optional_services.ServiceDAO;
import com.example.dao.order.OrderDAO;
import com.example.dao.owner.OwnerDAO;
import com.example.dao.place.PlaceDAO;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.place.PlaceAddress;
import com.example.domain.place.PlaceSchedule;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ScheduleDTO;
import com.example.services.authentication.CustomUserDetails;
import com.example.services.imageservice.ImageService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrij on 25.10.2015.
 **/

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    ImageService imageService;

    @Autowired
    PlaceDAO placeDAO;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    ServiceDAO serviceDAO;

    @Autowired
    LocaleResolver localeResolver;

    @Autowired
    OwnerDAO ownerDAO;

    @Override
    public void sendNewOrder(PlaceUser user, long placeId,
                             long menuId, List<Long> services,
                             PlaceEmployee employee) {

        Place place = placeDAO.getPlaceById(placeId);
        PlaceMenu menu = menuDAO.getMenuById(menuId);

        orderDAO.newOrder(user, place, menu, resolveOptionalServices(services), employee);
    }

    private ArrayList<PlaceMenuOptionalService> resolveOptionalServices(List<Long> servicesId) {
        ArrayList<PlaceMenuOptionalService> services = new ArrayList<>(servicesId.size());
        for (long id : servicesId)
            services.add(serviceDAO.getMenuServicesById(id));
        return services;
    }

    @Override
    public void addPhoto(CommonsMultipartFile photo, long id) {
        try {
            imageService.upload(new PhotoDTO(photo), placeDAO.getPlaceById(id), ImageContainerType.PLACE);
        } catch (BadHttpRequest badHttpRequest) {
            badHttpRequest.printStackTrace();
        }
    }

    @Override
    public List<PlaceSchedule> updatePlaceSchedual(List<ScheduleDTO> newSchedual) {
        return createSchedule(newSchedual);
    }

    private List<PlaceSchedule>createSchedule(List<ScheduleDTO> scheduleDTOs){
        List<PlaceSchedule> schedules = new ArrayList<>(7);
        for (ScheduleDTO schedule : scheduleDTOs)
            schedules.add(new PlaceSchedule(schedule));
        return schedules;
    }

    @Override
    public CustomUserDetails placeOwner(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) return null;
        return (CustomUserDetails)authentication.getPrincipal();
    }

    @Override
    public Place newPlace(PlaceDTO dto) {
        Place place  = new Place(dto);
        place.setPlaceSchedules(createSchedule(dto.getSchedules()));
        return placeDAO.addNewPlace(new Place(dto), ownerDAO.getOwnerById(placeOwner().getId()));
    }

    public Place registerNewPlace(PlaceDTO placeDTO, PlaceOwner owner) {

        Place place = new Place(placeDTO);
        PlaceAddress placeAddress = new PlaceAddress();
        place.setSpeciality(PlaceSpeciality.getInstance(placeDTO.getSpeciality()));

        List<PlaceSchedule> schedules = new ArrayList<>(7);
        for (ScheduleDTO schedule : placeDTO.getSchedules())
            schedules.add(new PlaceSchedule(schedule));

        placeDAO.addNewPlace(place, placeAddress, owner, schedules);

        return place;
    }
}
