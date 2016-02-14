package com.example;

import com.example.dao.employee.EmployeeDAO;
import com.example.dao.menu.MenuDAO;
import com.example.dao.menu.menu_optional_services.ServiceDAO;
import com.example.dao.menu.menu_tag_categories.TagDAO;
import com.example.dao.order.OrderDAO;
import com.example.dao.photos.PhotoDAO;
import com.example.dao.place.PlaceDAO;
import com.example.dao.user.UserDaoI;
import com.example.domain.UserOrders;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.ServiceDTO;
import com.example.pojo.dto.UserDTO;
import com.example.services.employee.EmployeeService;
import com.example.services.imageservice.ImageService;
import com.example.services.menu.MenuService;
import com.example.services.placeservice.PlaceService;
import com.example.services.registration.RegistrationService;
import com.example.services.userservice.UserService;
import com.example.validators.PlaceFormValidator;
import com.example.validators.PlaceMenuValidator;
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
import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes(types = {PlaceUser.class})
public class DemoController {


    @Autowired
    PlaceDAO dao;

    @Autowired
    UserDaoI userDaoI;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    MenuDAO menuDAO;
    @Autowired
    OrderDAO ordersDAO;
    @Autowired
    ServiceDAO serviceDAO;
    @Autowired
    PhotoDAO photoDAO;

    @Autowired
    TagDAO tagDAO;

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
    PlaceFormValidator placeValidator;

    @Autowired
    ServiceValidator serviceValidator;

    @Autowired
    EmployeeService employeeService;

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping("/page/login")
    public String login() {
        return "login";
    }


    @RequestMapping({"/home"})
    public String getIndexPage(@RequestParam(required = false, defaultValue = "1") int page,
                               Model model) {
        model.addAttribute("places", dao.getMainPlaces());
        model.addAttribute("manus", menuDAO.getBestMenus(page * 20 - 20, page * 20));
        return "index";
    }

    @RequestMapping(value = "/find")
    public
    @ResponseBody
    List<String> autocompliteTags(@RequestParam("tags") String query) {
        List<String> response = new LinkedList<>();
        //noinspection Convert2streamapi
        for (PlaceMenuTags tag : tagDAO.findTagsByName(query))
            response.add(tag.getName());
        return response;
    }

    @RequestMapping("/menu")
    public String findMenuWithTags(@RequestParam("tagsId") List<Long> tagsId,
                                   @RequestParam(required = false, value = "num", defaultValue = "1") int num,
                                   Model model) {
        model.addAttribute(tagDAO.findMenuWithTag(tagsId, num * 20 - 20, num * 20));
        return "menu_list";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView goToRegistration() {
        return new ModelAndView("registration", "user", new UserDTO());
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String newOwner(UserDTO userDTO,
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
        String userEmail = user.getEmail();
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
        byte[] photo = photoDAO.getUserPhotoBodyByName("small", id);
        if (photo == null)
            respose.sendRedirect("/static/images/user.png");
        else {
            return photo;
        }
        throw new IOException();
    }

    @ResponseBody
    @RequestMapping(value = "/photo/place/{id}/{name}", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPlaceMainPhoto(@PathVariable("id") long id, @PathVariable("name") String name) {
        return photoDAO.getPlaceImageBodyByName(id, name);
    }

    @ResponseBody
    @RequestMapping(value = "/photo/menu/{id}/{name}", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getMenuPhoto(@PathVariable("id") long id,
                               @PathVariable("name") String name) {
        return photoDAO.getMenuImageBodyByName(id, name);
    }


    @RequestMapping(value = "/place/{id}/menu", method = RequestMethod.POST)
    public String RegisterNewMenu(@ModelAttribute("place") Place place,
                                  @ModelAttribute("menu") MenuDTO menuDTO,
                                  @ModelAttribute("service") ServiceDTO serviceDTO,
                                  @RequestParam("tags") List<String> idS,
                                  BindingResult result) throws IOException {
        placeMenuValidator.validate(menuDTO, result);
        if (result.hasErrors()) {
            return "place-owner-page";
        }
        PlaceUser user = userService.placeUser();
        if (place.getPlaceOwner().getId() == user.getId()) menuService.registerNewPlaceMenu(place, menuDTO, idS);
        else return "redirect:/page/login";
        return "redirect:/place/" + place.getId();
    }

    @RequestMapping(value = "/place/{placeId}/menu/service", method = RequestMethod.POST)
    public String registerNewPlaceMenuService(@PathVariable("placeId") long placeId,
                                              @ModelAttribute("place") Place place,
                                              @ModelAttribute("menu") MenuDTO menuDTO,
                                              @ModelAttribute("service") ServiceDTO serviceDTO,
                                              BindingResult result) {
        serviceValidator.validate(serviceDTO, result);
        if (result.hasErrors()) {
            return "redirect:/place/" + placeId;
        }
        PlaceOwner owner = userService.placeOwner();
        if (place != null) {
            PlaceMenu menu = menuDAO.getMenuById(serviceDTO.getMenuId());
            if (menuDAO.isMenuFromPlace(menu, place)) {
                menuService.registerNewPlaceMenuService(menu, serviceDTO);
            }
            return "redirect:/place/" + placeId;
        }
        return "redirect:/place/" + placeId;
    }

    @RequestMapping(value = "/order/{id}/activate/", method = RequestMethod.PUT)
    public
    @ResponseBody
    void activateOrder(@PathVariable("id") long id, HttpServletResponse response,
                       @ModelAttribute PlaceUser user) {
        UserOrders orders = ordersDAO.findOrderById(id, user);
        if (orders != null) {
            ordersDAO.activateOrder(orders);
            response.setStatus(HttpStatus.OK.value());
        } else response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    /* @RequestMapping(value = "/place/{placeId}/menu/{menuId}", method = RequestMethod.POST)
     public
     @ResponseBody
     List<EmployeeTimePeriod> createOrder(@PathVariable("placeId") long placeId,
                                          @PathVariable("menuId") long menuId,
                                          @RequestParam(value = "services", required = false) List<Long> services,
                                          @RequestParam("employeeId") long employeeID) {
         PlaceUser user = userService.placeUser();
         UserOrders orders = dao.newOrder(user, dao.getPlaceById(placeId), dao.getMenuById(menuId), services, dao.getEmployeeById(employeeID));
         return employeeService.findEmployeeFreeTime(orders);
     }
 */
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


