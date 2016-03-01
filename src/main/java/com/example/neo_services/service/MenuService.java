package com.example.neo_services.service;

import com.example.graph.service.PlaceMenuService;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.ServiceDTO;

import java.util.List;

/**
 * Created by Dmitrij on 25.02.2016.
 */

public interface MenuService {
    PlaceMenuService registerNewPlaceMenu(String placeName, MenuDTO menuDTO, List<String> tagNames);

    void registerNewPlaceMenuService(String placeMenuServiceName, ServiceDTO serviceDTO);
}
