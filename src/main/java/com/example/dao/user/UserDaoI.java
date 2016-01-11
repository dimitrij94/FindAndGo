package com.example.dao.user;

import com.example.domain.users.PlaceUser;

/**
 * Created by Dmitrij on 08.01.2016.
 */

public interface UserDaoI {
    PlaceUser getUserById(long id);
}
