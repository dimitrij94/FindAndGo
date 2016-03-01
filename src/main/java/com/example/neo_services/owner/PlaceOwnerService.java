package com.example.neo_services.owner;

import com.example.graph.owner.PlaceOwner;
import com.example.neo_services.authentication.CustomUserDetails;
import com.example.pojo.dto.OwnerDTO;

/**
 * Created by Dmitrij on 24.02.2016.
 */
public interface PlaceOwnerService  {
    CustomUserDetails placeOwnerDetails();

    PlaceOwner placeOwner();

    boolean validateOwnerEmail(String email);

    boolean validateOwnerName(String name);

    PlaceOwner newOwner(OwnerDTO ownerDTO);

    void deleteOwner();

}
