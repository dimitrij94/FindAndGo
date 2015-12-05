package com.example;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.menu.PlaceMenu;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.pojo.dto.UserCreateForm;
import com.example.services.imageservice.ImageService;
import com.example.services.placeservice.PlaceService;
import com.example.services.placeservice.menu.MenuService;
import com.example.services.registration.RegistrationService;
import com.example.services.userservice.UserService;
import com.example.validators.PlaceMenuValidator;
import com.example.validators.PlaceRegistrationFormValidator;
import com.example.validators.ServiceValidator;
import com.example.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class DemoController {


    @Autowired
    IDBBean dao;

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    PlaceService placeService;

    @Autowired
    ImageService imageService;

    @Autowired
    MenuService menuService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    PlaceMenuValidator placeMenuValidator;

    @Autowired
    PlaceRegistrationFormValidator placeValidator;

    @Autowired
    ServiceValidator serviceValidator;

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping("/page/login")
    public String login() {
        return "login";
    }


    @RequestMapping({"/", "/home"})
    public String getIndexPage(Model model) {
        model.addAttribute("places", dao.getMainPlaces());
        return "index";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView goToRegistration() {
        return new ModelAndView("registration", "user", new UserCreateForm());
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String newOwner(UserCreateForm userDTO,
                           HttpServletRequest request,
                           Model model,
                           BindingResult result) {
        userValidator.validate(userDTO, result);
        if (result.hasErrors()) {
            return "redirect:/registration";
        }
        PlaceUser user = registrationService.register(userDTO, request);
        model.addAttribute("user", user);
        return "redirect:/confirm";
    }


    @RequestMapping(value = "/confirm")
    public String toConfirmationPage(@ModelAttribute("user") PlaceUser user, Model model) {
        String userEmail = user.getUserEmail();
        model.addAttribute("email", userEmail);
        return "/confirm_email_page";
    }

    @RequestMapping(value = "/resend", method = RequestMethod.GET)
    public String resendTokenPage() {
        return "resend";
    }

    @RequestMapping(value = "/resend", method = RequestMethod.POST)
    public String resendToken(@RequestParam String email, Model model) {
        model.addAttribute(registrationService.resendRegistrationToken(email));
        return "redirect:/confirm";
    }

    @RequestMapping(value = "/confirm/{token}")
    public String confirmEmail(@PathVariable("token") String token) {
        return registrationService.confirmToken(token) ?
                "redirect:/page/login" :
                "redirect:/error?error=Wrong verification number";
    }


    @RequestMapping(value = "/newplace", method = RequestMethod.GET)
    public String getNewPlacePage(Model model) {
        model.addAttribute("place", new PlaceDTO());
        return "newplace";
    }

    @RequestMapping(value = "/newplace", method = RequestMethod.POST)
    public String RegisterNewPlace(@ModelAttribute("place") PlaceDTO placeDTO,
                                   Model model,
                                   BindingResult result) throws IOException {
        placeValidator.validate(placeDTO, result);
        if (result.hasErrors()) {
            return "newplace";
        }
        PlaceOwner user = userService.placeOwner();
        Place newPlace = placeService.registerNewPlace(placeDTO, user);
        model.addAttribute("place", newPlace);
        return "redirect:/place/" + newPlace.getId();
    }

    @RequestMapping(value = "/place/{id}", method = RequestMethod.GET)
    public String getPlacePage(@PathVariable("id") String placeId,
                               Authentication authentication,
                               Model model) {

        Place p = dao.getPlaceById(Long.valueOf(placeId));
        if (p != null) model.addAttribute("place", p);
        else return "error";

        if (userService.isAuthenticated()) {
            if (userService.hasRole(authentication, "ROLE_OWNER")) {
                PlaceOwner owner = userService.placeOwner();
                if (p.getPlaceOwner().getId() == owner.getId()) {
                    model.addAttribute("menu", new MenuDTO());
                    model.addAttribute("service", new ServiceDTO());
                    return "place-owner-page";
                }
            } else if (userService.hasRole(authentication, "ROLE_USER")) {
                PlaceUser user = userService.placeUser();
                model.addAttribute("liked", (!(dao.isUserLikedPlace(user, p) > 0)) ? 0 : 1);
                model.addAttribute("user", user);
            }
        }
        return "place";
    }

    @RequestMapping(value = "/user/places")
    public String getUserPlaces(Model model) {
        PlaceUser user = userService.placeUser();
        model.addAttribute("places", user.getUserPlaces());
        model.addAttribute("user", user);
        return "places-list";
    }

    @RequestMapping(value = "/menu/{id}/comment", method = RequestMethod.POST)
    public void newMenuComment(
            @RequestParam("comment") String comment,
            @RequestParam("rating") int rating,
            @PathVariable("id") long id,
            HttpServletResponse response) {
        userService.newUserComment(comment, rating, id);
        response.setStatus(HttpStatus.OK.value());
    }

    @RequestMapping(value = "/place/{id}/liked/")
    public
    @ResponseBody
    int placeLiked(@PathVariable("id") long id) {
        return userService.newUserLikes(id);
    }

    @RequestMapping(value = "photo/user/{id}/small",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getUserPhoto(@PathVariable("id") long id,
                               HttpServletResponse respose) throws IOException {
        byte[] photo = dao.getUserPhoto(id, "small");
        if (photo == null)
            respose.sendRedirect("/static/images/user.png");
        else {
            return photo;
        }
        throw new IOException();
    }

    @ResponseBody
    @RequestMapping(value = "/photo/place/{id}/main", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceMainPhoto(@PathVariable("id") long id) {
        return dao.getPlaceImageByName(id, "main");
    }

    @ResponseBody
    @RequestMapping(value = "/photo/place/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceSmallPhoto(@PathVariable("id") long id) {
        return dao.getPlaceImageByName(id, "small");
    }

    @ResponseBody
    @RequestMapping(value = "/photo/menu/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getMenuPhoto(@PathVariable("id") long id) {
        return dao.getMenuImage(id, "small");
    }

    @RequestMapping(value = "/place/{id}/menu", method = RequestMethod.POST)
    public String RegisterNewMenu(@ModelAttribute("place") Place place,
                                  @ModelAttribute("menu")MenuDTO menuDTO,
                                  @ModelAttribute("service")ServiceDTO serviceDTO,
                                  BindingResult result) throws IOException {
        placeMenuValidator.validate(menuDTO, result);
        if (result.hasErrors()) {
            return "place-owner-page";
        }
        PlaceUser user = userService.placeUser();
        if (place.getPlaceOwner().getId() == user.getId()) placeService.registerNewPlaceMenu(place, menuDTO);
        else return "redirect:/page/login";
        return "redirect:/place/" + place.getId();
    }

    @RequestMapping(value = "/place/{placeId}/menu/service", method = RequestMethod.POST)
    public String registerNewPlaceMenuService(@PathVariable("placeId") long placeId,
                                              @ModelAttribute("place")Place place,
                                              @ModelAttribute("menu")MenuDTO menuDTO,
                                              @ModelAttribute("service")ServiceDTO serviceDTO,
                                              BindingResult result) {
        serviceValidator.validate(serviceDTO, result);
        if (result.hasErrors()) {
            return "redirect:/place/" + placeId;
        }
        PlaceOwner owner = userService.placeOwner();
        if (place != null) {
            PlaceMenu menu = dao.getMenuById(serviceDTO.getMenuId());
            if (placeService.isMenuFromPlace(menu, place)) {
                placeService.registerNewPlaceMenuService(menu, serviceDTO);
            }
            return "redirect:/place/" + placeId;
        }
        return "redirect:/place/" + placeId;
    }

    @RequestMapping(value = "/place/{placeId}/menu/{menuId}", method = RequestMethod.POST)
    public String createOrder(@PathVariable("placeId") long placeId,
                              @PathVariable("menuId") long menuId,
                              @RequestParam(value = "services", required = false) List<Long> services) {
        PlaceUser user = userService.placeUser();
        dao.newOrder(user, dao.getPlaceById(placeId), dao.getMenuById(menuId), services, );
        return "redirect:/place/" + placeId;
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public String toUserProfile(Model model) {
        PlaceUser user = userService.placeUser();
        model.addAttribute("ordersMap", userService.getOrderedServices(user));
        model.addAttribute("user", user);
        return "user_profile";
    }

    @RequestMapping(value = "owner/places")
    public String getOwnerPlaces(Model model) {
        model.addAttribute("places", userService.placeOwner().getPlaces());
        return "places-list";
    }

}


