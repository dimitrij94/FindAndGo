package com.example;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.exceptions.IllegalRequestEnctype;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.pojo.dto.UserCreateForm;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.dao.IDBBean;
import com.example.services.imageservice.ImageService;
import com.example.services.placeservice.PlaceService;
import com.example.services.placeservice.menu.MenuService;
import com.example.services.registration.RegistrationService;
import com.example.services.userservice.UserService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitrij on 08.10.2015.
 */
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

    Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("page/login")
    public String login(Model model) {
        return "login";
    }


    @RequestMapping({"/", "/home"})
    public String getIndexPage(Model model) {
        model.addAttribute(userService.placeUser());
        model.addAttribute("events", dao.getMainEvents());
        model.addAttribute("places", dao.getMainPlaces());
        return "index";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView goToRegistration() {
        return new ModelAndView("registration", "user", new UserCreateForm());
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String newOwner(@Valid @ModelAttribute UserCreateForm userForm,
                           HttpServletRequest request,
                           Model model,
                           BindingResult result) {
        if (!result.hasErrors()) {
            PlaceUser user = registrationService.register(userForm, request);

            if (user.getId() != null) {
                model.addAttribute(user);
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

    @RequestMapping(value = "resend", method = RequestMethod.GET)
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
    public String RegisterNewPlace(Authentication authentication,
                                   @Valid @ModelAttribute PlaceDTO placeDTO,
                                   Model model,
                                   BindingResult result) throws IOException {
        PlaceUser user = userService.placeUser();
        if (!result.hasErrors()) {
            Place newPlace = placeService.registerNewPlace(placeDTO, user);
            model.addAttribute("place", newPlace);
            return "redirect:/place/" + newPlace.getId();
        }
        return "redirect:/newplace";
    }

    @RequestMapping(value = "/place/{id}", method = RequestMethod.GET)
    public String getPlacePage(@PathVariable("id") String id,
                               Authentication authentication,
                               Model model) {
        PlaceUser user = userService.placeUser();
        long placeId = Long.valueOf(id);
        Place p = dao.getPlaceById(placeId);
        ;
        if (user != null) {
            model.addAttribute("user", user);
            if (user.getOwnerPlaces().contains(p)) {
                model.addAttribute("isOwner", true);
                model.addAttribute("menu", new MenuDTO());
                if (p.getPlaceMenu() != null)
                    model.addAttribute("service", new ServiceDTO());
            } else
                model.addAttribute("isOwner", false);
        }
        model.addAttribute(p);
        return "place-profile";
    }

    @ResponseBody
    @RequestMapping(value = "photo/place/{id}/main", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceMainPhoto(@PathVariable("id") long id) {
        return dao.getPlaceMainImage(id);
    }

    @ResponseBody
    @RequestMapping(value = "photo/place/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceSmallPhoto(@PathVariable("id") long id) {
        return dao.getPlaceSmallImage(id);
    }


    @ResponseBody
    @RequestMapping(value = "photo/menu/{id}/small", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getMenuPhoto(@PathVariable("id") long id) {
        return dao.getMenuSmallImage(id);
    }

    @RequestMapping(value = "place/{id}/menu/", method = RequestMethod.POST)
    public String RegisterNewMenu(@Valid MenuDTO menuDTO,
                                  @PathVariable("id") long id) throws IOException {

        PlaceUser user = userService.placeUser();

        Place place = userService.findPlaceByOwnerId(user, id);
        if (place != null) placeService.registerNewPlaceMenu(place, menuDTO);
        else return "redirect:/page/login";
        return "redirect:/place/" + id;
    }

    @RequestMapping(value = "place/{placeId}/menu/service", method = RequestMethod.POST)
    public String RegisterNewPlaceMenuService(@Valid ServiceDTO serviceDTO,
                                              @PathVariable("placeId") long placeId) {
        PlaceUser user = userService.placeUser();
        Place place = placeService.getOwnerPlace(placeId, user);
        if (place != null) {
            PlaceMenu menu = dao.getMenuById(serviceDTO.getMenuId());
            if (place.getPlaceMenu().contains(menu)) {
                placeService.registerNewPlaceMenuService(menu, serviceDTO);
            }
        }
        return "redirect:place/"+placeId;
    }

    @RequestMapping(value = "place/{placeId}/menu/{menuId}", method = RequestMethod.POST)
    public String createOrder(@PathVariable("placeId")long placeId,
                              @PathVariable("menuId")long menuId,
                              @RequestParam("services")List<Long> services){
        PlaceUser user = userService.placeUser();
        placeService.sendNewOrder(user,placeId,menuId,services);
        return "";
    }
}


