package com.example.dao.order;

import com.example.domain.UserOrders;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
public interface OrderDAO {
    List<UserOrders> getUserPlaceOrders(long userId, long placeId);

    List<Place> getPlacesWithUserOrder(PlaceUser user);

    @Transactional
    UserOrders newOrder(PlaceUser user,
                        Place place,
                        PlaceMenu menu,
                        List<PlaceMenuOptionalService> services,
                        PlaceEmployee employee);

    UserOrders activateOrder(UserOrders orders);

    UserOrders findOrderById(long id, PlaceUser user);

    UserOrders getOrder(long id);

    @Transactional
    void setOrderComplete(UserOrders order, boolean b);
}
