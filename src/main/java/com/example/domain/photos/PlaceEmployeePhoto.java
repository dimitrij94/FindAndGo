package com.example.domain.photos;

import com.example.constants.image.sizes.EmployeeImageSizes;
import com.example.constants.image.sizes.ImageSize;
import com.example.dao.IDBBean;
import com.example.domain.users.employee.PlaceEmployee;
import com.example.interfaces.PhotoCotainable;
import com.example.functional.photos.SavePhotoFunction;
import com.example.interfaces.Scaleble;

import javax.persistence.*;

/**
 * Created by Dmitrij on 10.10.2015.
 */
@Entity
@Table(name = "place_employee_photo")
public class PlaceEmployeePhoto implements Scaleble {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private byte[] body;
    @ManyToOne
    @JoinColumn(name = "employee")
    private PlaceEmployee employee;

    public PlaceEmployeePhoto(byte[] imageBody, PlaceEmployee employee, String name) {
        this.body = imageBody;
        this.employee = employee;
        this.name = name;
    }

    public PlaceEmployeePhoto() {
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



    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }
}
