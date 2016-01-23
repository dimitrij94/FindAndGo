package com.example.services.userservice;

import com.example.constants.image.ImageContainerType;
import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.dao.menu.MenuDAO;
import com.example.dao.order.OrderDAO;
import com.example.dao.place.PlaceDAO;
import com.example.dao.user.UserDaoI;
import com.example.domain.UserOrders;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.PlaceUserPhoto;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import com.example.services.imageservice.ImageService;
import com.example.services.mail.MailService;
import com.example.services.registration.RegistrationService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrij on 21.10.2015.
 */
@Service

public class UserServiceImpl implements UserService {

    @Autowired
    UserDaoI userDao;

    @Autowired
    AuthorizationDAO aDao;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    PlaceDAO placeDAO;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    ImageService imageService;

    @Autowired
    MailService mailService;

    @Autowired
    MenuDAO menuDAO;

    @Override
    public PlaceUser placeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken))
            return aDao.getUserByEmail(authentication.getName());
        else return null;
    }


    @Override
    public PlaceOwner placeOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken))
            return aDao.getOwnerByEmail(authentication.getName());
        else return null;
    }

    @Override
    public boolean validate(UserDTO user) {
        if (user.getUserEmail() == null || user.getUserEmail().equals(""))
            return false;
        if (user.getUserName() == null || user.getUserName().equals(""))
            return false;

        if (user.getUserPass() == null || user.getUserPass().equals(""))
            return false;


        return true;
    }

    @Override
    public ResponseEntity<Void> newUser(UserDTO userDTO, HttpServletRequest request, UriComponentsBuilder ucBuilder) {
        if (validate(userDTO)) {
            HttpStatus status = HttpStatus.CREATED;
            PlaceUser user = registrationService.register(userDTO, request);

            CommonsMultipartFile image = userDTO.getImage();
            if (image != null && !image.isEmpty()) {
                status = addPhoto(image, user);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(headers, status);

        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @Override
    public PlaceUserPhoto getUserPhotoByName(String name, long id) {
        return null;
    }

    private HttpStatus addPhoto(CommonsMultipartFile image, PlaceUser user) {
        if (imageService.validate(image)) {
            try {
                imageService.upload(new PhotoDTO(image), user, ImageContainerType.PLACE_USER);
                return HttpStatus.CREATED;
            } catch (BadHttpRequest badHttpRequest) {
                badHttpRequest.printStackTrace();
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream().parallel().anyMatch(c -> c.getAuthority().equals(role));
    }


    @Override
    public List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user) {
        long id = user.getId();
        List<UserPlaceOrdersDTO> placeOrders = new ArrayList<>();
        for (Place p : orderDAO.getPlacesWithUserOrder(user)) {
            UserPlaceOrdersDTO order = new UserPlaceOrdersDTO();
            order.setPlace(p);
            order.setUserUserOrderses(orderDAO.getUserPlaceOrders(id, p.getId()));
            placeOrders.add(order);
        }
        return placeOrders;
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public PlaceUser register(UserDTO blank, HttpServletRequest request) {
        PlaceUser user = new PlaceUser();

        user.setEmail(blank.getUserEmail());
        user.setUserName(blank.getUserName());
        user.setPassword(blank.getUserPass());
        user.setEnabled(false);

        user = userDao.registration(user);
        registrationService.saveRegistrationToken(user);
        mailService.confirmEmailMessage(user, request);
        return user;
    }

    @Override
    public int newUserLikes(long id) {
        PlaceUser user = placeUser();
        Place p = placeDAO.getPlaceById(id);
        if (!(userDao.isPlaceUser(p.getId(), user.getId()) > 0)) placeDAO.newPlaceLike(user, p);
        else placeDAO.removePlaceLike(user, id);
        return p.getPlaceUsers().size();
    }

    @Override
    public void newUserComment(String comment, int rating, long id) {
        if (rating > 5 || rating < 0 || comment.length() > 250)
            throw new IllegalArgumentException("Bad request");

        PlaceUser user = placeUser();
        UserOrders order = orderDAO.getOrder(id);
        PlaceMenu placeMenu = order.getMenu();
        if (!(menuDAO.countUserMenuRatings(placeMenu.getId(), user.getId()) > 0)) {
            menuDAO.newPlaceMenuRating(comment, rating, user, placeMenu);
        } else {
            menuDAO.updateMenuUserMenuRating(comment, rating, user, placeMenu);
        }
        orderDAO.setOrderComplete(order, true);
        new Thread(() -> {
            menuDAO.updateMenuFinalRating(placeMenu, menuDAO.getMenuFinalRating(placeMenu));
            placeDAO.getPlaceFinalRating(placeMenu.getMenuPlace());
        }).start();
    }
}
