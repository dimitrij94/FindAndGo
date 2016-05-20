package com.example.graph.owner;

import com.example.graph.Person;
import com.example.graph.place.Place;
import com.example.graph.verification_tokens.PlaceOwnerVerificationToken;
import com.example.pojo.dto.OwnerDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceOwner extends Person {

    public transient final static String OWNS = "OWNS";
    public transient final static String VERIFICATION_TOKEN = "VERIFICATION_TOKEN";

    @RelatedTo(type = OWNS, direction = Direction.OUTGOING)
    private Set<Place> places;

    @RelatedTo(type = VERIFICATION_TOKEN, direction = Direction.OUTGOING)
    private PlaceOwnerVerificationToken verificationToken;

    public PlaceOwner(OwnerDTO ownerDTO) {
        setUserName(ownerDTO.getName());
        setEmail(ownerDTO.getEmail());
    }

    @Override
    public String getAuthority() {
        return "ROLE_OWNER";
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public PlaceOwnerVerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(PlaceOwnerVerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }


}
