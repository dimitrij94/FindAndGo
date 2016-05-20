package com.example.graph.address;

import com.example.graph.place.Place;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class Address {
    @GraphId
    Long id;

    @Indexed(unique = true)
    String address;

    @RelatedTo(type = "STREET", direction = Direction.INCOMING)
    Street street;

    @RelatedTo(type = "ADDRESS", direction = Direction.INCOMING)
    Place place;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (!id.equals(address1.id)) return false;
        if (address != null ? !address.equals(address1.address) : address1.address != null) return false;
        return !(street != null ? !street.equals(address1.street) : address1.street != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }
}
