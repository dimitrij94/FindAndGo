package com.example.dao.employee;

import com.example.dao.DBBean;
import com.example.domain.UserOrders;
import com.example.domain.employee.EmployeePauses;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.employee.PlaceEmployeePhoto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dmitrij on 22.12.2015.
 */
@Component
public class EmployeeDAO extends DBBean implements IEmployeeDAO {

    @Override
    public void saveEmployeePhoto(PlaceEmployeePhoto placeEmployeePhoto) {
        em.persist(placeEmployeePhoto);
    }

    @Override
    public byte[] getEmployeeMainPhoto(long id, String name) {
        return (byte[]) em.createQuery("SELECT e.body FROM PlaceEmployeePhoto  e WHERE e.id=:id AND e.name=:name")
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public PlaceEmployee registerEmployee(PlaceEmployee employee) {
        em.persist(employee);
        em.merge(employee);
        return employee;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrders> getEmployeeTodayOrders(PlaceEmployee employee, LocalDate date) {
        return em.createQuery("SELECT e FROM UserOrders e " +
                "WHERE e.employee.id=:eId AND e.startTime=:oD ORDER BY e.startTime", UserOrders.class)
                .setParameter("eId", employee.getId())
                .setParameter("oD", date)
                .getResultList();
    }

    @Override
    public List<EmployeePauses> getEmployeeTodayPauses(PlaceEmployee employee, LocalDateTime localDate) {
        return em.createQuery("SELECT e FROM EmployeePauses e " +
                "WHERE e.start =:date AND e.employee.id=:eId ORDER BY e.start", EmployeePauses.class)
                .setParameter("eId", employee.getId())
                .setParameter("date", localDate)
                .getResultList();
    }

    @Override
    public PlaceEmployee getEmployeeById(long employeeID) {
        return em.find(PlaceEmployee.class, employeeID);
    }


}
