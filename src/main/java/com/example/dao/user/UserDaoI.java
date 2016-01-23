package com.example.dao.user;

import com.example.domain.users.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 08.01.2016.
 */

public interface UserDaoI {
    PlaceUser getUserById(long id);

    long checkCredentials(String email, String userName);

    @Transactional
    PlaceUser registration(PlaceUser user);

    byte[] getUserPhotoBodyByName(String name, long id);

    long isPlaceUser(Long id, Long id1);
}
