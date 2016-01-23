package com.example.dao.order;

import com.example.dao.DBBean;
import com.example.domain.UserOrders;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@Repository
public class OrderDaoImpl extends DBBean implements OrderDAO {


    @Override
    public List<Place> getPlacesWithUserOrder(PlaceUser user) {
        return em.createQuery("SELECT DISTINCT p FROM Place p,PlaceUser u INNER JOIN u.userOrders o " +
                "WHERE o.user.id=:userId AND o.place.id=p.id AND o.active=false", Place.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    public List<UserOrders> getUserPlaceOrders(long userId, long placeId) {
        return em.createQuery("SELECT e FROM UserOrders e " +
                "WHERE e.place.id=:placeId AND e.user.id=:userId AND e.active=false", UserOrders.class)
                .setParameter("placeId", placeId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public UserOrders newOrder(PlaceUser user,
                               Place place,
                               PlaceMenu menu,
                               List<PlaceMenuOptionalService> services,
                               PlaceEmployee employee) {
        UserOrders userOrders = new UserOrders();
        userOrders.setUser(user);
        userOrders.setPlace(place);
        userOrders.setMenu(menu);
        userOrders.setEmployee(employee);
        em.persist(userOrders);
        em.flush();
        if (services != null) {
            for (PlaceMenuOptionalService service: services) {
                userOrders.setServices(getServiceAsList(service, userOrders.getServices()));
                em.flush();
                service.setUserOrders(getOrdersList(userOrders, service.getUserOrders()));
            }
        }

        user.setUserOrders(getOrdersList(userOrders, user.getUserOrders()));
        em.merge(user);
        place.setUserOrderses(getOrdersList(userOrders, place.getUserOrderses()));
        em.merge(place);
        menu.setUserOrderses(getOrdersList(userOrders, menu.getUserOrderses()));
        em.merge(menu);
        employee.setOrders(getOrdersList(userOrders, employee.getOrders()));
        em.flush();
        return userOrders;
    }

    @Override
    public UserOrders activateOrder(UserOrders orders) {
        orders.setActive(true);
        em.merge(orders);
        return orders;
    }

    @Override
    public UserOrders findOrderById(long id, PlaceUser user) {
        return em.createQuery("SELECT e FROM UserOrders e WHERE e.user=:user AND e.id=:id", UserOrders.class)
                .setParameter("user", user)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public UserOrders getOrder(long id) {
        return em.find(UserOrders.class, id);
    }

    @Override
    @Transactional
    public void setOrderComplete(UserOrders order, boolean b) {
        order.setActive(b);
        em.merge(order);
    }
}
