package com.example.services.owner;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.dao.owner.OwnerDAO;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.registration.OwnerVerificationToken;
import com.example.pojo.dto.OwnerDTO;
import com.example.services.MyExecutorService;
import com.example.services.authentication.owner.CustomOwnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@Component
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    OwnerDAO dao;

    @Autowired
    AuthorizationDAO authorizationDAO;

    @Autowired
    MyExecutorService executorService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean validateOwnerName(String name) {
        return dao.countOwnersWithName(name) == 0;
    }

    @Override
    public boolean validateOwnerEmail(String email) {
        return dao.countOwnersWithEmail(email) == 0;
    }

    @Override
    public CustomOwnerDetails getOwner() {
        return (CustomOwnerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public PlaceOwner registerNewOwner(OwnerDTO ownerDTO) {
        ownerDTO.setPassword(passwordEncoder.encode(ownerDTO.getPassword()));
        PlaceOwner owner = new PlaceOwner(ownerDTO);
        owner = dao.addNewOwner(owner);
        saveRegistrationToken(owner);
        return owner;
    }

    @Override
    public void deleteOwner() {
        dao.deleteOwner(dao.getOwnerById(getOwner().getId()));
    }

    @Override
    public void updateOwner(OwnerDTO newOwner) {
        PlaceOwner owner = dao.getOwnerById(getOwner().getId());
        owner = owner.updateValues(newOwner);
        dao.updateOwner(owner);
    }


    private void resendRegistrationToken(String email, PlaceOwner owner) {
        authorizationDAO.deleteOwnerToken(owner.getToken());
        saveRegistrationToken(owner);
    }

    private void saveRegistrationToken(PlaceOwner owner) {
        executorService.getExecutor().execute(() -> {
            String token = UUID.randomUUID().toString();
            OwnerVerificationToken tokens = new OwnerVerificationToken();
            tokens.setToken(token);
            authorizationDAO.newOwnerAuthorizationToken(tokens, owner);
        });
    }


    private boolean confirmToken(String token) {
        OwnerVerificationToken tokens = authorizationDAO.findOwnerToken(token);
        if (tokens != null) {
            if (tokens.getDate().getTime() > Calendar.getInstance().getTime().getTime()) {
                PlaceOwner owner = tokens.getOwner();
                owner.setEnabled(true);
                authorizationDAO.deleteOwnerToken(owner.getToken());
                return true;
            }
        }
        return false;
    }


}
