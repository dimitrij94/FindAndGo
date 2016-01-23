package com.example.controllers.rest;

import com.example.dao.user.UserDaoI;
import com.example.pojo.dto.UserDTO;
import com.example.services.registration.RegistrationService;
import com.example.services.userservice.UserService;
import com.example.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by Dmitrij on 22.12.2015.
 */
@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    UserDaoI dao;

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserService service;

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(new UserDTO(dao.getUserById(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> newUser(@RequestParam("userEmail") String email,
                                        @RequestParam("userName") String name,
                                        @RequestParam("userPass") String pass,
                                        @RequestParam(required = false, value = "file") MultipartFile image,
                                        UriComponentsBuilder ucBuilder,
                                        HttpServletRequest request) throws IOException {

        UserDTO user = new UserDTO(email, name, pass, (CommonsMultipartFile) image);
        return service.newUser(user, request, ucBuilder);
    }


    @RequestMapping(value = "/user/{id}/photos/{name}", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getUserPhoto(@PathVariable("id") long id,
                               @PathVariable("name") String name) {
        return service.getUserPhotoByName(name, id).getBody();
    }

    @RequestMapping("/test")
    public UserDTO getUser() {
        return new UserDTO("myEmai@mail.com", "myName", "myPass", null);
    }
}
