package com.example.controllers.rest;

import com.example.domain.employee.PlaceEmployee;
import com.example.pojo.dto.EmployeeDTO;
import com.example.services.employee.EmployeeService;
import com.example.validators.EmployeeValidtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Dmitrij on 22.12.2015.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private EmployeeService service;
    private EmployeeValidtor validtor;



    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<Void> createNewEmployee(@RequestBody EmployeeDTO employeeDto,
                                                  UriComponentsBuilder uri,
                                                  BindingResult result){
        validtor.validate(employeeDto, result);
        if(result.hasErrors())return new ResponseEntity<>(HttpStatus.CONFLICT);
        PlaceEmployee employee = service.registerEmployee(employeeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.OK);
    }


    @Autowired
    public void setValidtor(EmployeeValidtor validtor) {
        this.validtor = validtor;
    }

    @Autowired
    public void setService(EmployeeService service) {
        this.service = service;
    }
}
