package com.example.services.owner;

import com.example.domain.owner.PlaceOwner;
import com.example.pojo.dto.OwnerDTO;
import com.example.services.authentication.owner.CustomOwnerDetails;

/**
 * Created by Dmitrij on 08.02.2016.
 */
public interface OwnerService {
    boolean validateOwnerName(String name);

    boolean validateOwnerEmail(String email);

    CustomOwnerDetails getOwner();

    PlaceOwner registerNewOwner(OwnerDTO ownerDTO);

    void deleteOwner();

    void updateOwner(OwnerDTO newOwner);
}
