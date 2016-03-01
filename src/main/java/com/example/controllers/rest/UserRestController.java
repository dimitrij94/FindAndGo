package com.example.controllers.rest;

import com.example.graph.user.PlaceUser;
import com.example.json_views.UserJsonView;
import com.example.neo_services.user.UserService;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;
import com.example.validators.PhotoValidator;
import com.example.validators.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private UserValidator userValidator;
    private UserService userService;
    private PhotoValidator photoValidator;

    @Autowired
    public UserRestController(UserValidator userValidator,
                              UserService userService,
                              PhotoValidator photoValidator) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.photoValidator = photoValidator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @JsonView(UserJsonView.class)
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<PlaceUser> getUserById(@PathVariable("name") String name) {
        return new ResponseEntity<>(userService.placeUser(name), HttpStatus.OK);
    }

    @JsonView(UserJsonView.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PlaceUser> newUser(UserDTO userDTO,
                                             BindingResult errors) throws IOException {
        userValidator.validate(userDTO, errors);
        if (!errors.hasErrors()) {
            PlaceUser user = userService.newUser(userDTO);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/{name}/token", method = RequestMethod.POST)
    public ResponseEntity<Void> confirmUserEmailToken(@PathVariable("name") String name,
                                                     @RequestBody String token){
        boolean confirmed = userService.confirmToken(name, token);
        if(confirmed) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}/image", method = RequestMethod.POST)
    public ResponseEntity<Void> addPhoto(@PathVariable("name") String userName,
                                         @RequestBody CommonsMultipartFile file,
                                         BindingResult result) {
        PhotoDTO photoDTO = new PhotoDTO(file);
        photoValidator.validate(photoDTO, result);
        if (!result.hasErrors()) {
            userService.savePhoto(userName, photoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
