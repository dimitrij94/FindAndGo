package com.example.domain.menu;

import com.example.domain.UserOrders;
import com.example.pojo.dto.ServiceDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Entity
@Table(name = "place_menu_services")
public class PlaceMenuOptionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private int price;
    private long durationMinutes;

    @ManyToOne
    @JoinColumn(name = "menu")
    private PlaceMenu menu;

    @ManyToMany(mappedBy = "services")
    private   List<UserOrders> userOrderses;

    public PlaceMenuOptionalService() {
    }

    public PlaceMenuOptionalService(DefaultMultipartHttpServletRequest dmhsRequest) {
        this.description = dmhsRequest.getParameter("serviceDescription");
        this.price = Integer.valueOf(dmhsRequest.getParameter("servicePrice"));
    }

    public PlaceMenuOptionalService(ServiceDTO serviceDTO) {
        description = serviceDTO.getServiceDescription();
        price = serviceDTO.getServicePrice();
        this.durationMinutes = Duration.ofHours(serviceDTO.getServiceHours())
                .plusMinutes(serviceDTO.getServiceMinutes()).toMinutes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<UserOrders> getUserOrderses() {
        return userOrderses;
    }

    public void setUserOrderses(List<UserOrders> userOrderses) {
        this.userOrderses = userOrderses;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(long durationSeconds) {
        this.durationMinutes = durationSeconds;
    }
}
