package com.example.controllers.rest;

import com.example.domain.owner.PlaceOwner;
import com.example.pojo.dto.OwnerDTO;
import com.example.services.owner.OwnerService;
import com.example.validators.PlaceOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@RestController
@RequestMapping("/owner")
public class OwnerRestController {
    @Autowired
    OwnerService service;

    @Autowired
    PlaceOwnerValidator validator;

    @RequestMapping("/name")
    public boolean validateName(String name) {
        return service.validateOwnerName(name);
    }

    @RequestMapping("/email")
    public boolean validateEmail(String email) {
        return service.validateOwnerEmail(email);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PlaceOwner> createOwner(@RequestBody OwnerDTO ownerDTO, BindingResult errors) {
        validator.validate(ownerDTO, errors);
        if (errors.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PlaceOwner owner = service.registerNewOwner(ownerDTO);
        return new ResponseEntity<>(owner, HttpStatus.CREATED);
    }

}
