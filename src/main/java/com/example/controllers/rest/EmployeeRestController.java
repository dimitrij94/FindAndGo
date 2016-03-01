package com.example.controllers.rest;

import com.example.graph.employee.PlaceEmployee;
import com.example.neo_services.employee.EmployeeService;
import com.example.pojo.dto.EmployeeDTO;
import com.example.validators.EmployeeValidtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Dmitrij on 22.12.2015.
 */
@RestController
@RequestMapping("/place/{placeName}/employee/")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private EmployeeValidtor validtor;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService,
                                  EmployeeValidtor validtor) {
        this.employeeService = employeeService;
        this.validtor = validtor;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createNewEmployee(@RequestBody EmployeeDTO employeeDto,
                                                  @PathVariable("placeName") String placeName,
                                                  UriComponentsBuilder uri,
                                                  BindingResult result) {
        validtor.validate(employeeDto, result);
        if (!result.hasErrors()) {
            PlaceEmployee employee = employeeService.addEmployee(employeeDto, placeName);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uri.path("/employee/{id}").buildAndExpand(employee.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
