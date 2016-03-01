package com.example.neo_services.place;

import com.example.graph.PlaceUserOrder;
import com.example.graph.employee.PlaceEmployee;
import com.example.graph.owner.PlaceOwner;
import com.example.graph.place.Place;
import com.example.graph.schedules.PlaceSchedule;
import com.example.graph.schedules.Schedule;
import com.example.graph.service.PlaceMenuAdditionalService;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.user.PlaceUser;
import com.example.graph_repositories.ScheduleRepository;
import com.example.graph_repositories.employee.EmployeeRepository;
import com.example.graph_repositories.menu.PlaceMenuServiceRepository;
import com.example.graph_repositories.order.OrderRepository;
import com.example.graph_repositories.owner.PlaceOwnerRepository;
import com.example.graph_repositories.place.PlaceRepository;
import com.example.graph_repositories.user.UserRepository;
import com.example.neo_services.image.ImageService;
import com.example.neo_services.owner.PlaceOwnerService;
import com.example.neo_services.user.UserService;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.pojo.dto.PlaceOrderDTO;
import com.example.pojo.dto.ScheduleDTO;
import javassist.tools.web.BadHttpRequest;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class PlaceServiceImplementation implements PlaceService {

    @Autowired
    public PlaceServiceImplementation(GraphDatabase db,
                                      UserRepository userRepository,
                                      PlaceRepository placeRepository,
                                      PlaceMenuServiceRepository serviceRepository,
                                      EmployeeRepository employeeRepository,
                                      OrderRepository repository,
                                      UserService userService,
                                      PlaceOwnerService ownerService,
                                      PlaceOwnerRepository ownerRepository,
                                      ImageService imageService,
                                      ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.serviceRepository = serviceRepository;
        this.employeeRepository = employeeRepository;
        this.db = db;
        this.orderRepository = repository;
        this.userService = userService;
        this.ownerService = ownerService;
        this.ownerRepository = ownerRepository;
        this.imageService = imageService;
        this.scheduleRepository = scheduleRepository;
    }

    private PlaceOwnerRepository ownerRepository;
    private PlaceOwnerService ownerService;
    private UserService userService;
    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private PlaceMenuServiceRepository serviceRepository;
    private EmployeeRepository employeeRepository;
    private GraphDatabase db;
    private OrderRepository orderRepository;
    private ImageService imageService;
    private ScheduleRepository scheduleRepository;

    @Override
    public void sendNewOrder(String userName, String serviceName,
                             List<String> additServicesNames, String employeeName) {

        try (Transaction tx = db.beginTx()) {
            PlaceUser user = userRepository.findByUserName(userName);
            PlaceMenuService service = serviceRepository.findByName(serviceName);
            PlaceEmployee employee = employeeRepository.findOne(employeeName);

            Set<PlaceMenuAdditionalService> placeMenuAdditionalServices = service.getService();
            Set<PlaceMenuAdditionalService> selectedServices = new HashSet<>(placeMenuAdditionalServices.size());
            for (PlaceMenuAdditionalService s : placeMenuAdditionalServices) {
                if (additServicesNames.contains(s.getName())) selectedServices.add(s);
            }

            PlaceUserOrder order = new PlaceUserOrder();
            order.setEmployee(employee);
            order.setService(service);
            order.setUser(user);
            orderRepository.save(order);
            tx.success();
        }
    }

    @Override
    public void sendNewOrder(PlaceOrderDTO orderDTO) {
        try (Transaction tx = db.beginTx()) {
            LocalDate now = LocalDate.now();
            orderRepository.saveOrder(orderDTO.getPlaceName(),
                    userService.placeUser().getUserName(),
                    orderDTO.getPlaceEmployeeName(),
                    orderDTO.getPlaceServiceName(),
                    orderDTO.getPlaceAdditionalServicesNamesList(),
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth());
            tx.success();
        }
    }

    @Override
    public Place newPlace(PlaceDTO placeDTO) {
        Place place;
        try (Transaction tx = db.beginTx()) {
            place = new Place(placeDTO);
            PlaceOwner owner = ownerRepository.findByUserName(ownerService.placeOwnerDetails().getUsername());
            place.setOwner(owner);
            placeRepository.save(place);
            tx.success();
        }
        return place;
    }

    @Override
    public void addPhoto(PhotoDTO photo, String placeName) {
        Place place = placeRepository.findByName(placeName);
        try {
            imageService.savePlacePhoto(photo, place);
        } catch (IOException | BadHttpRequest e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Schedule> updatePlaceSchedual(List<ScheduleDTO> newSchedual, String placeName) {
        List<Schedule> schedules = new ArrayList<>(newSchedual.size());
        try (Transaction tx = db.beginTx()) {
            Place place = placeRepository.findByName(placeName);
            for (ScheduleDTO s : newSchedual) {
                PlaceSchedule placeSchedule = new PlaceSchedule(s);
                placeSchedule.setPlace(place);
                scheduleRepository.save(placeSchedule);
            }
            tx.success();
        }
        return schedules;
    }

    @Override
    public void deletePlace(Place place) {
        try(Transaction tx = db.beginTx()){
            placeRepository.delete(place);
            tx.success();
        }
    }

}
