package com.example.dao.menu.menu_optional_services;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
public interface ServiceDAO {
    @Transactional
    void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service);

    @Transactional
    void newPlaceMenuOptionalService(PlaceMenuOptionalService service, PlaceMenu menu);

    PlaceMenuOptionalService getMenuServicesById(Long l);
}
