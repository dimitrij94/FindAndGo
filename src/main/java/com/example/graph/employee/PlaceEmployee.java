package com.example.graph.employee;

import com.example.graph.Person;
import com.example.graph.photos.PlaceEmployeePhoto;
import com.example.graph.place.Place;
import com.example.graph.schedules.EmployeeSchedule;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.verification_tokens.PlaceEmployeeVerificationToken;
import com.example.pojo.dto.EmployeeDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 20.02.2016.
 */
@NodeEntity
public class PlaceEmployee extends Person {
    //OUTGOING RELATIONSHIPS NAMES
    public transient static final String CAN_PREFORM = "CAN_PREFORM";
    public transient static final String PHOTO = "PHOTO";
    public transient static final String SCHEDULE = "SCHEDULE";
    public transient static final String ROLE = "ROLE_EMPLOYEE";
    public transient static final String VERIFICATION_TOKEN = "VERIFICATION_TOKEN";

    private String firstName;
    private String secondName;
    private int age;
    private String sex;

    @RelatedTo(type = CAN_PREFORM, direction = Direction.OUTGOING)
    Set<PlaceMenuService> service;

    @RelatedTo(type = PHOTO, direction = Direction.OUTGOING)
    Set<PlaceEmployeePhoto> photo;

    @RelatedTo(type = Place.HIRES, direction = Direction.INCOMING)
    Place place;

    @RelatedTo(type = SCHEDULE, direction = Direction.OUTGOING)
    Set<EmployeeSchedule> employeeSchedules;

    @RelatedTo(type = VERIFICATION_TOKEN, direction = Direction.OUTGOING)
    PlaceEmployeeVerificationToken verificationToken;

    public PlaceEmployee(EmployeeDTO employeeDto) {
        setUserName(employeeDto.getUserName());
        setEmail(employeeDto.getEmail());
        this.age = employeeDto.getAge();
        this.sex = employeeDto.getSex();
        this.firstName = employeeDto.getName();
        this.secondName = employeeDto.getSecondName();
    }

    @Override
    public String getAuthority() {
        return ROLE;
    }

    public PlaceEmployeeVerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(PlaceEmployeeVerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Set<PlaceMenuService> getService() {
        return service;
    }

    public void setService(Set<PlaceMenuService> service) {
        this.service = service;
    }

    public Set<PlaceEmployeePhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(Set<PlaceEmployeePhoto> photo) {
        this.photo = photo;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Set<EmployeeSchedule> getEmployeeSchedules() {
        return employeeSchedules;
    }

    public void setEmployeeSchedules(Set<EmployeeSchedule> employeeSchedules) {
        this.employeeSchedules = employeeSchedules;
    }

}
