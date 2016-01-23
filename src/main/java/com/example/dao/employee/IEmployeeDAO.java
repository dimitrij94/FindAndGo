package com.example.dao.employee;

import com.example.domain.UserOrders;
import com.example.domain.employee.EmployeePauses;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.employee.PlaceEmployeePhoto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dmitrij on 22.12.2015.
 */
public interface IEmployeeDAO {
    void saveEmployeePhoto(PlaceEmployeePhoto placeEmployeePhoto);

    byte[] getEmployeeMainPhoto(long id, String name);

    PlaceEmployee registerEmployee(PlaceEmployee employee);

    @SuppressWarnings("unchecked")
    List<UserOrders> getEmployeeTodayOrders(PlaceEmployee employee, LocalDate date);

    @SuppressWarnings("unchecked")
    List<EmployeePauses> getEmployeeTodayPauses(PlaceEmployee employee, LocalDateTime localDate);

    PlaceEmployee getEmployeeById(long employeeID);

}
