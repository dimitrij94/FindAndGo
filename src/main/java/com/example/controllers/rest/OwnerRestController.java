package com.example.controllers.rest;

import com.example.dao.owner.OwnerDAO;
import com.example.domain.owner.PlaceOwner;
import com.example.pojo.dto.OwnerDTO;
import com.example.services.owner.OwnerService;
import com.example.validators.PlaceOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@RestController
@RequestMapping("/owner")
public class OwnerRestController {
    @Autowired
    OwnerService service;

    @Autowired
    OwnerDAO dao;

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
    public ResponseEntity<Void> createOwner(@RequestBody OwnerDTO ownerDTO,
                                            UriComponentsBuilder builder,
                                            BindingResult errors) {
        validator.validate(ownerDTO, errors);
        if (errors.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PlaceOwner owner = service.registerNewOwner(ownerDTO);
        HttpHeaders locationHeader = new HttpHeaders();
        locationHeader.setLocation(builder.path("/owner/{id}").buildAndExpand(owner.getId()).toUri());
        return new ResponseEntity<>(locationHeader, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaceOwner> getOwner(@PathVariable("id") long id) {
        PlaceOwner owner = dao.getOwnerById(id);
        if (owner != null) return new ResponseEntity<>(owner, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateOwner(OwnerDTO newOwner, BindingResult errors){
        validator.validate(newOwner,errors);
        if(!errors.hasErrors()){
            service.updateOwner(newOwner);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOwner() {
        service.deleteOwner();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
