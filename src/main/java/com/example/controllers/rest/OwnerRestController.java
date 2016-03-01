package com.example.controllers.rest;

import com.example.graph.owner.PlaceOwner;
import com.example.graph_repositories.owner.PlaceOwnerRepository;
import com.example.neo_services.owner.PlaceOwnerService;
import com.example.pojo.dto.OwnerDTO;
import com.example.validators.PlaceOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@RestController
@RequestMapping("/owner")
public class OwnerRestController {

    private PlaceOwnerRepository ownerRepository;
    private PlaceOwnerService ownerService;
    private PlaceOwnerValidator validator;

    @Autowired
    public OwnerRestController(PlaceOwnerRepository ownerRepository,
                               PlaceOwnerService ownerService,
                               PlaceOwnerValidator validator) {
        this.ownerRepository = ownerRepository;
        this.ownerService = ownerService;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createOwner(@RequestBody OwnerDTO ownerDTO,
                                            UriComponentsBuilder builder,
                                            BindingResult errors) {
        validator.validate(ownerDTO, errors);
        if (errors.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PlaceOwner owner = ownerService.newOwner(ownerDTO);
        HttpHeaders locationHeader = new HttpHeaders();
        locationHeader.setLocation(builder.path("/owner/{id}").buildAndExpand(owner.getId()).toUri());
        return new ResponseEntity<>(locationHeader, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{ownerName}", method = RequestMethod.GET)
    public ResponseEntity<PlaceOwner> getOwner(@PathVariable("ownerName") String ownerName) {
        PlaceOwner owner = ownerRepository.findByUserName(ownerName);
        if (owner != null) return new ResponseEntity<>(owner, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOwner() {
        ownerService.deleteOwner();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
