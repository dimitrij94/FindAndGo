package com.example.controllers.rest;

import com.example.dao.user.UserDaoI;
import com.example.pojo.dto.UserCreateForm;
import com.example.services.registration.RegistrationService;
import com.example.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    RegistrationService registrationService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserCreateForm> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(new UserCreateForm(dao.getUserById(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity newUser(HttpServletRequest request,
                                  RequestEntity<UserCreateForm> userDTO,
                                  BindingResult result) {
        UserCreateForm user = userDTO.getBody();
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        registrationService.register(user, request);
        return new ResponseEntity(HttpStatus.OK);
    }
}
