package com.example.graph.address;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class Street {
    @GraphId
    Long id;

    @Indexed(unique = true)
    String name;

    @RelatedTo(type = "STREET", direction = Direction.INCOMING)
    City street;

    @RelatedTo(type = "ADDRESS", direction = Direction.OUTGOING)
    Set<Address> address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getStreet() {
        return street;
    }

    public void setStreet(City street) {
        this.street = street;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }
}
