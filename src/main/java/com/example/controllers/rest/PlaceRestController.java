package com.example.controllers.rest;

import com.example.graph.place.Place;
import com.example.graph_repositories.place.PlaceRepository;
import com.example.neo_services.place.PlaceService;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.validators.PhotoValidator;
import com.example.validators.PlaceFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@RestController
@RequestMapping("/place")
public class PlaceRestController {

    PlaceRepository placeRepository;
    PlaceService placeService;
    PlaceFormValidator validator;
    PhotoValidator photoValidator;

    @Autowired
    public PlaceRestController(PlaceRepository placeRepository,
                               PlaceService placeService,
                               PlaceFormValidator validator) {
        this.placeRepository = placeRepository;
        this.placeService = placeService;
        this.validator = validator;
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Place> createPlace(@RequestBody PlaceDTO place,
                                             BindingResult errors) {
        validator.validate(place, errors);
        if (!errors.hasErrors()) {
            return ResponseEntity.ok()
                    .eTag(place.getName())
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                    .body(placeService.newPlace(place));
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Place> getPlace(@PathVariable("name") String name) {
        return new ResponseEntity<>(placeRepository.findByName(name), HttpStatus.OK);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePlace(@PathVariable("name") String name) {
        Place place = placeRepository.findByName(name);
        placeService.deletePlace(place);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{placeName}/photo", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addPlacePhoto(@PathVariable("placeName") String placeName,
                                              MultipartFile file,
                                              UriComponentsBuilder builder,
                                              BindingResult result) {
        if (!file.isEmpty()) {
            CommonsMultipartFile photo = (CommonsMultipartFile) file;
            PhotoDTO photoDTO = new PhotoDTO(photo);
            photoValidator.validate(photoDTO, result);
            if (!result.hasErrors()) {
                placeService.addPhoto(photoDTO, placeName);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/{placeName}/photo/{name}").buildAndExpand(placeName, "main").toUri());
                return new ResponseEntity<>(headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
