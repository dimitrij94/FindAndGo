package com.example.services.employee;

import com.example.domain.employee.PlaceEmployee;
import com.example.pojo.dto.EmployeeDTO;

/**
 * Created by Dmitrij on 29.11.2015.
 */
public interface EmployeeService {

    PlaceEmployee registerEmployee(EmployeeDTO dto);
}
