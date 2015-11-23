package com.example;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
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
import org.springframework.beans.factory.annotation.Qualifier;
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
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class DemoController {


    @Autowired
    @Qualifier("dao")
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

    /*
        @Autowired
        UserValidator userValidator;

        @InitBinder
        protected void initBinder(WebDataBinder binder){
            binder.addValidators(userValidator);
        }
    */
    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping("/page/login")
    public String login() {
        return "login";
    }


    @RequestMapping({"/", "/home"})
    public String getIndexPage(Model model) {
        model.addAttribute("places", dao.getMainPlaces());
        PlaceUser user = userService.placeUser();
        if (user != null) {
            model.addAttribute(user);
        }
        return "index";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView goToRegistration() {
        return new ModelAndView("registration", "user", new UserCreateForm());
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String newOwner(@Valid UserCreateForm userForm,
                           HttpServletRequest request,
                           Model model,
                           BindingResult result) {
        if (!result.hasErrors()) {
            PlaceUser user = registrationService.register(userForm, request);
            if (user.getId() != null) {
                model.addAttribute("user", user);
                return "redirect:/confirm";
            }
        }
        return "redirect:/registration";
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
    public String RegisterNewPlace(@Valid PlaceDTO placeDTO,
                                   Model model,
                                   BindingResult result) throws IOException {
        if (!result.hasErrors()) {
            PlaceUser user = userService.placeUser();
            Place newPlace = placeService.registerNewPlace(placeDTO, user);
            model.addAttribute("place", newPlace);
            return "redirect:/place/" + newPlace.getId();
        }
        return "redirect:/newplace";
    }

    @RequestMapping(value = "/place/{id}", method = RequestMethod.GET)
    public String getPlacePage(@PathVariable("id") String placeId,
                               Model model) {
        PlaceUser user = userService.placeUser();
        Place p = dao.getPlaceById(Long.valueOf(placeId));
        model.addAttribute(p);
        if (user != null) {
            model.addAttribute("user", user);
            if (p.getPlaceOwner().getId().equals(user.getId())) {
                model.addAttribute("isOwner", true);
                model.addAttribute("menu", new MenuDTO());
                model.addAttribute("used", false);
                model.addAttribute("liked",true);
                if (p.getPlaceMenu() != null)
                    model.addAttribute("service", new ServiceDTO());
            } else if(userService.isUser(user)){
                model.addAttribute("liked", (!(dao.isUserLikedPlace(user, p) > 0)) ? 0 : 1);
                model.addAttribute("isOwner", false);
                model.addAttribute("used", dao.isUserUsedPlace(p, user));
            }
        }
        return "place";
    }

    @RequestMapping(value = "rating/place/{pId}")
    public
    @ResponseBody
    int ratePlace(@PathVariable("pId") long pId,
                  @RequestParam("rating") int rating) {
        PlaceUser user = userService.placeUser();
        return placeService.newPlaceRating(rating, pId, user);
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
        return dao.getPlaceMainImage(id);
    }

    @ResponseBody
    @RequestMapping(value = "/photo/place/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceSmallPhoto(@PathVariable("id") long id) {
        return dao.getPlaceSmallImage(id);
    }


    @ResponseBody
    @RequestMapping(value = "/photo/menu/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getMenuPhoto(@PathVariable("id") long id) {
        return dao.getMenuSmallImage(id);
    }

    @RequestMapping(value = "/place/{id}/menu", method = RequestMethod.POST)
    public String RegisterNewMenu(@Valid MenuDTO menuDTO,
                                  @PathVariable("id") long id,
                                  BindingResult result) throws IOException {
        if (!result.hasErrors()) {
            PlaceUser user = userService.placeUser();

            Place place = userService.findPlaceByOwnerId(user, id);
            if (place != null) placeService.registerNewPlaceMenu(place, menuDTO);
            else return "redirect:/page/login";
        }
        return "redirect:/place/" + id;

    }

    @RequestMapping(value = "/place/{placeId}/menu/service", method = RequestMethod.POST)
    public String registerNewPlaceMenuService(@Valid ServiceDTO serviceDTO,
                                              @PathVariable("placeId") long placeId,
                                              BindingResult result) {
        if (!result.hasErrors()) {
            PlaceUser user = userService.placeUser();
            Place place = dao.getOwnerPlace(placeId, user);

            if (place != null) {
                PlaceMenu menu = dao.getMenuById(serviceDTO.getMenuId());
                if (placeService.isMenuFromPlace(menu, place)) {
                    placeService.registerNewPlaceMenuService(menu, serviceDTO);
                }
                return "redirect:/place/" + placeId;
            }
        }
        return "redirect:/place/" + placeId;
    }

    @RequestMapping(value = "/place/{placeId}/menu/{menuId}", method = RequestMethod.POST)
    public String createOrder(@PathVariable("placeId") long placeId,
                              @PathVariable("menuId") long menuId,
                              @RequestParam(value = "services", required = false) List<Long> services) {
        PlaceUser user = userService.placeUser();
        dao.newOrder(user, dao.getPlaceById(placeId), dao.getMenuById(menuId), services);
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
        model.addAttribute("places", userService.placeUser().getOwnerPlaces());
        return "places-list";
    }

}


