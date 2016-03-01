package com.example.neo_services.employee;

import com.example.pojo.dto.EmployeeDTO;

/**
 * Created by Dmitrij on 28.02.2016.
 */
public interface EmployeeService {
    com.example.graph.employee.PlaceEmployee addEmployee(EmployeeDTO employeeDto, String placeName);
}
