package com.example.dao.employee;

import com.example.domain.photos.PlaceEmployeePhoto;
import com.example.domain.users.employee.PlaceEmployee;

/**
 * Created by Dmitrij on 22.12.2015.
 */
public interface IEmployeeDAO {
    void saveEmployeePhoto(PlaceEmployeePhoto placeEmployeePhoto);

    byte[] getEmployeeMainPhoto(long id, String name);

    PlaceEmployee registerEmployee(PlaceEmployee employee);

}
