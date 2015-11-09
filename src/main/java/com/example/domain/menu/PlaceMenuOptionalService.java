package com.example.domain.menu;

import com.example.domain.Order;
import com.example.pojo.dto.ServiceDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Entity
@Table(name = "place_menu_services")
public class PlaceMenuOptionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String description;
    int price;

    @ManyToOne
    @JoinColumn(name = "menu")
    PlaceMenu menu;

    @ManyToMany
    @JoinTable(joinColumns =@JoinColumn(name = "service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            name = "service_orders")
    List<Order> orders;

    public PlaceMenuOptionalService() {
    }

    public PlaceMenuOptionalService(DefaultMultipartHttpServletRequest dmhsRequest) {
        this.name = dmhsRequest.getParameter("serviceName");
        this.description = dmhsRequest.getParameter("serviceDescription");
        this.price = Integer.valueOf(dmhsRequest.getParameter("servicePrice"));
    }

    public PlaceMenuOptionalService(ServiceDTO serviceDTO) {
        name = serviceDTO.getServiceName();
        description = serviceDTO.getServiceDescription();
        price = serviceDTO.getServicePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PlaceMenu getMenu() {
        return menu;
    }

    public void setMenu(PlaceMenu menu) {
        this.menu = menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
