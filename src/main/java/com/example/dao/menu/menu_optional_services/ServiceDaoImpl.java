package com.example.dao.menu.menu_optional_services;

import com.example.dao.DBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
@Repository
public class ServiceDaoImpl extends DBBean implements ServiceDAO {
    @Override
    @Transactional
    public void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service) {
        service.setMenu(menu);
        this.em.persist(service);
        menu.getServices().add(service);
        em.merge(menu);
    }

    @Override
    @Transactional
    public void newPlaceMenuOptionalService(PlaceMenuOptionalService service, PlaceMenu menu) {
        service.setMenu(menu);
        this.em.persist(service);
        menu.setServices(setAsList(menu.getServices(), service));
        this.em.flush();
    }

    @Override
    public PlaceMenuOptionalService getMenuServicesById(Long l) {
        return em.find(PlaceMenuOptionalService.class, l);
    }

}
