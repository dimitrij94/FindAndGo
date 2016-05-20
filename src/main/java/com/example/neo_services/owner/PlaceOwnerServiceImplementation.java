package com.example.neo_services.owner;

import com.example.graph.owner.PlaceOwner;
import com.example.graph.verification_tokens.PlaceOwnerVerificationToken;
import com.example.graph_repositories.owner.PlaceOwnerRepository;
import com.example.graph_repositories.owner.PlaceOwnerVerificationTokenRepository;
import com.example.neo_services.authentication.CustomUserDetails;
import com.example.neo_services.mail.MailService;
import com.example.pojo.dto.OwnerDTO;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Dmitrij on 24.02.2016.
 */
@Service
public class PlaceOwnerServiceImplementation implements PlaceOwnerService {

    private PlaceOwnerRepository ownerRepository;
    private PasswordEncoder passwordEncoder;
    private GraphDatabase db;
    private PlaceOwnerVerificationTokenRepository ownerVerificationTokenRepository;
    private MailService mailService;

    @Autowired
    public PlaceOwnerServiceImplementation(PlaceOwnerRepository ownerRepository,
                                           PasswordEncoder passwordEncoder,
                                           GraphDatabase db,
                                           PlaceOwnerVerificationTokenRepository ownerVerificationTokenRepository,
                                           MailService mailService) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.db = db;
        this.ownerVerificationTokenRepository = ownerVerificationTokenRepository;
        this.mailService = mailService;
    }

    @Override
    public CustomUserDetails placeOwnerDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) return null;
        return (CustomUserDetails) auth.getDetails();
    }

    @Override
    public PlaceOwner placeOwner() {
        return ownerRepository.findByUserName(placeOwnerDetails().getUsername());
    }

    @Override
    public boolean validateOwnerEmail(String email) {
        return ownerRepository.findByEmail(email) == null;
    }

    @Override
    public boolean validateOwnerName(String name) {
        return ownerRepository.findByUserName(name) == null;
    }

    @Override
    public PlaceOwner newOwner(OwnerDTO ownerDTO) {
        PlaceOwner owner = new PlaceOwner(ownerDTO);
        String tokenValue = UUID.randomUUID().toString();
        PlaceOwnerVerificationToken verificationToken = new PlaceOwnerVerificationToken(tokenValue);
        owner.setPassword(passwordEncoder.encode(ownerDTO.getPassword()));

        try (Transaction tx = db.beginTx()) {
            owner.setVerificationToken(verificationToken);
            ownerRepository.save(owner);
            tx.success();
        }

        mailService.confirmEmailMessage(owner, tokenValue);
        return owner;
    }

    @Override
    public void deleteOwner() {
        try (Transaction tx = db.beginTx()) {
            PlaceOwner owner = ownerRepository.findByUserName(placeOwnerDetails().getUsername());
            ownerRepository.delete(owner);
            tx.success();
        }
    }
}
