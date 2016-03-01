package com.example.neo_services.service;

import com.example.graph.place.Place;
import com.example.graph.service.PlaceMenuAdditionalService;
import com.example.graph.service.PlaceMenuService;
import com.example.graph_repositories.menu.PlaceMenuAdditionalServiceRepository;
import com.example.graph_repositories.menu.PlaceMenuServiceRepository;
import com.example.graph_repositories.place.PlaceRepository;
import com.example.neo_services.image.ImageService;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.ServiceDTO;
import javassist.tools.web.BadHttpRequest;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitrij on 25.02.2016.
 */
@Service
public class PlaceMenuServiceImplementation implements MenuService {
    PlaceMenuServiceRepository placeMenuServiceRepository;
    PlaceMenuAdditionalServiceRepository additionalServiceRepository;
    PlaceRepository placeRepository;
    ImageService imageService;
    GraphDatabase db;

    @Autowired
    public PlaceMenuServiceImplementation(PlaceMenuServiceRepository placeMenuServiceRepository, PlaceRepository placeRepository) {
        this.placeMenuServiceRepository = placeMenuServiceRepository;
        this.placeRepository = placeRepository;
    }

    private int getMenuDuration(int hours, int minutes) {
        return hours * 60 + minutes;
    }

    @Override
    public PlaceMenuService registerNewPlaceMenu(String placeName, MenuDTO menuDTO, List<String> tagNames) {
        PlaceMenuService menuService;
        try (Transaction tx = db.beginTx()) {
            menuDTO.setDurationMinutes(getMenuDuration(menuDTO.getHours(), menuDTO.getMinutes()));
            menuService = new PlaceMenuService(menuDTO);
            Place place = placeRepository.findByName(placeName);
            menuService.setPlace(place);
            placeMenuServiceRepository.save(menuService);
            tx.success();
        }
        return menuService;
    }

    public void savePlaceMenuServicePhoto(PhotoDTO photoDTO, String placeMenuServiceName) throws IOException, BadHttpRequest {
        PlaceMenuService menuService = placeMenuServiceRepository.findByName(placeMenuServiceName);
        imageService.savePlaceMenuServicePhoto(photoDTO, menuService);
    }

    @Override
    public void registerNewPlaceMenuService(String placeMenuServiceName, ServiceDTO serviceDTO) {
        PlaceMenuService menuService = placeMenuServiceRepository.findByName(placeMenuServiceName);
        try (Transaction tx = db.beginTx()) {
            PlaceMenuAdditionalService additionalService = new PlaceMenuAdditionalService(serviceDTO);
            additionalService.setDuration(getMenuDuration(serviceDTO.getServiceHours(), serviceDTO.getServiceMinutes()));
            additionalServiceRepository.save(additionalService);
        }
    }
}
