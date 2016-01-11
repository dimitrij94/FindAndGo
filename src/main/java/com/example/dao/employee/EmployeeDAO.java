package com.example.dao.employee;

import com.example.domain.photos.PlaceEmployeePhoto;
import com.example.domain.users.employee.PlaceEmployee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Dmitrij on 22.12.2015.
 */
@Component
public class EmployeeDAO  implements IEmployeeDAO{
    @PersistenceContext
    EntityManager em;

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

}
