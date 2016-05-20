package com.example.neo_services.user;


import com.example.graph.PlaceUserOrder;
import com.example.graph.comments.OrderComment;
import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.place.Place;
import com.example.graph.user.PlaceUser;
import com.example.graph.verification_tokens.PlaceUserVerificationToken;
import com.example.graph_repositories.order.OrderCommentRepository;
import com.example.graph_repositories.order.OrderRepository;
import com.example.graph_repositories.place.PlaceRepository;
import com.example.graph_repositories.user.UserRepository;
import com.example.neo_services.authentication.CustomUserDetails;
import com.example.neo_services.image.ImageService;
import com.example.neo_services.mail.MailService;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;
import javassist.tools.web.BadHttpRequest;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Dmitrij on 21.02.2016.
 */

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,
                                     PlaceRepository placeRepository,
                                     OrderRepository orderRepository,
                                     OrderCommentRepository orderCommentRepository,
                                     @Qualifier("") ImageService<PlaceUserPhoto, PlaceUser> imageService,
                                     MailService mailService,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.orderRepository = orderRepository;
        this.orderCommentRepository = orderCommentRepository;
        this.imageService = imageService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private OrderRepository orderRepository;
    private OrderCommentRepository orderCommentRepository;
    private ImageService imageService;
    private MailService mailService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    GraphDatabase db;

    @Override
    public PlaceUser find() {
        return userRepository.findByEmail(user().getEmail());
    }

    public CustomUserDetails user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken)
            return null;
        return (CustomUserDetails) auth.getPrincipal();
    }

    @Override
    public void addMembership(String name) {
        Place place = placeRepository.findByName(name);
        try (Transaction tx = db.beginTx()) {
            userRepository.addMembership(place.getUserName(), user().getUsername());
            tx.success();
        }
    }

    @Override
    public void addMembership(Place place, PlaceUser user) {
        try (Transaction tx = db.beginTx()) {
            user.addMemebership(place);
            userRepository.save(user);
            tx.success();
        }
    }

    @Override
    public void newUserComment(String comment, int rating, PlaceUserOrder order) {
        try (Transaction tx = db.beginTx()) {
            OrderComment userComment = new OrderComment(comment, rating);
            orderCommentRepository.save(userComment);
            order.addComment(userComment);
            orderRepository.save(order);
            tx.success();
        }
    }


    @Override
    public PlaceUser create(UserDTO userDTO) {
        PlaceUser user = new PlaceUser(userDTO);
        PlaceUserVerificationToken token = new PlaceUserVerificationToken();
        user.setPassword(passwordEncoder.encode(userDTO.getUserPass()));

        try (Transaction tx = db.beginTx()) {
            user.setToken(token);
            token.setUser(user);

            user = userRepository.save(user);
            tx.success();
        }

        mailService.confirmEmailMessage(user, token.getToken());
        return user;
    }

    @Override
    public PlaceUser find(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public void savePhoto(String userName, PhotoDTO photoDTO) {
        try (Transaction tx = db.beginTx()) {
            PlaceUser user = userRepository.findByUserName(userName);
            imageService.savePhoto(photoDTO, user);
        } catch (IOException | BadHttpRequest e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean confirmToken(String name, String tokenValue) {
        PlaceUserVerificationToken token = userRepository.findByUserName(name).getToken();
        return token.getToken().equals(tokenValue) && token.getExpiresAt() >= new Date().getTime();
    }

    @Override
    public boolean isUsersWithThisCredentialsExist(String userEmail, String userName) {
        PlaceUser result = userRepository.findByUserName(userName);
        return result!=null;
    }


}
