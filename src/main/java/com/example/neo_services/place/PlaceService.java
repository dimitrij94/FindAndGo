package com.example.neo_services.place;

import com.example.graph.place.Place;
import com.example.graph.schedules.Schedule;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.PlaceOrderDTO;
import com.example.pojo.dto.ScheduleDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by Dmitrij on 21.02.2016.
 */
public interface PlaceService {
    void sendNewOrder(String userName, String serviceName,
                      List<String> additServicesNames, String employeeName);

    void sendNewOrder(PlaceOrderDTO orderDTO);

    Place newPlace(PlaceDTO placeDTO);

    void addPhoto(PhotoDTO photo, String placeName);

    List<Schedule> updatePlaceSchedual(List<ScheduleDTO> newSchedual, String placeName);

    @PreAuthorize("#place.owner.id == principal.id")
    void deletePlace(Place place);


}
