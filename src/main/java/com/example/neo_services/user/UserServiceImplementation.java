package com.example.neo_services.user;


import com.example.graph.PlaceUserOrder;
import com.example.graph.comments.OrderComment;
import com.example.graph.place.Place;
import com.example.graph.user.PlaceUser;
import com.example.graph.user.PlaceUserVerificationToken;
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
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
                                     ImageService imageService,
                                     MailService mailService) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.orderRepository = orderRepository;
        this.orderCommentRepository = orderCommentRepository;
        this.imageService = imageService;
        this.mailService = mailService;
    }

    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private OrderRepository orderRepository;
    private OrderCommentRepository orderCommentRepository;
    private ImageService imageService;
    private MailService mailService;

    @Autowired
    GraphDatabase db;

    @Override
    public PlaceUser placeUser() {
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
    public PlaceUser newUser(UserDTO userDTO) {
        PlaceUser user = new PlaceUser(userDTO);
        try (Transaction tx = db.beginTx()) {
            userRepository.save(user);
            tx.success();
        }
        mailService.confirmEmailMessage(user);
        PlaceUserVerificationToken token = new PlaceUserVerificationToken();
        user.setToken(token);
        return user;
    }

    @Override
    public PlaceUser placeUser(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public void savePhoto(String userName, PhotoDTO photoDTO) {
        try (Transaction tx = db.beginTx()) {
            PlaceUser user = userRepository.findByUserName(userName);
            imageService.savePlaceUserPhoto(photoDTO, user);
        } catch (IOException | BadHttpRequest e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean confirmToken(String name, String token) {
        PlaceUser user = userRepository.findByUserName(name);
        return user.getToken().getToken().equals(token);
    }

    @Override
    public boolean checkCredetials(String userEmail, String userName) {
        return userRepository.findByNameOrEmail(userName, userEmail) != 0;
    }

}
